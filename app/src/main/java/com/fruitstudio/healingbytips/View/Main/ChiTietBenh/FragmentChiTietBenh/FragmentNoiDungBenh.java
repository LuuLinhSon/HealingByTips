package com.fruitstudio.healingbytips.View.Main.ChiTietBenh.FragmentChiTietBenh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.Model.Object.LuuBenhModel;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLayChiTietBenh;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLayDanhSachLuuBenh;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLuuBenh;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.LogIn.LogInActivity;
import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.ChiTietBenhActivity;
import com.fruitstudio.healingbytips.View.Main.MainActivity;
import com.fruitstudio.healingbytips.View.Main.ViewChiTietBenh;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachLuuBenh;
import com.fruitstudio.healingbytips.View.Main.ViewLuuBenh;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 9/19/2017.
 */

public class FragmentNoiDungBenh extends Fragment implements ViewChiTietBenh, View.OnClickListener, ViewLuuBenh,ViewDanhSachLuuBenh {

    TextView tvTenBenh;
    TextView tvNoiDungBenh;
    ImageView imHinhAnh;
    Button btnLuuBenh;
    XuLyLayChiTietBenh xuLyLayChiTietBenh;
    ProgressBar progressBar;
    String loaibenh;
    String mabenh;
    XuLyLuuBenh xuLyLuuBenh;
    SharedPreferences sfDangNhap;
    SharedPreferences sfKiemTraTaiKhoanDangNhap;
    String mauser;
    XuLyLayDanhSachLuuBenh xuLyLayDanhSachLuuBenh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_noi_dung_benh, container, false);

        tvTenBenh = (TextView) view.findViewById(R.id.tvTenBenh);
        tvNoiDungBenh = (TextView) view.findViewById(R.id.tvNoiDungBenh);
        imHinhAnh = (ImageView) view.findViewById(R.id.imHinhAnh);
        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_chi_tiet_benh);
        btnLuuBenh = (Button) view.findViewById(R.id.btnLuuBenh);

        xuLyLuuBenh = new XuLyLuuBenh(this);
        xuLyLayDanhSachLuuBenh = new XuLyLayDanhSachLuuBenh(this);

        loaibenh = ((ChiTietBenhActivity) getActivity()).loaibenh;
        mabenh = ((ChiTietBenhActivity) getActivity()).mabenh;

        xuLyLayChiTietBenh = new XuLyLayChiTietBenh(this);
        xuLyLayChiTietBenh.XuLyLayChiTietBenh(loaibenh, mabenh);

        btnLuuBenh.setOnClickListener(this);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        return view;
    }

    @Override
    public void LayChiTietBenhThanhCong(BenhModel benh, Uri uri) {
        progressBar.setVisibility(View.GONE);
        tvTenBenh.setText(benh.getTenbenh());
        tvNoiDungBenh.setText(benh.getNoidung());
        Glide.with(getContext()).load(uri).into(imHinhAnh);
    }

    @Override
    public void LayChiTietBenhThatBai() {

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLuuBenh:
                sfKiemTraTaiKhoanDangNhap = getContext().getSharedPreferences("kiemtrataikhoandangnhap",MODE_PRIVATE);
                int taikhoandangnhap = sfKiemTraTaiKhoanDangNhap.getInt("taikhoandangnhap",0);
                if(taikhoandangnhap == 1){
                    sfDangNhap = getContext().getSharedPreferences("thongtindangnhap",MODE_PRIVATE);
                    mauser = sfDangNhap.getString("userid","");
                    xuLyLayDanhSachLuuBenh.XuLyLaydanhSachLuuBenh(mauser);
                }else {
                    Toast.makeText(getActivity(),"Bạn đang đăng nhập bằng tài khoản mạng xã hội nên không thể thực hiện chức năng lưu bệnh",Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void LuuBenhThanhCong() {
        Toast.makeText(getActivity(),"Đã lưu thành công",Toast.LENGTH_LONG).show();
    }

    @Override
    public void LuuBenhThatBai() {
        Toast.makeText(getActivity(),"Đã có lỗi xảy ra vui lòng thử lại",Toast.LENGTH_LONG).show();
    }

    @Override
    public void LayDanhSachLuuBenhThanhCong(List<LuuBenhModel> luuBenhModelList) {
        Boolean checkBenhDaDuocLuu = false;
        for (LuuBenhModel luuBenh : luuBenhModelList){
            if(luuBenh.getMabenh().equals(mabenh)){
                checkBenhDaDuocLuu = true;
            }
        }

        if(checkBenhDaDuocLuu){
            Toast.makeText(getActivity(),"Phương pháp này đã được lưu!",Toast.LENGTH_LONG).show();
        }else {
            new AlertDialog.Builder(getActivity())
                    .setMessage("Khi chọn lưu lại,phương pháp chữa bệnh này sẽ được lưu vào mục Cá Nhân của bạn.Bạn có muốn lưu?")
                    .setCancelable(false)
                    .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            sfDangNhap = getContext().getSharedPreferences("thongtindangnhap",MODE_PRIVATE);
                            mauser = sfDangNhap.getString("userid","");
                            LuuBenhModel luuBenh = new LuuBenhModel();
                            luuBenh.setMauser(mauser);
                            luuBenh.setMabenh(mabenh);
                            luuBenh.setMaloaibenh(loaibenh);

                            xuLyLuuBenh.LuuBenh(luuBenh);

                        }
                    })
                    .setNegativeButton("Không,Tôi không muốn", null)
                    .show();
        }
    }

    @Override
    public void LayDanhSachLuuBenhThatBai() {

    }
}
