package com.example.administrator.childreneduction.ui.me.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.UserTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.me.activity.ArticleActivty;
import com.example.administrator.childreneduction.ui.me.activity.MeArticleActivity;
import com.example.administrator.childreneduction.ui.me.activity.MeVideoActivity;
import com.example.administrator.childreneduction.ui.me.activity.SettingActivity;
import com.example.administrator.childreneduction.ui.me.activity.VideoPublishActivity;
import com.example.administrator.childreneduction.ui.me.iview.MeFragmentUI;
import com.example.administrator.childreneduction.ui.me.presenter.MeFragmentPresenter;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.google.gson.Gson;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * Created by Administrator on 2017/5/7.
 */

public class MeFragment extends BaseFagment implements MeFragmentUI {
    private LinearLayout mUserLogined;
    private CircleImageView mImgFragMeHead;
    private TextView mTvFragMeName;
    private TextView mTvFragMePubnum;
    private TextView mTvFragMeCollnum;
    private LinearLayout mUserLogin;
    private LinearLayout mTvFragMePhone;
    private LinearLayout mTvFragMeWx;
    private LinearLayout mTvFragMqq;
    private LinearLayout mTvFragMeWb;
    private TextView mTvFragMeMesg;
    private TextView mTvFragMeSetting;
    private TextView mTvFragMeSuggest;
    private TextView mTvFragMeAdd;
    private TextView mTvFragMeVideo;
    private TextView mTvFragMeMearct;
    private TextView mTvFragMeMevideo;


    private MeFragmentPresenter mFragmentPresenter;
    private Context mContext;
    private Activity mActivity;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson gson;
    private LoginInfo mLoginInfo;

    private boolean isExit;
    boolean isauth;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

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
        mUserLogined = (LinearLayout) mRootView.findViewById(R.id.user_logined);
        mImgFragMeHead = (CircleImageView) mRootView.findViewById(R.id.img_frag_me_head);
        mTvFragMeName = (TextView) mRootView.findViewById(R.id.tv_frag_me_name);
        mTvFragMePubnum = (TextView) mRootView.findViewById(R.id.tv_frag_me_pubnum);
        mTvFragMeCollnum = (TextView) mRootView.findViewById(R.id.tv_frag_me_collnum);
        mUserLogin = (LinearLayout) mRootView.findViewById(R.id.user_login);
        mTvFragMePhone = (LinearLayout) mRootView.findViewById(R.id.tv_frag_me_phone);
        mTvFragMeWx = (LinearLayout) mRootView.findViewById(R.id.tv_frag_me_wx);
        mTvFragMqq = (LinearLayout) mRootView.findViewById(R.id.tv_frag_mqq);
        mTvFragMeWb = (LinearLayout) mRootView.findViewById(R.id.tv_frag_me_wb);
        mTvFragMeMesg = (TextView) mRootView.findViewById(R.id.tv_frag_me_mesg);
        mTvFragMeSetting = (TextView) mRootView.findViewById(R.id.tv_frag_me_setting);
        mTvFragMeSuggest = (TextView) mRootView.findViewById(R.id.tv_frag_me_suggest);
        mTvFragMeAdd = (TextView) mRootView.findViewById(R.id.tv_frag_me_add);
        mTvFragMeVideo = (TextView) mRootView.findViewById(R.id.tv_frag_me_video);
        mTvFragMeMearct = (TextView) mRootView.findViewById(R.id.tv_frag_me_mearct);
        mTvFragMeMevideo = (TextView) mRootView.findViewById(R.id.tv_frag_me_mevideo);

        mContext = this.getContext();
        mActivity = this.getActivity();

        gson = new Gson();
        mPrefernceUtils = new SharePrefernceUtils(getContext(), Content.SP_NAME);
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        mLoginInfo = gson.fromJson(string, LoginInfo.class);

        if (mLoginInfo != null) {
            mUserLogined.setVisibility(View.VISIBLE);
            mUserLogin.setVisibility(View.GONE);
            Glide.with(getContext())
                    .load(mLoginInfo.getUrl())
                    .into(mImgFragMeHead);
            mTvFragMeName.setText(mLoginInfo.getName());
        } else {
            mUserLogined.setVisibility(View.GONE);
            mUserLogin.setVisibility(View.VISIBLE);
        }
        setListener();
    }

    @Override
    public void initData() {
        mFragmentPresenter = new MeFragmentPresenter(this);

    }


    /**
     * 设置监听
     */
    private void setListener() {

        //QQ登录
        mTvFragMqq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "授权" + isauth, Toast.LENGTH_SHORT);
                isauth = UMShareAPI.get(mContext).isAuthorize(mActivity, SHARE_MEDIA.QQ);
