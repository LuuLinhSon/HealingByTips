package com.fruitstudio.healingbytips.Presenter.Main;

import com.fruitstudio.healingbytips.Model.Object.LuuBenhModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackLuuBenh;
import com.fruitstudio.healingbytips.View.Main.ViewLuuBenh;

/**
 * Created by Admin on 9/25/2017.
 */

public class XuLyLuuBenh implements iXuLyLuuBenh {

    ViewLuuBenh viewLuuBenh;
    LuuBenhModel luuBenhModel;

    public XuLyLuuBenh(ViewLuuBenh viewLuuBenh){
        this.viewLuuBenh = viewLuuBenh;
        luuBenhModel = new LuuBenhModel();
    }

    @Override
    public void LuuBenh(LuuBenhModel luuBenh) {
        luuBenhModel.LuuBenh(luuBenh, new ServerCallbackLuuBenh() {
            @Override
            public void onSuccess(Boolean bl) {
                if(bl){
                    viewLuuBenh.LuuBenhThanhCong();
                }else {
                    viewLuuBenh.LuuBenhThatBai();
                }
            }
        });
    }
}
