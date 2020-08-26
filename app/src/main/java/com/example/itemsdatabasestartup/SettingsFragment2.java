package com.example.itemsdatabasestartup;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment2 extends Fragment {


    private NavController navController;
    private LinearLayout counterSettingsLinearLayout,SMSSettingsLinearLayout,CategoryLinearLayout,ItemSettingsLinearLayout;
    private LinearLayout PasswordSettingsLinearLayout;

    public SettingsFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_settings_fragment2, container, false);
        counterSettingsLinearLayout = view.findViewById(R.id.CounterSettingsLinearLayout);
        SMSSettingsLinearLayout = view.findViewById(R.id.SMSSettingsLinearLayout);
        CategoryLinearLayout = view.findViewById(R.id.ItemCategorySettingsLinearLayout);
        ItemSettingsLinearLayout = view.findViewById(R.id.MenuItemSettingsLinearLayout);
        PasswordSettingsLinearLayout = view.findViewById(R.id.ChangePasswordSettingsLinearLayout);


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);


        counterSettingsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
        SMSSettingsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_settingsFragment_to_billSettings);
            }
        });

        CategoryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                navController.navigate(R.id.action_settingsFragment_to_categorySettingsFragment);

            }
        });


        ItemSettingsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                navController.navigate(R.id.action_settingsFragment_to_displayMenuItemsFragment);
            }
        });


        PasswordSettingsLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.bottom_navigationView).setVisibility(View.VISIBLE);
    }
}
