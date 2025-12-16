const { executeQuery } = require('../config/database');
const sql = require('mssql');

// Lấy danh sách lịch bảo dưỡng
const getAllSchedules = async (req, res) => {
    try {
        const { page = 1, limit = 50, trangThai = '' } = req.query;
        const offset = (page - 1) * limit;

        let query = `
            SELECT 
                DV.MaDV,
                DV.LoaiDichVu,
                DV.MoTa,
                DV.TrangThai,
                DV.NgayTao,
                ND.MaND,
                ND.HoTen as TenKhachHang,
                ND.DienThoai,
                ND.Email,
                DL.TenDaiLy,
                DL.DiaChi as DiaChiDaiLy,
                X.MaXe,
                X.BienSo,
                LX.TenLoaiXe
            FROM DICH_VU DV
            INNER JOIN NGUOI_DUNG ND ON DV.MaND = ND.MaND
            INNER JOIN DAI_LY DL ON DV.MaDaiLy = DL.MaDaiLy
            LEFT JOIN XE X ON ND.MaND = X.MaND
            LEFT JOIN LOAI_XE LX ON X.MaLoaiXe = LX.MaLoaiXe
            WHERE 1=1
        `;

        const params = {};

        if (trangThai) {
            query += ` AND DV.TrangThai = @trangThai`;
            params.trangThai = { type: sql.NVarChar, value: trangThai };
        }

        query += ` ORDER BY DV.NgayTao DESC OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY`;

        params.offset = { type: sql.Int, value: offset };
        params.limit = { type: sql.Int, value: parseInt(limit) };

        const schedules = await executeQuery(query, params);

        // Đếm tổng
        let countQuery = `
            SELECT COUNT(*) as total 
            FROM DICH_VU DV
            WHERE 1=1
        `;
        if (trangThai) {
            countQuery += ` AND DV.TrangThai = @trangThai`;
        }
        const countResult = await executeQuery(countQuery, params);
        const total = countResult[0]?.total || 0;

        res.json({
            success: true,
            data: schedules,
            pagination: {
                page: parseInt(page),
                limit: parseInt(limit),
                total,
                totalPages: Math.ceil(total / limit)
            }
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách lịch bảo dưỡng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy chi tiết lịch bảo dưỡng
const getScheduleById = async (req, res) => {
    try {
        const { id } = req.params;

        const query = `
            SELECT 
                DV.*,
                ND.HoTen as TenKhachHang,
                ND.DienThoai,
                ND.Email,
                ND.NgayBaoDuong,
                ND.SoLanBaoDuong,
                DL.TenDaiLy,
                DL.DiaChi,
                DL.SoDienThoai as SoDienThoaiDaiLy,
                X.MaXe,
                X.BienSo,
                LX.TenLoaiXe
            FROM DICH_VU DV
            INNER JOIN NGUOI_DUNG ND ON DV.MaND = ND.MaND
            INNER JOIN DAI_LY DL ON DV.MaDaiLy = DL.MaDaiLy
            LEFT JOIN XE X ON ND.MaND = X.MaND
            LEFT JOIN LOAI_XE LX ON X.MaLoaiXe = LX.MaLoaiXe
            WHERE DV.MaDV = @id
        `;

        const result = await executeQuery(query, {
            id: { type: sql.Int, value: parseInt(id) }
        });

        if (result.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy lịch bảo dưỡng'
            });
        }

        res.json({
            success: true,
            data: result[0]
        });
    } catch (error) {
        console.error('Lỗi lấy chi tiết lịch bảo dưỡng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Cập nhật trạng thái lịch bảo dưỡng
const updateScheduleStatus = async (req, res) => {
    try {
        const { id } = req.params;
        const { TrangThai, MoTa } = req.body;

        const query = `
            UPDATE DICH_VU
            SET 
                TrangThai = @TrangThai,
                MoTa = @MoTa
            WHERE MaDV = @id
        `;

        await executeQuery(query, {
            id: { type: sql.Int, value: parseInt(id) },
            TrangThai: { type: sql.NVarChar, value: TrangThai },
            MoTa: { type: sql.NVarChar, value: MoTa || null }
        });

        res.json({
            success: true,
            message: 'Cập nhật trạng thái bảo dưỡng thành công'
        });
    } catch (error) {
        console.error('Lỗi cập nhật lịch bảo dưỡng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getAllSchedules,
    getScheduleById,
    updateScheduleStatus
};

