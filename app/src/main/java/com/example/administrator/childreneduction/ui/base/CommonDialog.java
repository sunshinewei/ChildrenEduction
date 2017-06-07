package com.example.administrator.childreneduction.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/6/5.
 * 添加评论Dialog
 */

public class CommonDialog extends Dialog {

    public CommonDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public CommonDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    public void setLayoutAttrebutes(){
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        WindowManager manager = window.getWindowManager();
        attributes.height= (int) (manager.getDefaultDisplay().getHeight()*0.2);
        attributes.width= manager.getDefaultDisplay().getWidth();
        window.setAttributes(attributes);
        window.setGravity(Gravity.BOTTOM);
    }

}
