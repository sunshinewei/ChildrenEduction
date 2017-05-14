package com.example.administrator.childreneduction.ui.coll.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.coll.adapter.CollectAdapter;

/**
 * Created by Administrator on 2017/5/7.
 * 收藏
 */

public class CollectFragment extends BaseFagment {
    private RecyclerView mRecyFragCollItem;

    private CollectAdapter mCollectAdapter;
    @Override
    public int getLayOutID() {
        return R.layout.fragment_collect;
    }

    @Override
    public void initView(View mRootView) {
        mRecyFragCollItem = (RecyclerView) mRootView.findViewById(R.id.recy_frag_coll_item);

    }

    @Override
    public void initData() {
    initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFragCollItem.setLayoutManager(manager);
        mCollectAdapter=new CollectAdapter(getContext());
        mRecyFragCollItem.setAdapter(mCollectAdapter);
    }
}
