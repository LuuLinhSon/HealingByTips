package com.fruitstudio.healingbytips.View.Main.FragmentMain;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fruitstudio.healingbytips.Adapter.AdapterRecyclerBenh;
import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.Presenter.Main.XuLyLayDanhSachBenh;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachBenh;

import java.util.List;

/**
 * Created by Admin on 8/27/2017.
 */

public class FragmentCoXuongKhop extends Fragment implements ViewDanhSachBenh{

    RecyclerView recyclerCoXuongKhop;
    XuLyLayDanhSachBenh xuLyLayDanhSachBenh;
    ProgressBar progressBarCoXuongKhop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_co_xuong_khop,container,false);

        recyclerCoXuongKhop = (RecyclerView) view.findViewById(R.id.recyclerCoXuongKhop);
        progressBarCoXuongKhop = (ProgressBar) view.findViewById(R.id.progressBarCoXuongKhop);
        progressBarCoXuongKhop.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        xuLyLayDanhSachBenh = new XuLyLayDanhSachBenh(this);
        xuLyLayDanhSachBenh.LayDanhSachBenh("coxuongkhops");

        return view;
    }

    @Override
    public void LayDanhSachBenhThanhCong(List<BenhModel> benhModelList) {
        AdapterRecyclerBenh adapterRecyclerBenh = new AdapterRecyclerBenh(getContext(),R.layout.layout_custom_benh,benhModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerCoXuongKhop.removeAllViews();
        recyclerCoXuongKhop.setLayoutManager(layoutManager);
        recyclerCoXuongKhop.setAdapter(adapterRecyclerBenh);

        progressBarCoXuongKhop.setVisibility(View.GONE);
    }

    @Override
    public void LayDanhsachBenhThatBai() {
        progressBarCoXuongKhop.setVisibility(View.GONE);

    }

    @Override
    public void LayDanhSachBenhTheoDanhSachLuuBenhThanhCong(List<BenhModel> benhModelList) {

    }
}
