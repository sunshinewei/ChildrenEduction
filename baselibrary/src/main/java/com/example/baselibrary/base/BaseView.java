package com.example.baselibrary.base;

import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public interface BaseView {
    void showProgress(@Nullable String msg);
    void hideProgress();
    void showToast(String msg);
    void showAlert(String msg);
    void showLongToast(String msg);
}
