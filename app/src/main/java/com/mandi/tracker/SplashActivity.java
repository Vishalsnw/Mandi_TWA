package com.mandi.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    
    private static final int SPLASH_DURATION = 2500;
    private Handler handler;
    private Runnable splashRunnable;
    private boolean launched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splash_logo);
        
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeIn);

        handler = new Handler(Looper.getMainLooper());
        splashRunnable = new Runnable() {
            @Override
            public void run() {
                if (!launched) {
                    launched = true;
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!launched && handler != null && splashRunnable != null) {
            handler.postDelayed(splashRunnable, SPLASH_DURATION);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null && splashRunnable != null) {
            handler.removeCallbacks(splashRunnable);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && splashRunnable != null) {
            handler.removeCallbacks(splashRunnable);
        }
    }
}
