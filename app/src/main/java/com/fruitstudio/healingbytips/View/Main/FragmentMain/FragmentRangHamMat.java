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

public class FragmentRangHamMat extends Fragment implements ViewDanhSachBenh{

    RecyclerView recyclerRangHamMat;
    XuLyLayDanhSachBenh xuLyLayDanhSachBenh;
    ProgressBar progressBarRangHamMat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_rang_ham_mat,container,false);

        recyclerRangHamMat = (RecyclerView) view.findViewById(R.id.recyclerRangHamMat);
        progressBarRangHamMat = (ProgressBar) view.findViewById(R.id.progressBarRangHamMat);
        progressBarRangHamMat.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        xuLyLayDanhSachBenh = new XuLyLayDanhSachBenh(this);
        xuLyLayDanhSachBenh.LayDanhSachBenh("ranghammats");

        return view;
    }

    @Override
    public void LayDanhSachBenhThanhCong(List<BenhModel> benhModelList) {
        AdapterRecyclerBenh adapterRecyclerBenh = new AdapterRecyclerBenh(getContext(),R.layout.layout_custom_benh,benhModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerRangHamMat.removeAllViews();
        recyclerRangHamMat.setLayoutManager(layoutManager);
        recyclerRangHamMat.setAdapter(adapterRecyclerBenh);

        progressBarRangHamMat.setVisibility(View.GONE);
    }

    @Override
    public void LayDanhsachBenhThatBai() {
        progressBarRangHamMat.setVisibility(View.GONE);
    }

    @Override
    public void LayDanhSachBenhTheoDanhSachLuuBenhThanhCong(List<BenhModel> benhModelList) {

    }
}
