# OTOTECH Admin Backend API

Backend API cho Admin Panel OTOTECH được xây dựng bằng Node.js và Express.

## 📋 Yêu cầu

- Node.js >= 14.0.0
- SQL Server (đã có database QLOTOANDROID)
- npm hoặc yarn

## 🚀 Cài đặt

1. **Cài đặt dependencies:**
```bash
cd admin/backend-nodejs
npm install
```

2. **Cấu hình môi trường:**
```bash
cp .env.example .env
```

Chỉnh sửa file `.env` với thông tin database của bạn:
```env
DB_SERVER=localhost
DB_DATABASE=QLOTOANDROID
DB_USER=sa
DB_PASSWORD=your_password
DB_PORT=1433
JWT_SECRET=your_super_secret_jwt_key
```

3. **Chạy server:**
```bash
# Development mode (với nodemon)
npm run dev

# Production mode
npm start
```

Server sẽ chạy tại `http://localhost:3000`

## 📚 API Endpoints

### Authentication
- `POST /api/auth/login` - Đăng nhập admin
- `GET /api/auth/me` - Lấy thông tin admin hiện tại

### Products (Phụ tùng)
- `GET /api/products` - Lấy danh sách phụ tùng
- `GET /api/products/:id` - Lấy chi tiết phụ tùng
- `POST /api/products` - Thêm phụ tùng mới
- `PUT /api/products/:id` - Cập nhật phụ tùng
- `DELETE /api/products/:id` - Xóa phụ tùng

### Users (Người dùng)
- `GET /api/users` - Lấy danh sách người dùng
- `GET /api/users/:id` - Lấy chi tiết người dùng
- `PUT /api/users/:id` - Cập nhật thông tin người dùng
- `PATCH /api/users/:id/lock` - Khóa/Mở khóa tài khoản
- `DELETE /api/users/:id` - Xóa người dùng

### Orders (Đơn hàng)
- `GET /api/orders` - Lấy danh sách đơn hàng
- `GET /api/orders/:id` - Lấy chi tiết đơn hàng
- `PUT /api/orders/:id` - Cập nhật trạng thái đơn hàng

### Schedules (Lịch bảo dưỡng)
- `GET /api/schedules` - Lấy danh sách lịch bảo dưỡng
- `GET /api/schedules/:id` - Lấy chi tiết lịch bảo dưỡng
- `PUT /api/schedules/:id` - Cập nhật trạng thái bảo dưỡng

### Notifications (Thông báo)
- `GET /api/notifications` - Lấy danh sách thông báo
- `GET /api/notifications/reminders` - Lấy danh sách nhắc bảo dưỡng
- `POST /api/notifications/reminders/send` - Gửi thông báo nhắc nhở
- `DELETE /api/notifications/reminders` - Xóa khỏi danh sách nhắc nhở

### Chatbox
- `GET /api/chatbox/users` - Lấy danh sách người dùng để chat
- `GET /api/chatbox/messages` - Lấy lịch sử tin nhắn
- `POST /api/chatbox/messages` - Gửi tin nhắn

## 🔐 Authentication

Tất cả API (trừ `/api/auth/login`) đều yêu cầu JWT token trong header:

```
Authorization: Bearer <token>
```

hoặc

```
x-access-token: <token>
```

## 📝 Ví dụ sử dụng

### Đăng nhập
```bash
curl -X POST http://localhost:3000/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "tenDangNhap": "phangiahai",
    "matKhau": "123456"
  }'
```

### Lấy danh sách phụ tùng
```bash
curl -X GET http://localhost:3000/api/products \
  -H "Authorization: Bearer <token>"
```

### Cập nhật trạng thái đơn hàng
```bash
curl -X PUT http://localhost:3000/api/orders/DH001 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "TrangThai": "Đã giao"
  }'
```

## 🗂️ Cấu trúc thư mục

```
backend-nodejs/
├── config/
│   └── database.js          # Cấu hình kết nối SQL Server
├── controllers/
│   ├── authController.js     # Xử lý authentication
│   ├── productController.js # Xử lý phụ tùng
│   ├── userController.js    # Xử lý người dùng
│   ├── orderController.js   # Xử lý đơn hàng
│   ├── scheduleController.js # Xử lý lịch bảo dưỡng
│   ├── notificationController.js # Xử lý thông báo
│   └── chatboxController.js # Xử lý chatbox
├── middleware/
│   └── auth.js              # Middleware xác thực
├── routes/
│   ├── authRoutes.js
│   ├── productRoutes.js
│   ├── userRoutes.js
│   ├── orderRoutes.js
│   ├── scheduleRoutes.js
│   ├── notificationRoutes.js
│   └── chatboxRoutes.js
├── .env.example            # File mẫu cấu hình
├── .gitignore
├── package.json
├── server.js               # File chính
└── README.md
```

## 🛠️ Development

Để chạy ở chế độ development với auto-reload:
```bash
npm run dev
```

## 📦 Production

Để deploy production, đảm bảo:
1. Set `NODE_ENV=production` trong `.env`
2. Sử dụng process manager như PM2
3. Cấu hình reverse proxy (nginx) nếu cần

## ⚠️ Lưu ý

- Đảm bảo SQL Server đang chạy và database `QLOTOANDROID` đã được tạo
- Thay đổi `JWT_SECRET` trong production
- Cấu hình CORS phù hợp với domain frontend
- Hash mật khẩu admin trong database để bảo mật tốt hơn

## 📞 Hỗ trợ

Nếu gặp vấn đề, kiểm tra:
1. Kết nối database có thành công không
2. Port 3000 có bị chiếm không
3. File `.env` đã được cấu hình đúng chưa

