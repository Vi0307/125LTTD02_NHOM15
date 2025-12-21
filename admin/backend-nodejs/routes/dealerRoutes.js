const express = require('express');
const router = express.Router();
const dealerController = require('../controllers/dealerController');

// GET /api/dealers - Lấy tất cả đại lý
router.get('/', dealerController.getAllDealers);

// GET /api/dealers/:id - Lấy chi tiết đại lý
router.get('/:id', dealerController.getDealerById);

// POST /api/dealers - Thêm đại lý mới
router.post('/', dealerController.createDealer);

// PUT /api/dealers/:id - Cập nhật đại lý
router.put('/:id', dealerController.updateDealer);

// DELETE /api/dealers/:id - Xóa đại lý
router.delete('/:id', dealerController.deleteDealer);

module.exports = router;
