package com.example.administrator.childreneduction.ui.vedio.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.vedio.adapter.VedioAdapter;

/**
 * Created by Administrator on 2017/5/7.
 */

public class VedioFragment extends BaseFagment {
    private RecyclerView mRecyFragVedioItem;

    private VedioAdapter mVedioAdapter;


    @Override
    public int getLayOutID() {
        return R.layout.fragment_vedio;
    }

    @Override
    public void initView(View mRootView) {
        mRecyFragVedioItem = (RecyclerView) mRootView.findViewById(R.id.recy_frag_vedio_item);
        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFragVedioItem.setLayoutManager(manager);
        mVedioAdapter=new VedioAdapter(getContext());
        mRecyFragVedioItem.setAdapter(mVedioAdapter);
    }



    @Override
    public void initData() {

    }

}
