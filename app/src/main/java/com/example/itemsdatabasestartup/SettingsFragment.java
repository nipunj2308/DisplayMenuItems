package com.example.itemsdatabasestartup;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    //https://stackoverflow.com/questions/43131537/recyclerview-exception-using-preferencefragmentcompat-without-any-recyclerview

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);

       // EditTextPreference editTextPreference= findPreference(bill_display_settings);
    }


   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }


*/

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //((BaseActivity)getActivity()).setToolbarTitle("Settings");
    }
}
