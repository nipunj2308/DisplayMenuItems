package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

    public Toolbar toolbar;
    public TextView toolbarHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        toolbar = findViewById(R.id.toolbar4);
        toolbarHeading = toolbar.findViewById(R.id.ToolbarHeadingtextview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if(findViewById(R.id.mainFrame)!=null)
        {


          if(savedInstanceState!=null)
              return;

          getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new SettingsFragment()).commit();
         // getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new TakeOrderDetailsFragment()).commit();

        }
    }

    @Override
    public boolean onPreferenceStartFragment(PreferenceFragmentCompat caller, Preference pref) {
        return false;
    }

    public void setToolbarTitle(String title)
    {
        toolbarHeading.setText(title);
    }
}
