const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');

// Routes
const authRoutes = require('./routes/authRoutes');
const productRoutes = require('./routes/productRoutes');
const categoryRoutes = require('./routes/categoryRoutes');
const userRoutes = require('./routes/userRoutes');
const orderRoutes = require('./routes/orderRoutes');
const scheduleRoutes = require('./routes/scheduleRoutes');
const notificationRoutes = require('./routes/notificationRoutes');
const chatboxRoutes = require('./routes/chatboxRoutes');

// Database
const { getPool, closePool } = require('./config/database');

const app = express();
const PORT = 3001;

/* =======================
   MIDDLEWARE
======================= */
app.use(cors({
    origin: '*',
    credentials: true
}));

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

/* =======================
   HEALTH CHECK
======================= */
app.get('/health', (req, res) => {
    res.status(200).json({
        success: true,
        message: 'Server is running OK',
        time: new Date().toISOString()
    });
});

/* =======================
   API ROUTES
======================= */
app.use('/api/auth', authRoutes);
app.use('/api/products', productRoutes);
app.use('/api/categories', categoryRoutes);
app.use('/api/users', userRoutes);
app.use('/api/orders', orderRoutes);
app.use('/api/schedules', scheduleRoutes);
app.use('/api/notifications', notificationRoutes);
app.use('/api/chatbox', chatboxRoutes);

/* =======================
   404 HANDLER
======================= */
app.use((req, res) => {
    res.status(404).json({
        success: false,
        message: 'API not found'
    });
});

/* =======================
   ERROR HANDLER
======================= */
app.use((err, req, res, next) => {
    console.error('❌ Server error:', err);
    res.status(500).json({
        success: false,
        message: err.message || 'Internal Server Error'
    });
});

/* =======================
   GRACEFUL SHUTDOWN
======================= */
const shutdown = async () => {
    console.log('\n🛑 Shutting down server...');
    await closePool();
    process.exit(0);
};

process.on('SIGINT', shutdown);
process.on('SIGTERM', shutdown);

/* =======================
   START SERVER
======================= */
const startServer = async () => {
    try {
        // Test DB connection
        await getPool();

        app.listen(PORT, () => {
            console.log('====================================');
            console.log(`🚀 Server running at http://localhost:${PORT}`);
            console.log('📌 Available endpoints:');
            console.log('   GET  /health');
            console.log('   POST /api/auth');
            console.log('   GET  /api/products');
            console.log('   GET  /api/categories');
            console.log('   GET  /api/users');
            console.log('   GET  /api/orders');
            console.log('   GET  /api/schedules');
            console.log('   GET  /api/notifications');
            console.log('   GET  /api/chatbox');
            console.log('====================================');
        });
    } catch (err) {
        console.error('❌ Không thể khởi động server:', err);
        process.exit(1);
    }
};

startServer();

module.exports = app;
