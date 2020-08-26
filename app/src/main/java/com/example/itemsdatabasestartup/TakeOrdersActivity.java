package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

public class TakeOrdersActivity extends AppCompatActivity {

    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_orders);
        toolbar = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        if(findViewById(R.id.mainFrame)!=null)
        {


            if(savedInstanceState!=null)
                return;

            //getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new SettingsFragment()).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,new TakeOrderDetailsFragment()).commit();

        }
    }
}
