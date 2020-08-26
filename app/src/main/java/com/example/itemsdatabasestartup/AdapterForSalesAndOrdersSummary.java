package com.example.itemsdatabasestartup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdapterForSalesAndOrdersSummary extends FragmentStateAdapter {

   // private final List<Fragment> FragmentList = new ArrayList<>();
   // private final List<String> FragmentTitleList = new ArrayList<>();


    public AdapterForSalesAndOrdersSummary(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new SalesSumary();

            case 1:
                return new OrdersListSummary();

             default:
                return new SalesSumary();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
