package com.example.quanlyoto.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.quanlyoto.model.ApiResponse;
import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.network.ApiService;
import com.example.quanlyoto.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository - trung gian giữa ViewModel và Network
 */
public class DaiLyRepository {

    private final ApiService apiService;

    public DaiLyRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    public void getAllDaiLy(MutableLiveData<List<DaiLy>> liveData, MutableLiveData<String> errorLiveData) {
        apiService.getAllDaiLy().enqueue(new Callback<ApiResponse<List<DaiLy>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<DaiLy>>> call, Response<ApiResponse<List<DaiLy>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<List<DaiLy>> apiResponse = response.body();
                    if (apiResponse.isSuccess() && apiResponse.getData() != null) {
                        liveData.postValue(apiResponse.getData());
                    } else {
                        errorLiveData.postValue(
                                apiResponse.getError() != null ? apiResponse.getError() : "Lỗi không xác định");
                    }
                } else {
                    errorLiveData.postValue("Lỗi server: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<DaiLy>>> call, Throwable t) {
                errorLiveData.postValue("Lỗi kết nối: " + t.getMessage());
            }
        });
    }
}