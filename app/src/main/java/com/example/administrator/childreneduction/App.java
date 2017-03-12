package com.example.administrator.childreneduction;

import android.app.Application;
import android.content.Context;

import com.example.administrator.childreneduction.baseAdapter.AndroidLogAdapter;
import com.example.administrator.childreneduction.da.component.APPComponent;
import com.example.administrator.childreneduction.da.component.DaggerAPPComponent;
import com.example.administrator.childreneduction.da.module.APPModule;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;


/**
 * Created by Administrator on 2017/2/28 0028.
 */
public class App extends Application{
    public static Context mContext;
    private static APPComponent mAppComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        mAppComponent = DaggerAPPComponent.builder()
                .aPPModule(new APPModule(this))
                .build();
    }

    /**
     *打印日志
     */
    private void initLogger(){
        Logger.init()                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.NONE)        // default LogLevel.FULL
                .methodOffset(2)               // default 0
                .logAdapter(new AndroidLogAdapter());//default AndroidLogAdapter
    }

    /**
     * 友盟统计初始化
     */
    private void UmengApp(){
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);//场景类型设置接口
    }

    /**
     * 获取Application的context
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

    public static APPComponent getAppComponent(){
        return mAppComponent;
    }


}
