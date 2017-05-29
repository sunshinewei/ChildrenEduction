package com.example.administrator.childreneduction.ui.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.BaseFagment;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * Created by Administrator on 2017/5/29.
 * 搜索视频
 */

public class SearchVideoFragment extends BaseFagment {
    private BGARefreshLayout mRefresh;
    private RecyclerView mRecyFramVidsearItem;


    public static SearchVideoFragment newInstance(){
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
    }

    /**
     * 设置标签
     * @param label
     */
    public void setLabel(String label){

    }

    @Override
    public void initData() {
    }
}
