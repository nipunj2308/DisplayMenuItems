package com.example.itemsdatabasestartup;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderAndsalesSummary extends Fragment {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private AdapterForSalesAndOrdersSummary adapterForSalesAndOrdersSummary;
    private TabLayoutMediator tabLayoutMediator;



    public OrderAndsalesSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_order_andsales_summary, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutForSummary);
        viewPager = view.findViewById(R.id.viewPagerForSummary);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
      //  setupViewPager(viewPager);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterForSalesAndOrdersSummary = new AdapterForSalesAndOrdersSummary(this);
        viewPager.setAdapter(adapterForSalesAndOrdersSummary);

        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setText("Sales record");
                        //tab.setIcon(R.drawable.category_icon_24dp);
                        break;

                    case 1:
                        tab.setText("Orders List");
                        //tab.setIcon(R.drawable.counter_icon_24dp);
                        break;


                }

            }
        });
        tabLayoutMediator.attach();




    }

    private void setupViewPager(ViewPager2 viewPager) {
       //adapterForSalesAndOrdersSummary = new AdapterForSalesAndOrdersSummary(this);

       // adapterForSalesAndOrdersSummary.addFragment(new SalesSumary(), "Home");
        //adapterForSalesAndOrdersSummary.addFragment(new OrdersListSummary(), "Trending");


      //  viewPager.setAdapter(adapterForSalesAndOrdersSummary);
    }
}
