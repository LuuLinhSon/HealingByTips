package com.fruitstudio.healingbytips.Presenter.TimKiem;

import com.fruitstudio.healingbytips.Model.Object.BenhModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallBackLayDanhSachBenh;
import com.fruitstudio.healingbytips.View.TimKiem.ViewTimKiem;

import java.util.List;

/**
 * Created by Admin on 9/28/2017.
 */

public class XuLyTimKiem implements iXuLyTimKiem {

    ViewTimKiem viewTimKiem;
    BenhModel benhModel;

    public XuLyTimKiem(ViewTimKiem viewTimKiem){
        this.viewTimKiem = viewTimKiem;
        benhModel = new BenhModel();
    }

    @Override
    public void XuLyTimKiem(String keyword) {
        benhModel.TimKiemTenBenh(keyword, new ServerCallBackLayDanhSachBenh() {
            @Override
            public void onSuccess(List<BenhModel> benhModelList) {
                if(benhModelList.size() > 0){
                    viewTimKiem.TimKiemThanhCong(benhModelList);
                }else {
                    viewTimKiem.TimKiemThatBai();
                }
            }
        });
    }
}
