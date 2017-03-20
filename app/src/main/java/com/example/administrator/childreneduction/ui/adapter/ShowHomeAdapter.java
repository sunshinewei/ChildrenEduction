package com.example.administrator.childreneduction.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.administrator.childreneduction.widgets.recyclerview.BaseAdapter;
import com.example.administrator.childreneduction.widgets.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class ShowHomeAdapter extends BaseAdapter {


    public ShowHomeAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }


    @Override
    public BaseViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindCustomViewHolder(BaseViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
