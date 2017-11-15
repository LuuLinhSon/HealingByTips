package com.fruitstudio.healingbytips.Presenter.Main;

import com.fruitstudio.healingbytips.Model.Object.BinhLuanModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackLayDanhSachBinhLuan;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachBinhLuan;

import java.util.List;

/**
 * Created by Admin on 9/19/2017.
 */

public class XuLyLayDanhSachBinhLuan implements iXuLyLayDanhSachBinhLuan {

    ViewDanhSachBinhLuan viewDanhSachBinhLuan;
    BinhLuanModel binhLuanModel;
    public XuLyLayDanhSachBinhLuan(ViewDanhSachBinhLuan viewDanhSachBinhLuan){
        this.viewDanhSachBinhLuan = viewDanhSachBinhLuan;
        binhLuanModel = new BinhLuanModel();
    }
    @Override
    public void LayDanhSachBinhLuan(String mabenh) {
        binhLuanModel.LayDanhSachBinhLuan(mabenh, new ServerCallbackLayDanhSachBinhLuan() {
            @Override
            public void onSuccess(List<BinhLuanModel> binhLuanModelList) {
                if(binhLuanModelList != null){
                    viewDanhSachBinhLuan.LayDanhSachBinhLuanThanhCong(binhLuanModelList);
                }else {
                    viewDanhSachBinhLuan.LayDanhSachBinhLuanThatBai();
                }
            }
        });
    }
}
