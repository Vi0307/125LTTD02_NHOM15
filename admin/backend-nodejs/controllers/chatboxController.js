const { executeQuery } = require('../config/database');
const sql = require('mssql');

// Lấy danh sách người dùng để chat (khách hàng)
const getChatUsers = async (req, res) => {
    try {
        const query = `
            SELECT DISTINCT
                ND.MaND,
                ND.HoTen as TenKhachHang,
                ND.Email,
                ND.DienThoai,
                X.MaXe,
                LX.TenLoaiXe
            FROM NGUOI_DUNG ND
            LEFT JOIN XE X ON ND.MaND = X.MaND
            LEFT JOIN LOAI_XE LX ON X.MaLoaiXe = LX.MaLoaiXe
            WHERE ND.VaiTro = 'customer'
            ORDER BY ND.HoTen
        `;

        const users = await executeQuery(query, {});

        res.json({
            success: true,
            data: users
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách người dùng chat:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy lịch sử tin nhắn (giả lập - có thể tạo bảng TIN_NHAN sau)
const getChatMessages = async (req, res) => {
    try {
        const { maND } = req.query;

        if (!maND) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp mã người dùng'
            });
        }

        // Tạm thời trả về tin nhắn mẫu
        // Trong thực tế, cần tạo bảng TIN_NHAN để lưu trữ
        const messages = [
            {
                MaTinNhan: 1,
                MaND: parseInt(maND),
                NoiDung: 'Chào anh/chị, em muốn tư vấn về phụ tùng',
                Loai: 'customer',
                ThoiGian: new Date().toISOString()
            },
            {
                MaTinNhan: 2,
                MaND: parseInt(maND),
                NoiDung: 'Chào em! Anh tư vấn ngay đây!',
                Loai: 'admin',
                ThoiGian: new Date().toISOString()
            }
        ];

        res.json({
            success: true,
            data: messages
        });
    } catch (error) {
        console.error('Lỗi lấy tin nhắn:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Gửi tin nhắn
const sendMessage = async (req, res) => {
    try {
        const { maND, noiDung } = req.body;

        if (!maND || !noiDung) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp đầy đủ thông tin'
            });
        }

        // Tạm thời trả về thành công
        // Trong thực tế, cần insert vào bảng TIN_NHAN
        res.json({
            success: true,
            message: 'Gửi tin nhắn thành công',
            data: {
                MaTinNhan: Date.now(),
                MaND: parseInt(maND),
                NoiDung: noiDung,
                Loai: 'admin',
                ThoiGian: new Date().toISOString()
            }
        });
    } catch (error) {
        console.error('Lỗi gửi tin nhắn:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getChatUsers,
    getChatMessages,
    sendMessage
};

