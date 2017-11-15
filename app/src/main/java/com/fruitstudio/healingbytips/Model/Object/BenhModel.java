package com.fruitstudio.healingbytips.Model.Object;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.fruitstudio.healingbytips.ServerCallback.ServerCallBackLayDanhSachBenh;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackChiTietBenh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/14/2017.
 */

public class BenhModel {
    private String mabenh;
    private String maloaibenh;
    private String tenbenh;
    private String noidung;
    private String hinhanh;
    private List<BinhLuanModel> binhLuanModelList;


    DatabaseReference nodeRoot;

    public BenhModel() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }


    public String getHinhanh() {
        return hinhanh;
    }

    public BenhModel setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
        return this;
    }


    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public BenhModel setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
        return this;
    }


    public String getNoidung() {
        return noidung;
    }

    public BenhModel setNoidung(String noidung) {
        this.noidung = noidung;
        return this;
    }

    public String getMabenh() {
        return mabenh;
    }

    public BenhModel setMabenh(String mabenh) {
        this.mabenh = mabenh;
        return this;
    }

    public String getMaloaibenh() {
        return maloaibenh;
    }

    public BenhModel setMaloaibenh(String maloaibenh) {
        this.maloaibenh = maloaibenh;
        return this;
    }

    public String getTenbenh() {
        return tenbenh;
    }

    public BenhModel setTenbenh(String tenbenh) {
        this.tenbenh = tenbenh;
        return this;
    }


    public void LayDanhSachBenh(final String loaibenh, final ServerCallBackLayDanhSachBenh serverCallBackLayDanhSachBenh) {

        final List<BenhModel> benhModelList = new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                benhModelList.clear();
                DataSnapshot dataBenhs = dataSnapshot.child("benhs").child(loaibenh);
                for (DataSnapshot value : dataBenhs.getChildren()) {
                    BenhModel benhModel = value.getValue(BenhModel.class);
                    benhModel.setMabenh(value.getKey());
                    benhModel.setMaloaibenh(loaibenh);

                    DataSnapshot dataBinhLuan = dataSnapshot.child("binhluans").child(benhModel.getMabenh());
                    final List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : dataBinhLuan.getChildren()) {
                        BinhLuanModel binhLuan = valueBinhLuan.getValue(BinhLuanModel.class);
                        binhLuanModelList.add(binhLuan);
                    }
                    benhModel.setBinhLuanModelList(binhLuanModelList);
                    benhModelList.add(benhModel);
                }
                serverCallBackLayDanhSachBenh.onSuccess(benhModelList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        nodeRoot.addValueEventListener(valueEventListener);
    }

    public void LayChiTietBenh(final String loaiBenh, final String maBenh, final ServerCallbackChiTietBenh serverCallbackChiTietBenh) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final BenhModel benh = dataSnapshot.getValue(BenhModel.class);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/" + benh.getHinhanh());
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        serverCallbackChiTietBenh.onSuccess(benh, uri);
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        nodeRoot.child("benhs").child(loaiBenh).child(maBenh).addValueEventListener(valueEventListener);
    }

    public void LayDanhSachBenhTheoDanhSachLuuBenh(final List<LuuBenhModel> luuBenhModelList, final ServerCallBackLayDanhSachBenh serverCallBackLayDanhSachBenh) {
        final List<BenhModel> benhModelList = new ArrayList<>();

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                benhModelList.clear();
                for (LuuBenhModel luuBenh : luuBenhModelList) {
                    DataSnapshot dataBenh = dataSnapshot.child("benhs").child(luuBenh.getMaloaibenh()).child(luuBenh.getMabenh());
                    BenhModel benh = dataBenh.getValue(BenhModel.class);
                    benh.setMaloaibenh(luuBenh.getMaloaibenh());
                    benh.setMabenh(luuBenh.getMabenh());

                    DataSnapshot dataBinhLuan = dataSnapshot.child("binhluans").child(benh.getMabenh());
                    final List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
                    for (DataSnapshot valueBinhLuan : dataBinhLuan.getChildren()) {
                        BinhLuanModel binhLuan = valueBinhLuan.getValue(BinhLuanModel.class);
                        binhLuanModelList.add(binhLuan);
                    }
                    benh.setBinhLuanModelList(binhLuanModelList);
                    benhModelList.add(benh);
                }

                serverCallBackLayDanhSachBenh.onSuccess(benhModelList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nodeRoot.addValueEventListener(valueEventListener);
    }

    public void TimKiemBenh(final String keyword, final ServerCallBackLayDanhSachBenh serverCallBackLayDanhSachBenh) {

        ValueEventListener valueEventListener = new ValueEventListener() {
            List<BenhModel> benhModelList = new ArrayList<>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataSnapshot dataBenhs = dataSnapshot.child("benhs");
                for (DataSnapshot dataLoaiBenh : dataBenhs.getChildren()) {
                    for (DataSnapshot dataBenh : dataLoaiBenh.getChildren()) {
                        BenhModel benh = dataBenh.getValue(BenhModel.class);
                        benh.setMabenh(dataBenh.getKey());
                        benh.setMaloaibenh(dataLoaiBenh.getKey());

                        DataSnapshot dataBinhLuan = dataSnapshot.child("binhluans").child(benh.getMabenh());
                        final List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
                        for (DataSnapshot valueBinhLuan : dataBinhLuan.getChildren()) {
                            BinhLuanModel binhLuan = valueBinhLuan.getValue(BinhLuanModel.class);
                            binhLuanModelList.add(binhLuan);
                        }
                        benh.setBinhLuanModelList(binhLuanModelList);
                        if (benh.getTenbenh().contains(keyword)) {
                            benhModelList.add(benh);
                        }
                    }
                }
                serverCallBackLayDanhSachBenh.onSuccess(benhModelList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        nodeRoot.addListenerForSingleValueEvent(valueEventListener);
    }

    public void TimKiemTenBenh(final String keyword, final ServerCallBackLayDanhSachBenh serverCallBackLayDanhSachBenh) {

        final List<BenhModel> benhModelList = new ArrayList<>();
        nodeRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {

                nodeRoot.child("benhs").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshotLoaiBenh) {
                        for (final DataSnapshot dataLoaiBenh : dataSnapshotLoaiBenh.getChildren()) {
                            Query query = nodeRoot.child("benhs").child(dataLoaiBenh.getKey()).orderByChild("tenbenh")
                                    .startAt(keyword)
                                    .endAt(keyword + "\uf8ff");

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot1) {
                                    for (DataSnapshot dataChild : dataSnapshot1.getChildren()) {
                                        BenhModel benh = dataChild.getValue(BenhModel.class);
                                        benh.setMabenh(dataChild.getKey());
                                        benh.setMaloaibenh(dataLoaiBenh.getKey());

                                        DataSnapshot dataBinhLuan = dataSnapshot.child("binhluans").child(benh.getMabenh());
                                        final List<BinhLuanModel> binhLuanModelList = new ArrayList<>();
                                        for (DataSnapshot valueBinhLuan : dataBinhLuan.getChildren()) {
                                            BinhLuanModel binhLuan = valueBinhLuan.getValue(BinhLuanModel.class);
                                            binhLuanModelList.add(binhLuan);
                                        }
                                        benh.setBinhLuanModelList(binhLuanModelList);
                                        benhModelList.add(benh);
                                    }

                                    serverCallBackLayDanhSachBenh.onSuccess(benhModelList);

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//                Query query = nodeRoot.child("benhs").child("thankinhs").orderByChild("tenbenh")
//                        .startAt(keyword)
//                        .endAt(keyword + "\uf8ff");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}
