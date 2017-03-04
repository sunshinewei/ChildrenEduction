package com.example.administrator.childreneduction;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/2/28 0028.
 */
public class App extends Application{
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();

    }

    /**
     * 获取Application的context
     * @return
     */
    public static Context getContext(){
        return mContext;
    }
}
