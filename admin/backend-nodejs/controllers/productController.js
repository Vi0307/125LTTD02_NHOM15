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

        // Đếm tổng số (trước khi thêm OFFSET/FETCH)
        let countQuery = `
            SELECT COUNT(*) as total
            FROM PHU_TUNG PT
            INNER JOIN HANG_XE HX ON PT.MaHangXe = HX.MaHangXe
            INNER JOIN LOAI_XE LX ON PT.MaLoaiXe = LX.MaLoaiXe
            INNER JOIN DM_PHUTUNG DM ON PT.MaDanhMuc = DM.MaDanhMuc
            WHERE 1=1
        `;

        if (search) {
            countQuery += ` AND (PT.TenPhuTung LIKE @search OR PT.MaPhuTung LIKE @search)`;
        }
        if (maHangXe) {
            countQuery += ` AND PT.MaHangXe = @maHangXe`;
        }
        if (maLoaiXe) {
            countQuery += ` AND PT.MaLoaiXe = @maLoaiXe`;
        }
        if (maDanhMuc) {
            countQuery += ` AND PT.MaDanhMuc = @maDanhMuc`;
        }

        const countParams = { ...params };
        delete countParams.offset;
        delete countParams.limit;
        const countResult = await executeQuery(countQuery, countParams);
        const total = countResult[0]?.total || 0;

        // Thêm ORDER BY và OFFSET/FETCH cho query chính
        query += ` ORDER BY PT.MaPhuTung OFFSET @offset ROWS FETCH NEXT @limit ROWS ONLY`;

        params.offset = { type: sql.Int, value: offset };
        params.limit = { type: sql.Int, value: parseInt(limit) };

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
            // HinhAnh sẽ được lấy từ req.file nếu có upload
            MaDanhMuc,
            NhaCC
        } = req.body;

        // Xử lý hình ảnh
        let hinhAnhPath = '';
        if (req.file) {
            hinhAnhPath = req.file.filename;
            console.log('🖼️ Đã upload ảnh mới:', hinhAnhPath);
        } else if (req.body.HinhAnh) {
            // Trường hợp gửi tên ảnh (khi không chọn file mới nhưng API cũ gửi text)
            hinhAnhPath = req.body.HinhAnh;
        }

        // Log dữ liệu nhận được
        console.log('📦 Dữ liệu nhận được:', req.body);

        // Validate
        if (!MaPhuTung || !TenPhuTung || !GiaBan || !MaHangXe || !MaLoaiXe || !MaDanhMuc) {
            console.log('❌ Validation failed:', { MaPhuTung, TenPhuTung, GiaBan, MaHangXe, MaLoaiXe, MaDanhMuc });
            return res.status(400).json({
                success: false,
                message: 'Vui lòng điền đầy đủ thông tin bắt buộc'
            });
        }

        // Parse giá trị
        const giaNumber = parseFloat(GiaBan) || 0;
        const soLuongNumber = parseInt(SoLuong) || 1;

        console.log('📊 Giá trị sau parse:', { giaNumber, soLuongNumber });

        const query = `
            INSERT INTO PHU_TUNG 
            (MaPhuTung, MaHangXe, MaLoaiXe, TenPhuTung, GiaBan, SoLuong, MoTa, HinhAnh, MaDanhMuc, NhaCC)
            VALUES 
            (@MaPhuTung, @MaHangXe, @MaLoaiXe, @TenPhuTung, @GiaBan, @SoLuong, @MoTa, @HinhAnh, @MaDanhMuc, @NhaCC)
        `;

        await executeQuery(query, {
            MaPhuTung: { type: sql.VarChar(10), value: MaPhuTung },
            MaHangXe: { type: sql.VarChar(10), value: MaHangXe },
            MaLoaiXe: { type: sql.VarChar(10), value: MaLoaiXe },
            TenPhuTung: { type: sql.NVarChar(100), value: TenPhuTung },
            GiaBan: { type: sql.Decimal(18, 0), value: giaNumber },
            SoLuong: { type: sql.Int, value: soLuongNumber },
            MoTa: { type: sql.NVarChar(sql.MAX), value: MoTa || null },
            HinhAnh: { type: sql.VarChar(255), value: hinhAnhPath || '' },
            MaDanhMuc: { type: sql.VarChar(10), value: MaDanhMuc },
            NhaCC: { type: sql.NVarChar(100), value: NhaCC || 'Royal Auto' }
        });

        console.log('✅ Thêm phụ tùng thành công:', MaPhuTung);

        res.json({
            success: true,
            message: 'Thêm phụ tùng thành công'
        });
    } catch (error) {
        console.error('❌ Lỗi thêm phụ tùng:', error);
        console.error('❌ Chi tiết lỗi:', error.message);
        res.status(500).json({
            success: false,
            message: 'Lỗi server: ' + error.message,
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
            // HinhAnh, -> Xử lý riêng
            NhaCC
        } = req.body;

        // Xử lý hình ảnh
        let hinhAnhPath = req.body.HinhAnh; // Mặc định giữ ảnh cũ hoặc giá trị gửi lên
        if (req.file) {
            hinhAnhPath = req.file.filename;
            console.log('🖼️ Cập nhật ảnh mới:', hinhAnhPath);
        }

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
            HinhAnh: { type: sql.VarChar, value: hinhAnhPath || '' },
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

