package com.fruitstudio.healingbytips.View.Main.ChiTietBenh.FragmentChiTietBenh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fruitstudio.healingbytips.Adapter.AdapterRecyclerBinhLuan;
import com.fruitstudio.healingbytips.Model.Object.BinhLuanModel;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLayDanhSachBinhLuan;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.ChiTietBenhActivity;
import com.fruitstudio.healingbytips.View.BinhLuan.ThemBinhLuanActivity;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachBinhLuan;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 9/19/2017.
 */

public class FragmentBinhLuan extends Fragment implements ViewDanhSachBinhLuan{

    RecyclerView recyclerBinhLuan;
    XuLyLayDanhSachBinhLuan xuLyLayDanhSachBinhLuan;
    String mabenh;
    TextView tvSoLuotThich,tvSoLuotKhongThich,tvTongBinhLuan;
    Button btnBinhLuan;
    int indexLike = 0;
    int indexUnLike = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_binh_luan_benh,container,false);

        mabenh = ((ChiTietBenhActivity)getActivity()).mabenh;

        recyclerBinhLuan = (RecyclerView) view.findViewById(R.id.recyclerBinhLuan);
        tvSoLuotThich = (TextView) view.findViewById(R.id.tvSoLuotThich);
        tvSoLuotKhongThich = (TextView) view.findViewById(R.id.tvSoLuotKhongThich);
        tvTongBinhLuan = (TextView) view.findViewById(R.id.tvSoLuotBinhLuan);
        btnBinhLuan = (Button) view.findViewById(R.id.btnBinhLuan);

        xuLyLayDanhSachBinhLuan = new XuLyLayDanhSachBinhLuan(this);
        xuLyLayDanhSachBinhLuan.LayDanhSachBinhLuan(mabenh);

        btnBinhLuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sfKiemTraTaiKhoanDangNhap;
                sfKiemTraTaiKhoanDangNhap = getContext().getSharedPreferences("kiemtrataikhoandangnhap",MODE_PRIVATE);
                int taikhoandangnhap = sfKiemTraTaiKhoanDangNhap.getInt("taikhoandangnhap",0);
                if(taikhoandangnhap == 1){
                    Intent iThemBinhLuan = new Intent(getContext(), ThemBinhLuanActivity.class);
                    iThemBinhLuan.putExtra("mabenh",mabenh);
                    startActivity(iThemBinhLuan);
                }else {
                    Toast.makeText(getContext(),"Bạn đang đăng nhập bằng tài khoản mạng xã hội nên không thể thực hiện chức năng bình luận",Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    @Override
    public void LayDanhSachBinhLuanThanhCong(List<BinhLuanModel> binhLuanModelList) {
        indexLike = 0;
        indexUnLike = 0;
        for(BinhLuanModel binhLuan : binhLuanModelList){
            if(binhLuan.isThich()){
                indexLike++;
            }else {
                indexUnLike++;
            }
        }

        tvSoLuotThich.setText(String.valueOf(indexLike));
        tvSoLuotKhongThich.setText(String.valueOf(indexUnLike));
        tvTongBinhLuan.setText(String.valueOf(binhLuanModelList.size()));

        Log.d("size", "LayDanhSachBinhLuanThanhCong: " + binhLuanModelList.size() + "");
        AdapterRecyclerBinhLuan adapterRecyclerBinhLuan = new AdapterRecyclerBinhLuan(getContext(),R.layout.custom_recycler_binhluan,binhLuanModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerBinhLuan.setLayoutManager(layoutManager);
        recyclerBinhLuan.setAdapter(adapterRecyclerBinhLuan);
        adapterRecyclerBinhLuan.notifyDataSetChanged();
    }

    @Override
    public void LayDanhSachBinhLuanThatBai() {
        Toast.makeText(getContext(),"Đã có lỗi xảy ra",Toast.LENGTH_LONG).show();

    }
}
