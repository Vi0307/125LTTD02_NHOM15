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
        const query = `
            SELECT 
                ND.MaND,
                ND.HoTen as TenKhachHang,
                ND.NgayBaoDuong,
                ND.SoLanBaoDuong,
                X.MaXe,
                LX.TenLoaiXe,
                BD.MaBD,
                BD.TrangThai,
                BD.DaNhacNho
            FROM NGUOI_DUNG ND
            LEFT JOIN XE X ON ND.MaND = X.MaND
            LEFT JOIN LOAI_XE LX ON X.MaLoaiXe = LX.MaLoaiXe
            LEFT JOIN BAO_DUONG BD ON ND.MaND = BD.MaND
            WHERE ND.NgayBaoDuong IS NOT NULL 
                AND (BD.TrangThai = N'Nhắc nhở' OR BD.TrangThai IS NULL)
            ORDER BY ND.NgayBaoDuong ASC
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

// Gửi thông báo nhắc nhở
const sendReminder = async (req, res) => {
    try {
        const { maND } = req.body;

        if (!maND) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp mã người dùng'
            });
        }

        // Cập nhật trạng thái bảo dưỡng
        const updateQuery = `
            UPDATE BAO_DUONG
            SET TrangThai = N'Đã nhắc nhở', DaNhacNho = 1
            WHERE MaND = @maND AND TrangThai = N'Nhắc nhở'
        `;

        await executeQuery(updateQuery, {
            maND: { type: sql.Int, value: parseInt(maND) }
        });

        // Tạo thông báo
        const insertQuery = `
            INSERT INTO THONG_BAO (MaND, TieuDe, NoiDung)
            VALUES (@maND, N'Thông báo bảo dưỡng', N'Xe của bạn sắp đến hạn bảo dưỡng định kỳ. Vui lòng liên hệ đại lý để đặt lịch.')
        `;

        await executeQuery(insertQuery, {
            maND: { type: sql.Int, value: parseInt(maND) }
        });

        res.json({
            success: true,
            message: 'Đã gửi thông báo nhắc nhở thành công'
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

// Xóa khỏi danh sách nhắc nhở
const deleteReminder = async (req, res) => {
    try {
        const { maND } = req.body;

        if (!maND) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng cung cấp mã người dùng'
            });
        }

        const query = `
            UPDATE BAO_DUONG
            SET TrangThai = N'Chưa đến hạn'
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

module.exports = {
    getAllNotifications,
    getMaintenanceReminders,
    sendReminder,
    deleteReminder
};

