package com.example.administrator.childreneduction.ui.me.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LabelBean;
import com.example.administrator.childreneduction.ui.adapter.LabelAdater;
import com.example.administrator.childreneduction.ui.base.EduBaseActivity;
import com.example.administrator.childreneduction.widgets.recyclerview.RecycleViewDivider;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/28.
 * <p>
 * 标签Activity。
 */

public class LabelActivity extends EduBaseActivity {
    private RecyclerView mRecyActLabelItem;
    private TextView mTvActLabelOk;

    private StringBuffer mBuffer;

    private LabelAdater mLabelAdater;

    public static Intent createIntent(Context mContext){
        return new Intent(mContext,LabelActivity.class);
    }

    @Override
    public int setLayout() {
        return R.layout.activity_label;
    }

    @Override
    public void initView() {
        mRecyActLabelItem = (RecyclerView) findViewById(R.id.recy_act_label_item);
        mTvActLabelOk = (TextView) findViewById(R.id.tv_act_label_ok);
        initData();
        initsetListener();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        mBuffer=new StringBuffer();
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyActLabelItem.setLayoutManager(manager);
        mRecyActLabelItem.addItemDecoration(new RecycleViewDivider(this, DividerItemDecoration.VERTICAL));

        mLabelAdater=new LabelAdater(this);
        mRecyActLabelItem.setAdapter(mLabelAdater);

    }

    /**
     * 添加监听
     */
    private void initsetListener(){

        mTvActLabelOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<LabelBean> labes = mLabelAdater.getLabes();
                for (int i=0;i<labes.size();i++){
                    LabelBean bean = labes.get(i);
                    if (bean.isCheck()){
                        mBuffer.append(bean.getName()+",");
                    }
                }
                if (mBuffer!=null){
                    String substring = mBuffer.substring(0, mBuffer.length() - 1);
                    if (substring!=null){
                        Intent intent = new Intent();
                        intent.putExtra(Content.LABEL,substring);
                        setResult(RESULT_OK,intent);
                        finish();
                    }else {
                        Toast.makeText(LabelActivity.this,"请选择标签！",Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }
}