package com.fruitstudio.healingbytips.View.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Error.ErrorInternetActivity;
import com.fruitstudio.healingbytips.View.LogIn.LogInActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import io.fabric.sdk.android.Fabric;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends AppCompatActivity {

    ImageView imLogoApp, imLogoCompany;
    TextView tvVersion;
    LinearLayout lnLogo;
    InterstitialAd fullAds;

    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);

        fullAds = new InterstitialAd(this);
        fullAds.setAdUnitId(getResources().getString(R.string.full_banner_unit_id));
        fullAds.loadAd(new AdRequest.Builder().build());
        fullAds.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Intent iLogIn = new Intent(SplashActivity.this, LogInActivity.class);
                startActivity(iLogIn);
                finish();
            }

        });

        imLogoApp = (ImageView) findViewById(R.id.imLogoApp);
        imLogoCompany = (ImageView) findViewById(R.id.imLogoCompany);
        tvVersion = (TextView) findViewById(R.id.tvVersion);
        lnLogo = (LinearLayout) findViewById(R.id.lnLogo);


        Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animAlp = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim_alpha);
                lnLogo.setVisibility(View.VISIBLE);
                lnLogo.setAnimation(animAlp);
            }
        }, 1000);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animTran = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim_translate);
                imLogoCompany.setVisibility(View.VISIBLE);
                imLogoCompany.setAnimation(animTran);
            }
        }, 4000);

        Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (KiemTraKetNoiMang()) {
                    if (fullAds.isLoaded()) {
                        fullAds.show();
                    }
//                    Intent iLogIn = new Intent(SplashActivity.this, LogInActivity.class);
//                    startActivity(iLogIn);
                    finish();

                } else {
                    Intent iError = new Intent(SplashActivity.this, ErrorInternetActivity.class);
                    startActivity(iError);
                    finish();
                }

            }
        }, 8000);
    }

    private boolean KiemTraKetNoiMang() {
        ConnectivityManager connectivityManager = (ConnectivityManager) SplashActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }
}
