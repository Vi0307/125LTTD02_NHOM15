const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');
const { executeQuery, sql } = require('../config/database');

// ======================
// ĐĂNG NHẬP ADMIN
// ======================
const login = async (req, res) => {
    try {
        const tenDangNhap = (req.body.tenDangNhap || '').trim();
        const matKhau = req.body.matKhau || '';

        // Validate input
        if (!tenDangNhap || !matKhau) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu'
            });
        }

        // Query tìm admin
        const query = `
            SELECT MaAdmin, TenDangNhap, MatKhau, VaiTro
            FROM ADMIN
            WHERE TenDangNhap = @tenDangNhap
        `;

        const admins = await executeQuery(query, {
            tenDangNhap: { type: sql.VarChar, value: tenDangNhap }
        });

        if (admins.length === 0) {
            return res.status(401).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        const admin = admins[0];

        // Validate mật khẩu không được rỗng hoặc NULL
        if (!admin.MatKhau || admin.MatKhau === null || admin.MatKhau === undefined || String(admin.MatKhau).trim() === '') {
            console.log('⚠️ Admin không có mật khẩu trong database');
            return res.status(401).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        // Đảm bảo mật khẩu nhập vào không rỗng
        if (!matKhau || typeof matKhau !== 'string' || matKhau.trim() === '') {
            console.log('⚠️ Mật khẩu nhập vào rỗng hoặc không hợp lệ');
            return res.status(401).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        // So sánh mật khẩu (ưu tiên hash nếu có)
        let isMatch = false;
        const dbPassword = String(admin.MatKhau).trim();
        const inputPassword = String(matKhau).trim();
        
        // Đảm bảo cả hai mật khẩu đều không rỗng sau khi trim
        if (!dbPassword || !inputPassword || dbPassword === '' || inputPassword === '') {
            console.log('❌ Mật khẩu rỗng sau khi trim');
            return res.status(401).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }
        
        // SO SÁNH MẬT KHẨU - CHẶT CHẼ
        try {
            if (dbPassword.startsWith('$2')) {
                // Mật khẩu đã được hash bằng bcrypt
                isMatch = await bcrypt.compare(inputPassword, dbPassword);
            } else {
                // Mật khẩu plain text - so sánh chính xác (case-sensitive, byte-by-byte)
                // Sử dụng strict comparison ===
                isMatch = (dbPassword === inputPassword);
                
                // Double check: đảm bảo độ dài cũng khớp
                if (isMatch && dbPassword.length !== inputPassword.length) {
                    console.log('❌ Độ dài mật khẩu không khớp!');
                    isMatch = false;
                }
            }
        } catch (err) {
            console.error('❌ Lỗi so sánh mật khẩu:', err);
            isMatch = false;
        }

        // Log để debug
        console.log('🔐 Login attempt:', {
            tenDangNhap,
            inputPassword: `"${inputPassword}"`,
            inputPasswordLength: inputPassword.length,
            dbPassword: `"${dbPassword}"`,
            dbPasswordLength: dbPassword.length,
            isHash: dbPassword.startsWith('$2'),
            isMatch: isMatch,
            passwordsEqual: dbPassword === inputPassword,
            typeCheck: typeof isMatch === 'boolean'
        });

        // CHẶN NẾU KHÔNG KHỚP - KHÔNG CÓ NGOẠI LỆ - ĐẢM BẢO isMatch LÀ BOOLEAN
        if (isMatch !== true) {
            console.log('❌ Mật khẩu không khớp! Từ chối đăng nhập. isMatch =', isMatch);
            return res.status(401).json({
                success: false,
                message: 'Tên đăng nhập hoặc mật khẩu không đúng'
            });
        }

        // CHỈ ĐẾN ĐÂY KHI isMatch === true
        console.log('✅ Mật khẩu khớp, cho phép đăng nhập');

        // Tạo JWT
        const token = jwt.sign(
            {
                maAdmin: admin.MaAdmin,
                tenDangNhap: admin.TenDangNhap,
                vaiTro: admin.VaiTro
            },
            process.env.JWT_SECRET || 'SECRET_KEY_ADMIN',
            { expiresIn: process.env.JWT_EXPIRE || '24h' }
        );

        return res.status(200).json({
            success: true,
            message: 'Đăng nhập thành công',
            data: {
                token,
                admin: {
                    maAdmin: admin.MaAdmin,
                    tenDangNhap: admin.TenDangNhap,
                    vaiTro: admin.VaiTro
                }
            }
        });
    } catch (error) {
        console.error('❌ Lỗi login:', error);
        return res.status(500).json({
            success: false,
            message: 'Lỗi server khi đăng nhập',
            error: error.message
        });
    }
};

// ======================
// LẤY ADMIN HIỆN TẠI
// ======================
const getCurrentAdmin = async (req, res) => {
    try {
        const query = `
            SELECT MaAdmin, TenDangNhap, VaiTro
            FROM ADMIN
            WHERE MaAdmin = @maAdmin
        `;

        const result = await executeQuery(query, {
            maAdmin: req.admin.maAdmin
        });

        if (result.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy admin'
            });
        }

        return res.status(200).json({
            success: true,
            data: result[0]
        });
    } catch (error) {
        console.error('❌ Lỗi getCurrentAdmin:', error);
        return res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    login,
    getCurrentAdmin
};
