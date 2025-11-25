package com.mandi.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SplashActivity extends AppCompatActivity {
    
    private static final int SPLASH_DURATION = 2500;
    private static final String TAG = "SplashActivity";
    private Handler handler;
    private Runnable splashRunnable;
    private boolean launched = false;
    private InterstitialAd interstitialAd;
    private boolean isAdLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splash_logo);
        
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeIn);

        MobileAds.initialize(this, initializationStatus -> {
            Log.d(TAG, "AdMob initialized");
            loadInterstitialAd();
        });

        handler = new Handler(Looper.getMainLooper());
        splashRunnable = new Runnable() {
            @Override
            public void run() {
                if (!launched) {
                    launched = true;
                    showInterstitialAndProceed();
                }
            }
        };
    }

    private void loadInterstitialAd() {
        if (isAdLoading || interstitialAd != null) {
            return;
        }

        isAdLoading = true;
        AdRequest adRequest = new AdRequest.Builder().build();
        
        InterstitialAd.load(
            this,
            "ca-app-pub-5538218540896625/7579365861",
            adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd ad) {
                    Log.d(TAG, "Interstitial ad loaded");
                    interstitialAd = ad;
                    isAdLoading = false;
                    setAdCallbacks();
                }
                
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Log.d(TAG, "Interstitial ad failed to load: " + loadAdError.getMessage());
                    interstitialAd = null;
                    isAdLoading = false;
                }
            }
        );
    }

    private void setAdCallbacks() {
        if (interstitialAd == null) {
            return;
        }

        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdDismissedFullScreenContent() {
                Log.d(TAG, "Interstitial ad dismissed");
                interstitialAd = null;
                proceedToMainActivity();
            }
            
            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                Log.d(TAG, "Interstitial ad failed to show: " + adError.getMessage());
                interstitialAd = null;
                proceedToMainActivity();
            }
            
            @Override
            public void onAdShowedFullScreenContent() {
                Log.d(TAG, "Interstitial ad showed");
            }
        });
    }

    private void showInterstitialAndProceed() {
        if (interstitialAd != null) {
            interstitialAd.show(this);
        } else {
            Log.d(TAG, "Interstitial ad not ready, proceeding to main activity");
            proceedToMainActivity();
        }
    }

    private void proceedToMainActivity() {
        if (!launched) {
            return;
        }
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
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
