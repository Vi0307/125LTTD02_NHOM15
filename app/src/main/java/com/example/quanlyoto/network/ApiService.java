package com.example.quanlyoto.network;

import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.model.DiaChi;
import com.example.quanlyoto.model.NguoiDung;
import com.example.quanlyoto.model.PhuongThucVanChuyen;
import com.example.quanlyoto.model.Voucher;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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

    // ==================== NGƯỜI DÙNG ====================
    @GET("api/nguoidung/{id}")
    Call<NguoiDung> getNguoiDungById(@Path("id") Integer id);

    // ==================== ĐỊA CHỈ ====================
    @GET("api/address/{maND}")
    Call<List<DiaChi>> getDiaChiByUser(@Path("maND") Integer maND);

    @GET("api/address/{maND}/default")
    Call<DiaChi> getDiaChiMacDinh(@Path("maND") Integer maND);

    // ==================== PHƯƠNG THỨC VẬN CHUYỂN ====================
    @GET("api/shipping")
    Call<List<PhuongThucVanChuyen>> getAllShipping();

    // ==================== VOUCHER ====================
    @GET("api/voucher/{maND}")
    Call<List<Voucher>> getVoucherByUser(@Path("maND") Integer maND);

    @GET("api/voucher")
    Call<List<Voucher>> getAllVouchers();
}

