package com.example.itemsdatabasestartup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class AppStartupSplashScreen extends AppCompatActivity {

    Animation topAnimation , bottomAnimation;
    ImageView application_logo;
    private static int SPLASH_SCREEN=5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_app_startup_splash_screen);

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        application_logo =findViewById(R.id.application_logo_image);
        application_logo.setAnimation(topAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                Intent intent = new Intent(AppStartupSplashScreen.this, LoginActivity.class);
                Pair[] pair = new Pair[1];
                pair[0] = new Pair<View, String>(application_logo, "app_logo");

                ActivityOptions activityOption = ActivityOptions.makeSceneTransitionAnimation(AppStartupSplashScreen.this,pair);
                startActivity(intent,activityOption.toBundle());
                finish();
            }
        }, SPLASH_SCREEN);

    }
}
