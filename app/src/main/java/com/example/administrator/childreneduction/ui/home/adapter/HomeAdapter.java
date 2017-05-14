package com.example.administrator.childreneduction.ui.home.adapter;

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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List mList;
    private OnClickListener mOnClickListener;
    public HomeAdapter(Context mContext){
        this.mContext=mContext;
        this.mList=mList;
    }


    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_home, parent, false);
        inflate.setOnClickListener(this);
        return new HViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mList!=null ? mList.size():10;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener!=null){
            mOnClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    /**
     * 设置条目点击监听
     * @param mOnClickListener
     */
    public void setOnClickListener(OnClickListener mOnClickListener){
        this.mOnClickListener=mOnClickListener;
    }
    class HViewHolder extends RecyclerView.ViewHolder {
        public HViewHolder(View itemView) {
            super(itemView);
        }
    }
}
