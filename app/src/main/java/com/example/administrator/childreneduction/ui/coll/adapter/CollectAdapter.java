package com.example.administrator.childreneduction.ui.coll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.CViewHolder> implements View.OnClickListener {
    private Context mContext;
    private OnClickListener mOnClickListener;
    private List mList;

    public CollectAdapter(Context mContext){
        this.mContext=mContext;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_collect, parent, false);
        inflate.setOnClickListener(this);
        return new CViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():10;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener!=null){
            mOnClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.mOnClickListener=onClickListener;
    }

    class CViewHolder extends RecyclerView.ViewHolder{

        public CViewHolder(View itemView) {
            super(itemView);
        }
    }
}
