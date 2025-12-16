const { executeQuery } = require('../config/database');
const sql = require('mssql');

// Lấy danh sách tất cả phụ tùng
const getAllProducts = async (req, res) => {
    try {
        const { page = 1, limit = 50, search = '', maHangXe = '', maLoaiXe = '', maDanhMuc = '' } = req.query;
        const offset = (page - 1) * limit;

        let query = `
            SELECT 
                PT.MaPhuTung,
                PT.TenPhuTung,
                PT.GiaBan,
                PT.SoLuong,
                PT.MoTa,
                PT.HinhAnh,
                PT.NhaCC,
                HX.TenHangXe,
                LX.TenLoaiXe,
                DM.TenDanhMuc,
                PT.MaHangXe,
                PT.MaLoaiXe,
                PT.MaDanhMuc
            FROM PHU_TUNG PT
            INNER JOIN HANG_XE HX ON PT.MaHangXe = HX.MaHangXe
            INNER JOIN LOAI_XE LX ON PT.MaLoaiXe = LX.MaLoaiXe
            INNER JOIN DM_PHUTUNG DM ON PT.MaDanhMuc = DM.MaDanhMuc
            WHERE 1=1
        `;

        const params = {};

        if (search) {
            query += ` AND (PT.TenPhuTung LIKE @search OR PT.MaPhuTung LIKE @search)`;
            params.search = { type: sql.NVarChar, value: `%${search}%` };
        }

        if (maHangXe) {
            query += ` AND PT.MaHangXe = @maHangXe`;
            params.maHangXe = { type: sql.VarChar, value: maHangXe };
        }

        if (maLoaiXe) {
            query += ` AND PT.MaLoaiXe = @maLoaiXe`;
            params.maLoaiXe = { type: sql.VarChar, value: maLoaiXe };
        }

        if (maDanhMuc) {
            query += ` AND PT.MaDanhMuc = @maDanhMuc`;
            params.maDanhMuc = { type: sql.VarChar, value: maDanhMuc };
        }

        query += ` ORDER BY PT.MaPhuTung OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY`;

        params.offset = { type: sql.Int, value: offset };
        params.limit = { type: sql.Int, value: parseInt(limit) };

        // Đếm tổng số
        let countQuery = query.replace(/SELECT[\s\S]*?FROM/, 'SELECT COUNT(*) as total FROM');
        countQuery = countQuery.replace(/ORDER BY[\s\S]*$/, '');
        const countResult = await executeQuery(countQuery, params);
        const total = countResult[0]?.total || 0;

        const products = await executeQuery(query, params);

        res.json({
            success: true,
            data: products,
            pagination: {
                page: parseInt(page),
                limit: parseInt(limit),
                total,
                totalPages: Math.ceil(total / limit)
            }
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách phụ tùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy chi tiết một phụ tùng
const getProductById = async (req, res) => {
    try {
        const { id } = req.params;

        const query = `
            SELECT 
                PT.*,
                HX.TenHangXe,
                LX.TenLoaiXe,
                DM.TenDanhMuc
            FROM PHU_TUNG PT
            INNER JOIN HANG_XE HX ON PT.MaHangXe = HX.MaHangXe
            INNER JOIN LOAI_XE LX ON PT.MaLoaiXe = LX.MaLoaiXe
            INNER JOIN DM_PHUTUNG DM ON PT.MaDanhMuc = DM.MaDanhMuc
            WHERE PT.MaPhuTung = @id
        `;

        const result = await executeQuery(query, {
            id: { type: sql.VarChar, value: id }
        });

        if (result.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy phụ tùng'
            });
        }

        res.json({
            success: true,
            data: result[0]
        });
    } catch (error) {
        console.error('Lỗi lấy chi tiết phụ tùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Thêm phụ tùng mới
const createProduct = async (req, res) => {
    try {
        const {
            MaPhuTung,
            MaHangXe,
            MaLoaiXe,
            TenPhuTung,
            GiaBan,
            SoLuong,
            MoTa,
            HinhAnh,
            MaDanhMuc,
            NhaCC
        } = req.body;

        // Validate
        if (!MaPhuTung || !TenPhuTung || !GiaBan || !MaHangXe || !MaLoaiXe || !MaDanhMuc) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng điền đầy đủ thông tin bắt buộc'
            });
        }

        const query = `
            INSERT INTO PHU_TUNG 
            (MaPhuTung, MaHangXe, MaLoaiXe, TenPhuTung, GiaBan, SoLuong, MoTa, HinhAnh, MaDanhMuc, NhaCC)
            VALUES 
            (@MaPhuTung, @MaHangXe, @MaLoaiXe, @TenPhuTung, @GiaBan, @SoLuong, @MoTa, @HinhAnh, @MaDanhMuc, @NhaCC)
        `;

        await executeQuery(query, {
            MaPhuTung: { type: sql.VarChar, value: MaPhuTung },
            MaHangXe: { type: sql.VarChar, value: MaHangXe },
            MaLoaiXe: { type: sql.VarChar, value: MaLoaiXe },
            TenPhuTung: { type: sql.NVarChar, value: TenPhuTung },
            GiaBan: { type: sql.Decimal(18, 0), value: parseFloat(GiaBan) },
            SoLuong: { type: sql.Int, value: parseInt(SoLuong) || 1 },
            MoTa: { type: sql.NVarChar, value: MoTa || null },
            HinhAnh: { type: sql.VarChar, value: HinhAnh || '' },
            MaDanhMuc: { type: sql.VarChar, value: MaDanhMuc },
            NhaCC: { type: sql.NVarChar, value: NhaCC || 'Royal Auto' }
        });

        res.json({
            success: true,
            message: 'Thêm phụ tùng thành công'
        });
    } catch (error) {
        console.error('Lỗi thêm phụ tùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Cập nhật phụ tùng
const updateProduct = async (req, res) => {
    try {
        const { id } = req.params;
        const {
            TenPhuTung,
            GiaBan,
            SoLuong,
            MoTa,
            HinhAnh,
            NhaCC
        } = req.body;

        const query = `
            UPDATE PHU_TUNG
            SET 
                TenPhuTung = @TenPhuTung,
                GiaBan = @GiaBan,
                SoLuong = @SoLuong,
                MoTa = @MoTa,
                HinhAnh = @HinhAnh,
                NhaCC = @NhaCC
            WHERE MaPhuTung = @id
        `;

        await executeQuery(query, {
            id: { type: sql.VarChar, value: id },
            TenPhuTung: { type: sql.NVarChar, value: TenPhuTung },
            GiaBan: { type: sql.Decimal(18, 0), value: parseFloat(GiaBan) },
            SoLuong: { type: sql.Int, value: parseInt(SoLuong) },
            MoTa: { type: sql.NVarChar, value: MoTa || null },
            HinhAnh: { type: sql.VarChar, value: HinhAnh || '' },
            NhaCC: { type: sql.NVarChar, value: NhaCC || 'Royal Auto' }
        });

        res.json({
            success: true,
            message: 'Cập nhật phụ tùng thành công'
        });
    } catch (error) {
        console.error('Lỗi cập nhật phụ tùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Xóa phụ tùng
const deleteProduct = async (req, res) => {
    try {
        const { id } = req.params;

        const query = `DELETE FROM PHU_TUNG WHERE MaPhuTung = @id`;

        await executeQuery(query, {
            id: { type: sql.VarChar, value: id }
        });

        res.json({
            success: true,
            message: 'Xóa phụ tùng thành công'
        });
    } catch (error) {
        console.error('Lỗi xóa phụ tùng:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getAllProducts,
    getProductById,
    createProduct,
    updateProduct,
    deleteProduct
};

