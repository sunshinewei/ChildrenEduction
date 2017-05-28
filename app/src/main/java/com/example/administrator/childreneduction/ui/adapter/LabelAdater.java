package com.example.administrator.childreneduction.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.childreneduction.model.LabelBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/28.
 */

public class LabelAdater extends RecyclerView.Adapter<LabelAdater.LViewHolder> {

    private ArrayList<LabelBean> mLabes;

    public LabelAdater(){
        initData();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        if (mLabes==null){
            mLabes = new ArrayList<>();
        }
        mLabes.add(new LabelBean("0-1岁"));
        mLabes.add(new LabelBean("1-2岁"));
        mLabes.add(new LabelBean("2-3岁"));
        mLabes.add(new LabelBean("3-4岁"));
        mLabes.add(new LabelBean("4-5岁"));
        mLabes.add(new LabelBean("5-6岁"));
        mLabes.add(new LabelBean("6-7岁"));
        mLabes.add(new LabelBean("6-7岁"));
        mLabes.add(new LabelBean("8-9岁"));
        mLabes.add(new LabelBean("9-10岁"));
        mLabes.add(new LabelBean("10-11岁"));
        mLabes.add(new LabelBean("11-12岁"));
    }

    @Override
    public LViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mLabes != null ? mLabes.size() : 0;
    }

    class LViewHolder extends RecyclerView.ViewHolder {

        public LViewHolder(View itemView) {
            super(itemView);
        }
    }
}
