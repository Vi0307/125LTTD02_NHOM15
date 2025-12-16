const express = require('express');
const router = express.Router();
const {
    getChatUsers,
    getChatMessages,
    sendMessage
} = require('../controllers/chatboxController');
const { authenticateAdmin } = require('../middleware/auth');

router.use(authenticateAdmin);

router.get('/users', getChatUsers);
router.get('/messages', getChatMessages);
router.post('/messages', sendMessage);

module.exports = router;

