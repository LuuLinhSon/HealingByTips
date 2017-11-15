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

public class FragmentTimMach extends Fragment implements ViewDanhSachBenh {

    RecyclerView recyclerTimMach;
    XuLyLayDanhSachBenh xuLyLayDanhSachBenh;
    ProgressBar progressBarTimMach;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_tim_mach,container,false);

        recyclerTimMach = (RecyclerView) view.findViewById(R.id.recyclerTimMach);
        progressBarTimMach = (ProgressBar) view.findViewById(R.id.progressBarTimMach);
        progressBarTimMach.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        xuLyLayDanhSachBenh = new XuLyLayDanhSachBenh(this);
        xuLyLayDanhSachBenh.LayDanhSachBenh("timmachs");

        return view;
    }

    @Override
    public void LayDanhSachBenhThanhCong(List<BenhModel> benhModelList) {
        AdapterRecyclerBenh adapterRecyclerBenh = new AdapterRecyclerBenh(getContext(),R.layout.layout_custom_benh,benhModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerTimMach.removeAllViews();
        recyclerTimMach.setLayoutManager(layoutManager);
        recyclerTimMach.setAdapter(adapterRecyclerBenh);

        progressBarTimMach.setVisibility(View.GONE);
    }

    @Override
    public void LayDanhsachBenhThatBai() {
        progressBarTimMach.setVisibility(View.GONE);

    }

    @Override
    public void LayDanhSachBenhTheoDanhSachLuuBenhThanhCong(List<BenhModel> benhModelList) {

    }
}
