package com.fruitstudio.healingbytips.View.Main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fruitstudio.healingbytips.Adapter.AdapterViewPager;
import com.fruitstudio.healingbytips.Model.Object.AppRater;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.LogIn.LogInActivity;
import com.fruitstudio.healingbytips.View.Main.CaNhan.CaNhanActivity;
import com.fruitstudio.healingbytips.View.TimKiem.TimKiemActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.auth.FirebaseAuth;
import com.kobakei.ratethisapp.RateThisApp;
import com.rom4ek.arcnavigationview.ArcNavigationView;

import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    CoordinatorTabLayout coordinatorTabLayout;
    ViewPager viewPager;
    ActionBarDrawerToggle drawerToggle;
    int[] mColorArray, mImageArray;
    final int ALPHA2 = (int) (255 * .60f);
    ArcNavigationView arcNavigationView;
    ImageView imHeaderAvatar;
    TextView tvHeaderName;
    FirebaseAuth firebaseAuth;
    String avatar = "";
    int kiemtrataikhoandangnhap;
    String duongdan = "";
    SharedPreferences sfKiemTraTaiKhoanDangNhap;
    SharedPreferences sfLuuTrangThaiTrangChu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RateThisApp.onCreate(this);

        firebaseAuth = FirebaseAuth.getInstance();

        //Ánh xạ giao diện
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        coordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        arcNavigationView = (ArcNavigationView) findViewById(R.id.nav_view);
        View view = arcNavigationView.inflateHeaderView(R.layout.layout_header_navigation);
        imHeaderAvatar = (ImageView) view.findViewById(R.id.imHeaderAvatar);
        tvHeaderName = (TextView) view.findViewById(R.id.tvHeaderName);

        //Khởi tạo drawerlayout navigation ,xử lý nút home
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        drawerToggle.syncState();

        // Xét adater cho tablayout
        AdapterViewPager adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        adapterViewPager.notifyDataSetChanged();

        //Xét sự kiện click
        arcNavigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String tendangnhap = intent.getStringExtra("tendangnhap");
        String tendangnhapfacebook = intent.getStringExtra("tendangnhapfacebook");
        avatar = intent.getStringExtra("avatar");
        duongdan = intent.getStringExtra("duongdan");
        kiemtrataikhoandangnhap = intent.getIntExtra("kiemtrataikhoandangnhap", 0);
        Log.d("avatar", "onCreate: " + tendangnhap + " - " + tendangnhapfacebook);

        sfLuuTrangThaiTrangChu = getSharedPreferences("trangthaitrangchu",MODE_PRIVATE);
        SharedPreferences.Editor edTrangThaiTrangChu = sfLuuTrangThaiTrangChu.edit();
        edTrangThaiTrangChu.putString("tendangnhap",tendangnhap);
        edTrangThaiTrangChu.putString("tendangnhapfacebook",tendangnhapfacebook);
        edTrangThaiTrangChu.putString("avatar",avatar);
        edTrangThaiTrangChu.putString("duongdan",duongdan);
        edTrangThaiTrangChu.commit();


