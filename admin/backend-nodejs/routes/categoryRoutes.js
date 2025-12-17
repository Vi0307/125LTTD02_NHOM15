const express = require('express');
const router = express.Router();
const {
    getAllCategories,
    getCategoryById
} = require('../controllers/categoryController');
const { authenticateAdmin } = require('../middleware/auth');

// Tất cả routes đều yêu cầu authentication
router.use(authenticateAdmin);

router.get('/', getAllCategories);
router.get('/:id', getCategoryById);

module.exports = router;
