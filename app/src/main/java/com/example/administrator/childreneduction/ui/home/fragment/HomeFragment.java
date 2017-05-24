package com.example.administrator.childreneduction.ui.home.fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.home.activity.LookArticleActivity;
import com.example.administrator.childreneduction.ui.home.adapter.HomeAdapter;
import com.example.administrator.childreneduction.ui.home.iview.HomeFragmentUI;
import com.example.administrator.childreneduction.ui.home.presenter.HomeFragmentPresenter;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.ui.listener.OnLongClickListener;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.willowtreeapps.spruce.Spruce;
import com.willowtreeapps.spruce.animation.DefaultAnimations;
import com.willowtreeapps.spruce.sort.DefaultSort;

import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

import static com.example.administrator.childreneduction.App.mContext;

/**
 * Created by Administrator on 2017/5/7.
 */

public class HomeFragment extends BaseFagment implements HomeFragmentUI, BGARefreshLayout.BGARefreshLayoutDelegate {

    private RecyclerView mRecyFragHomeItem;
    private HomeAdapter mHomeAdapter;
    private BGARefreshLayout mRefresh;
    private int state;

    private HomeFragmentPresenter mFragmentPresenter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public int getLayOutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View mRootView) {
        mRecyFragHomeItem = (RecyclerView) mRootView.findViewById(R.id.recy_frag_home_item);
        mRefresh = (BGARefreshLayout) mRootView.findViewById(R.id.refresh);


    }

    @Override
    public void initData() {
        mFragmentPresenter = new HomeFragmentPresenter(this);
        initRecyclerView();

        mRefresh.setDelegate(this);
        BGANormalRefreshViewHolder normalRefreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        mRefresh.setRefreshViewHolder(normalRefreshViewHolder);
        mRefresh.beginRefreshing();
    }

    /**
     *
     */
    private void initRecyclerView() {
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                super.onLayoutChildren(recycler, state);
                new Spruce.SpruceBuilder(mRecyFragHomeItem)
                        .sortWith(new DefaultSort(100))
                        .animateWith(DefaultAnimations.shrinkAnimator(mRecyFragHomeItem, 800),
                                ObjectAnimator.ofFloat(mRecyFragHomeItem, "translationX", -mRecyFragHomeItem.getWidth(), 0f).setDuration(800))
                        .start();
            }
        };
        mRecyFragHomeItem.setLayoutManager(manager);
        mHomeAdapter = new HomeAdapter(getContext());
        mRecyFragHomeItem.setAdapter(mHomeAdapter);
        mRecyFragHomeItem.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));

        setListener();

    }

    private void setListener() {
        //点击监听
        mHomeAdapter.setOnClickListener(new OnClickListener() {
            @Override
            public void setOnClickListener(View view, int position) {
                //查看每个条目的文章
                List<ArticleTable> list = mHomeAdapter.getList();
                ArticleTable articleTable = list.get(position);
                Intent intent = LookArticleActivity.createIntent(getContext());
                intent.putExtra(Content.ARTICLE_INFO, articleTable);
                startActivity(intent);
            }
        });

        mHomeAdapter.setLongClickListener(new OnLongClickListener() {
            @Override
            public void setLongClickListener(View view, int position) {

            }
        });
    }

    @Override
    public void getArticle_ok(List<ArticleTable> list) {
        if (state == 0) {
            mHomeAdapter.refresh(list);
        }
        if (state==1){
            mHomeAdapter.addData(list);
        }
        mRefresh.endRefreshing();
        mRefresh.endLoadingMore();
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        state=0;
        mFragmentPresenter.getArticle(getContext(),state);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        state=1;
        mFragmentPresenter.getArticle(getContext(),state);
        return false;
    }
}
