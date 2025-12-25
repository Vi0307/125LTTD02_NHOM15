const { getPool } = require('../config/database');

// Lấy tất cả đại lý
const getAllDealers = async (req, res) => {
    try {
        const pool = await getPool();
        const result = await pool.request().query(`
            SELECT MaDaiLy, TenDaiLy, DiaChi, SoDienThoai, GioLamViec, MoTa
            FROM DAI_LY
            ORDER BY MaDaiLy
        `);

        res.json({
            success: true,
            data: result.recordset,
            total: result.recordset.length
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách đại lý:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server: ' + error.message
        });
    }
};

// Lấy chi tiết đại lý theo ID
const getDealerById = async (req, res) => {
    try {
        const { id } = req.params;
        const pool = await getPool();
        const result = await pool.request()
            .input('MaDaiLy', id)
            .query(`
                SELECT MaDaiLy, TenDaiLy, DiaChi, SoDienThoai, GioLamViec, MoTa
                FROM DAI_LY
                WHERE MaDaiLy = @MaDaiLy
            `);

        if (result.recordset.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đại lý'
            });
        }

        res.json({
            success: true,
            data: result.recordset[0]
        });
    } catch (error) {
        console.error('Lỗi lấy chi tiết đại lý:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server: ' + error.message
        });
    }
};

// Thêm đại lý mới
const createDealer = async (req, res) => {
    try {
        const { TenDaiLy, DiaChi, SoDienThoai, GioLamViec, MoTa } = req.body;

        if (!TenDaiLy || !DiaChi || !SoDienThoai) {
            return res.status(400).json({
                success: false,
                message: 'Vui lòng nhập đầy đủ thông tin bắt buộc'
            });
        }

        const pool = await getPool();
        const result = await pool.request()
            .input('TenDaiLy', TenDaiLy)
            .input('DiaChi', DiaChi)
            .input('SoDienThoai', SoDienThoai)
            .input('GioLamViec', GioLamViec || null)
            .input('MoTa', MoTa || null)
            .query(`
                INSERT INTO DAI_LY (TenDaiLy, DiaChi, SoDienThoai, GioLamViec, MoTa)
                OUTPUT INSERTED.*
                VALUES (@TenDaiLy, @DiaChi, @SoDienThoai, @GioLamViec, @MoTa)
            `);

        res.status(201).json({
            success: true,
            message: 'Thêm đại lý thành công',
            data: result.recordset[0]
        });
    } catch (error) {
        console.error('Lỗi thêm đại lý:', error);
        
    }
};

// Cập nhật đại lý
const updateDealer = async (req, res) => {
    try {
        const { id } = req.params;
        const { TenDaiLy, DiaChi, SoDienThoai, GioLamViec, MoTa } = req.body;

        const pool = await getPool();
        const result = await pool.request()
            .input('MaDaiLy', id)
            .input('TenDaiLy', TenDaiLy)
            .input('DiaChi', DiaChi)
            .input('SoDienThoai', SoDienThoai)
            .input('GioLamViec', GioLamViec || null)
            .input('MoTa', MoTa || null)
            .query(`
                UPDATE DAI_LY
                SET TenDaiLy = @TenDaiLy,
                    DiaChi = @DiaChi,
                    SoDienThoai = @SoDienThoai,
                    GioLamViec = @GioLamViec,
                    MoTa = @MoTa
                OUTPUT INSERTED.*
                WHERE MaDaiLy = @MaDaiLy
            `);

        if (result.recordset.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đại lý'
            });
        }

        res.json({
            success: true,
            message: 'Cập nhật đại lý thành công',
            data: result.recordset[0]
        });
    } catch (error) {
        console.error('Lỗi cập nhật đại lý:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server: ' + error.message
        });
    }
};

// Xóa đại lý
const deleteDealer = async (req, res) => {
    try {
        const { id } = req.params;
        const pool = await getPool();

        const result = await pool.request()
            .input('MaDaiLy', id)
            .query(`DELETE FROM DAI_LY WHERE MaDaiLy = @MaDaiLy`);

        if (result.rowsAffected[0] === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy đại lý'
            });
        }

        res.json({
            success: true,
            message: 'Xóa đại lý thành công'
        });
    } catch (error) {
        console.error('Lỗi xóa đại lý:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server: ' + error.message
        });
    }
};

module.exports = {
    getAllDealers,
    getDealerById,
    createDealer,
    updateDealer,
    deleteDealer
};
