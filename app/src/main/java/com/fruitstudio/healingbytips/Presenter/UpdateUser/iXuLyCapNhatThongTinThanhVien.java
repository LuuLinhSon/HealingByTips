package com.fruitstudio.healingbytips.Presenter.UpdateUser;

import com.fruitstudio.healingbytips.Model.Object.ThanhVienModel;

/**
 * Created by Admin on 9/11/2017.
 */

public interface iXuLyCapNhatThongTinThanhVien {
    void XuLyCapNhatThongTinThanhVien(String userId, ThanhVienModel thanhVien,String realUri);
    void CapNhatThongTinNhungKhongSuaAnh(String userId, ThanhVienModel thanhVien);
}
