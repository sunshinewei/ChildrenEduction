package com.example.administrator.childreneduction.ui.me.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.InforType;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.ui.me.adapter.MeVideoAdapter;
import com.example.administrator.childreneduction.ui.me.iview.MeVideoUI;
import com.example.administrator.childreneduction.ui.me.presenter.MeVideoPresenter;
import com.example.administrator.childreneduction.ui.vedio.activity.VideoPlayerActivity;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
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
 */

public class MeVideoActivity extends EduBaseActivity implements MeVideoUI, BGARefreshLayout.BGARefreshLayoutDelegate {

    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyActViditem;

    private MeVideoAdapter mVideoAdapter;
    private MeVideoPresenter mVideoPresenter;

    private int state=0;
    private LoginInfo login;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;

    public static Intent createIntent(Context mContext){
        return new Intent(mContext,MeVideoActivity.class);
    }
    @Override
    public int setLayout() {
        return R.layout.activity_mevideo;
    }

    @Override
    public void initView() {
        mRefresh = (BGARefreshLayout) findViewById(R.id.refresh);
        mRecyActViditem = (RecyclerView) findViewById(R.id.recy_act_viditem);

        EventBus.getDefault().post(new InforType("video"));

        initData();
    }

    private void initData(){
        mPrefernceUtils=new SharePrefernceUtils(this, Content.SP_NAME);
        mGson=new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        login = mGson.fromJson(string, LoginInfo.class);

        mVideoPresenter=new MeVideoPresenter(this);

        mRefresh.setDelegate(this);
        BGANormalRefreshViewHolder normalRefreshViewHolder=new BGANormalRefreshViewHolder(this,true);
        mRefresh.setRefreshViewHolder(normalRefreshViewHolder);
        initRecyclerView();
//        mRefresh.beginRefreshing();
        state=0;
        mVideoPresenter.load_mearticle(this,login.getId(),state);
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false){
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                new Spruce.SpruceBuilder(mRecyActViditem)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(mRecyActViditem, 800),
                                ObjectAnimator.ofFloat(mRecyActViditem, "translationX", -mRecyActViditem.getWidth(), 0f).setDuration(800))
                        .start();
            }
        };
        mRecyActViditem.setLayoutManager(manager);
        mRecyActViditem.addItemDecoration(new RecycleViewDivider(this, DividerItemDecoration.VERTICAL,3,R.color.color_text_blank_100));
        mVideoAdapter=new MeVideoAdapter(this);
        mRecyActViditem.setAdapter(mVideoAdapter);

       mVideoAdapter.setOnClickListener(new OnClickListener() {
           @Override
           public void setOnClickListener(View view, int position) {

               VedioTable vedioTable = mVideoAdapter.getList().get(position);
               vedioTable.setType("video");
               Intent intent = VideoPlayerActivity.createIntent(MeVideoActivity.this);
               intent.putExtra("VEDIO", vedioTable);
               startActivity(intent);
           }
       });
    }

    @Override
    public void load_mevideo_ok(List<VedioTable> list) {
        if (state==0){
            mVideoAdapter.refresh(list);
        }
        if (state==1){
            mVideoAdapter.setAddData(list);
        }
        mRefresh.endRefreshing();
    }

    @Override
    public void load_mevideo_fail() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        state=0;
        mVideoPresenter.load_mearticle(this,login.getId(),state);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        state=1;
        mVideoPresenter.load_mearticle(this,login.getId(),state);
        return false;
    }
}
