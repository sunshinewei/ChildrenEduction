package com.example.administrator.childreneduction.ui.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.home.adapter.HomeAdapter;
import com.example.administrator.childreneduction.ui.vedio.adapter.VedioAdapter;

/**
 * Created by Administrator on 2017/5/7.
 */

public class HomeFragment extends BaseFagment {

    private RecyclerView mRecyFragHomeItem;
    private HomeAdapter mHomeAdapter;

    @Override
    public int getLayOutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View mRootView) {
        mRecyFragHomeItem = (RecyclerView) mRootView.findViewById(R.id.recy_frag_home_item);
        initRecyclerView();
    }

    @Override
    public void initData() {

    }
    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFragHomeItem.setLayoutManager(manager);
        mHomeAdapter=new HomeAdapter(getContext());
        mRecyFragHomeItem.setAdapter(mHomeAdapter);
    }
}
