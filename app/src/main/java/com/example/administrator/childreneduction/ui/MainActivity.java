package com.example.administrator.childreneduction.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.ui.coll.fragment.CollectFragment;
import com.example.administrator.childreneduction.ui.home.fragment.HomeFragment;
import com.example.administrator.childreneduction.ui.me.fragment.MeFragment;
import com.example.administrator.childreneduction.ui.vedio.fragment.VedioFragment;
import com.umeng.socialize.UMShareAPI;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页
 */
public class MainActivity extends EduBaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private ViewPager mVpActMainShow;
    private LinearLayout mLinActMainBottom;
    private TextView mTvActMainHomeimg;
    private TextView mTvActMainHometext;
    private TextView mTvActMainVedioimg;
    private TextView mTvActMainVediotext;
    private TextView mTvActMainCollimg;
    private TextView mTvActMainColltext;
    private TextView mTvActMainMeimg;
    private TextView mTvActMainMetext;


    private long start;
    private boolean isThemeDay = false;

    private List<Fragment> mFragments;
    private HomeFragment mHomeFragment;
    private CollectFragment mCollectFragment;
    private MeFragment mMeFragment;
    private VedioFragment mVedioFragment;
    private VPAdapter mVPAdapter;

    @Override
    public void initActivityComponent() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        mVpActMainShow = (ViewPager) findViewById(R.id.vp_act_main_show);
        mLinActMainBottom = (LinearLayout) findViewById(R.id.lin_act_main_bottom);
        mTvActMainHomeimg = (TextView) findViewById(R.id.tv_act_main_homeimg);
        mTvActMainHometext = (TextView) findViewById(R.id.tv_act_main_hometext);
        mTvActMainVedioimg = (TextView) findViewById(R.id.tv_act_main_vedioimg);
        mTvActMainVediotext = (TextView) findViewById(R.id.tv_act_main_vediotext);
        mTvActMainCollimg = (TextView) findViewById(R.id.tv_act_main_collimg);
        mTvActMainColltext = (TextView) findViewById(R.id.tv_act_main_colltext);
        mTvActMainMeimg = (TextView) findViewById(R.id.tv_act_main_meimg);
        mTvActMainMetext = (TextView) findViewById(R.id.tv_act_main_metext);

        initFragment();
        initViewPager();
        setListener();
    }

    private void setListener() {
        mTvActMainHomeimg.setOnClickListener(this);
        mTvActMainMeimg.setOnClickListener(this);
        mTvActMainVedioimg.setOnClickListener(this);
        mTvActMainCollimg.setOnClickListener(this);
    }

    /**
     * 初始化Fragment
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mCollectFragment = CollectFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
        mHomeFragment = HomeFragment.newInstance();
        mVedioFragment = VedioFragment.newInstance();
        mFragments.add(mHomeFragment);
        mFragments.add(mVedioFragment);
        mFragments.add(mCollectFragment);
        mFragments.add(mMeFragment);
    }

    /**
     * 初始化ViewPager
     */
    private void initViewPager() {
        FragmentManager manager = getSupportFragmentManager();
        mVPAdapter = new VPAdapter(manager, mFragments);
        mVpActMainShow.setAdapter(mVPAdapter);
        mVpActMainShow.addOnPageChangeListener(this);
    }


    public void setAPPTheme() {
        if (isThemeDay) {
            setTheme(R.style.commonTheme_Night);
            isThemeDay = true;
        } else {
            setTheme(R.style.commonTheme_Day);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis - start >= 3000) {
            showToast("请再按一次退出！");
            start = currentTimeMillis;
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                mVpActMainShow.setCurrentItem(position);
                initButton(R.id.tv_act_main_homeimg,0);
                break;
            case 1:
                initButton(R.id.tv_act_main_vedioimg,1);
                mVpActMainShow.setCurrentItem(position);
                break;
            case 2:
                initButton(R.id.tv_act_main_collimg,2);
                mVpActMainShow.setCurrentItem(position);
                break;
            case 3:
                initButton(R.id.tv_act_main_meimg,3);
                mVpActMainShow.setCurrentItem(position);
                break;
        }
    }



    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_act_main_homeimg:
                mVpActMainShow.setCurrentItem(0);
                initButton(v.getId(),6);
                break;
            case R.id.tv_act_main_vedioimg:
                mVpActMainShow.setCurrentItem(1);
                initButton(v.getId(),6);
                break;
            case R.id.tv_act_main_collimg:
                mVpActMainShow.setCurrentItem(2);
                initButton(v.getId(),6);
                break;
            case R.id.tv_act_main_meimg:
                mVpActMainShow.setCurrentItem(3);
                initButton(v.getId(),6);
                break;
        }
    }

    /**
     *
     */
    class VPAdapter extends FragmentPagerAdapter {

        private List<Fragment> mFragments;

        public VPAdapter(FragmentManager fm, List<Fragment> mList) {
            super(fm);
            this.mFragments = mList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments != null ? mFragments.size() : 0;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 显示按钮和字体
     * @param id
     * @param position
     */
    private void initButton(int id,int position ){
        if (position==0 || id==R.id.tv_act_main_homeimg){
            mTvActMainHomeimg.setBackground(getResources().getDrawable(R.mipmap.articled));
            mTvActMainHometext.setTextColor(getResources().getColor(R.color.color_toolbar_background_red));

            mTvActMainVedioimg.setBackground(getResources().getDrawable(R.mipmap.video));
            mTvActMainVediotext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainCollimg.setBackground(getResources().getDrawable(R.mipmap.collect));
            mTvActMainColltext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainMeimg.setBackground(getResources().getDrawable(R.mipmap.me));
            mTvActMainMetext.setTextColor(getResources().getColor(R.color.color_text_blank_800));
        }
        if (position==1 || id==R.id.tv_act_main_vedioimg){
            mTvActMainHomeimg.setBackground(getResources().getDrawable(R.mipmap.arctle));
            mTvActMainHometext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainVedioimg.setBackground(getResources().getDrawable(R.mipmap.videoed));
            mTvActMainVediotext.setTextColor(getResources().getColor(R.color.color_toolbar_background_red));

            mTvActMainCollimg.setBackground(getResources().getDrawable(R.mipmap.collect));
            mTvActMainColltext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainMeimg.setBackground(getResources().getDrawable(R.mipmap.me));
            mTvActMainMetext.setTextColor(getResources().getColor(R.color.color_text_blank_800));
        }
        if (position==2 || id==R.id.tv_act_main_collimg){
            mTvActMainHomeimg.setBackground(getResources().getDrawable(R.mipmap.arctle));
            mTvActMainHometext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainVedioimg.setBackground(getResources().getDrawable(R.mipmap.video));
            mTvActMainVediotext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainCollimg.setBackground(getResources().getDrawable(R.mipmap.collected));
            mTvActMainColltext.setTextColor(getResources().getColor(R.color.color_toolbar_background_red));

            mTvActMainMeimg.setBackground(getResources().getDrawable(R.mipmap.me));
            mTvActMainMetext.setTextColor(getResources().getColor(R.color.color_text_blank_800));
        }
        if (position==3 || id==R.id.tv_act_main_meimg){
            mTvActMainHomeimg.setBackground(getResources().getDrawable(R.mipmap.arctle));
            mTvActMainHometext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainVedioimg.setBackground(getResources().getDrawable(R.mipmap.video));
            mTvActMainVediotext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainCollimg.setBackground(getResources().getDrawable(R.mipmap.collect));
            mTvActMainColltext.setTextColor(getResources().getColor(R.color.color_text_blank_800));

            mTvActMainMeimg.setBackground(getResources().getDrawable(R.mipmap.med));
            mTvActMainMetext.setTextColor(getResources().getColor(R.color.color_toolbar_background_red));
        }
    }

}
