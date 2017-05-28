package com.example.administrator.childreneduction.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.example.baselibrary.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/29.
 * 搜索Activity
 */

public class SearchActivity extends BaseActivity {

    private EditText mTvActivityLabel;
    private RelativeLayout mRelActSearch;
    private RecyclerView mRecyActSearchItem;
    private ViewPager mVpActSearch;
    private TextView mTvActSearchDelete;


    public static Intent createIntent(Context mContext){
        return new Intent(mContext,SearchActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        mTvActivityLabel = (EditText) findViewById(R.id.tv_activity_label);
        mRelActSearch = (RelativeLayout) findViewById(R.id.rel_act_search);
        mRecyActSearchItem = (RecyclerView) findViewById(R.id.recy_act_search_item);
        mVpActSearch = (ViewPager) findViewById(R.id.vp_act_search);
        mTvActSearchDelete = (TextView) findViewById(R.id.tv_act_search_delete);

        setListener();
        initData();
    }

    /**
     *
     */
    private void initData() {
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyActSearchItem.addItemDecoration(new RecycleViewDivider(this, DividerItemDecoration.VERTICAL));
        mRecyActSearchItem.setLayoutManager(layoutManager);
    }

    /**
     *
     */
    public void setListener() {

    }
}
