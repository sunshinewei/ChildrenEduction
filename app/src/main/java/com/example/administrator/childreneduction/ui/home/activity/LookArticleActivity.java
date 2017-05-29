package com.example.administrator.childreneduction.ui.home.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.view.ContextMenu;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.bmob.UA_Table;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.picture.Article;
import com.example.administrator.childreneduction.widgets.picture.WebViewHelper;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/16.
 * 查看文章
 */

public class LookArticleActivity extends EduBaseActivity {
    private LinearLayout mUserTitle;
    private TextView mTvActLookUser;
    private TextView mTvActLookHomeTime;
    private WebView mWbActivityWebviewShow;
    private ImageView mImgActLookContent;
    private ImageView mImgActLookColl;
    private ImageView mImgActLookShare;
    private TextView mTvActLookTitle;
    private Button mBtnGetData;

    private long mTime=0;

    private SharePrefernceUtils mPrefernceUtils;

    private LoginInfo loginInfo;
    private ArticleTable extra;

    public static Intent createIntent(Context mContext){
        return new Intent(mContext,LookArticleActivity.class);
    }


    @Override
    public void initActivityComponent() {

    }

    @Override
    public int setLayout() {
        return R.layout.activity_lookarticle;
    }

    @Override
    public void initView() {
        mUserTitle = (LinearLayout) findViewById(R.id.user_title);
        mTvActLookUser = (TextView) findViewById(R.id.tv_act_look_user);
        mTvActLookHomeTime = (TextView) findViewById(R.id.tv_act_look_home_time);
        mWbActivityWebviewShow = (WebView) findViewById(R.id.wb_activity_webview_show);
        mImgActLookContent = (ImageView) findViewById(R.id.img_act_look_content);
        mImgActLookColl = (ImageView) findViewById(R.id.img_act_look_coll);
        mImgActLookShare = (ImageView) findViewById(R.id.img_act_look_share);
        mTvActLookTitle = (TextView) findViewById(R.id.tv_act_look_title);
        mBtnGetData = (Button) findViewById(R.id.btn_getData);

        initData();
        setListener();
        initWebView();
    }

    private void initWebView(){
        mWbActivityWebviewShow.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                mBtnGetData.setVisibility(View.VISIBLE);
            }
        });
        WebViewHelper.getInstance().setUpWebView(mWbActivityWebviewShow, new WebViewHelper.OnGetDataListener() {
            @Override
            public void getDataListener(String text) {
                Intent intent = new Intent(LookArticleActivity.this,GenPictureActivity.class);
                Article article = new Article(text,extra.getA_title());
//                TextUtils.isEmpty(WebViewHelper.getInstance().getTitle())?"":"《"+WebViewHelper.getInstance().getTitle()+"》"
//                if (extra!=null){
//                    intent.putExtra("data",extra);
//                    System.out.println("data"+extra.getA_title());
//                }
                intent.putExtra("data",article);
                startActivity(intent);
            }
        });
        mWbActivityWebviewShow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        mTime = SystemClock.uptimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        if(SystemClock.uptimeMillis() - mTime < 300){
                            mBtnGetData.setVisibility(View.GONE);
                        }
                        break;
                }
                return false;
            }
        });
    }

    /**
     *
     */
    private void initData(){

        mPrefernceUtils=new SharePrefernceUtils(mContext, Content.SP_NAME);
        Gson gson = new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        loginInfo = gson.fromJson(string, LoginInfo.class);

        Intent intent = getIntent();
        extra= (ArticleTable) intent.getSerializableExtra(Content.ARTICLE_INFO);
        mTvActLookTitle.setText(extra.getA_title());
        mTvActLookHomeTime.setText("发表日期："+extra.getCreatedAt());
//        mWbActivityWebviewShow.loadDataWithBaseURL(null,extra.getA_content(), "text/html", "utf-8", null);
        String content = extra.getA_content();
        StringBuffer stringBuffer=new StringBuffer(content);
        String substring = stringBuffer.substring(12, stringBuffer.length() - 2);
        mWbActivityWebviewShow.loadDataWithBaseURL(null,substring, "text/html", "utf-8", null);

        //收藏
        mImgActLookColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UA_Table ua_table=new UA_Table();
                ua_table.setU_id(loginInfo.getId());
                ua_table.setA_id(extra.getObjectId());
                ua_table.setA_title(extra.getA_title());
                ua_table.setAu_name(extra.getU_name());
                ua_table.setUa_coll("1");
                ua_table.save(LookArticleActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LookArticleActivity.this,"收藏成功！",Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });
            }
        });
        //分享
        mImgActLookShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(LookArticleActivity.this,mPermissionList,123);
                }
                new ShareAction(LookArticleActivity.this).withText(extra.getA_title())
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                        .setCallback(mShareListener).open();
            }
        });
    }

    /**
     * 生成图片按钮
     * @param v
     */
    public void ClickOnSelect(View v){
        mWbActivityWebviewShow.post(new Runnable() {
            @Override
            public void run() {
                WebViewHelper.getInstance().getSelectedData(mWbActivityWebviewShow);
            }
        });
        mBtnGetData.setVisibility(View.GONE);
    }

    private UMShareListener mShareListener=new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            if (platform.name().equals("WEIXIN_FAVORITE")) {
                Toast.makeText(LookArticleActivity.this, platform + " 收藏成功啦", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(LookArticleActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
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


    //设置监听
    private void setListener(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

}
