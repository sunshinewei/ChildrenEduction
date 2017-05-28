package com.example.administrator.childreneduction.ui.coll.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.UV_Table;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.coll.adapter.VideoCollAdapter;
import com.example.administrator.childreneduction.ui.coll.iview.VideoCollUI;
import com.example.administrator.childreneduction.ui.coll.presenter.VideoCollPresenter;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.google.gson.Gson;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2017/5/21.
 */

public class VideoCollFragment extends BaseFagment implements BGARefreshLayout.BGARefreshLayoutDelegate,VideoCollUI{
    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyFramVideocollItem;

    private VideoCollAdapter mCollectAdapter;
    private VideoCollPresenter mCollPresenter;
    private int state=0;
    private LoginInfo login;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static VideoCollFragment newInsatance(){
        return new VideoCollFragment();
    }
    @Override
    public int getLayOutID() {
        return R.layout.fragment_videocoll;
    }

    @Override
    public void initView(View mRootView) {
        mRefresh = (BGARefreshLayout) mRootView.findViewById(R.id.refresh);
        mRecyFramVideocollItem = (RecyclerView) mRootView.findViewById(R.id.recy_fram_videocoll_item);
        initLoadData();
    }

    /**
     * 加载数据
     */
    private void initLoadData(){
        mPrefernceUtils=new SharePrefernceUtils(getContext(), Content.SP_NAME);
        mGson=new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        login = mGson.fromJson(string, LoginInfo.class);
        mCollPresenter=new VideoCollPresenter(this);

        mRefresh.setDelegate(this);
        BGANormalRefreshViewHolder normalRefreshViewHolder=new BGANormalRefreshViewHolder(getContext(),true);
        mRefresh.setRefreshViewHolder(normalRefreshViewHolder);
        initRecyclerView();

        state=0;
        mCollPresenter.coll_video(getContext(),state,login);
    }

    @Override
    public void initData() {
//        mRefresh.beginRefreshing();
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFramVideocollItem.setLayoutManager(manager);
        mRecyFramVideocollItem.addItemDecoration(new RecycleViewDivider(getContext(), DividerItemDecoration.VERTICAL));
        mCollectAdapter=new VideoCollAdapter(getContext());
        mRecyFramVideocollItem.setAdapter(mCollectAdapter);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        state=0;
        mCollPresenter.coll_video(getContext(),state,login);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        state=1;
        mCollPresenter.coll_video(getContext(),state,login);
        return false;
    }

    @Override
    public void video_coll_data_ok(List<UV_Table> list) {
        if (state==0){
            mCollectAdapter.refresh(list);
        }
        if (state==1){
            mCollectAdapter.setAddData(list);
        }
        mRefresh.endRefreshing();
    }

    @Override
    public void video_coll_data_fail() {

    }
}
