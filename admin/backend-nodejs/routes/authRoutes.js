const express = require('express');
const router = express.Router();

const {
    login,
    getCurrentAdmin
} = require('../controllers/authController');

const { authenticateAdmin } = require('../middleware/auth');

// Đăng nhập admin
router.post('/login', login);

// Lấy thông tin admin hiện tại (cần token)
router.get('/me', authenticateAdmin, getCurrentAdmin);

module.exports = router;
