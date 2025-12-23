package com.example.quanlyoto.network;

import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.BaoDuong;
import com.example.quanlyoto.model.ChiTietLichSuBaoDuong;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.model.DiaChi;
import com.example.quanlyoto.model.DmPhuTung;
import com.example.quanlyoto.model.LichSuBaoDuong;
import com.example.quanlyoto.model.LoaiXe;
import com.example.quanlyoto.model.NguoiDung;
import com.example.quanlyoto.model.PhuongThucVanChuyen;
import com.example.quanlyoto.model.RewardRequest;
import com.example.quanlyoto.model.Voucher;
import com.example.quanlyoto.model.Xe;
import com.example.quanlyoto.model.Xe;
import com.example.quanlyoto.model.PhuTung;
import com.example.quanlyoto.model.DonHang;
import com.example.quanlyoto.model.ChiTietDonHang;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Retrofit API Service - định nghĩa các endpoint
 */
public interface ApiService {

        // ==================== ĐẠI LÝ ====================
        @GET("api/daily")
        Call<ApiResponse<List<DaiLy>>> getAllDaiLy();

        @GET("api/daily/{id}")
        Call<ApiResponse<DaiLy>> getDaiLyById(@Path("id") Integer id);

        // ==================== DANH MỤC PHỤ TÙNG ====================
        @GET("api/danhmuc-phutung")
        Call<List<DmPhuTung>> getAllDanhMuc();

        // ==================== NGƯỜI DÙNG ====================
        @POST("api/nguoidung/login")
        Call<NguoiDung> login(@Body com.example.quanlyoto.model.LoginRequest request);

        @GET("api/nguoidung/{id}")
        Call<NguoiDung> getNguoiDungById(@Path("id") Integer id);

        @retrofit2.http.PUT("api/nguoidung/{id}/update-maintenance-count")
        Call<NguoiDung> updateMaintenanceCount(@Path("id") Integer id, @retrofit2.http.Query("count") Integer count);

        // ==================== VOUCHER ====================
        @GET("api/voucher/{maND}")
        Call<List<Voucher>> getVoucherByUser(@Path("maND") Integer maND);

        @GET("api/voucher")
        Call<List<Voucher>> getAllVouchers();

        @POST("api/voucher/reward")
        Call<ApiResponse<String>> rewardVoucher(@Body RewardRequest request);

        // ==================== ĐỊA CHỈ ====================
        @GET("api/address/{maND}")
        Call<List<DiaChi>> getDiaChiByUser(@Path("maND") Integer maND);

        @GET("api/address/{maND}/default")
        Call<DiaChi> getDiaChiMacDinh(@Path("maND") Integer maND);

        @POST("api/address/{maND}")
        Call<DiaChi> addDiaChi(@Path("maND") Integer maND, @Body DiaChi diaChi);

        // ==================== PHƯƠNG THỨC VẬN CHUYỂN ====================
        @GET("api/shipping")
        Call<List<PhuongThucVanChuyen>> getAllShipping();

        // ==================== XE ====================
        @GET("api/xe/nguoidung/{maND}")
        Call<ApiResponse<List<Xe>>> getXeByNguoiDung(@Path("maND") Integer maND);

        // ==================== LOẠI XE ====================
        @GET("api/loaixe")
        Call<List<LoaiXe>> getAllLoaiXe();

        @GET("api/loaixe/{id}")
        Call<LoaiXe> getLoaiXeById(@Path("id") String id);

        // ==================== PHỤ TÙNG ====================
        @GET("api/phutung")
        Call<List<PhuTung>> getAllPhuTung();

        @GET("api/phutung/danhmuc/{maDanhMuc}")
        Call<List<PhuTung>> getPhuTungByDanhMuc(@Path("maDanhMuc") String maDanhMuc);

        // ==================== BẢO DƯỠNG ====================
        @GET("api/bao-duong/nguoi-dung/{maND}")
        Call<ApiResponse<List<BaoDuong>>> getBaoDuongByNguoiDung(@Path("maND") Integer maND);

