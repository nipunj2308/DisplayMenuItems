package com.example.itemsdatabasestartup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class TrackOrdersSMSActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private AdapterForTrackOrdersAndInvoiceStatus adapterForTrackOrdersAndInvoiceStatus;
    private TabLayoutMediator tabLayoutMediator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_orders_sms);
    }
}// viewPager = findViewById(R.id.viewPagerForTrackActivity);
        //tabLayout = findViewById(R.id.tabLayoutForTrackActivity);

        /*adapterForTrackOrdersAndInvoiceStatus = new AdapterForTrackOrdersAndInvoiceStatus(this);
        viewPager.setAdapter(adapterForTrackOrdersAndInvoiceStatus);
        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position)
                {
                    case 0:
                        tab.setText("Order Status");
                        *//*tab.setIcon(R.drawable.category_icon_24dp);
                        BadgeDrawable badgeDrawable = tab.getOrCreateBadge();
                        badgeDrawable.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorRed));
                        badgeDrawable.setVisible(true);
                        badgeDrawable.setNumber(100);*//*
                        break;

                   *//* case 1:
                        tab.setText("Invoice Status");
                        //tab.setIcon(R.drawable.counter_icon_24dp);
                        break;*//*


                }

            }
        });
        tabLayoutMediator.attach();*/

