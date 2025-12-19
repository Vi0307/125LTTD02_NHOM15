package com.example.quanlyoto.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quanlyoto.model.DaiLy;
import com.example.quanlyoto.repository.DaiLyRepository;

import java.util.List;

/**
 * ViewModel cho DaiLy - quản lý dữ liệu đại lý
 */
public class DaiLyViewModel extends ViewModel {

    private final DaiLyRepository repository;
    private final MutableLiveData<List<DaiLy>> daiLyList = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public DaiLyViewModel() {
        repository = new DaiLyRepository();
    }

    public LiveData<List<DaiLy>> getDaiLyList() {
        return daiLyList;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void loadDaiLy() {
        loading.setValue(true);
        repository.getAllDaiLy(daiLyList, error);
        loading.setValue(false);
    }
}
