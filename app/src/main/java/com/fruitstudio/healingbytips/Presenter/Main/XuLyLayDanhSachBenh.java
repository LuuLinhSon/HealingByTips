package com.fruitstudio.healingbytips.Presenter.Main;

import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.Model.Object.LuuBenhModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallBackLayDanhSachBenh;
import com.fruitstudio.healingbytips.View.Main.ViewDanhSachBenh;

import java.util.List;

/**
 * Created by Admin on 9/14/2017.
 */

public class XuLyLayDanhSachBenh implements iXuLyLayDanhSachBenh {

    ViewDanhSachBenh viewDanhSachBenh;
    BenhModel benhModel;

    public XuLyLayDanhSachBenh(ViewDanhSachBenh viewDanhSachBenh){
        this.viewDanhSachBenh = viewDanhSachBenh;
        benhModel = new BenhModel();
    }

    @Override
    public void LayDanhSachBenh(String loaibenh) {
        benhModel.LayDanhSachBenh(loaibenh, new ServerCallBackLayDanhSachBenh() {
            @Override
            public void onSuccess(List<BenhModel> benhModelList) {
                if(benhModelList.size() > 0){
                    viewDanhSachBenh.LayDanhSachBenhThanhCong(benhModelList);
                }else {
                    viewDanhSachBenh.LayDanhsachBenhThatBai();
                }
            }
        });

    }

    @Override
    public void LayDanhSachBenhTheoDanhSachLuuBenh(List<LuuBenhModel> luuBenhModelList) {
        benhModel.LayDanhSachBenhTheoDanhSachLuuBenh(luuBenhModelList, new ServerCallBackLayDanhSachBenh() {
            @Override
            public void onSuccess(List<BenhModel> benhModelList) {
                if(benhModelList != null){
                    viewDanhSachBenh.LayDanhSachBenhTheoDanhSachLuuBenhThanhCong(benhModelList);
                }
            }
        });
    }
}
