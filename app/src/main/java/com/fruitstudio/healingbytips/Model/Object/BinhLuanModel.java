package com.fruitstudio.healingbytips.Model.Object;

import android.util.Log;

import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackLayDanhSachBinhLuan;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackThemBinhLuan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/19/2017.
 */

public class BinhLuanModel {
    private String mabinhluan;
    private String tieude;
    private String noidungbinhluan;
    private boolean thich;
    private String mauser;
    private String avatarbinhluan;
    private String tennguoibinhluan;
    private String ngaydang;

    public BinhLuanModel() {

    }


    public String getNgaydang() {
        return ngaydang;
    }

    public BinhLuanModel setNgaydang(String ngaydang) {
        this.ngaydang = ngaydang;
        return this;
    }


    public String getTennguoibinhluan() {
        return tennguoibinhluan;
    }

    public BinhLuanModel setTennguoibinhluan(String tennguoibinhluan) {
        this.tennguoibinhluan = tennguoibinhluan;
        return this;
    }

    public String getAvatarbinhluan() {
        return avatarbinhluan;
    }

    public BinhLuanModel setAvatarbinhluan(String avatarbinhluan) {
        this.avatarbinhluan = avatarbinhluan;
        return this;
    }

    public String getTieude() {
        return tieude;
    }

    public BinhLuanModel setTieude(String tieude) {
        this.tieude = tieude;
        return this;
    }

    public String getMabinhluan() {
        return mabinhluan;
    }

    public BinhLuanModel setMabinhluan(String mabinhluan) {
        this.mabinhluan = mabinhluan;
        return this;
    }

    public String getNoidungbinhluan() {
        return noidungbinhluan;
    }

    public BinhLuanModel setNoidungbinhluan(String noidungbinhluan) {
        this.noidungbinhluan = noidungbinhluan;
        return this;
    }

    public boolean isThich() {
        return thich;
    }

    public BinhLuanModel setThich(boolean thich) {
        this.thich = thich;
        return this;
    }

    public String getMauser() {
        return mauser;
    }

    public BinhLuanModel setMauser(String mauser) {
        this.mauser = mauser;
        return this;
    }

    public void LayDanhSachBinhLuan(final String mabenh, final ServerCallbackLayDanhSachBinhLuan serverCallbackLayDanhSachBinhLuan) {
        final List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
        DatabaseReference nodeRoot = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binhLuanModelList.clear();
                DataSnapshot dataBinhLuan = dataSnapshot.child("binhluans").child(mabenh);
                for (DataSnapshot valueBinhLuan : dataBinhLuan.getChildren()) {
                    BinhLuanModel binhLuan = valueBinhLuan.getValue(BinhLuanModel.class);
                    binhLuan.setMabinhluan(valueBinhLuan.getKey());

                    DataSnapshot dataAvatar = dataSnapshot.child("thanhviens").child(binhLuan.getMauser());
                    ThanhVienModel thanhVien = dataAvatar.getValue(ThanhVienModel.class);

                    String hinhanh = thanhVien.getHinhanh();
                    String tennguoibinhluan = thanhVien.getHoten();

                    binhLuan.setAvatarbinhluan(hinhanh);
                    binhLuan.setTennguoibinhluan(tennguoibinhluan);
                    binhLuanModelList.add(binhLuan);

                }
                serverCallbackLayDanhSachBinhLuan.onSuccess(binhLuanModelList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nodeRoot.addValueEventListener(valueEventListener);

    }

    public void ThemBinhLuan(String mabenh, BinhLuanModel binhLuan, final ServerCallbackThemBinhLuan serverCallbackThemBinhLuan){
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan = nodeBinhLuan.child(mabenh).push().getKey();
        nodeBinhLuan.child(mabenh).child(mabinhluan).setValue(binhLuan).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    serverCallbackThemBinhLuan.onSuccess(true);
                }else {
                    serverCallbackThemBinhLuan.onSuccess(false);
                }
            }
        });

    }


}
