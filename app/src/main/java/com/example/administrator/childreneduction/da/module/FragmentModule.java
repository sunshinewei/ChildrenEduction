package com.example.administrator.childreneduction.da.module;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.example.administrator.childreneduction.da.scope.ContextLife;
import com.example.administrator.childreneduction.da.scope.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

@Module
public class FragmentModule {
    private Fragment mFragment;
    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    @ContextLife("Activity")
    public Context provideActivityContext() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Activity provideActivity() {
        return mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public Fragment provideFragment() {
        return mFragment;
    }
}
