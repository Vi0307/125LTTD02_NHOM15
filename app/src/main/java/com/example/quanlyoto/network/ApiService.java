package com.example.quanlyoto.network;

import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.model.LoaiXe;
import com.example.quanlyoto.model.NguoiDung;
import com.example.quanlyoto.model.Xe;

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

    // ==================== XE ====================
    @GET("api/xe/nguoidung/{maND}")
    Call<ApiResponse<List<Xe>>> getXeByNguoiDung(@Path("maND") Integer maND);

    // ==================== LOẠI XE ====================
    @GET("api/loaixe/{id}")
    Call<LoaiXe> getLoaiXeById(@Path("id") String id);
}
