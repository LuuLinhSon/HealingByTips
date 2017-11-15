package com.fruitstudio.healingbytips.View.Error;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Splash.SplashActivity;

public class ErrorInternetActivity extends AppCompatActivity {

    Button btnThuLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_internet);

        btnThuLai = (Button) findViewById(R.id.btnThuLai);

        btnThuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSplash = new Intent(ErrorInternetActivity.this, SplashActivity.class);
                startActivity(iSplash);
                finish();
            }
        });


    }
}
