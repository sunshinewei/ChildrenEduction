package com.example.administrator.childreneduction.ui.home.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.bmob.UA_Table;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.InforType;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.adapter.CommonAdapter;
import com.example.administrator.childreneduction.ui.base.CommonDialog;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.ui.home.iview.LookArticleUI;
import com.example.administrator.childreneduction.ui.home.presenter.LookArticlePresenter;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.picture.Article;
import com.example.administrator.childreneduction.widgets.picture.WebViewHelper;
import com.google.gson.Gson;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/16.
 * 查看文章
 */

public class LookArticleActivity extends EduBaseActivity implements LookArticleUI {
    private LinearLayout mUserTitle;
    private TextView mTvActLookUser;
    private TextView mTvActLookHomeTime;
    private WebView mWbActivityWebviewShow;
    private ImageView mImgActLookContent;
    private ImageView mImgActLookColl;
    private ImageView mImgActLookShare;
    private TextView mTvActLookTitle;
    private Button mBtnGetData;
    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyActContentItem;
    private ImageView mImgActLookClear;


    private long mTime = 0;

    private SharePrefernceUtils mPrefernceUtils;
    private CommonDialog mCommonDialog;

    private LoginInfo loginInfo;
    private ArticleTable extra;
    private LookArticlePresenter mPresenter;

    private CommonAdapter mCommonAdapter;//评论Adapter


    public static Intent createIntent(Context mContext) {
        return new Intent(mContext, LookArticleActivity.class);
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
        EventBus.getDefault().register(this);

        mUserTitle = (LinearLayout) findViewById(R.id.user_title);
        mTvActLookUser = (TextView) findViewById(R.id.tv_act_look_user);
        mTvActLookHomeTime = (TextView) findViewById(R.id.tv_act_look_home_time);
        mWbActivityWebviewShow = (WebView) findViewById(R.id.wb_activity_webview_show);
        mImgActLookContent = (ImageView) findViewById(R.id.img_act_look_content);
        mImgActLookColl = (ImageView) findViewById(R.id.img_act_look_coll);
        mImgActLookShare = (ImageView) findViewById(R.id.img_act_look_share);
        mTvActLookTitle = (TextView) findViewById(R.id.tv_act_look_title);
        mBtnGetData = (Button) findViewById(R.id.btn_getData);
        mRefresh = (BGARefreshLayout) findViewById(R.id.refresh);
        mRecyActContentItem = (RecyclerView) findViewById(R.id.recy_act_content_item);
        mImgActLookClear = (ImageView) findViewById(R.id.img_act_look_clear);

        mPresenter = new LookArticlePresenter(this);
        initData();
        setListener();
        initWebView();
        show_common();
    }

