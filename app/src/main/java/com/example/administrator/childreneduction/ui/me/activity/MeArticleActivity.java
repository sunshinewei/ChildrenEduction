package com.example.administrator.childreneduction.ui.me.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.InforType;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.home.activity.LookArticleActivity;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.ui.me.adapter.MeArticleAdapter;
import com.example.administrator.childreneduction.ui.me.iview.MeArticleUI;
import com.example.administrator.childreneduction.ui.me.presenter.MeArticlePresenter;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.example.baselibrary.base.BaseActivity;
import com.google.gson.Gson;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2017/5/22.
 * 我的文章
 */

public class MeArticleActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate,MeArticleUI {
    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyActItem;

    private MeArticleAdapter mArticleAdapter;
    private MeArticlePresenter mArticlePresenter;

    private int state=0;
    private LoginInfo login;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;


    public static Intent createIntent(Context mContext){
        return new Intent(mContext,MeArticleActivity.class);
    }
    @Override
    public int setLayout() {
        return R.layout.activity_mearticle;
    }

    @Override
    public void initView() {
        mRefresh = (BGARefreshLayout) findViewById(R.id.refresh);
        mRecyActItem = (RecyclerView) findViewById(R.id.recy_act_item);
        EventBus.getDefault().post(new InforType("me"));

        initData();
    }

    private void initData(){
        mPrefernceUtils=new SharePrefernceUtils(this, Content.SP_NAME);
        mGson=new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        login = mGson.fromJson(string, LoginInfo.class);

        mArticlePresenter=new MeArticlePresenter(this);

        mRefresh.setDelegate(this);
        BGANormalRefreshViewHolder normalRefreshViewHolder=new BGANormalRefreshViewHolder(this,true);
        mRefresh.setRefreshViewHolder(normalRefreshViewHolder);
        initRecyclerView();
//        mRefresh.beginRefreshing();
        state=0;
        mArticlePresenter.load_mearticle(this,login.getId(),state);

    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                new Spruce.SpruceBuilder(mRecyActItem)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(mRecyActItem, 800),
                                ObjectAnimator.ofFloat(mRecyActItem, "translationX", -mRecyActItem.getWidth(), 0f).setDuration(800))
                        .start();
            }
        };
        mRecyActItem.setLayoutManager(manager);
        mRecyActItem.addItemDecoration(new RecycleViewDivider(this, DividerItemDecoration.VERTICAL));
        mArticleAdapter=new MeArticleAdapter(this);
        mRecyActItem.setAdapter(mArticleAdapter);

        mArticleAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {

                ArticleTable articleTable = mArticleAdapter.getList().get(position);
                articleTable.setType("me");
                Intent intent = LookArticleActivity.createIntent(MeArticleActivity.this);
                intent.putExtra(Content.ARTICLE_INFO,articleTable);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        state=0;
        mArticlePresenter.load_mearticle(this,login.getId(),state);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        state=1;
        mArticlePresenter.load_mearticle(this,login.getId(),state);
        return false;
    }

    @Override
    public void load_mearticle_ok(List<ArticleTable> list) {
        if (state==0){
            mArticleAdapter.refresh(list);
        }
        if (state==1){
            mArticleAdapter.setAddData(list);
        }
        mRefresh.endRefreshing();
    }

    @Override
    public void load_mearticle_fail() {
    }
}
