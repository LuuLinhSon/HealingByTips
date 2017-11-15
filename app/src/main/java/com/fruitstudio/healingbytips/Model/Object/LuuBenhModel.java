package com.fruitstudio.healingbytips.Model.Object;

import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackLayDanhSachLuuBenh;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackLuuBenh;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/25/2017.
 */

public class LuuBenhModel {

    private String maloaibenh;
    private String mabenh;
    private String mauser;

    public LuuBenhModel(){

    }

    public String getMauser() {
        return mauser;
    }

    public LuuBenhModel setMauser(String mauser) {
        this.mauser = mauser;
        return this;
    }

    public String getMaloaibenh() {
        return maloaibenh;
    }

    public LuuBenhModel setMaloaibenh(String maloaibenh) {
        this.maloaibenh = maloaibenh;
        return this;
    }

    public String getMabenh() {
        return mabenh;
    }

    public LuuBenhModel setMabenh(String mabenh) {
        this.mabenh = mabenh;
        return this;
    }

    public void LuuBenh(LuuBenhModel luuBenh, final ServerCallbackLuuBenh serverCallbackLuuBenh){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("luubenhs").child(luuBenh.getMauser());
        String maluubenh = databaseReference.push().getKey();
        databaseReference.child(maluubenh).setValue(luuBenh).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    serverCallbackLuuBenh.onSuccess(true);
                }else {
                    serverCallbackLuuBenh.onSuccess(false);
                }
            }
        });
    }

    public void LayCacBenhDaLuu(String mauser, final ServerCallbackLayDanhSachLuuBenh serverCallbackLayDanhSachLuuBenh){
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("luubenhs").child(mauser);
        final List<LuuBenhModel> luuBenhModels = new ArrayList<>();
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataLuuBenh : dataSnapshot.getChildren()){
                    LuuBenhModel luuBenh = dataLuuBenh.getValue(LuuBenhModel.class);

                    luuBenhModels.add(luuBenh);
                }
                serverCallbackLayDanhSachLuuBenh.onSuccess(luuBenhModels);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
//        databaseReference.addValueEventListener(valueEventListener);
        databaseReference.addListenerForSingleValueEvent(valueEventListener);

    }


}
