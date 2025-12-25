const { executeQuery } = require('../config/database');

// Lấy danh sách tất cả loại xe
const getAllVehicleTypes = async (req, res) => {
    try {
        const query = `
            SELECT 
                LX.MaLoaiXe,
                LX.TenLoaiXe,
                LX.MoTa,
                LX.MaHangXe,
                HX.TenHangXe
            FROM LOAI_XE LX
            INNER JOIN HANG_XE HX ON LX.MaHangXe = HX.MaHangXe
            ORDER BY LX.MaLoaiXe
        `;

        const vehicleTypes = await executeQuery(query);

        res.json({
            success: true,
            data: vehicleTypes
        });
    } catch (error) {
        console.error('Lỗi lấy danh sách loại xe:', error);
        res.status(500).json({
            success: false,
            message: 'Lỗi server',
            error: error.message
        });
    }
};

module.exports = {
    getAllVehicleTypes
};
