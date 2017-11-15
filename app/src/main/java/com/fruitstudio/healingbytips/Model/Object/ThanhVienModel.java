package com.fruitstudio.healingbytips.Model.Object;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackThongTinThanhVien;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 8/24/2017.
 */

public class ThanhVienModel implements Parcelable{

    private String hoten;
    private String hinhanh;

    public ThanhVienModel(){

    }

    protected ThanhVienModel(Parcel in) {
        hoten = in.readString();
        hinhanh = in.readString();
    }

    public static final Creator<ThanhVienModel> CREATOR = new Creator<ThanhVienModel>() {
        @Override
        public ThanhVienModel createFromParcel(Parcel in) {
            return new ThanhVienModel(in);
        }

        @Override
        public ThanhVienModel[] newArray(int size) {
            return new ThanhVienModel[size];
        }
    };

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public void DangKyThongTinThanhVien(String userId, ThanhVienModel thanhVien, final ServerCallbackThongTinThanhVien serverCallbackThongTinThanhVien){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("thanhviens");
        databaseReference.child(userId).setValue(thanhVien).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                serverCallbackThongTinThanhVien.onSuccess(true);
            }
        });
    }

    public void CapNhatThongTinThanhVien(final String uid, final ThanhVienModel thanhVien, final String realUri, final ServerCallbackThongTinThanhVien serverCallbackThongTinThanhVien){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("thanhviens");
        databaseReference.child(uid).setValue(thanhVien).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Uri uri = Uri.fromFile(new File(realUri));
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("thanhvien/" + uri.getLastPathSegment());
                storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            serverCallbackThongTinThanhVien.onSuccess(true);
                        }
                    }
                });

            }
        });

    }

    public void CapNhatThanhVienNhungKhongSuaAnh(final String uid, final ThanhVienModel thanhVien, final ServerCallbackThongTinThanhVien serverCallbackThongTinThanhVien){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("thanhviens");
        databaseReference.child(uid).setValue(thanhVien).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                serverCallbackThongTinThanhVien.onSuccess(true);
            }
        });
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hoten);
        dest.writeString(hinhanh);
    }
}
