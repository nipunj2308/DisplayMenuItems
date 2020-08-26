package com.example.itemsdatabasestartup;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrackOrdersAndSMS extends Fragment {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private AdapterForTrackOrdersAndInvoiceStatus adapterForTrackOrdersAndInvoiceStatus;
    private TabLayoutMediator tabLayoutMediator;


    public TrackOrdersAndSMS()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_track_orders_and_sm, container, false);
        tabLayout = view.findViewById(R.id.tabLayoutForTrackFragment);
        viewPager = view.findViewById(R.id.viewPagerForTrackFragment);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapterForTrackOrdersAndInvoiceStatus = new AdapterForTrackOrdersAndInvoiceStatus(this);
        viewPager.setAdapter(adapterForTrackOrdersAndInvoiceStatus);
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setText("Order Status");
                        /*tab.setIcon(R.drawable.category_icon_24dp);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorRed));
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(100);*/
                        break;

                    case 1:
                        tab.setText("Invoice Status");
                        //tab.setIcon(R.drawable.counter_icon_24dp);
                        break;


                }

            }
        });
        tabLayoutMediator.attach();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
