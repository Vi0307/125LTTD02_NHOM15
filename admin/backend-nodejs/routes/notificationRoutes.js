const express = require('express');
const router = express.Router();
const {
    getAllNotifications,
    getMaintenanceReminders,
    sendReminder,
    deleteReminder,
    getBaoDuongReminders,
    updateBaoDuongStatus,
    createBaoDuongReminder
} = require('../controllers/notificationController');
const { authenticateAdmin } = require('../middleware/auth');

router.use(authenticateAdmin);

router.get('/', getAllNotifications);
router.get('/reminders', getMaintenanceReminders);
router.post('/reminders/send', sendReminder);
router.delete('/reminders', deleteReminder);

// API cho bảng BAO_DUONG (nhắc nhở thay phụ tùng - giống Android app)
router.get('/bao-duong', getBaoDuongReminders);
router.put('/bao-duong/status', updateBaoDuongStatus);
router.post('/bao-duong', createBaoDuongReminder);

module.exports = router;

