package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {
    public BottomNavigationView bottomNavigationView;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        bottomNavigationView = findViewById(R.id.bottom_navigationView);
        navController= Navigation.findNavController(this,R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}
