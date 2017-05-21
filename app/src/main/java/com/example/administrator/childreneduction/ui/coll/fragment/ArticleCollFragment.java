package com.example.administrator.childreneduction.ui.coll.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.UA_Table;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.coll.adapter.ArticleCollAdapter;
import com.example.administrator.childreneduction.ui.coll.iview.ArticleFragmentUI;
import com.example.administrator.childreneduction.ui.coll.presenter.ArticleCollPresenter;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.google.gson.Gson;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2017/5/21.
 */

public class ArticleCollFragment extends BaseFagment implements BGARefreshLayout.BGARefreshLayoutDelegate,ArticleFragmentUI{
    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyFramArtcollItem;

    private ArticleCollAdapter mCollectAdapter;
    private ArticleCollPresenter mCollPresenter;
    private int state=0;
    private LoginInfo login;
    private SharePrefernceUtils mPrefernceUtils;
    private Gson mGson;
    public static ArticleCollFragment newInstance(){
        return new ArticleCollFragment();
    }
    @Override
    public int getLayOutID() {
        return R.layout.fragment_articlecoll;
    }

    @Override
    public void initView(View mRootView) {
        mRefresh = (BGARefreshLayout) mRootView.findViewById(R.id.refresh);
        mRecyFramArtcollItem = (RecyclerView) mRootView.findViewById(R.id.recy_fram_artcoll_item);
    }

    @Override
    public void initData() {
        mPrefernceUtils=new SharePrefernceUtils(getContext(), Content.SP_NAME);
        mGson=new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        login = mGson.fromJson(string, LoginInfo.class);
        mCollPresenter=new ArticleCollPresenter(this);

        mRefresh.setDelegate(this);
        BGANormalRefreshViewHolder normalRefreshViewHolder=new BGANormalRefreshViewHolder(getContext(),true);
        mRefresh.setRefreshViewHolder(normalRefreshViewHolder);
        initRecyclerView();
        mRefresh.beginRefreshing();
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFramArtcollItem.setLayoutManager(manager);
        mRecyFramArtcollItem.addItemDecoration(new RecycleViewDivider(getContext(), DividerItemDecoration.VERTICAL));
        mCollectAdapter=new ArticleCollAdapter(getContext());
        mRecyFramArtcollItem.setAdapter(mCollectAdapter);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        state=0;
        mCollPresenter.coll_article(getContext(),state,login);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        state=1;
        mCollPresenter.coll_article(getContext(),state,login);
        return false;
    }

    /**
     * 加载收藏文章成功
     * @param list
     */
    @Override
    public void article_collect_data_ok(List<UA_Table> list) {
        if (state==0){
            mCollectAdapter.refresh(list);
        }
        if (state==1){
            mCollectAdapter.setAddData(list);
        }
        mRefresh.endRefreshing();
    }

    @Override
    public void article_collect_data_fail() {

    }
}
