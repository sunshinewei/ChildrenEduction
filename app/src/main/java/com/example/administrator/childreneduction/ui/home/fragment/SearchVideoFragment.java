package com.example.administrator.childreneduction.ui.home.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.home.adapter.SearchVideoAdapter;
import com.example.administrator.childreneduction.ui.home.iview.SearchVideoUI;
import com.example.administrator.childreneduction.ui.home.presenter.SearchVideoPresenter;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.ui.vedio.activity.VideoPlayerActivity;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.google.gson.Gson;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2017/5/29.
 * 搜索视频
 */

public class SearchVideoFragment extends BaseFagment implements SearchVideoUI, BGARefreshLayout.BGARefreshLayoutDelegate {
    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyFramVidsearItem;

    private int state = 0;
    private LoginInfo login;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;

    private SearchVideoPresenter mVideoPresenter;
    private SearchVideoAdapter mVideoAdapter;
    private String mLabel;


    public static SearchVideoFragment newInstance() {
        return new SearchVideoFragment();
    }

    @Override
    public int getLayOutID() {
        return R.layout.fragment_search_video;
    }

    @Override
    public void initView(View mRootView) {
        mRefresh = (BGARefreshLayout) mRootView.findViewById(R.id.refresh);
        mRecyFramVidsearItem = (RecyclerView) mRootView.findViewById(R.id.recy_fram_vidsear_item);

        initToData();
    }

    private void initToData() {
        mPrefernceUtils = new SharePrefernceUtils(getContext(), Content.SP_NAME);
        mGson = new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        login = mGson.fromJson(string, LoginInfo.class);

        mVideoPresenter = new SearchVideoPresenter(this);

        mRefresh.setDelegate(this);
        BGANormalRefreshViewHolder normalRefreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        mRefresh.setRefreshViewHolder(normalRefreshViewHolder);
        initRecyclerView();
//        mRefresh.beginRefreshing();

    }


    private void initRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                new Spruce.SpruceBuilder(mRecyFramVidsearItem)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(mRecyFramVidsearItem, 800),
                                ObjectAnimator.ofFloat(mRecyFramVidsearItem, "translationX", -mRecyFramVidsearItem.getWidth(), 0f).setDuration(800))
                        .start();
            }
        };
        mRecyFramVidsearItem.setLayoutManager(manager);
        mRecyFramVidsearItem.addItemDecoration(new RecycleViewDivider(getContext(), DividerItemDecoration.VERTICAL));
        mVideoAdapter = new SearchVideoAdapter(getContext());
        mRecyFramVidsearItem.setAdapter(mVideoAdapter);


        mVideoAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {
                VedioTable vedioTable = mVideoAdapter.getTables().get(position);
                Intent intent = VideoPlayerActivity.createIntent(getContext());
                intent.putExtra("VEDIO",vedioTable);
                startActivity(intent);
            }
        });

        state = 0;
        mVideoPresenter.upload_video(getContext(), state,mLabel);
    }

    /**
     * 设置标签
     *
     * @param label
     */
    public void setLabel(String label) {
        this.mLabel=label;
    }

    @Override
    public void initData() {
    }

    @Override
    public void upload_video_ok(List<VedioTable> list) {
        if (state==0){
            mVideoAdapter.refresh(list);
        }
        if (state==1){
            mVideoAdapter.addData((ArrayList<VedioTable>) list);
        }
        mRefresh.endRefreshing();
        mRefresh.endLoadingMore();
    }

    @Override
    public void upload_video_fail() {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        state=0;
        mVideoPresenter.upload_video(getContext(),state,mLabel);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        state=1;
        mVideoPresenter.upload_video(getContext(),state,mLabel);
        return true;
    }
}
