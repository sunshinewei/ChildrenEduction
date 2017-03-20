package com.example.administrator.childreneduction.baseAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 * FragmentStatePagerAdapter  //展示的视图是Fragment，并且数量比较多时。在destroyItem方法 调用时移除对应的Fragment。
 * FragmentPagerAdapter  //所展示的视图是Fragment，并且数量比较少。在destroyItem方法调用时只是detach对应的Fragment，并没有真正移除！
 * PagerAdapter  //视图比较简单时使用
 */

public class BaseViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList=new ArrayList<>();
    public BaseViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    /**
     * 添加Fragment
     * @param mFragment
     */
    public void addFragment(Fragment mFragment){
        this.mFragmentList.add(mFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }



}
