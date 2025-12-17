const { executeQuery } = require('../config/database');
const sql = require('mssql');

// Lấy danh sách tất cả danh mục
const getAllCategories = async (req, res) => {
    try {
        const query = `
            SELECT 
                DM.MaDanhMuc,
                DM.TenDanhMuc,
                COUNT(PT.MaPhuTung) as SoLuongSanPham
            FROM DM_PHUTUNG DM
            LEFT JOIN PHU_TUNG PT ON DM.MaDanhMuc = PT.MaDanhMuc
            GROUP BY DM.MaDanhMuc, DM.TenDanhMuc
            ORDER BY DM.MaDanhMuc
        `;

        const categories = await executeQuery(query);

        res.json({
            success: true,
            data: categories
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách danh mục:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

// Lấy chi tiết một danh mục
const getCategoryById = async (req, res) => {
    try {
        const { id } = req.params;

        const query = `
            SELECT 
                DM.MaDanhMuc,
                DM.TenDanhMuc,
                COUNT(PT.MaPhuTung) as SoLuongSanPham
            FROM DM_PHUTUNG DM
            LEFT JOIN PHU_TUNG PT ON DM.MaDanhMuc = PT.MaDanhMuc
            WHERE DM.MaDanhMuc = @id
            GROUP BY DM.MaDanhMuc, DM.TenDanhMuc
        `;

        const result = await executeQuery(query, {
            id: { type: sql.VarChar, value: id }
        });

        if (result.length === 0) {
            return res.status(404).json({
                success: false,
                message: 'Không tìm thấy danh mục'
            });
        }

        res.json({
            success: true,
            data: result[0]
        });
    } catch (error) {
        console.error('Lỗi lấy chi tiết danh mục:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getAllCategories,
    getCategoryById
};
