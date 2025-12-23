package com.example.quanlyoto.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanlyoto.fragment.MyOrderDeliveried;
import com.example.quanlyoto.fragment.MyOrderDelivering;

public class MyOrderPagerAdapter extends FragmentStateAdapter {

    public MyOrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public MyOrderPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new MyOrderDelivering();
            case 1:
                return new MyOrderDeliveried();
            default:
                return new MyOrderDelivering();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // 2 tabs: Đang giao, Đã giao
    }
}
