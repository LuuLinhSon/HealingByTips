package com.fruitstudio.healingbytips.ServerCallback;

import android.net.Uri;

import com.fruitstudio.healingbytips.Model.Object.BenhModel;

/**
 * Created by Admin on 9/16/2017.
 */

public interface ServerCallbackChiTietBenh {
    void onSuccess(BenhModel benh,Uri uri);
}
