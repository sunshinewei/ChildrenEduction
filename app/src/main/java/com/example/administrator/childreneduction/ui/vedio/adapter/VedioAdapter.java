package com.example.administrator.childreneduction.ui.vedio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;

import java.util.ArrayList;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/13.
 */

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.VViewHolder> implements View.OnClickListener {

    private Context mContext;
    private ArrayList mList;

    private OnClickListener mOnClickListener;

    public VedioAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public VViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_vedio, parent, false);
        inflate.setOnClickListener(this);
        return new VViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(VViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 10;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    class VViewHolder extends RecyclerView.ViewHolder {
        public VViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
