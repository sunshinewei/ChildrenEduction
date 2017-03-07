package com.example.administrator.childreneduction;

import android.app.Application;
import android.content.Context;

import com.example.administrator.childreneduction.da.component.APPComponent;
import com.example.administrator.childreneduction.da.component.DaggerAPPComponent;
import com.example.administrator.childreneduction.da.module.APPModule;


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
