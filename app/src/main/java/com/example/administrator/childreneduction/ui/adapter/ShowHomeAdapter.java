package com.example.administrator.childreneduction.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.widgets.recyclerview.BaseAdapter;
import com.example.administrator.childreneduction.widgets.recyclerview.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class ShowHomeAdapter extends BaseAdapter {
    private Context mContext;

    public ShowHomeAdapter(Context context, List list) {
        super(context, list);
        this.mContext=context;
    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }


    @Override
    public BaseViewHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_home, parent, false);

        return new BaseViewHolder(inflate);
    }

    @Override
    public void bindCustomViewHolder(BaseViewHolder holder, int position) {
//        holder.setText()
    }
}
