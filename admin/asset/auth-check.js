// File này sẽ được include vào tất cả các trang admin (trừ welcome.html)
// Kiểm tra authentication và redirect nếu chưa đăng nhập

(function() {
    // Chỉ chạy nếu đã load api.js
    if (typeof AuthService === 'undefined') {
        console.warn('api.js chưa được load');
        return;
    }

    // Kiểm tra authentication
    if (!AuthService.isAuthenticated()) {
        window.location.href = 'welcome.html';
    }
})();

