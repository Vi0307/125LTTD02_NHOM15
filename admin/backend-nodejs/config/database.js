const sql = require('mssql');


const config = {
    server: 'localhost',
    database: 'QLOTOANDROID',
    user: 'sa',
    password: 'Vi03072005@',
    port: 1433,
    options: {
        encrypt: false,
        trustServerCertificate: true,
        enableArithAbort: true
    },
    pool: {
        max: 10,
        min: 0,
        idleTimeoutMillis: 30000
    }
};

let pool = null;

/**
 * Lấy connection pool (singleton)
 */
const getPool = async () => {
    try {
        if (pool) return pool;

        console.log('🔌 Đang kết nối SQL Server...');
        console.log('👉 CONFIG:', {
            server: config.server,
            database: config.database,
            user: config.user,
            port: config.port
        });

        pool = await sql.connect(config);
        console.log('✅ Kết nối SQL Server THÀNH CÔNG!');
        return pool;
    } catch (err) {
        console.error('❌ LỖI KẾT NỐI DATABASE');
        console.error(err);
        throw err;
    }
};

/**
 * Đóng pool
 */
const closePool = async () => {
    try {
        if (pool) {
            await pool.close();
            pool = null;
            console.log('✅ Đã đóng kết nối database');
        }
    } catch (err) {
        console.error('❌ Lỗi khi đóng pool:', err);
    }
};

/**
 * Execute query thường
 */
const executeQuery = async (query, params = {}) => {
    try {
        const pool = await getPool();
        const request = pool.request();

        Object.keys(params).forEach(key => {
            const param = params[key];
            if (param && typeof param === 'object' && param.type && param.value !== undefined) {
                request.input(key, param.type, param.value);
            } else {
                request.input(key, param);
            }
        });

        const result = await request.query(query);
        return result.recordset;
    } catch (err) {
        console.error('❌ Lỗi executeQuery');
        console.error(err);
        throw err;
    }
};

/**
 * Execute stored procedure
 */
const executeProcedure = async (procedureName, params = {}) => {
    try {
        const pool = await getPool();
        const request = pool.request();

        Object.keys(params).forEach(key => {
            const param = params[key];
            if (param && typeof param === 'object' && param.type && param.value !== undefined) {
                request.input(key, param.type, param.value);
            } else {
                request.input(key, param);
            }
        });

        const result = await request.execute(procedureName);
        return result.recordset;
    } catch (err) {
        console.error('❌ Lỗi executeProcedure');
        console.error(err);
        throw err;
    }
};

module.exports = {
    sql,
    getPool,
    closePool,
    executeQuery,
    executeProcedure
};
