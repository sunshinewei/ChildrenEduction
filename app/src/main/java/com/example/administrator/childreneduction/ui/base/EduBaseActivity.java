package com.example.administrator.childreneduction.ui.base;

import android.os.Bundle;

import com.example.administrator.childreneduction.App;
import com.example.administrator.childreneduction.da.component.ActivityComponent;
import com.example.baselibrary.base.BaseActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/3/7 0007.
 */

public abstract class EduBaseActivity extends BaseActivity {

    public ActivityComponent mActivityComponent;
    /**
     * 初始化dagger2中的componnent
     */
    public  void initActivityComponent() {

    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    /**
     * 停止推送服务
     */
    public void stopJpush() {
        JPushInterface.stopPush(App.getAppComponent().getConext());
    }

    /**
     * 恢复极光推送
     */
    public void resumePush(){
        if (JPushInterface.isPushStopped(App.getAppComponent().getConext())){//判断当前极光推送是否工作
            JPushInterface.resumePush(App.getAppComponent().getConext());
        }
    }

    /**
     * 防止被杀死
     */
    public void shareSDK(Bundle savedInstanceState){
        UMShareAPI.get(this).fetchAuthResultWithBundle(this, savedInstanceState, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {

            }
        });
    }



}
