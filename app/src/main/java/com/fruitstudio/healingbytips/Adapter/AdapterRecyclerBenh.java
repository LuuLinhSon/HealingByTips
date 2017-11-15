package com.fruitstudio.healingbytips.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.Model.Object.BinhLuanModel;
import com.fruitstudio.healingbytips.R;
import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.ChiTietBenhActivity;
import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.FragmentChiTietBenh.FragmentBinhLuan;
import com.fruitstudio.healingbytips.View.Main.ChiTietBenh.FragmentChiTietBenh.FragmentNoiDungBenh;

import java.util.List;

/**
 * Created by Admin on 9/14/2017.
 */

public class AdapterRecyclerBenh extends RecyclerView.Adapter<AdapterRecyclerBenh.ViewHolder> {

    Context context;
    int layout;
    List<BenhModel> benhModelList;

    public AdapterRecyclerBenh(Context context, int layout, List<BenhModel> benhModelList){
        this.context = context;
        this.layout = layout;
        this.benhModelList = benhModelList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenBenh,tvSoLuotThich,tvSoLuotKhongThich,tvSoLuotBinhLuan;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTenBenh = (TextView) itemView.findViewById(R.id.tvTenBenh);
            tvSoLuotThich = (TextView) itemView.findViewById(R.id.tvSoLuotThich);
            tvSoLuotKhongThich = (TextView) itemView.findViewById(R.id.tvSoLuotKhongThich);
            tvSoLuotBinhLuan = (TextView) itemView.findViewById(R.id.tvSoLuotBinhLuan);
            cardView = (CardView) itemView.findViewById(R.id.cardview);
        }
    }

    @Override
    public AdapterRecyclerBenh.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterRecyclerBenh.ViewHolder viewHolder, int i) {
        int indexLike = 0;
        int indexUnLike = 0;
        final BenhModel benhModel = benhModelList.get(i);
        viewHolder.tvTenBenh.setText(benhModel.getTenbenh());
        viewHolder.tvSoLuotBinhLuan.setText(String.valueOf(benhModel.getBinhLuanModelList().size()));
        for(BinhLuanModel binhLuan : benhModel.getBinhLuanModelList()){
            if(binhLuan.isThich()){
                indexLike++;
            }else {
                indexUnLike++;
            }
        }
        viewHolder.tvSoLuotThich.setText(String.valueOf(indexLike));
        viewHolder.tvSoLuotKhongThich.setText(String.valueOf(indexUnLike));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iChiTietBenh = new Intent(context, ChiTietBenhActivity.class);
                iChiTietBenh.putExtra("loaibenh",benhModel.getMaloaibenh());
                iChiTietBenh.putExtra("mabenh",benhModel.getMabenh());
                context.startActivity(iChiTietBenh);
            }
        });

    }

    @Override
    public int getItemCount() {
        return benhModelList.size();
    }


}
