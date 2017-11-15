package com.fruitstudio.healingbytips.View.Main.CaNhan;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fruitstudio.healingbytips.Adapter.AdapterRecyclerBenh;
import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.Model.Object.LuuBenhModel;
import com.fruitstudio.healingbytips.Model.Object.ThanhVienModel;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLayDanhSachBenh;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLayDanhSachLuuBenh;
import com.fruitstudio.healingbytips.Presenter.UpdateUser.XuLyCapNhatThongTinThanhVien;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackThongTinThanhVien;
import com.fruitstudio.healingbytips.View.LogIn.LogInActivity;
import com.fruitstudio.healingbytips.View.Main.MainActivity;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachBenh;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachLuuBenh;
import com.fruitstudio.healingbytips.View.Popup.PopupChangeName;
import com.fruitstudio.healingbytips.View.Popup.PopupChooseAvatar;
import com.fruitstudio.healingbytips.View.Register.RegisterActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaNhanActivity extends AppCompatActivity implements View.OnClickListener,ViewCapNhatThongTinCaNhan,ViewDanhSachLuuBenh,ViewDanhSachBenh{

    CircleImageView imAvatarCaNhan;
    TextView tvNameCaNhan;
    Button btnLuuThayDoi;
    RecyclerView recyclerLuuBenh;
    ImageView imBackCaNhan;
    public static final int REQUES_CODE_AVATAR = 8888;
    public static final int REQUES_CODE_CHANGE_NAME = 9999;
    private static final int RESQUEST_CHOOSE_PHOTO = 321;
    XuLyCapNhatThongTinThanhVien xuLyCapNhatThongTinThanhVien;
    SharedPreferences  sfThongTinDangNhap;
    String userId = "";
    Uri imageUri = null;
    ProgressDialog progressDialog;
    XuLyLayDanhSachLuuBenh xuLyLayDanhSachLuuBenh;
    XuLyLayDanhSachBenh xuLyLayDanhSachBenh;
    SharedPreferences sfDangNhap;
    String mauser;
    String avatar;
    String duongdan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ca_nhan);

        sfThongTinDangNhap = getSharedPreferences("thongtindangnhap",MODE_PRIVATE);
        userId = sfThongTinDangNhap.getString("userid","");

        imAvatarCaNhan = (CircleImageView) findViewById(R.id.imAvatarCaNhan);
        tvNameCaNhan = (TextView) findViewById(R.id.tvNameCaNhan);
        btnLuuThayDoi = (Button) findViewById(R.id.btnLuuThayDoi);
        recyclerLuuBenh = (RecyclerView) findViewById(R.id.recyclerLuuBenh);
        imBackCaNhan = (ImageView) findViewById(R.id.imBackCaNhan);

        Intent intent = getIntent();
        avatar = intent.getStringExtra("avatar");
        duongdan = intent.getStringExtra("duongdan");
        Log.d("imageavatar" ,"onCreate: " + avatar);
        String name = intent.getStringExtra("name");

        if (avatar == null || avatar.equals("")) {
            imAvatarCaNhan.setImageDrawable(getIdDrawable(R.drawable.user));
        } else {
            Glide.with(CaNhanActivity.this).load(avatar).into(imAvatarCaNhan);
        }

        tvNameCaNhan.setText(name);

        tvNameCaNhan.setOnClickListener(this);
        imAvatarCaNhan.setOnClickListener(this);
        btnLuuThayDoi.setOnClickListener(this);
        imBackCaNhan.setOnClickListener(this);

        int checkReadExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (checkReadExternalStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {

        }

        sfDangNhap = getSharedPreferences("thongtindangnhap",MODE_PRIVATE);
        mauser = sfDangNhap.getString("userid","");
        xuLyCapNhatThongTinThanhVien = new XuLyCapNhatThongTinThanhVien(this);
        xuLyLayDanhSachLuuBenh = new XuLyLayDanhSachLuuBenh(this);
        xuLyLayDanhSachLuuBenh.XuLyLaydanhSachLuuBenh(mauser);
        xuLyLayDanhSachBenh = new XuLyLayDanhSachBenh(this);
        progressDialog = new ProgressDialog(this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.imAvatarCaNhan:
                choosePicture();
                break;
            case R.id.tvNameCaNhan:
                Intent iPopupChangeName = new Intent(CaNhanActivity.this, PopupChangeName.class);
                startActivityForResult(iPopupChangeName, REQUES_CODE_CHANGE_NAME);
                break;
            case R.id.btnLuuThayDoi:
                if(KiemTraKetNoiMang()){
                    progressDialog.setMessage("Vui lòng đợi.....");
                    progressDialog.setIndeterminate(true);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    ThanhVienModel thanhVienModel = new ThanhVienModel();
                    thanhVienModel.setHoten(tvNameCaNhan.getText().toString());

                    if(imageUri != null){
                        Uri uri = Uri.fromFile(new File(getRealPathFromDocumentUri(CaNhanActivity.this,imageUri)));
                        thanhVienModel.setHinhanh(uri.getLastPathSegment());
                        xuLyCapNhatThongTinThanhVien.XuLyCapNhatThongTinThanhVien(userId,thanhVienModel,getRealPathFromDocumentUri(CaNhanActivity.this,imageUri));
                    }else {
                        thanhVienModel.setHinhanh(duongdan);
                        xuLyCapNhatThongTinThanhVien.CapNhatThongTinNhungKhongSuaAnh(userId,thanhVienModel);
                    }

                }else {
                    Toast.makeText(CaNhanActivity.this,"Vui lòng kết nối mạng",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.imBackCaNhan:
//                Intent iLogIn = new Intent(CaNhanActivity.this,MainActivity.class);
//                startActivity(iLogIn);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == RESQUEST_CHOOSE_PHOTO) {
                try {
                    imageUri = data.getData();
                    Log.d("imageuri", "onActivityResult: " + imageUri);
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Log.d("bitmap", "onActivityResult: " + bitmap.toString());
                    imAvatarCaNhan.setImageBitmap(bitmap);
                    btnLuuThayDoi.setVisibility(View.VISIBLE);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else if (requestCode == REQUES_CODE_CHANGE_NAME){
                Intent intent = data;
                String name = intent.getStringExtra("name");
                tvNameCaNhan.setText(name);
                btnLuuThayDoi.setVisibility(View.VISIBLE);
            }
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

    private void choosePicture() {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESQUEST_CHOOSE_PHOTO);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, RESQUEST_CHOOSE_PHOTO);
        }
    }

    @Override
    public void CapNhatThanhVienThanhCong() {
        progressDialog.dismiss();
        Intent iLogin = new Intent(CaNhanActivity.this, LogInActivity.class);
        iLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iLogin);
        finish();
    }

    @Override
    public void CapNhatThanhVienThatBai() {
        progressDialog.dismiss();
        Toast.makeText(CaNhanActivity.this,"Đã có lỗi xảy ra trong quá trình cập nhật tài khoản.Vui lòng thử lại",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void CapNhatThanhVienNhungKhongSuaAnh() {
        progressDialog.dismiss();
        Intent iLogin = new Intent(CaNhanActivity.this, LogInActivity.class);
        iLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(iLogin);
        finish();
    }

    public static String getRealPathFromDocumentUri(Context context, Uri uri){
        String filePath = "";

        Pattern p = Pattern.compile("(\\d+)$");
        Matcher m = p.matcher(uri.toString());
        if (!m.find()) {
            return filePath;
        }
        String imgId = m.group();

        String[] column = { MediaStore.Images.Media.DATA };
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ imgId }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();

        return filePath;
    }

    @Override
    public void LayDanhSachLuuBenhThanhCong(List<LuuBenhModel> luuBenhModelList) {
        Log.d("list", "LayDanhSachLuuBenhThanhCong: " + luuBenhModelList.size() + "");
        xuLyLayDanhSachBenh.LayDanhSachBenhTheoDanhSachLuuBenh(luuBenhModelList);

    }

    @Override
    public void LayDanhSachLuuBenhThatBai() {

    }

    @Override
    public void LayDanhSachBenhThanhCong(List<BenhModel> benhModelList) {

    }

    @Override
    public void LayDanhsachBenhThatBai() {

    }

    @Override
    public void LayDanhSachBenhTheoDanhSachLuuBenhThanhCong(List<BenhModel> benhModelList) {
        Log.d("list2", "LayDanhSachLuuBenhThanhCong: " + benhModelList.size() + "");
        AdapterRecyclerBenh adapterRecyclerBenh = new AdapterRecyclerBenh(CaNhanActivity.this,R.layout.layout_custom_benh,benhModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(CaNhanActivity.this);
        recyclerLuuBenh.setLayoutManager(layoutManager);
        recyclerLuuBenh.setAdapter(adapterRecyclerBenh);
        adapterRecyclerBenh.notifyDataSetChanged();
    }

    private boolean KiemTraKetNoiMang() {
        ConnectivityManager connectivityManager = (ConnectivityManager) CaNhanActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }

    }
}
