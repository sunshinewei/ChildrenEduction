package com.example.administrator.childreneduction.ui.vedio.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.UV_Table;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.InforType;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.adapter.CommonAdapter;
import com.example.administrator.childreneduction.ui.base.CommonDialog;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.baselibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/21.
 */

public class VideoPlayerActivity extends BaseActivity implements EasyVideoCallback {
    private EasyVideoPlayer mEvpActVpPlayer;
    private ImageView mImgAdapterHomeColl;
    private ImageView mImgAdapterHomeShare;
    private RecyclerView mRecyActComm;
    private ImageView mImgActComm;
    private ImageView mImgActClear;

    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;
    private LoginInfo mLoginInfo;
    private CommonDialog mCommonDialog;
    private CommonAdapter mCommonAdapter;

    private String mVideoPath;


    public static Intent createIntent(Context mContext) {
        return new Intent(mContext, VideoPlayerActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_videoplayer;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);

        mEvpActVpPlayer = (EasyVideoPlayer) findViewById(R.id.evp_act_vp_player);
        mImgAdapterHomeColl = (ImageView) findViewById(R.id.img_adapter_home_coll);
        mImgAdapterHomeShare = (ImageView) findViewById(R.id.img_adapter_home_share);
        mRecyActComm = (RecyclerView) findViewById(R.id.recy_act_comm);
        mImgActComm = (ImageView) findViewById(R.id.img_act_comm);
        mImgActClear = (ImageView) findViewById(R.id.img_act_clear);

        initData();
    }

    /**
     * 初始化评论
     */
    private void initCommon(VedioTable vedio){
        mCommonAdapter=new CommonAdapter(this,"1");
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyActComm.setLayoutManager(layoutManager);
        mRecyActComm.setAdapter(mCommonAdapter);
        BmobQuery<UV_Table> query=new BmobQuery<>();
        query.addWhereEqualTo("v_id",vedio.getObjectId());
//        query.addWhereNotEqualTo("uv_comm",".");
        query.findObjects(this, new FindListener<UV_Table>() {
            @Override
            public void onSuccess(List<UV_Table> list) {
                System.out.println("加载评论成功");
                mCommonAdapter.addData(list);
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }


    private void initData() {
        mPrefernceUtils = new SharePrefernceUtils(this, Content.SP_NAME);
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        mGson = new Gson();
        mLoginInfo = mGson.fromJson(string, LoginInfo.class);
        Intent intent = getIntent();
        VedioTable vedio = (VedioTable) intent.getSerializableExtra("VEDIO");

        if (vedio.getType()!=null){
            if ("video".equals(vedio.getType())){
                mImgActClear.setVisibility(View.VISIBLE);
                mImgAdapterHomeColl.setVisibility(View.GONE);
            }
        }else {
                mImgActClear.setVisibility(View.GONE);
                mImgAdapterHomeColl.setVisibility(View.VISIBLE);
        }

        mVideoPath=vedio.getV_url();
//
        initCommon(vedio);
        mEvpActVpPlayer.setCallback(this);
//        mEvpActVpPlayer.setAutoPlay(false);
        mEvpActVpPlayer.setInitialPosition(0);
        mEvpActVpPlayer.setSource(Uri.parse(vedio.getV_url()));
        mEvpActVpPlayer.stop();
        setListner(vedio);
    }

    /**
     * 从哪个条目中跳转到视频播放
     * @param inforType
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void me_look_video(InforType inforType){
        System.out.println("我的界面进来");
        if ("video".equals(inforType.getType())){
            mImgActClear.setVisibility(View.VISIBLE);
            mImgAdapterHomeColl.setVisibility(View.GONE);
        }
        else {
            mImgActClear.setVisibility(View.GONE);
            mImgAdapterHomeColl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置监听
     */
    public void setListner(final VedioTable vedio) {
        //收藏
        mImgAdapterHomeColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UV_Table uv_table = new UV_Table();
                uv_table.setU_id(mLoginInfo.getId());
                uv_table.setU_url(mLoginInfo.getUrl());
                uv_table.setV_id(vedio.getObjectId());
                uv_table.setVu_name(vedio.getU_name());
                uv_table.setVu_title(vedio.getV_title());
                uv_table.setUv_url(vedio.getV_url());
                uv_table.setUv_coll("1");
                uv_table.setUv_comm(".");
                uv_table.save(VideoPlayerActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(VideoPlayerActivity.this, "收藏成功！", Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(VideoPlayerActivity.this, "收藏失败！", Toast.LENGTH_LONG);
                    }
                });

            }
        });
        //分享
        mImgAdapterHomeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(VideoPlayerActivity.this, mPermissionList, 123);
                }
                new ShareAction(VideoPlayerActivity.this).withText(vedio.getV_title())
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                        .setCallback(mShareListener).open();
            }
        });
        //添加评论
        mImgActComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog(vedio);
            }
        });

        //删除此文章
        mImgActClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VedioTable vedioTable=new VedioTable();
                vedioTable.setObjectId(vedio.getObjectId());
                vedioTable.delete(VideoPlayerActivity.this, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(VideoPlayerActivity.this,"视频删除成功！",Toast.LENGTH_LONG);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        });
    }

    private void setDialog(final VedioTable vedio) {
        mCommonDialog = new CommonDialog(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_common, null, false);
        mCommonDialog.setContentView(inflate);
        mCommonDialog.show();
        mCommonDialog.setLayoutAttrebutes();
        final EditText content = (EditText) inflate.findViewById(R.id.edt_dia_comm);
        final TextView pub = (TextView) inflate.findViewById(R.id.tv_dia_pub);
        pub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String con = content.getText().toString().trim();
                if (con.length() == 0) {
                    Toast.makeText(VideoPlayerActivity.this, "内容不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                pub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UV_Table uv_table=new UV_Table();
                        uv_table.setU_id(mLoginInfo.getId());
                        uv_table.setVu_name(mLoginInfo.getName());
                        uv_table.setU_url(mLoginInfo.getUrl());
                        uv_table.setVu_name(mLoginInfo.getName());
                        uv_table.setV_id(vedio.getObjectId());
                        uv_table.setUv_comm(con);
                        uv_table.setUv_coll("0");

                        uv_table.save(VideoPlayerActivity.this, new SaveListener() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(VideoPlayerActivity.this,"添加评论成功！",Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                                Toast.makeText(VideoPlayerActivity.this,"添加评论失败！",Toast.LENGTH_LONG);
                            }
                        });
                       mCommonDialog.dismiss();
                    }
                });
            }
        });
    }

    private UMShareListener mShareListener = new UMShareListener() {
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
        System.out.println("准备中");
    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        System.out.println("准备结束");
    }

    @Override
    public void onBuffering(int percent) {
        System.out.println("缓存中");
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
//        mEvpActVpPlayer.setSource();
        System.out.println("点击提交，加载数据中");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
