package com.fruitstudio.healingbytips.Presenter.Main;

import com.fruitstudio.healingbytips.Model.Object.LuuBenhModel;

import java.util.List;

/**
 * Created by Admin on 9/14/2017.
 */

public interface iXuLyLayDanhSachBenh {
    void LayDanhSachBenh(String loaibenh);
    void LayDanhSachBenhTheoDanhSachLuuBenh(List<LuuBenhModel> luuBenhModelList);
}
