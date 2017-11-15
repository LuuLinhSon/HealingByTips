package com.fruitstudio.healingbytips.Presenter.BinhLuan;

import com.fruitstudio.healingbytips.Model.Object.BinhLuanModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackThemBinhLuan;
import com.fruitstudio.healingbytips.View.BinhLuan.ViewThemBinhLuan;

/**
 * Created by Admin on 9/23/2017.
 */

public class XuLyThemBinhLuan implements iXuLyThemBinhLuan {

    BinhLuanModel binhLuanModel;
    ViewThemBinhLuan viewThemBinhLuan;

    public XuLyThemBinhLuan(ViewThemBinhLuan viewThemBinhLuan){
        this.viewThemBinhLuan = viewThemBinhLuan;
        binhLuanModel = new BinhLuanModel();
    }


    @Override
    public void ThemBinhLuan(String mabenh, BinhLuanModel binhLuan) {
        binhLuanModel.ThemBinhLuan(mabenh, binhLuan, new ServerCallbackThemBinhLuan() {
            @Override
            public void onSuccess(Boolean bl) {
                if(bl){
                    viewThemBinhLuan.ThemBinhLuanThanhCong();
                }else {
                    viewThemBinhLuan.ThemBinhLuanThatBai();
                }
            }
        });
    }
}
