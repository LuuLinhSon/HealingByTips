package com.fruitstudio.healingbytips.Presenter.UpdateUser;

import com.fruitstudio.healingbytips.Model.Object.ThanhVienModel;
import com.fruitstudio.healingbytips.ServerCallback.ServerCallbackThongTinThanhVien;
import com.fruitstudio.healingbytips.View.Main.CaNhan.ViewCapNhatThongTinCaNhan;

/**
 * Created by Admin on 9/11/2017.
 */

public class XuLyCapNhatThongTinThanhVien implements iXuLyCapNhatThongTinThanhVien {

    ViewCapNhatThongTinCaNhan viewCapNhatThongTinCaNhan;
    ThanhVienModel thanhVienModel;

    public XuLyCapNhatThongTinThanhVien(ViewCapNhatThongTinCaNhan viewCapNhatThongTinCaNhan){
        this.viewCapNhatThongTinCaNhan = viewCapNhatThongTinCaNhan;
        thanhVienModel = new ThanhVienModel();
    }

    @Override
    public void XuLyCapNhatThongTinThanhVien(String userId, ThanhVienModel thanhVien,String realUri) {
        thanhVienModel.CapNhatThongTinThanhVien(userId, thanhVien,realUri ,new ServerCallbackThongTinThanhVien() {
            @Override
            public void onSuccess(boolean bl) {
                if(bl){
                    viewCapNhatThongTinCaNhan.CapNhatThanhVienThanhCong();
                }else {
                    viewCapNhatThongTinCaNhan.CapNhatThanhVienThatBai();
                }
            }
        });
    }

    @Override
    public void CapNhatThongTinNhungKhongSuaAnh(String userId, ThanhVienModel thanhVien) {
        thanhVienModel.CapNhatThanhVienNhungKhongSuaAnh(userId, thanhVien, new ServerCallbackThongTinThanhVien() {
            @Override
            public void onSuccess(boolean bl) {
                if(bl){
                    viewCapNhatThongTinCaNhan.CapNhatThanhVienNhungKhongSuaAnh();
                }
            }
        });
    }
}