//                if (isauth) {
//                    mUserLogin.setVisibility(View.GONE);
//                    mUserLogined.setVisibility(View.VISIBLE);
//                    UMShareAPI.get(mContext).deleteOauth(mActivity, SHARE_MEDIA.QQ, umAuthListener);
//                } else {
//                    mUserLogined.setVisibility(View.GONE);
//                    mUserLogin.setVisibility(View.VISIBLE);
//                }
                UMShareAPI.get(mContext).getPlatformInfo(mActivity, SHARE_MEDIA.QQ, umAuthListener);
            }
        });
        //微信登录
        mTvFragMeWx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "授权" + isauth, Toast.LENGTH_LONG);
                isauth = UMShareAPI.get(mContext).isAuthorize(mActivity, SHARE_MEDIA.WEIXIN);
                if (isauth) {
//                    UMShareAPI.get(mContext).deleteOauth(mActivity, SHARE_MEDIA.WEIXIN, umAuthListener);
                } else {
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
                isauth = UMShareAPI.get(mContext).isAuthorize(mActivity, SHARE_MEDIA.SINA);
                Toast.makeText(mContext, "授权" + isauth, Toast.LENGTH_SHORT);
                if (isauth) {
//                    UMShareAPI.get(mContext).deleteOauth(mActivity, SHARE_MEDIA.SINA, umAuthListener);
                } else {
                    UMShareAPI.get(mContext).getPlatformInfo(mActivity, SHARE_MEDIA.SINA, umAuthListener);
                }
            }
        });
        //添加文章
        mTvFragMeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = mPrefernceUtils.getString(Content.SP_NAME);
                mLoginInfo = gson.fromJson(string, LoginInfo.class);
                if (mLoginInfo != null) {
                    startActivity(ArticleActivty.createInent(getContext()));
                } else {
                    Toast.makeText(getContext(), "请用户登录！", Toast.LENGTH_LONG).show();
                }
            }
        });
        //添加视频
        mTvFragMeVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = mPrefernceUtils.getString(Content.SP_NAME);
                mLoginInfo = gson.fromJson(string, LoginInfo.class);
                if (mLoginInfo != null) {
                    Intent mIntent = new Intent(getContext(), VideoPublishActivity.class);
                    startActivity(mIntent);
                } else {
                    Toast.makeText(getContext(), "请用户登录！", Toast.LENGTH_LONG).show();
                }

            }
        });
        //我的文章
        mTvFragMeMearct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = mPrefernceUtils.getString(Content.SP_NAME);
                mLoginInfo = gson.fromJson(string, LoginInfo.class);
                if (mLoginInfo != null) {
                    startActivity(MeArticleActivity.createIntent(getContext()));
                } else {
                    Toast.makeText(getContext(), "请用户登录！", Toast.LENGTH_LONG).show();
                }
            }
        });
        //我的视频
        mTvFragMeMevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLoginInfo != null) {
                    startActivity(MeVideoActivity.createIntent(getContext()));
                } else {
                    Toast.makeText(getContext(), "请用户登录！", Toast.LENGTH_LONG).show();
                }
            }
        });
        //系统设置
        mTvFragMeSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String string = mPrefernceUtils.getString(Content.SP_NAME);
                mLoginInfo = gson.fromJson(string, LoginInfo.class);
                if (mLoginInfo!=null){
                    startActivity(SettingActivity.createIntent(getContext()));
                }else {
                    Toast.makeText(getContext(), "请用户登录！", Toast.LENGTH_LONG).show();
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
            if (map != null) {
                queryData(map);
            }
        }

        @Override
        public void onError(SHARE_MEDIA media, int i, Throwable throwable) {
            Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA media, int i) {

        }
    };

    @Override
    public void login_ok(Map<String, String> map) {
//        queryData(map);

    }

    /**
     * 查询用户是否存在，如果存在不添加
     */
    private void queryData(Map<String, String> map) {
        final String uid = map.get("uid");
        final String name = map.get("name");
        final String gender = map.get("gender");
        final String iconurl = map.get("iconurl");
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setId(uid);
        loginInfo.setName(name);
        loginInfo.setSex(gender);
        loginInfo.setUrl(iconurl);
        Gson gson = new Gson();
        String s = gson.toJson(loginInfo);
        SharePrefernceUtils sharePrefernceUtils = new SharePrefernceUtils(getContext(), Content.SP_NAME);
        sharePrefernceUtils.putString(Content.SP_NAME, s);
        mUserLogin.setVisibility(View.GONE);
        mUserLogined.setVisibility(View.VISIBLE);
        Glide.with(mContext).load(iconurl).into(mImgFragMeHead);
        mTvFragMeName.setText(name);

        BmobQuery<UserTable> query = new BmobQuery<>();
        query.addWhereEqualTo("u_id", uid);
        query.findObjects(getContext(), new FindListener<UserTable>() {
            @Override
            public void onSuccess(List<UserTable> list) {
                if (list.size() > 0) {
                    System.out.println("存在此用户，不需要再次添加！");
                    isExit = true;
                } else {
                    System.out.println("size" + list.size());
                    UserTable userTable = new UserTable();
                    userTable.setU_id(uid);
                    userTable.setU_name(name);
                    userTable.setU_img(iconurl);
                    userTable.setU_sex(gender);
                    userTable.save(getContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            System.out.println("用户保存成功！");
                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
        Toast.makeText(getContext(), "返回信息" + uid + ":" + name + ":" + iconurl, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void login_fail() {
        Toast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
    }

}
