package com.example.quanlyoto.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit Client Singleton
 * 
 * LƯU Ý QUAN TRỌNG về BASE_URL:
 * - Emulator Android: dùng 10.0.2.2 (trỏ về localhost máy tính)
 * - Device thật: dùng IP máy tính (ví dụ: 192.168.1.x)
 * - Genymotion: dùng 10.0.3.2
 */
public class RetrofitClient {

    // ⚠️ THAY ĐỔI IP NÀY PHÙ HỢP VỚI MÔI TRƯỜNG CỦA BẠN
    // Emulator: 10.0.2.2 | Device: IP máy tính
    private static final String BASE_URL = "http://10.0.2.2:8080/";

    private static Retrofit retrofit = null;
    private static ApiService apiService = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // Helper method để lấy ApiService dễ dàng hơn
    public static ApiService getApiService() {
        if (apiService == null) {
            apiService = getClient().create(ApiService.class);
        }
        return apiService;
    }
}
