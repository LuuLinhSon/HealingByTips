package com.fruitstudio.healingbytips.Presenter.Main;

import com.fruitstudio.healingbytips.Model.Object.LuuBenhModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackLayDanhSachLuuBenh;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachLuuBenh;

import java.util.List;

/**
 * Created by Admin on 9/25/2017.
 */

public class XuLyLayDanhSachLuuBenh implements iXuLyLayDanhSachLuuBenh {

    ViewDanhSachLuuBenh viewDanhSachLuuBenh;
    LuuBenhModel luuBenhModel;

    public XuLyLayDanhSachLuuBenh(ViewDanhSachLuuBenh viewDanhSachLuuBenh){
        this.viewDanhSachLuuBenh = viewDanhSachLuuBenh;
        luuBenhModel = new LuuBenhModel();
    }

    @Override
    public void XuLyLaydanhSachLuuBenh(String mauser) {
        luuBenhModel.LayCacBenhDaLuu(mauser, new ServerCallbackLayDanhSachLuuBenh() {
            @Override
            public void onSuccess(List<LuuBenhModel> luuBenhModelList) {
                if(luuBenhModelList != null){
                    viewDanhSachLuuBenh.LayDanhSachLuuBenhThanhCong(luuBenhModelList);
                }else {
                    viewDanhSachLuuBenh.LayDanhSachLuuBenhThatBai();
                }
            }
        });
    }
}
