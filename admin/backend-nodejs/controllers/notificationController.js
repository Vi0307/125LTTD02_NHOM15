const { executeQuery } = require('../config/database');
const sql = require('mssql');

// Lấy danh sách thông báo
const getAllNotifications = async (req, res) => {
    try {
        const { page = 1, limit = 50 } = req.query;
        const offset = (page - 1) * limit;

        const query = `
            SELECT 
                TB.MaThongBao,
                TB.TieuDe,
                TB.NoiDung,
                TB.NgayTao,
                ND.MaND,
                ND.HoTen as TenKhachHang,
                ND.DienThoai,
                ND.Email
            FROM THONG_BAO TB
            INNER JOIN NGUOI_DUNG ND ON TB.MaND = ND.MaND
            ORDER BY TB.NgayTao DESC
            OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY
        `;

        const notifications = await executeQuery(query, {
            offset: { type: sql.Int, value: offset },
            limit: { type: sql.Int, value: parseInt(limit) }
        });

        // Đếm tổng
        const countResult = await executeQuery(`SELECT COUNT(*) as total FROM THONG_BAO`, {});
        const total = countResult[0]?.total || 0;

        res.json({
            success: true,
            data: notifications,
            pagination: {
                page: parseInt(page),
                limit: parseInt(limit),
                total,
                totalPages: Math.ceil(total / limit)
            }
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách thông báo:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy danh sách khách hàng cần nhắc bảo dưỡng
const getMaintenanceReminders = async (req, res) => {
    try {
        // Lấy danh sách người dùng có xe và cần bảo dưỡng (số lần bảo dưỡng < 6)
        const query = `
            SELECT 
                ND.MaND,
                ND.HoTen as TenKhachHang,
                ND.DienThoai,
                ND.Email,
                ND.NgayBaoDuong,
                ND.SoLanBaoDuong,
                X.MaXe,
                LX.TenLoaiXe
            FROM NGUOI_DUNG ND
            LEFT JOIN XE X ON ND.MaND = X.MaND
            LEFT JOIN LOAI_XE LX ON X.MaLoaiXe = LX.MaLoaiXe
            WHERE ND.SoLanBaoDuong < 6 OR ND.SoLanBaoDuong IS NULL
            ORDER BY ND.SoLanBaoDuong DESC, ND.MaND ASC
        `;

        const reminders = await executeQuery(query, {});

        res.json({
            success: true,
            data: reminders
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách nhắc bảo dưỡng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Gửi thông báo nhắc nhở - tăng số lần bảo dưỡng
const sendReminder = async (req, res) => {
    try {
        const { maND } = req.body;

        if (!maND) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp mã người dùng'
            });
        }

        // Tăng số lần bảo dưỡng và cập nhật ngày bảo dưỡng
        const updateQuery = `
            UPDATE NGUOI_DUNG
            SET SoLanBaoDuong = ISNULL(SoLanBaoDuong, 0) + 1,
                NgayBaoDuong = GETDATE()
            WHERE MaND = @maND
        `;

        await executeQuery(updateQuery, {
            maND: { type: sql.Int, value: parseInt(maND) }
        });

        res.json({
            success: true,
            message: 'Đã gửi thông báo nhắc nhở và cập nhật số lần bảo dưỡng'
        });
    } catch (error) {
        console.error('Lỗi gửi thông báo:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Xóa khỏi danh sách nhắc nhở - đặt số lần bảo dưỡng = 6 (hoàn tất)
const deleteReminder = async (req, res) => {
    try {
        const { maND } = req.body;

        if (!maND) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp mã người dùng'
            });
        }

        // Đặt số lần bảo dưỡng = 6 để xóa khỏi danh sách nhắc nhở
        const query = `
            UPDATE NGUOI_DUNG
            SET SoLanBaoDuong = 6
            WHERE MaND = @maND
        `;

        await executeQuery(query, {
            maND: { type: sql.Int, value: parseInt(maND) }
        });

        res.json({
            success: true,
            message: 'Đã xóa khỏi danh sách nhắc nhở'
        });
    } catch (error) {
        console.error('Lỗi xóa nhắc nhở:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy danh sách nhắc nhở từ bảng BAO_DUONG (giống Android app)
const getBaoDuongReminders = async (req, res) => {
    try {
        const query = `
            SELECT 
                BD.MaBD,
                BD.NgayBaoDuong,
                BD.MoTa,
                BD.TrangThai,
                BD.DaNhacNho,
                BD.MaND,
                ND.HoTen as TenKhachHang,
                ND.DienThoai,
                ND.Email,
                ND.SoLanBaoDuong,
                X.MaXe,
                X.BienSo,
                LX.TenLoaiXe
            FROM BAO_DUONG BD
            INNER JOIN NGUOI_DUNG ND ON BD.MaND = ND.MaND
            LEFT JOIN XE X ON ND.MaND = X.MaND
            LEFT JOIN LOAI_XE LX ON X.MaLoaiXe = LX.MaLoaiXe
            WHERE BD.TrangThai = N'Nhắc nhở'
            ORDER BY BD.NgayBaoDuong DESC
        `;

        const reminders = await executeQuery(query, {});

        res.json({
            success: true,
            data: reminders
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách nhắc nhở bảo dưỡng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Cập nhật trạng thái nhắc nhở trong bảng BAO_DUONG
const updateBaoDuongStatus = async (req, res) => {
    try {
        const { maBD, trangThai } = req.body;

        if (!maBD || !trangThai) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp mã bảo dưỡng và trạng thái'
            });
        }

        const query = `
            UPDATE BAO_DUONG
            SET TrangThai = @trangThai,
                DaNhacNho = CASE WHEN @trangThai = N'Đã nhắc nhở' THEN 1 ELSE DaNhacNho END
            WHERE MaBD = @maBD
        `;

        await executeQuery(query, {
            maBD: { type: sql.Int, value: parseInt(maBD) },
            trangThai: { type: sql.NVarChar, value: trangThai }
        });

        res.json({
            success: true,
            message: 'Đã cập nhật trạng thái nhắc nhở'
        });
    } catch (error) {
        console.error('Lỗi cập nhật trạng thái:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Tạo nhắc nhở mới trong bảng BAO_DUONG
const createBaoDuongReminder = async (req, res) => {
    try {
        const { maND, moTa } = req.body;

        if (!maND) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp mã người dùng'
            });
        }

        const query = `
            INSERT INTO BAO_DUONG (MaND, MoTa, TrangThai, DaNhacNho)
            VALUES (@maND, @moTa, N'Nhắc nhở', 0)
        `;

        await executeQuery(query, {
            maND: { type: sql.Int, value: parseInt(maND) },
            moTa: { type: sql.NVarChar, value: moTa || 'Nhắc nhở thay thế phụ tùng' }
        });

        res.json({
            success: true,
            message: 'Đã tạo nhắc nhở mới'
        });
    } catch (error) {
        console.error('Lỗi tạo nhắc nhở:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getAllNotifications,
    getMaintenanceReminders,
    sendReminder,
    deleteReminder,
    getBaoDuongReminders,
    updateBaoDuongStatus,
    createBaoDuongReminder
};

