package com.example.administrator.childreneduction.da.module;

import android.app.Activity;
import android.content.Context;

import com.example.administrator.childreneduction.da.scope.ContextLife;
import com.example.administrator.childreneduction.da.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 *Created by Administrator on 2017/3/6 0006.
 */
@Module
public class ActivityModule {

    private Activity mActivity;
    public ActivityModule(Activity mActivity){
        this.mActivity=mActivity;
    }

    @Provides
    @PerActivity
    @ContextLife("Activity")
    public Context provideActivityContext(){
        return mActivity;
    }

    @Provides
    @PerActivity
    public Activity provideActivity(){
        return mActivity;
    }

}
