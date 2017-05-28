package com.example.administrator.childreneduction.ui.me.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.adapter.LabelAdater;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;
import com.example.baselibrary.base.BaseActivity;

/**
 * Created by Administrator on 2017/5/28.
 * <p>
 * 标签Activity。
 */

public class LabelActivity extends BaseActivity {
    private RecyclerView mRecyActLabelItem;

    private LabelAdater mLabelAdater;
    @Override
    public int setLayout() {
        return R.layout.activity_label;
    }

    @Override
    public void initView() {
        mRecyActLabelItem = (RecyclerView) findViewById(R.id.recy_act_label_item);

        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyActLabelItem.setLayoutManager(manager);
        mRecyActLabelItem.addItemDecoration(new RecycleViewDivider(this, DividerItemDecoration.VERTICAL));

        mLabelAdater=new LabelAdater();
        mRecyActLabelItem.setAdapter(mLabelAdater);

    }
}