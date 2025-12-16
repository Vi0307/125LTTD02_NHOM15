# Hướng dẫn tích hợp Backend API với Admin Panel

## 📋 Tổng quan

Backend Node.js đã được tích hợp hoàn toàn với frontend admin panel. Tất cả các trang HTML đã được cập nhật để sử dụng API thay vì dữ liệu tĩnh.

## 🚀 Cách chạy

### 1. Khởi động Backend

```bash
cd admin/backend-nodejs
npm install
npm run dev
```

Backend sẽ chạy tại: `http://localhost:3000`

### 2. Mở Admin Panel

Mở file `admin/welcome.html` trong trình duyệt để đăng nhập.

**Thông tin đăng nhập mẫu:**
- Tên đăng nhập: `phangiahai`
- Mật khẩu: `123456`

## 📁 Cấu trúc tích hợp

### File API Service
- `admin/asset/api.js` - File chứa tất cả các hàm gọi API

### Các trang đã tích hợp:
1. ✅ `welcome.html` - Đăng nhập
2. ✅ `product1.html` - Quản lý sản phẩm
3. ✅ `user.html` - Quản lý người dùng
4. ✅ `order.html` - Quản lý đơn hàng
5. ✅ `schedule.html` - Quản lý lịch bảo dưỡng
6. ✅ `notifications.html` - Thông báo nhắc bảo dưỡng
7. ✅ `chatbox.html` - Chatbox với khách hàng
8. ✅ `dashboard.html` - Dashboard chính

## 🔐 Authentication Flow

1. User đăng nhập tại `welcome.html`
2. Token được lưu vào `localStorage`
3. Tất cả các trang admin khác kiểm tra token
4. Nếu không có token → redirect về `welcome.html`
5. Token được gửi trong header `Authorization: Bearer <token>`

## 📡 API Endpoints được sử dụng

### Authentication
- `POST /api/auth/login` - Đăng nhập

### Products
- `GET /api/products` - Lấy danh sách
- `GET /api/products/:id` - Chi tiết
- `POST /api/products` - Thêm mới
- `PUT /api/products/:id` - Cập nhật
- `DELETE /api/products/:id` - Xóa

### Users
- `GET /api/users` - Lấy danh sách
- `GET /api/users/:id` - Chi tiết
- `PUT /api/users/:id` - Cập nhật
- `PATCH /api/users/:id/lock` - Khóa/Mở khóa
- `DELETE /api/users/:id` - Xóa

### Orders
- `GET /api/orders` - Lấy danh sách
- `GET /api/orders/:id` - Chi tiết
- `PUT /api/orders/:id` - Cập nhật trạng thái

### Schedules
- `GET /api/schedules` - Lấy danh sách
- `GET /api/schedules/:id` - Chi tiết
- `PUT /api/schedules/:id` - Cập nhật trạng thái

### Notifications
- `GET /api/notifications/reminders` - Danh sách nhắc bảo dưỡng
- `POST /api/notifications/reminders/send` - Gửi thông báo
- `DELETE /api/notifications/reminders` - Xóa nhắc nhở

### Chatbox
- `GET /api/chatbox/users` - Danh sách người dùng
- `GET /api/chatbox/messages` - Lịch sử tin nhắn
- `POST /api/chatbox/messages` - Gửi tin nhắn

## ⚙️ Cấu hình

### Thay đổi API URL

Nếu backend chạy ở port khác, sửa trong `admin/asset/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:3000/api'; // Thay đổi port nếu cần
```

### CORS

Đảm bảo backend cho phép CORS từ domain frontend. Đã cấu hình sẵn trong `server.js`.

## 🐛 Xử lý lỗi

- Nếu token hết hạn → Tự động redirect về trang đăng nhập
- Lỗi kết nối server → Hiển thị thông báo lỗi
- Lỗi API → Hiển thị message từ server

## 📝 Lưu ý

1. **Database**: Đảm bảo SQL Server đang chạy và database `QLOTOANDROID` đã được tạo
2. **Backend**: Phải chạy backend trước khi sử dụng admin panel
3. **Token**: Token được lưu trong localStorage, xóa khi đăng xuất
4. **CORS**: Nếu gặp lỗi CORS, kiểm tra cấu hình trong `server.js`

## ✅ Checklist tích hợp

- [x] Tạo API service (`api.js`)
- [x] Tích hợp authentication
- [x] Tích hợp Products API
- [x] Tích hợp Users API
- [x] Tích hợp Orders API
- [x] Tích hợp Schedules API
- [x] Tích hợp Notifications API
- [x] Tích hợp Chatbox API
- [x] Xóa dữ liệu mẫu trong HTML
- [x] Thêm authentication check cho tất cả trang

## 🎉 Hoàn thành!

Tất cả các trang đã được tích hợp với backend API. Bạn có thể:
- Đăng nhập và quản lý dữ liệu từ database
- CRUD đầy đủ cho tất cả modules
- Real-time updates khi thay đổi dữ liệu

