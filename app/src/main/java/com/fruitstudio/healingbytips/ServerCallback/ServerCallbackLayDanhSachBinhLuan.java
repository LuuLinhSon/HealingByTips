package com.fruitstudio.healingbytips.ServerCallback;

import com.fruitstudio.healingbytips.Model.Object.BinhLuanModel;

import java.util.List;

/**
 * Created by Admin on 9/19/2017.
 */

public interface ServerCallbackLayDanhSachBinhLuan {
    void onSuccess(List<BinhLuanModel> binhLuanModelList);
}
