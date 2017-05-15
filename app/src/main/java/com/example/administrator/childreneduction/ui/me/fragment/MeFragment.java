package com.example.administrator.childreneduction.ui.me.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.me.iview.MeFragmentUI;
import com.example.administrator.childreneduction.ui.me.presenter.MeFragmentPresenter;
import com.example.youngkaaa.ycircleview.CircleView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2017/5/7.
 */

public class MeFragment extends BaseFagment implements MeFragmentUI{
    private LinearLayout mUserLogin;
    private LinearLayout mTvFragMePhone;
    private LinearLayout mTvFragMeWx;
    private LinearLayout mTvFragMqq;
    private LinearLayout mTvFragMeWb;
    private TextView mTvFragMeMesg;
    private TextView mTvFragMeSetting;
    private TextView mTvFragMeSuggest;
    private LinearLayout mUserLogined;
    private CircleView mImgFragMeHead;
    private TextView mTvFragMeName;



    private MeFragmentPresenter mFragmentPresenter;
    private Context mContext;
    private Activity mActivity;

    boolean isauth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UMShareAPI.get(this.getContext()).fetchAuthResultWithBundle(this.getActivity(), savedInstanceState, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize succeed", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize onError", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize onCancel", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getLayOutID() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView(View mRootView) {
        mUserLogin = (LinearLayout) mRootView.findViewById(R.id.user_login);
        mTvFragMePhone = (LinearLayout) mRootView.findViewById(R.id.tv_frag_me_phone);
        mTvFragMeWx = (LinearLayout) mRootView.findViewById(R.id.tv_frag_me_wx);
        mTvFragMqq = (LinearLayout) mRootView.findViewById(R.id.tv_frag_mqq);
        mTvFragMeWb = (LinearLayout) mRootView.findViewById(R.id.tv_frag_me_wb);
        mTvFragMeMesg = (TextView) mRootView.findViewById(R.id.tv_frag_me_mesg);
        mTvFragMeSetting = (TextView) mRootView.findViewById(R.id.tv_frag_me_setting);
        mTvFragMeSuggest = (TextView) mRootView.findViewById(R.id.tv_frag_me_suggest);
        mUserLogined = (LinearLayout) mRootView.findViewById(R.id.user_logined);
        mImgFragMeHead = (CircleView) mRootView.findViewById(R.id.img_frag_me_head);
        mTvFragMeName = (TextView) mRootView.findViewById(R.id.tv_frag_me_name);


        mContext=this.getContext();
        mActivity=this.getActivity();
        setListener();
    }

    @Override
    public void initData() {
        mFragmentPresenter=new MeFragmentPresenter(this);

    }


    /**
     * 设置监听
     */
    private void setListener(){
        //QQ登录
        mTvFragMqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"授权"+isauth,Toast.LENGTH_SHORT);
                isauth = UMShareAPI.get(mContext).isAuthorize(mActivity,SHARE_MEDIA.QQ);
                if (isauth){
                    UMShareAPI.get(mContext).deleteOauth(mActivity, SHARE_MEDIA.QQ, umAuthListener);
                }else {
                    UMShareAPI.get(mContext).getPlatformInfo(mActivity, SHARE_MEDIA.QQ, umAuthListener);
                }
            }
        });
        //微信登录
        mTvFragMeWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"授权"+isauth,Toast.LENGTH_LONG);
                isauth = UMShareAPI.get(mContext).isAuthorize(mActivity,SHARE_MEDIA.WEIXIN);

                if (isauth){
                    UMShareAPI.get(mContext).deleteOauth(mActivity, SHARE_MEDIA.WEIXIN, umAuthListener);
                }else {
                    UMShareAPI.get(mContext).getPlatformInfo(mActivity, SHARE_MEDIA.WEIXIN, umAuthListener);
                }
            }
        });
        //微博登录
        mTvFragMeWb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareConfig config = new UMShareConfig();
                config.setSinaAuthType(UMShareConfig.AUTH_TYPE_WEBVIEW);
                UMShareAPI.get(mContext).setShareConfig(config);
                isauth = UMShareAPI.get(mContext).isAuthorize(mActivity,SHARE_MEDIA.SINA);
                Toast.makeText(mContext,"授权"+isauth,Toast.LENGTH_SHORT);
                if (isauth){
                    UMShareAPI.get(mContext).deleteOauth(mActivity, SHARE_MEDIA.SINA, umAuthListener);
                }else {
                    UMShareAPI.get(mContext).getPlatformInfo(mActivity, SHARE_MEDIA.SINA, umAuthListener);
                }
            }
        });

    }
    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA media, int i, Map<String, String> map) {
            if (map!=null){
                String uid = map.get("uid");
                String name = map.get("name");
                String gender = map.get("gender");
                String iconurl = map.get("iconurl");
                System.out.println("返回信息"+uid+":"+name+":"+iconurl);
                mUserLogin.setVisibility(View.GONE);
                mUserLogined.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(iconurl).into(mImgFragMeHead);
                mTvFragMeName.setText(name);
            }
        }

        @Override
        public void onError(SHARE_MEDIA media, int i, Throwable throwable) {
            Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT);
        }

        @Override
        public void onCancel(SHARE_MEDIA media, int i) {

        }
    };
    @Override
    public void login_ok(Map<String, String> map) {
        String uid = map.get("uid");
        String name = map.get("name");
        String gender = map.get("gender");
        String iconurl = map.get("iconurl");
        Toast.makeText(getContext(),"返回信息"+uid+":"+name+":"+iconurl,Toast.LENGTH_SHORT);
    }

    @Override
    public void login_fail() {
        Toast.makeText(getContext(),"登录失败",Toast.LENGTH_SHORT);
    }

}
