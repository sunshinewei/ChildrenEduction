package com.example.administrator.childreneduction.ui.base;

import com.example.administrator.childreneduction.App;
import com.example.administrator.childreneduction.da.component.ActivityComponent;
import com.example.baselibrary.base.BaseActivity;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/3/7 0007.
 */

public abstract class EduBaseActivity extends BaseActivity {

    public ActivityComponent mActivityComponent;
    /**
     * 初始化dagger2中的componnent
     */
    public abstract void initActivityComponent() ;

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


}
