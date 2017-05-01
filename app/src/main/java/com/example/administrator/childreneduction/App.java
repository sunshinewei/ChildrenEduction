package com.example.administrator.childreneduction;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.administrator.childreneduction.baseAdapter.AndroidLogAdapter;
import com.example.administrator.childreneduction.da.component.APPComponent;
import com.example.administrator.childreneduction.da.component.DaggerAPPComponent;
import com.example.administrator.childreneduction.da.module.APPModule;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.umeng.analytics.MobclickAgent;

import cn.bmob.v3.Bmob;
import cn.jpush.android.api.JPushInterface;


/**
 * Created by Administrator on 2017/2/28 0028.
 */
public class App extends Application {
    public static Context mContext;
    private static APPComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mAppComponent = DaggerAPPComponent.builder()
                .aPPModule(new APPModule(this))
                .build();

        initJpush();
        initCrashHandler();
        initLogger();
        initUmeng();
    }

    private void initBmob(){
        //第一：默认初始化
        Bmob.initialize(this, "4f44154c942912226ccb1fb492f4c3eb");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
    /**
     * 打印日志
     */
    private void initLogger() {
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
    private void initUmeng() {
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);//场景类型设置接口
    }

    /**
     * 初始化极光推送
     */
    private void initJpush() {
        JPushInterface.setDebugMode(true);//设置为调试模式
        JPushInterface.init(this);//初始化SDK
    }

    /**
     * 获取Application的context
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    public static APPComponent getAppComponent() {
        return mAppComponent;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode() {
        return getPackageInfo().versionCode;
    }

    /**
     * 获取版本名
     *
     * @return
     */
    public static String getVersionName() {
        return getPackageInfo().versionName;
    }

    /**
     * @return
     */
    private static PackageInfo getPackageInfo() {
        PackageManager manager = mAppComponent.getConext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(mAppComponent.getConext().getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return info;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 初始化CrashHandler中的Crash信息
     */
    private void initCrashHandler(){
        CrashHandler mCrashHandler=CrashHandler.getInstance();
        mCrashHandler.init(mAppComponent.getConext());
    }
}
