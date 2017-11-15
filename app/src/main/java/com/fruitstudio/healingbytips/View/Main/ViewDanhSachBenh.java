package com.fruitstudio.healingbytips.View.Main;

import com.fruitstudio.healingbytips.Model.Object.BenhModel;

import java.util.List;

/**
 * Created by Admin on 9/14/2017.
 */

public interface ViewDanhSachBenh {
    void LayDanhSachBenhThanhCong(List<BenhModel> benhModelList);
    void LayDanhsachBenhThatBai();
    void LayDanhSachBenhTheoDanhSachLuuBenhThanhCong(List<BenhModel> benhModelList);
}
