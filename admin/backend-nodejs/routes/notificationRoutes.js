const express = require('express');
const router = express.Router();
const {
    getAllNotifications,
    getMaintenanceReminders,
    sendReminder,
    deleteReminder
} = require('../controllers/notificationController');
const { authenticateAdmin } = require('../middleware/auth');

router.use(authenticateAdmin);

router.get('/', getAllNotifications);
router.get('/reminders', getMaintenanceReminders);
router.post('/reminders/send', sendReminder);
router.delete('/reminders', deleteReminder);

module.exports = router;