    private void initWebView() {
        mWbActivityWebviewShow.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                mBtnGetData.setVisibility(View.VISIBLE);
            }
        });
        WebViewHelper.getInstance().setUpWebView(mWbActivityWebviewShow, new WebViewHelper.OnGetDataListener() {
            @Override
            public void getDataListener(String text) {
                Intent intent = new Intent(LookArticleActivity.this, GenPictureActivity.class);
                Article article = new Article(extra.getA_content(), extra.getA_title());
//                TextUtils.isEmpty(WebViewHelper.getInstance().getTitle())?"":"《"+WebViewHelper.getInstance().getTitle()+"》"
//                if (extra!=null){
//                    intent.putExtra("data",extra);
//                    System.out.println("data"+extra.getA_title());
//              }
                intent.putExtra("data", article);
                startActivity(intent);
            }
        });
        mWbActivityWebviewShow.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mTime = SystemClock.uptimeMillis();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (SystemClock.uptimeMillis() - mTime < 300) {
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
    private void initData() {

        mPrefernceUtils = new SharePrefernceUtils(mContext, Content.SP_NAME);
        Gson gson = new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        loginInfo = gson.fromJson(string, LoginInfo.class);

        Intent intent = getIntent();
        extra = (ArticleTable) intent.getSerializableExtra(Content.ARTICLE_INFO);

        if (extra.getType() != null) {
            if ("me".equals(extra.getType())) {
                mImgActLookClear.setVisibility(View.VISIBLE);
                mImgActLookColl.setVisibility(View.GONE);
            }
        } else {
            mImgActLookClear.setVisibility(View.GONE);
            mImgActLookColl.setVisibility(View.VISIBLE);
        }

        mTvActLookTitle.setText(extra.getA_title());
        mTvActLookHomeTime.setText("发表日期：" + extra.getCreatedAt());
//        mWbActivityWebviewShow.loadDataWithBaseURL(null,extra.getA_content(), "text/html", "utf-8", null);
        String content = extra.getA_content();
        StringBuffer stringBuffer = new StringBuffer(content);
        String substring = stringBuffer.substring(12, stringBuffer.length() - 2);
        mWbActivityWebviewShow.loadDataWithBaseURL(null, substring, "text/html", "utf-8", null);

        mPresenter.query_Comment(this, extra.getObjectId());
        //收藏
        mImgActLookColl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UA_Table ua_table = new UA_Table();
                ua_table.setU_id(loginInfo.getId());
                ua_table.setU_url(loginInfo.getUrl());
                ua_table.setA_id(extra.getObjectId());
                ua_table.setA_title(extra.getA_title());
                ua_table.setAu_name(extra.getU_name());
                ua_table.setUa_coll("1");
                ua_table.setUa_comm(".");
                ua_table.save(LookArticleActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LookArticleActivity.this, "收藏成功！", Toast.LENGTH_LONG);
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
                if (Build.VERSION.SDK_INT >= 23) {
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(LookArticleActivity.this, mPermissionList, 123);
                }
                new ShareAction(LookArticleActivity.this).withText(extra.getA_title())
                        .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE,
                                SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)
                        .setCallback(mShareListener).open();
            }
        });
        //添加评论
        mImgActLookContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDialog();
            }
        });
        //删除文章
        mImgActLookClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleTable articleTable = new ArticleTable();
                articleTable.setObjectId(extra.getObjectId());
                articleTable.delete(LookArticleActivity.this, new DeleteListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LookArticleActivity.this, "文章删除成功！", Toast.LENGTH_LONG);
                        finish();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Toast.makeText(LookArticleActivity.this, "文章删除失败！", Toast.LENGTH_LONG);
                    }
                });
            }
        });
    }

    private void setDialog() {
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
                    Toast.makeText(LookArticleActivity.this, "内容不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                pub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UA_Table ua_table = new UA_Table();
                        ua_table.setU_id(loginInfo.getId());
                        ua_table.setU_url(loginInfo.getUrl());
                        ua_table.setAu_name(loginInfo.getName());
                        ua_table.setA_id(extra.getObjectId());
                        ua_table.setA_title(extra.getA_title());
                        ua_table.setUa_comm(con);
                        ua_table.setUa_coll("0");
                        mPresenter.addComment(LookArticleActivity.this, ua_table);
                        mCommonAdapter.add_common(null, ua_table);
                    }
                });
            }
        });
    }

    public void show_common() {
        mCommonAdapter = new CommonAdapter(this, "0");
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyActContentItem.setLayoutManager(layoutManager);
        mRecyActContentItem.setAdapter(mCommonAdapter);
    }


    /**
     * 判断从哪个业务逻辑跳进阅读界面
     *
     * @param inforType
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void me_look_article(InforType inforType) {
        if ("me".equals(inforType.getType())) {
            mImgActLookClear.setVisibility(View.VISIBLE);
            mImgActLookColl.setVisibility(View.GONE);
        } else {
            mImgActLookClear.setVisibility(View.GONE);
            mImgActLookColl.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 生成图片按钮
     *
     * @param v
     */
    public void ClickOnSelect(View v) {
        mWbActivityWebviewShow.post(new Runnable() {
            @Override
            public void run() {
                WebViewHelper.getInstance().getSelectedData(mWbActivityWebviewShow);
            }
        });
        mBtnGetData.setVisibility(View.GONE);
    }

    private UMShareListener mShareListener = new UMShareListener() {
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
    private void setListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void addComment_ok() {
        Toast.makeText(this, "发布成功！", Toast.LENGTH_LONG);
        mCommonDialog.dismiss();
    }

    @Override
    public void addComment_fail() {

    }

    @Override
    public void query_comment_ok(List<UA_Table> list) {
        if (list != null && list.size() > 0) {
            mCommonAdapter.addData(list);
        }

    }

    @Override
    public void query_comment_fail() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
