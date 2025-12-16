const express = require('express');
const router = express.Router();
const {
    getAllSchedules,
    getScheduleById,
    updateScheduleStatus
} = require('../controllers/scheduleController');
const { authenticateAdmin } = require('../middleware/auth');

router.use(authenticateAdmin);

router.get('/', getAllSchedules);
router.get('/:id', getScheduleById);
router.put('/:id', updateScheduleStatus);

module.exports = router;

