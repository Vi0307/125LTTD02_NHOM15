// API Configuration
const API_BASE_URL = 'http://localhost:3001/api';

// ==================== AUTHENTICATION ====================
const AuthService = {
    // Lưu token vào localStorage
    setToken(token) {
        if (!token || typeof token !== 'string' || token.trim() === '') {
            console.error('❌ Cannot set invalid token');
            return;
        }
        console.log('💾 Saving token to localStorage');
        localStorage.setItem('admin_token', token);
    },

    // Lấy token từ localStorage
    getToken() {
        const token = localStorage.getItem('admin_token');
        if (token && typeof token === 'string' && token.trim() !== '') {
            return token;
        }
        return null;
    },

    // Xóa token
    removeToken() {
        console.log('🗑️ Removing token from localStorage');
        localStorage.removeItem('admin_token');
        // Đảm bảo xóa hoàn toàn
        if (localStorage.getItem('admin_token')) {
            localStorage.removeItem('admin_token');
        }
    },

    // Kiểm tra đã đăng nhập chưa
    isAuthenticated() {
        const token = this.getToken();
        const isAuth = !!token;
        console.log('🔍 Auth check:', { hasToken: isAuth, tokenPreview: token ? token.substring(0, 20) + '...' : 'null' });
        return isAuth;
    },

    // Đăng nhập
    async login(tenDangNhap, matKhau) {
        try {
            // Validate input trước khi gửi
            if (!tenDangNhap || !matKhau || tenDangNhap.trim() === '' || matKhau.trim() === '') {
                console.error('❌ Input validation failed');
                return { success: false, message: 'Vui lòng nhập đầy đủ thông tin' };
            }

            // Đảm bảo không có token cũ
            this.removeToken();

            const response = await fetch(`${API_BASE_URL}/auth/login`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ tenDangNhap: tenDangNhap.trim(), matKhau })
            });

            // Parse JSON response
            let data;
            try {
                const text = await response.text();
                data = text ? JSON.parse(text) : {};
            } catch (e) {
                console.error('❌ Cannot parse response:', e);
                this.removeToken();
                return { success: false, message: 'Lỗi phản hồi từ server' };
            }
            
            console.log('🔐 Login response:', {
                status: response.status,
                ok: response.ok,
                success: data.success,
                hasToken: !!(data.data && data.data.token),
                fullData: data
            });
            
            // KIỂM TRA CHẶT CHẼ - CHỈ THÀNH CÔNG KHI:
            // 1. Response status = 200 (OK)
            // 2. response.ok = true
            // 3. data.success === true (strict boolean check)
            // 4. Có token trong data.data.token và là string
            const isSuccess = (
                response.status === 200 && 
                response.ok === true && 
                data.success === true && 
                data.data && 
                data.data.token && 
                typeof data.data.token === 'string' &&
                data.data.token.length > 0
            );
            
            if (isSuccess) {
                console.log('✅ Login success, setting token:', data.data.token.substring(0, 20) + '...');
                this.setToken(data.data.token);
                return { success: true, data: data.data };
            } else {
                // XÓA TOKEN - KHÔNG ĐƯỢC SET TOKEN KHI FAIL
                console.error('❌ Login failed:', {
                    status: response.status,
                    ok: response.ok,
                    success: data.success,
                    hasToken: !!(data.data && data.data.token),
                    reason: !response.ok ? 'Response not OK' : 
                            data.success !== true ? 'Success is not true' :
                            !data.data ? 'No data' :
                            !data.data.token ? 'No token' :
                            typeof data.data.token !== 'string' ? 'Token is not string' :
                            data.data.token.length === 0 ? 'Token is empty' : 'Unknown'
                });
                this.removeToken(); // QUAN TRỌNG: Xóa token khi fail
                const errorMsg = data.message || `Đăng nhập thất bại (Status: ${response.status})`;
                return { success: false, message: errorMsg };
            }
        } catch (error) {
            console.error('❌ Lỗi đăng nhập:', error);
            this.removeToken();
            return { success: false, message: 'Lỗi kết nối server: ' + error.message };
        }
    },

    // Đăng xuất
    logout() {
        this.removeToken();
        window.location.href = 'welcome.html';
    }
};

// ==================== API HELPER ====================
const apiCall = async (endpoint, options = {}) => {
    const token = AuthService.getToken();
    
    const defaultHeaders = {
        'Content-Type': 'application/json'
    };

    if (token) {
        defaultHeaders['Authorization'] = `Bearer ${token}`;
    }

    const config = {
        ...options,
        headers: {
            ...defaultHeaders,
            ...options.headers
        }
    };

    try {
        const response = await fetch(`${API_BASE_URL}${endpoint}`, config);
        
        // Nếu token hết hạn hoặc không hợp lệ - logout ngay
        if (response.status === 401) {
            AuthService.logout();
            return { success: false, message: 'Phiên đăng nhập đã hết hạn' };
        }

        const data = await response.json();

        // Đảm bảo nếu response không thành công thì success = false
        if (!response.ok) {
            return { 
                success: false, 
                message: data.message || 'Lỗi từ server',
                status: response.status
            };
        }

        return data;
    } catch (error) {
        console.error('API Error:', error);
        return { success: false, message: 'Lỗi kết nối server' };
    }
};