        // ==================== LỊCH SỬ BẢO DƯỠNG ====================
        @GET("api/lich-su-bao-duong/nguoi-dung/{maND}")
        Call<ApiResponse<List<LichSuBaoDuong>>> getLichSuBaoDuongByNguoiDung(@Path("maND") Integer maND);

        @GET("api/lich-su-bao-duong/{maLSBD}/chi-tiet")
        Call<ApiResponse<List<ChiTietLichSuBaoDuong>>> getChiTietLichSuBaoDuong(@Path("maLSBD") Integer maLSBD);

        // ==================== DỊCH VỤ ====================
        @POST("api/dich-vu")
        Call<ApiResponse<com.example.quanlyoto.model.DichVuDTO>> createDichVu(
                        @Body com.example.quanlyoto.model.DichVuDTO dichVuDTO);

        @GET("api/dich-vu/nguoi-dung/{maND}")
        Call<ApiResponse<List<com.example.quanlyoto.model.DichVuDTO>>> getDichVuByNguoiDung(@Path("maND") Integer maND);

        // ==================== GIỎ HÀNG ====================
        @POST("api/chi-tiet-gio-hang/nguoi-dung/{maND}")
        Call<ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> themVaoGioHang(
                        @Path("maND") Integer maND,
                        @Body com.example.quanlyoto.model.ThemVaoGioHangRequest request);

        @GET("api/chi-tiet-gio-hang/nguoi-dung/{maND}")
        Call<ApiResponse<List<com.example.quanlyoto.model.ChiTietGioHangDTO>>> getGioHangByNguoiDung(
                        @Path("maND") Integer maND);

        @retrofit2.http.DELETE("api/chi-tiet-gio-hang/{maCTGH}")
        Call<ApiResponse<Void>> xoaKhoiGioHang(@Path("maCTGH") Integer maCTGH);

        @retrofit2.http.PUT("api/chi-tiet-gio-hang/{maCTGH}/so-luong")
        Call<ApiResponse<com.example.quanlyoto.model.ChiTietGioHangDTO>> capNhatSoLuong(
                        @Path("maCTGH") Integer maCTGH,
                        @retrofit2.http.Query("soLuong") Integer soLuong);

        @GET("api/chi-tiet-gio-hang/nguoi-dung/{maND}/tong-tien")
        Call<ApiResponse<java.math.BigDecimal>> getTongTienGioHang(@Path("maND") Integer maND);

        // ==================== ĐƠN HÀNG ====================
        @GET("api/donhang/{maDH}")
        Call<ApiResponse<DonHang>> getDonHangById(@Path("maDH") String maDH);

        @GET("api/donhang/{maDH}/chitiet")
        Call<ApiResponse<List<ChiTietDonHang>>> getChiTietDonHangByMaDH(@Path("maDH") String maDH);

        // ==================== PHƯƠNG THỨC THANH TOÁN ====================
        @GET("api/payment-methods")
        Call<List<com.example.quanlyoto.model.PhuongThucThanhToan>> getAllPaymentMethods();

        @GET("api/payment-methods/{id}")
        Call<com.example.quanlyoto.model.PhuongThucThanhToan> getPaymentMethodById(@Path("id") Integer id);

        @GET("api/payment-methods/default")
        Call<com.example.quanlyoto.model.PhuongThucThanhToan> getDefaultPaymentMethod();

        // ==================== ĐƠN HÀNG ====================
        @retrofit2.http.POST("api/orders")
        Call<com.example.quanlyoto.model.DonHangResponse> createOrder(
                        @Body com.example.quanlyoto.model.DonHangRequest request);

        // ==================== THÔNG BÁO ====================
        @GET("api/thong-bao/nguoi-dung/{maND}")
        Call<ApiResponse<List<com.example.quanlyoto.model.ThongBao>>> getThongBaoByMaND(@Path("maND") Integer maND);
}