//        if (avatar == null || avatar.equals("")) {
//            imHeaderAvatar.setImageDrawable(getIdDrawable(R.drawable.user));
//        } else {
//            Glide.with(MainActivity.this).load(avatar).into(imHeaderAvatar);
//        }
//
//        if (tendangnhap == null || tendangnhap.equals("")) {
//            tvHeaderName.setText(tendangnhapfacebook);
//        } else {
//            tvHeaderName.setText(tendangnhap);
//        }

        if (sfLuuTrangThaiTrangChu.getString("avatar","").equals("")) {
            imHeaderAvatar.setImageDrawable(getIdDrawable(R.drawable.user));
        } else {
            Glide.with(MainActivity.this).load(sfLuuTrangThaiTrangChu.getString("avatar","")).into(imHeaderAvatar);
        }

        if (sfLuuTrangThaiTrangChu.getString("tendangnhap","").equals("")) {
            tvHeaderName.setText(sfLuuTrangThaiTrangChu.getString("tendangnhapfacebook",""));
        } else {
            tvHeaderName.setText(sfLuuTrangThaiTrangChu.getString("tendangnhap",""));
        }

        sfKiemTraTaiKhoanDangNhap = getSharedPreferences("kiemtrataikhoandangnhap",MODE_PRIVATE);
        SharedPreferences.Editor edCheckTaiKhoan = sfKiemTraTaiKhoanDangNhap.edit();
        edCheckTaiKhoan.putInt("taikhoandangnhap",kiemtrataikhoandangnhap);
        edCheckTaiKhoan.commit();

        khoiTaoCoordinatorTabLayout();

    }

    public void khoiTaoCoordinatorTabLayout() {
        mImageArray = new int[]{
                R.drawable.thankinh,
                R.drawable.hohap,
                R.drawable.tieuhoa,
                R.drawable.timmach,
                R.drawable.coxuongkhop,
                R.drawable.sinhduc,
                R.drawable.ranghammat,
                R.drawable.taimuihong,
//                R.drawable.mat,
                R.drawable.babau,
                R.drawable.tresosinh,
//                R.drawable.khac
        };

        mColorArray = new int[]{
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary,
//                R.color.colorPrimary,
                R.color.colorPrimary


//                android.R.color.holo_blue_light,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_green_light,
//                android.R.color.holo_blue_light,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_green_light,
//                android.R.color.holo_blue_light,
//                android.R.color.holo_red_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_green_light
        };

        coordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        coordinatorTabLayout.getTabLayout().setTabTextColors(getIdColor(R.color.colorGray), getIdColor(R.color.colorWhile));
        coordinatorTabLayout.getTabLayout().setTabMode(View.OVER_SCROLL_ALWAYS); // để các tab dàn đều và scoll được
        coordinatorTabLayout.getImageView().setScaleType(ImageView.ScaleType.FIT_XY);
        coordinatorTabLayout.getImageView().setImageAlpha(ALPHA2);
        coordinatorTabLayout.setBackEnable(false); // Có để xuất hiện nút back hay không
        coordinatorTabLayout.setTitle("")
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app_bar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.itsearch:
                Intent iSearch = new Intent(MainActivity.this, TimKiemActivity.class);
                startActivity(iSearch);
                break;
        }

        if (drawerToggle.onOptionsItemSelected(item)) { // code này để khi ấn vào menu home mới hiện ra menu đa cấp
            return true;
        }
        return true;
    }

    private Drawable getIdDrawable(int idDrawable) {

        Drawable drawable;
        if (Build.VERSION.SDK_INT > 21) {
            drawable = ContextCompat.getDrawable(this, idDrawable);
        } else {
            drawable = getResources().getDrawable(idDrawable);
        }

        return drawable;

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
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.canhan:
                if (kiemtrataikhoandangnhap == 1) {
                    Intent iCaNhan = new Intent(MainActivity.this, CaNhanActivity.class);
                    iCaNhan.putExtra("avatar", avatar);
                    iCaNhan.putExtra("duongdan",duongdan);
                    iCaNhan.putExtra("name", tvHeaderName.getText());
                    startActivity(iCaNhan);
//                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Bạn đang đăng nhập bằng tài khoản mạng xã hội nên không thể sử dụng chức năng này!", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.danhgia:
                RateThisApp.showRateDialog(this);

                break;
            case R.id.caidat:
                Toast.makeText(MainActivity.this, "Cài đặt", Toast.LENGTH_LONG).show();
                break;
            case R.id.dangxuat:
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Bạn có muốn đăng xuất?")
                        .setCancelable(false)
                        .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SharedPreferences sfDangNhap = getSharedPreferences("thongtindangnhap", Context.MODE_PRIVATE);
                                sfDangNhap.edit().clear().commit();
                                firebaseAuth.signOut();
                                Intent iLogIn = new Intent(MainActivity.this, LogInActivity.class);
                                startActivity(iLogIn);
                                finish();
                            }
                        })
                        .setNegativeButton("Không,Tôi không muốn", null)
                        .show();
                break;
        }
        return false;
    }


}
