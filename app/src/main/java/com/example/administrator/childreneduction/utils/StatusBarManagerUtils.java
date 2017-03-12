package com.example.administrator.childreneduction.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.administrator.childreneduction.R;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2017/3/12 0012.
 * 沉浸模式
 */

public class StatusBarManagerUtils {
    private static final int BUILD_VERSION_KITKAT = 19;
    private static final int BUILD_VERSION_LOLLIPOP = 21;
    //WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
    public static final int FLAG_TRANSLUCENT_STATUS = 0x04000000;
    //WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
    public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = 0x80000000;
    private Activity mActivity;
    private View statusBarView;
    private int statusBarHeight;
    public StatusBarManagerUtils(Activity activity) {
        this.mActivity = activity;
        statusBarHeight = getStatusBarHeight(activity);
    }
    public void setStatusBarView(View statusBarView) {
        this.statusBarView = statusBarView;
        setTransparent();
    }
    public void setStatusBarView() {
        setTransparent();
    }
    public int getStatusBarHeight() {
        return statusBarHeight;
    }
    /**
     * 设置状态栏全透明
     *
     */
    private void setTransparent() {
        if (Build.VERSION.SDK_INT < BUILD_VERSION_KITKAT) {
            return;
        }
        if(statusBarHeight <= 0){
            return;
        }
        transparentStatusBar();
        showStatusBarView();
    }
    @TargetApi(19)
    private void showStatusBarView() {
        int color = mActivity.getResources().getColor(R.color.colorPrimary);
        if(statusBarView == null){
            statusBarView = new View(mActivity);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(mActivity));
            statusBarView.setLayoutParams(params);
            statusBarView.setBackgroundColor(color);
            ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
            FrameLayout content = (FrameLayout) decorView.findViewById(android.R.id.content);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) content.getChildAt(0).getLayoutParams();
            layoutParams.setMargins(0,statusBarHeight,0,0);
            decorView.addView(statusBarView);
        }else{
            ViewGroup.LayoutParams layoutParams = statusBarView.getLayoutParams();
            layoutParams.height = getStatusBarHeight(mActivity);
            statusBarView.setLayoutParams(layoutParams);
            statusBarView.setBackgroundColor(color);
        }
    }
    //参考上面注释掉的代码 因为需要用隐藏API 调用方式进行改成反射
    private void transparentStatusBar(){
        Window window =  mActivity.getWindow();
        if (Build.VERSION.SDK_INT >= BUILD_VERSION_LOLLIPOP) {
            //不add此条flag 会导致在EMUI3.1(华为)上失效，add这个flag 会导致在其它机型上面添加一个半透明黑条
            window.addFlags(FLAG_TRANSLUCENT_STATUS);
            //下面的代码段是不加上面的flag时，要显示纯色的状态栏时需要加的代码 不用了
/*            window.clearFlags(FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);*/
            //因为需要用隐藏API，没有重新编译5.x版本的android.jar，使用的还是18的api，这里用的反射
            try {
                Class[] argsClass=new Class[]{int.class};
                Method setStatusBarColorMethod = Window.class.getMethod("setStatusBarColor",argsClass);
                setStatusBarColorMethod.invoke(window, Color.TRANSPARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            window.addFlags(FLAG_TRANSLUCENT_STATUS);
        }
    }
    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT < BUILD_VERSION_KITKAT) {
            return 0;
        }
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
    public void setStatusbarVisibility(int visibility){
        if(statusBarView != null) {
            this.statusBarView.setVisibility(visibility);
        }
    }
    public void setColor(int color){
        if(statusBarView != null){
            this.statusBarView.setBackgroundColor(color);
        }
    }
}
