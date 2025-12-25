const express = require('express');
const router = express.Router();
const multer = require('multer');
const path = require('path');
const {
    getAllProducts,
    getProductById,
    createProduct,
    updateProduct,
    deleteProduct
} = require('../controllers/productController');
const { authenticateAdmin } = require('../middleware/auth');

// Cấu hình Multer để lưu ảnh vào thư mục admin/asset
const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        // Lưu vào folder asset của admin (lên 2 cấp từ routes -> backend-nodejs -> admin -> asset)
        cb(null, path.join(__dirname, '../../asset'));
    },
    filename: function (req, file, cb) {
        // Đặt tên file unique để tránh trùng lặp
        const uniqueSuffix = Date.now() + '-' + Math.round(Math.random() * 1E9);
        cb(null, uniqueSuffix + path.extname(file.originalname));
    }
});

const upload = multer({
    storage: storage,
    limits: { fileSize: 5 * 1024 * 1024 }, // Giới hạn 5MB
    fileFilter: function (req, file, cb) {
        // Chỉ chấp nhận file ảnh
        if (!file.originalname.match(/\.(jpg|jpeg|png|gif|webp)$/)) {
            return cb(new Error('Chỉ chấp nhận file ảnh!'), false);
        }
        cb(null, true);
    }
});

// Tất cả routes đều yêu cầu authentication
router.use(authenticateAdmin);

router.get('/', getAllProducts);
router.get('/:id', getProductById);
// Thêm middleware upload.single('image') cho create và update
router.post('/', upload.single('image'), createProduct);
router.put('/:id', upload.single('image'), updateProduct);
router.delete('/:id', deleteProduct);

module.exports = router;

