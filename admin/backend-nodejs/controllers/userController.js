const { executeQuery } = require('../config/database');
const sql = require('mssql');

// Lấy danh sách người dùng
const getAllUsers = async (req, res) => {
    try {
        const { page = 1, limit = 50, search = '' } = req.query;
        const offset = (page - 1) * limit;

        let query = `
            SELECT 
                ND.MaND,
                ND.HoTen,
                ND.Email,
                ND.DienThoai,
                ND.NgaySinh,
                ND.VaiTro,
                ND.NgayBaoDuong,
                ND.SoLanBaoDuong,
                ND.IsLocked,
                X.MaXe,
                LX.TenLoaiXe
            FROM NGUOI_DUNG ND
            LEFT JOIN XE X ON ND.MaND = X.MaND
            LEFT JOIN LOAI_XE LX ON X.MaLoaiXe = LX.MaLoaiXe
            WHERE 1=1
        `;

        const params = {};

        if (search) {
            query += ` AND (ND.HoTen LIKE @search OR ND.Email LIKE @search OR ND.DienThoai LIKE @search)`;
            params.search = { type: sql.NVarChar, value: `%${search}%` };
        }

        query += ` ORDER BY ND.MaND OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY`;

        params.offset = { type: sql.Int, value: offset };
        params.limit = { type: sql.Int, value: parseInt(limit) };

        const users = await executeQuery(query, params);

        // Đếm tổng
        let countQuery = `SELECT COUNT(DISTINCT ND.MaND) as total FROM NGUOI_DUNG ND LEFT JOIN XE X ON ND.MaND = X.MaND WHERE 1=1`;
        if (search) {
            countQuery += ` AND (ND.HoTen LIKE @search OR ND.Email LIKE @search OR ND.DienThoai LIKE @search)`;
        }
        const countResult = await executeQuery(countQuery, params);
        const total = countResult[0]?.total || 0;

        res.json({
            success: true,
            data: users,
            pagination: {
                page: parseInt(page),
                limit: parseInt(limit),
                total,
                totalPages: Math.ceil(total / limit)
            }
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách người dùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy chi tiết người dùng
const getUserById = async (req, res) => {
    try {
        const { id } = req.params;

        const query = `
            SELECT 
                MaND,
                HoTen,
                Email,
                DienThoai,
                NgaySinh,
                VaiTro,
                NgayBaoDuong,
                SoLanBaoDuong,
                IsLocked
            FROM NGUOI_DUNG
            WHERE MaND = @id
        `;

        const result = await executeQuery(query, {
            id: { type: sql.Int, value: parseInt(id) }
        });

        if (result.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy người dùng'
            });
        }

        res.json({
            success: true,
            data: result[0]
        });
    } catch (error) {
        console.error('Lỗi lấy chi tiết người dùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Cập nhật thông tin người dùng
const updateUser = async (req, res) => {
    try {
        const { id } = req.params;
        const { HoTen, Email, DienThoai, NgaySinh, NgayBaoDuong, SoLanBaoDuong } = req.body;

        const query = `
            UPDATE NGUOI_DUNG
            SET 
                HoTen = @HoTen,
                Email = @Email,
                DienThoai = @DienThoai,
                NgaySinh = @NgaySinh,
                NgayBaoDuong = @NgayBaoDuong,
                SoLanBaoDuong = @SoLanBaoDuong
            WHERE MaND = @id
        `;

        await executeQuery(query, {
            id: { type: sql.Int, value: parseInt(id) },
            HoTen: { type: sql.NVarChar, value: HoTen },
            Email: { type: sql.VarChar, value: Email || null },
            DienThoai: { type: sql.VarChar, value: DienThoai || null },
            NgaySinh: { type: sql.VarChar, value: NgaySinh },
            NgayBaoDuong: { type: sql.DateTime, value: NgayBaoDuong ? new Date(NgayBaoDuong) : null },
            SoLanBaoDuong: { type: sql.Int, value: parseInt(SoLanBaoDuong) || 0 }
        });

        res.json({
            success: true,
            message: 'Cập nhật thông tin người dùng thành công'
        });
    } catch (error) {
        console.error('Lỗi cập nhật người dùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Khóa/Mở khóa tài khoản
const toggleLockUser = async (req, res) => {
    try {
        const { id } = req.params;
        const { IsLocked } = req.body;

        const query = `
            UPDATE NGUOI_DUNG
            SET IsLocked = @IsLocked
            WHERE MaND = @id
        `;

        await executeQuery(query, {
            id: { type: sql.Int, value: parseInt(id) },
            IsLocked: { type: sql.Bit, value: IsLocked === true || IsLocked === 1 }
        });

        res.json({
            success: true,
            message: IsLocked ? 'Đã khóa tài khoản' : 'Đã mở khóa tài khoản'
        });
    } catch (error) {
        console.error('Lỗi khóa/mở khóa tài khoản:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Xóa người dùng
const deleteUser = async (req, res) => {
    try {
        const { id } = req.params;

        const query = `DELETE FROM NGUOI_DUNG WHERE MaND = @id`;

        await executeQuery(query, {
            id: { type: sql.Int, value: parseInt(id) }
        });

        res.json({
            success: true,
            message: 'Xóa người dùng thành công'
        });
    } catch (error) {
        console.error('Lỗi xóa người dùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getAllUsers,
    getUserById,
    updateUser,
    toggleLockUser,
    deleteUser
};

