package com.fruitstudio.healingbytips.Presenter.Main;

import android.net.Uri;

import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackChiTietBenh;
import com.fruitstudio.healingbytips.View.Main.ViewChiTietBenh;

/**
 * Created by Admin on 9/16/2017.
 */

public class XuLyLayChiTietBenh implements iXuLyLayChiTietBenh {

    ViewChiTietBenh viewChiTietBenh;
    BenhModel benhModel;

    public XuLyLayChiTietBenh(ViewChiTietBenh viewChiTietBenh){
        this.viewChiTietBenh = viewChiTietBenh;
        benhModel = new BenhModel();
    }

    @Override
    public void XuLyLayChiTietBenh(String loaiBenh, String maBenh) {

        benhModel.LayChiTietBenh(loaiBenh, maBenh, new ServerCallbackChiTietBenh() {
            @Override
            public void onSuccess(BenhModel benh, Uri uri) {
                if(benh.getTenbenh() != null){
                    viewChiTietBenh.LayChiTietBenhThanhCong(benh,uri);
                }else {
                    viewChiTietBenh.LayChiTietBenhThatBai();
                }
            }
        });

    }
}
