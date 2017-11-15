package com.fruitstudio.healingbytips.Presenter.Register;

import com.fruitstudio.healingbytips.Model.Object.ThanhVienModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackThongTinThanhVien;
import com.fruitstudio.healingbytips.View.Register.ViewDangKyThongTinThanhVien;

/**
 * Created by Admin on 8/24/2017.
 */

public class XuLyDangKyThongTinThanhVien implements iXuLyDangKyThongTinThanhVien {

    ViewDangKyThongTinThanhVien viewDangKyThongTinThanhVien;
    ThanhVienModel thanhVien;
    public XuLyDangKyThongTinThanhVien(ViewDangKyThongTinThanhVien viewDangKyThongTinThanhVien){
        this.viewDangKyThongTinThanhVien = viewDangKyThongTinThanhVien;
        thanhVien = new ThanhVienModel();
    }

    @Override
    public void XuLyDangKyThongTinThanhVien(String userId, ThanhVienModel thanhVien) {
        thanhVien.DangKyThongTinThanhVien(userId, thanhVien, new ServerCallbackThongTinThanhVien() {
            @Override
            public void onSuccess(boolean bl) {
                if(bl){
                    viewDangKyThongTinThanhVien.dangKyThanhCong();
                }else {
                    viewDangKyThongTinThanhVien.dangKyThatBai();
                }
            }
        });
    }
}
