const { executeQuery, sql } = require('./config/database');

async function checkPassword() {
    try {
        const query = `
            SELECT MaAdmin, TenDangNhap, MatKhau, LEN(MatKhau) as PasswordLength
            FROM ADMIN
            WHERE TenDangNhap = @tenDangNhap
        `;
        
        const result = await executeQuery(query, {
            tenDangNhap: { type: sql.VarChar, value: 'phangiahai' }
        });
        
        if (result.length > 0) {
            const admin = result[0];
            console.log('📋 Thông tin admin:');
            console.log('  - MaAdmin:', admin.MaAdmin);
            console.log('  - TenDangNhap:', admin.TenDangNhap);
            console.log('  - MatKhau:', admin.MatKhau ? `"${admin.MatKhau}"` : 'NULL');
            console.log('  - PasswordLength:', admin.PasswordLength);
            console.log('  - IsNull:', admin.MatKhau === null);
            console.log('  - IsEmpty:', admin.MatKhau === '');
        } else {
            console.log('❌ Không tìm thấy admin');
        }
        
        process.exit(0);
    } catch (error) {
        console.error('❌ Lỗi:', error);
        process.exit(1);
    }
}

checkPassword();
