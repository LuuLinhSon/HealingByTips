package com.fruitstudio.healingbytips.View.BinhLuan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.fruitstudio.healingbytips.Model.Object.BinhLuanModel;
import com.fruitstudio.healingbytips.Presenter.BinhLuan.XuLyThemBinhLuan;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Reminder.ReminderActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThemBinhLuanActivity extends AppCompatActivity implements View.OnClickListener,ViewThemBinhLuan{

    ImageView imLike,imUnLike,imLike2,imUnLike2;
    FrameLayout frameLike,frameUnLike;
    Button btnDangBinhLuan;
    XuLyThemBinhLuan xuLyThemBinhLuan;
    String mabenh = "";
    EditText edTieuDeBinhLuan,edNoiDungBinhLuan;
    SharedPreferences sfDangNhap;
    String mauser;
    Toolbar toolbarBinhLuan;
    ImageView imBackBinhLuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_binh_luan);

        imLike = (ImageView) findViewById(R.id.imLikeThemBinhLuan);
        imUnLike = (ImageView) findViewById(R.id.imUnLikeThemBinhLuan);
        imLike2 = (ImageView) findViewById(R.id.imLikeThemBinhLuan2);
        imUnLike2 = (ImageView) findViewById(R.id.imUnLikeThemBinhLuan2);
        frameLike = (FrameLayout) findViewById(R.id.framelike);
        frameUnLike = (FrameLayout) findViewById(R.id.frameunlike);
        btnDangBinhLuan = (Button) findViewById(R.id.btnDangBinhLuan);
        edTieuDeBinhLuan = (EditText) findViewById(R.id.edTieuDeBinhLuan);
        edNoiDungBinhLuan = (EditText) findViewById(R.id.edNoiDungBinhLuan);
        toolbarBinhLuan = (Toolbar) findViewById(R.id.toolbarBinhLuan);
        imBackBinhLuan = (ImageView) findViewById(R.id.imBackBinhLuan);


        frameLike.setOnClickListener(this);
        frameUnLike.setOnClickListener(this);
        btnDangBinhLuan.setOnClickListener(this);
        imBackBinhLuan.setOnClickListener(this);

        xuLyThemBinhLuan = new XuLyThemBinhLuan(this);

        Intent intent = getIntent();
        mabenh = intent.getStringExtra("mabenh");

        sfDangNhap = getSharedPreferences("thongtindangnhap",MODE_PRIVATE);
        mauser = sfDangNhap.getString("userid","");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.framelike:
                imLike.setVisibility(View.GONE);
                imLike2.setVisibility(View.VISIBLE);
                imUnLike.setVisibility(View.VISIBLE);
                imUnLike2.setVisibility(View.GONE);
                break;
            case R.id.frameunlike:
                imUnLike.setVisibility(View.GONE);
                imUnLike2.setVisibility(View.VISIBLE);
                imLike.setVisibility(View.VISIBLE);
                imLike2.setVisibility(View.GONE);
                break;
            case R.id.btnDangBinhLuan:
                if(KiemTraKetNoiMang()){
                    String tieude = edTieuDeBinhLuan.getText().toString();
                    String noidungbinhluan = edNoiDungBinhLuan.getText().toString();
                    BinhLuanModel binhLuan = new BinhLuanModel();
                    binhLuan.setMauser(mauser);
                    binhLuan.setTieude(tieude);
                    binhLuan.setNoidungbinhluan(noidungbinhluan);

                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String ngaydang = formatter.format(currentTime);
                    binhLuan.setNgaydang(ngaydang);

                    if(tieude.equals("") || noidungbinhluan.equals("")){
                        Toast.makeText(ThemBinhLuanActivity.this,"Vui lòng điền đầy đủ các trường",Toast.LENGTH_LONG).show();
                    }else {
                        if(imLike2.getVisibility() == View.VISIBLE){
                            binhLuan.setThich(true);
                            xuLyThemBinhLuan.ThemBinhLuan(mabenh,binhLuan);
                        }else if(imLike.getVisibility() == View.VISIBLE && imUnLike.getVisibility() == View.VISIBLE) {
                            Toast.makeText(ThemBinhLuanActivity.this,"Bạn cần đánh giá thích hoặc không thích!",Toast.LENGTH_LONG).show();
                        }else {
                            binhLuan.setThich(false);
                            xuLyThemBinhLuan.ThemBinhLuan(mabenh,binhLuan);
                        }
                    }
                }else {
                    Toast.makeText(ThemBinhLuanActivity.this,"Vui lòng kết nối mạng",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.imBackBinhLuan:
                finish();
                break;
        }
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
    public void ThemBinhLuanThanhCong() {
        Toast.makeText(ThemBinhLuanActivity.this,"Thêm bình luận thành công",Toast.LENGTH_LONG).show();
        finish();

    }

    @Override
    public void ThemBinhLuanThatBai() {
        Toast.makeText(ThemBinhLuanActivity.this,"Không thể thêm bình luận",Toast.LENGTH_LONG).show();
        finish();
    }

    private boolean KiemTraKetNoiMang() {
        ConnectivityManager connectivityManager = (ConnectivityManager) ThemBinhLuanActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }
}
