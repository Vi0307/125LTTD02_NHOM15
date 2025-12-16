const { executeQuery } = require('../config/database');
const sql = require('mssql');

// Lấy danh sách đơn hàng
const getAllOrders = async (req, res) => {
    try {
        const { page = 1, limit = 50, trangThai = '', search = '' } = req.query;
        const offset = (page - 1) * limit;

        let query = `
            SELECT 
                DH.MaDH,
                DH.NgayDat,
                DH.TenPhuTung,
                DH.HinhAnh,
                DH.TongTien,
                DH.PhiVanChuyen,
                DH.TongThanhToan,
                DH.DiaChiGiao,
                DH.TrangThai,
                DH.PhuongThucThanhToan,
                ND.MaND,
                ND.HoTen as TenKhachHang,
                ND.DienThoai,
                ND.Email
            FROM DON_HANG DH
            INNER JOIN NGUOI_DUNG ND ON DH.MaND = ND.MaND
            WHERE 1=1
        `;

        const params = {};

        if (trangThai) {
            query += ` AND DH.TrangThai = @trangThai`;
            params.trangThai = { type: sql.NVarChar, value: trangThai };
        }

        if (search) {
            query += ` AND (DH.MaDH LIKE @search OR ND.HoTen LIKE @search OR ND.DienThoai LIKE @search)`;
            params.search = { type: sql.NVarChar, value: `%${search}%` };
        }

        query += ` ORDER BY DH.NgayDat DESC OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY`;

        params.offset = { type: sql.Int, value: offset };
        params.limit = { type: sql.Int, value: parseInt(limit) };

        const orders = await executeQuery(query, params);

        // Đếm tổng
        let countQuery = `
            SELECT COUNT(*) as total 
            FROM DON_HANG DH
            INNER JOIN NGUOI_DUNG ND ON DH.MaND = ND.MaND
            WHERE 1=1
        `;
        if (trangThai) {
            countQuery += ` AND DH.TrangThai = @trangThai`;
        }
        if (search) {
            countQuery += ` AND (DH.MaDH LIKE @search OR ND.HoTen LIKE @search OR ND.DienThoai LIKE @search)`;
        }
        const countResult = await executeQuery(countQuery, params);
        const total = countResult[0]?.total || 0;

        res.json({
            success: true,
            data: orders,
            pagination: {
                page: parseInt(page),
                limit: parseInt(limit),
                total,
                totalPages: Math.ceil(total / limit)
            }
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách đơn hàng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy chi tiết đơn hàng
const getOrderById = async (req, res) => {
    try {
        const { id } = req.params;

        const query = `
            SELECT 
                DH.*,
                ND.HoTen as TenKhachHang,
                ND.DienThoai,
                ND.Email,
                PTVC.TenPTVC,
                VC.LoaiVoucher
            FROM DON_HANG DH
            INNER JOIN NGUOI_DUNG ND ON DH.MaND = ND.MaND
            LEFT JOIN PHUONG_THUC_VAN_CHUYEN PTVC ON DH.MaPTVC = PTVC.MaPTVC
            LEFT JOIN VOUCHER VC ON DH.MaVC = VC.MaVC
            WHERE DH.MaDH = @id
        `;

        const result = await executeQuery(query, {
            id: { type: sql.VarChar, value: id }
        });

        if (result.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đơn hàng'
            });
        }

        // Lấy chi tiết đơn hàng
        const detailQuery = `
            SELECT 
                CTDH.*,
                PT.TenPhuTung,
                PT.HinhAnh
            FROM CHI_TIET_DON_HANG CTDH
            INNER JOIN PHU_TUNG PT ON CTDH.MaPhuTung = PT.MaPhuTung
            WHERE CTDH.MaDH = @id
        `;

        const details = await executeQuery(detailQuery, {
            id: { type: sql.VarChar, value: id }
        });

        res.json({
            success: true,
            data: {
                ...result[0],
                chiTiet: details
            }
        });
    } catch (error) {
        console.error('Lỗi lấy chi tiết đơn hàng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Cập nhật trạng thái đơn hàng
const updateOrderStatus = async (req, res) => {
    try {
        const { id } = req.params;
        const { TrangThai, PhuongThucThanhToan, TongTien, PhiVanChuyen } = req.body;

        let query = `UPDATE DON_HANG SET `;
        const updates = [];
        const params = { id: { type: sql.VarChar, value: id } };

        if (TrangThai) {
            updates.push(`TrangThai = @TrangThai`);
            params.TrangThai = { type: sql.NVarChar, value: TrangThai };
        }

        if (PhuongThucThanhToan) {
            updates.push(`PhuongThucThanhToan = @PhuongThucThanhToan`);
            params.PhuongThucThanhToan = { type: sql.NVarChar, value: PhuongThucThanhToan };
        }

        if (TongTien !== undefined) {
            updates.push(`TongTien = @TongTien`);
            params.TongTien = { type: sql.Decimal(18, 0), value: parseFloat(TongTien) };
        }

        if (PhiVanChuyen !== undefined) {
            updates.push(`PhiVanChuyen = @PhiVanChuyen`);
            params.PhiVanChuyen = { type: sql.Decimal(18, 0), value: parseFloat(PhiVanChuyen) };
        }

        if (updates.length === 0) {
            return res.status(400).json({
                success: false,
                message: 'Không có thông tin để cập nhật'
            });
        }

        query += updates.join(', ') + ` WHERE MaDH = @id`;

        await executeQuery(query, params);

        res.json({
            success: true,
            message: 'Cập nhật đơn hàng thành công'
        });
    } catch (error) {
        console.error('Lỗi cập nhật đơn hàng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getAllOrders,
    getOrderById,
    updateOrderStatus
};