// ==================== PRODUCTS API ====================
const ProductAPI = {
    // Lấy danh sách phụ tùng
    async getAll(params = {}) {
        const queryString = new URLSearchParams(params).toString();
        return await apiCall(`/products?${queryString}`);
    },

    // Lấy chi tiết phụ tùng
    async getById(id) {
        return await apiCall(`/products/${id}`);
    },

    // Thêm phụ tùng
    async create(productData) {
        return await apiCall('/products', {
            method: 'POST',
            body: JSON.stringify(productData)
        });
    },

    // Cập nhật phụ tùng
    async update(id, productData) {
        return await apiCall(`/products/${id}`, {
            method: 'PUT',
            body: JSON.stringify(productData)
        });
    },

    // Xóa phụ tùng
    async delete(id) {
        return await apiCall(`/products/${id}`, {
            method: 'DELETE'
        });
    }
};

// ==================== USERS API ====================
const UserAPI = {
    // Lấy danh sách người dùng
    async getAll(params = {}) {
        const queryString = new URLSearchParams(params).toString();
        return await apiCall(`/users?${queryString}`);
    },

    // Lấy chi tiết người dùng
    async getById(id) {
        return await apiCall(`/users/${id}`);
    },

    // Cập nhật người dùng
    async update(id, userData) {
        return await apiCall(`/users/${id}`, {
            method: 'PUT',
            body: JSON.stringify(userData)
        });
    },

    // Khóa/Mở khóa tài khoản
    async toggleLock(id, isLocked) {
        return await apiCall(`/users/${id}/lock`, {
            method: 'PATCH',
            body: JSON.stringify({ IsLocked: isLocked })
        });
    },

    // Xóa người dùng
    async delete(id) {
        return await apiCall(`/users/${id}`, {
            method: 'DELETE'
        });
    }
};

// ==================== ORDERS API ====================
const OrderAPI = {
    // Lấy danh sách đơn hàng
    async getAll(params = {}) {
        const queryString = new URLSearchParams(params).toString();
        return await apiCall(`/orders?${queryString}`);
    },

    // Lấy chi tiết đơn hàng
    async getById(id) {
        return await apiCall(`/orders/${id}`);
    },

    // Cập nhật đơn hàng
    async update(id, orderData) {
        return await apiCall(`/orders/${id}`, {
            method: 'PUT',
            body: JSON.stringify(orderData)
        });
    }
};

// ==================== SCHEDULES API ====================
const ScheduleAPI = {
    // Lấy danh sách lịch bảo dưỡng
    async getAll(params = {}) {
        const queryString = new URLSearchParams(params).toString();
        return await apiCall(`/schedules?${queryString}`);
    },

    // Lấy chi tiết lịch bảo dưỡng
    async getById(id) {
        return await apiCall(`/schedules/${id}`);
    },

    // Cập nhật trạng thái bảo dưỡng
    async updateStatus(id, status) {
        return await apiCall(`/schedules/${id}`, {
            method: 'PUT',
            body: JSON.stringify({ TrangThai: status })
        });
    }
};

// ==================== NOTIFICATIONS API ====================
const NotificationAPI = {
    // Lấy danh sách thông báo
    async getAll(params = {}) {
        const queryString = new URLSearchParams(params).toString();
        return await apiCall(`/notifications?${queryString}`);
    },

    // Lấy danh sách nhắc bảo dưỡng
    async getReminders() {
        return await apiCall('/notifications/reminders');
    },

    // Gửi thông báo nhắc nhở
    async sendReminder(maND) {
        return await apiCall('/notifications/reminders/send', {
            method: 'POST',
            body: JSON.stringify({ maND })
        });
    },

    // Xóa khỏi danh sách nhắc nhở
    async deleteReminder(maND) {
        return await apiCall('/notifications/reminders', {
            method: 'DELETE',
            body: JSON.stringify({ maND })
        });
    }
};

// ==================== CHATBOX API ====================
const ChatboxAPI = {
    // Lấy danh sách người dùng để chat
    async getUsers() {
        return await apiCall('/chatbox/users');
    },

    // Lấy lịch sử tin nhắn
    async getMessages(maND) {
        return await apiCall(`/chatbox/messages?maND=${maND}`);
    },

    // Gửi tin nhắn
    async sendMessage(maND, noiDung) {
        return await apiCall('/chatbox/messages', {
            method: 'POST',
            body: JSON.stringify({ maND, noiDung })
        });
    }
};

// ==================== UTILITY FUNCTIONS ====================
const Utils = {
    // Format số tiền
    formatCurrency(amount) {
        return new Intl.NumberFormat('vi-VN', {
            style: 'currency',
            currency: 'VND'
        }).format(amount);
    },

    // Format ngày tháng
    formatDate(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleDateString('vi-VN');
    },

    // Format datetime
    formatDateTime(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toLocaleString('vi-VN');
    },

    // Show alert
    showAlert(message, type = 'info') {
        alert(message);
    },

    // Show loading
    showLoading(element) {
        if (element) {
            element.innerHTML = '<div style="text-align:center;padding:20px;">Đang tải...</div>';
        }
    },

    // Navigate
    goTo(page) {
        window.location.href = page;
    }
};

// Export để sử dụng trong các file HTML
if (typeof window !== 'undefined') {
    window.AuthService = AuthService;
    window.ProductAPI = ProductAPI;
    window.UserAPI = UserAPI;
    window.OrderAPI = OrderAPI;
    window.ScheduleAPI = ScheduleAPI;
    window.NotificationAPI = NotificationAPI;
    window.ChatboxAPI = ChatboxAPI;
    window.Utils = Utils;
    window.apiCall = apiCall;
}

