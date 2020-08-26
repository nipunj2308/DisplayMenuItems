package com.example.itemsdatabasestartup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterForTrackOrdersAndInvoiceStatus extends FragmentStateAdapter {



    public AdapterForTrackOrdersAndInvoiceStatus(@NonNull Fragment fragment) {
        super(fragment);
    }

    public AdapterForTrackOrdersAndInvoiceStatus(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                return new TrackOrders();

           /* case 1:
                return new TrackInvoiceSMS();*/

            default:
                return new TrackOrders();

        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
