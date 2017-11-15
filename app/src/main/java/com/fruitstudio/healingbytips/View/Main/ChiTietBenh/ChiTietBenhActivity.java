package com.fruitstudio.healingbytips.View.Main.ChiTietBenh;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fruitstudio.healingbytips.Adapter.AdapterViewPagerChiTietBenh;
import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLayChiTietBenh;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.FragmentChiTietBenh.FragmentNoiDungBenh;
import com.fruitstudio.healingbytips.View.Main.ViewChiTietBenh;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachBenh;

import java.util.List;

public class ChiTietBenhActivity extends AppCompatActivity implements View.OnClickListener{

    TabLayout tabLayoutChiTiet;
    ViewPager viewPagerChiTiet;
    public String loaibenh;
    public String mabenh;
    ImageView imBackChiTiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_benh);

        Intent intent = getIntent();
        loaibenh= intent.getStringExtra("loaibenh");
        mabenh = intent.getStringExtra("mabenh");

        Log.d("kiemtra", "onCreate: " + loaibenh + " - " + mabenh);

        viewPagerChiTiet = (ViewPager) findViewById(R.id.viewpagerchitiet);
        tabLayoutChiTiet = (TabLayout) findViewById(R.id.tablayoutchitiet);
        imBackChiTiet = (ImageView) findViewById(R.id.imBackChiTiet);

        AdapterViewPagerChiTietBenh adapterViewPagerChiTietBenh = new AdapterViewPagerChiTietBenh(getSupportFragmentManager());
        viewPagerChiTiet.setAdapter(adapterViewPagerChiTietBenh);
        adapterViewPagerChiTietBenh.notifyDataSetChanged();

        tabLayoutChiTiet.setupWithViewPager(viewPagerChiTiet);
        tabLayoutChiTiet.setTabTextColors(getIdColor(R.color.colorGray),getIdColor(R.color.colorWhile));

        imBackChiTiet.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private int getIdColor(int idcolor) {
        int color = 0;
        if (Build.VERSION.SDK_INT > 21) {
            color = ContextCompat.getColor(this, idcolor);
        } else {
            color = getResources().getColor(idcolor);
        }
        return color;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imBackChiTiet:
                finish();
                break;
        }
    }
}
