const express = require('express');
const router = express.Router();
const { getAllVehicleTypes } = require('../controllers/vehicleTypeController');
const { authenticateAdmin } = require('../middleware/auth');

// Tất cả routes đều yêu cầu authentication
router.use(authenticateAdmin);

router.get('/', getAllVehicleTypes);

module.exports = router;
