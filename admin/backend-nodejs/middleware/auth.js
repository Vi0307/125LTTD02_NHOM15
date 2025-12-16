const jwt = require('jsonwebtoken');
require('dotenv').config();

// Middleware xác thực admin
const authenticateAdmin = (req, res, next) => {
    try {
        const token = req.headers.authorization?.split(' ')[1] || 
                     req.headers['x-access-token'] ||
                     req.cookies?.token;

        if (!token) {
            return res.status(401).json({
                success: false,
                message: 'Không có token xác thực'
            });
        }

        const jwtSecret = process.env.JWT_SECRET || 'SECRET_KEY_ADMIN';
        const decoded = jwt.verify(token, jwtSecret);
        
        // Kiểm tra vai trò admin
        if (decoded.vaiTro !== 'admin') {
            return res.status(403).json({
                success: false,
                message: 'Không có quyền truy cập'
            });
        }

        req.admin = {
            maAdmin: decoded.maAdmin,
            tenDangNhap: decoded.tenDangNhap,
            vaiTro: decoded.vaiTro
        };

        next();
    } catch (err) {
        return res.status(401).json({
            success: false,
            message: 'Token không hợp lệ hoặc đã hết hạn'
        });
    }
};

// Middleware xác thực (không bắt buộc admin)
const authenticateOptional = (req, res, next) => {
    try {
        const token = req.headers.authorization?.split(' ')[1] || 
                     req.headers['x-access-token'] ||
                     req.cookies?.token;

        if (token) {
            const jwtSecret = process.env.JWT_SECRET || 'SECRET_KEY_ADMIN';
            const decoded = jwt.verify(token, jwtSecret);
            req.user = decoded;
        }
        next();
    } catch (err) {
        next();
    }
};

module.exports = {
    authenticateAdmin,
    authenticateOptional
};

