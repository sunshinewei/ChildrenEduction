package com.example.administrator.childreneduction.ui.vedio.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.UV_Table;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.baselibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/21.
 */

public class VideoPlayerActivity extends BaseActivity implements EasyVideoCallback {
    private EasyVideoPlayer mEvpActVpPlayer;
    private ImageView mImgAdapterHomeColl;
    private ImageView mImgAdapterHomeShare;

    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;
    private LoginInfo mLoginInfo;

    public static Intent createIntent(Context mContext){
        return new Intent(mContext,VideoPlayerActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_videoplayer;
    }

    @Override
    public void initView() {
        mEvpActVpPlayer = (EasyVideoPlayer) findViewById(R.id.evp_act_vp_player);
        mImgAdapterHomeColl = (ImageView) findViewById(R.id.img_adapter_home_coll);
        mImgAdapterHomeShare = (ImageView) findViewById(R.id.img_adapter_home_share);
        initData();
    }

    private void initData(){
        mPrefernceUtils=new SharePrefernceUtils(this, Content.SP_NAME);
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        mGson=new Gson();
        mLoginInfo = mGson.fromJson(string, LoginInfo.class);
        Intent intent = getIntent();
        VedioTable vedio =(VedioTable) intent.getSerializableExtra("VEDIO");
        mEvpActVpPlayer.setCallback(this);
        mEvpActVpPlayer.setSource(Uri.parse(vedio.getV_url()));

        setListner(vedio);
    }

    /**
     * 设置监听
     */
    public void setListner(final VedioTable vedio){
        //收藏
        mImgAdapterHomeColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UV_Table uv_table=new UV_Table();
                uv_table.setU_id(mLoginInfo.getId());
                uv_table.setV_id(vedio.getV_id());
                System.out.println("v_id"+vedio.getV_id());
                uv_table.setVu_name(vedio.getU_name());
                uv_table.setVu_title(vedio.getV_title());
                uv_table.setUv_url(vedio.getV_url());
                uv_table.save(VideoPlayerActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(VideoPlayerActivity.this,"收藏成功！",Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(VideoPlayerActivity.this,"收藏失败！",Toast.LENGTH_LONG);
                    }
                });

            }
        });
        //分享
        mImgAdapterHomeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(VideoPlayerActivity.this,mPermissionList,123);
                }
                new ShareAction(VideoPlayerActivity.this).withText(vedio.getV_title())
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                        .setCallback(mShareListener).open();
            }
        });
    }
    private UMShareListener mShareListener=new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(VideoPlayerActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
            } else {
                if (platform != SHARE_MEDIA.MORE && platform != SHARE_MEDIA.SMS
                        && platform != SHARE_MEDIA.EMAIL
                        && platform != SHARE_MEDIA.FLICKR
                        && platform != SHARE_MEDIA.FOURSQUARE
                        && platform != SHARE_MEDIA.TUMBLR
                        && platform != SHARE_MEDIA.POCKET
                        && platform != SHARE_MEDIA.PINTEREST

                        && platform != SHARE_MEDIA.INSTAGRAM
                        && platform != SHARE_MEDIA.GOOGLEPLUS
                        && platform != SHARE_MEDIA.YNOTE
                        && platform != SHARE_MEDIA.EVERNOTE) {
                    Toast.makeText(VideoPlayerActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
                }

            }

        }

        @Override
        public void onError(SHARE_MEDIA media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA media) {

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }


    @Override
    public void onPause() {
        super.onPause();
        // Make sure the player stops playing if the user presses the home button.
        mEvpActVpPlayer.pause();
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {

    }

    @Override
    public void onPaused(EasyVideoPlayer player) {

    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {

    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {

    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {

    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {

    }

    @Override
    public void onRetry(EasyVideoPlayer player, Uri source) {

    }

    @Override
    public void onSubmit(EasyVideoPlayer player, Uri source) {

    }
}
