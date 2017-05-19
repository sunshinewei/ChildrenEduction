package com.example.administrator.childreneduction.ui.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.ui.base.BaseFagment;
import com.example.administrator.childreneduction.ui.home.adapter.HomeAdapter;
import com.example.administrator.childreneduction.ui.home.iview.HomeFragmentUI;
import com.example.administrator.childreneduction.ui.home.presenter.HomeFragmentPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/7.
 */

public class HomeFragment extends BaseFagment implements HomeFragmentUI{

    private RecyclerView mRecyFragHomeItem;
    private HomeAdapter mHomeAdapter;

    private HomeFragmentPresenter mFragmentPresenter;

    @Override
    public int getLayOutID() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView(View mRootView) {
        mRecyFragHomeItem = (RecyclerView) mRootView.findViewById(R.id.recy_frag_home_item);
    }

    @Override
    public void initData() {
        mFragmentPresenter=new HomeFragmentPresenter(this);
        mFragmentPresenter.getArticle(getContext());
    }
    private void initRecyclerView(List<ArticleTable> list){
        RecyclerView.LayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyFragHomeItem.setLayoutManager(manager);
        mHomeAdapter=new HomeAdapter(getContext(),list);
        mRecyFragHomeItem.setAdapter(mHomeAdapter);
    }

    @Override
    public void getArticle_ok(List<ArticleTable> list) {
        initRecyclerView(list);
    }
}
