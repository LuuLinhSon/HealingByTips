package com.fruitstudio.healingbytips.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fruitstudio.healingbytips.Model.Object.BinhLuanModel;
import com.fruitstudio.healingbytips.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 9/19/2017.
 */

public class AdapterRecyclerBinhLuan extends RecyclerView.Adapter<AdapterRecyclerBinhLuan.ViewHolder> {

    Context context;
    int layout;
    List<BinhLuanModel> binhLuanModelList;

    public AdapterRecyclerBinhLuan(Context context, int layout, List<BinhLuanModel> binhLuanModelList){
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTieuDe,tvNoiDungBinhLuan,tvTenNguoiBinhLuan,tvNgayDang;
        ImageView imLike;
        CircleImageView imAvatarBinhLuan;
        public ViewHolder(View itemView) {
            super(itemView);
            tvTieuDe = (TextView) itemView.findViewById(R.id.tvTieude);
            tvNoiDungBinhLuan = (TextView) itemView.findViewById(R.id.tvNoiDungBinhLuan);
            imLike = (ImageView) itemView.findViewById(R.id.imLike);
            imAvatarBinhLuan = (CircleImageView) itemView.findViewById(R.id.imAvatarBinhLuan);
            tvTenNguoiBinhLuan = (TextView) itemView.findViewById(R.id.tvTenNguoiBinhLuan);
            tvNgayDang = (TextView) itemView.findViewById(R.id.tvNgayDang);
        }
    }


    @Override
    public AdapterRecyclerBinhLuan.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerBinhLuan.ViewHolder viewHolder, int i) {
        BinhLuanModel binhLuan = binhLuanModelList.get(i);
        viewHolder.tvTenNguoiBinhLuan.setText(binhLuan.getTennguoibinhluan());
        viewHolder.tvTieuDe.setText(binhLuan.getTieude());
        viewHolder.tvNoiDungBinhLuan.setText(binhLuan.getNoidungbinhluan());
        viewHolder.tvNgayDang.setText(binhLuan.getNgaydang());
        if(binhLuan.isThich()){
            viewHolder.imLike.setImageDrawable(getIdDrawable(R.drawable.ic_like));
        }else {
            viewHolder.imLike.setImageDrawable(getIdDrawable(R.drawable.ic_unlike));
        }
        String hinhanh = binhLuan.getAvatarbinhluan();
        Log.d("uri", "onSuccess: " + hinhanh + "");
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("thanhvien/" + hinhanh);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(viewHolder.imAvatarBinhLuan);
            }
        });

    }

    @Override
    public int getItemCount() {
        return binhLuanModelList.size();
    }

    private Drawable getIdDrawable(int idDrawable) {

        Drawable drawable;
        if (Build.VERSION.SDK_INT > 21) {
            drawable = ContextCompat.getDrawable(context, idDrawable);
        } else {
            drawable = context.getResources().getDrawable(idDrawable);
        }

        return drawable;

    }


}
