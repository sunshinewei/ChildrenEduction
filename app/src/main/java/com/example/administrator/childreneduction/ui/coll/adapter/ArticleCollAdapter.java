package com.example.administrator.childreneduction.ui.coll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.UA_Table;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class ArticleCollAdapter extends RecyclerView.Adapter<ArticleCollAdapter.CViewHolder> implements View.OnClickListener {
    private Context mContext;
    private OnClickListener mOnClickListener;
    private List<UA_Table> mList;

    public ArticleCollAdapter(Context mContext){
        this.mContext=mContext;
//        this.mList=list;
        mList=new ArrayList<>();
    }

    /**
     * 加载更多
     * @param list
     */
    public void setAddData(List<UA_Table> list){
        mList.addAll(list);
//        notifyDataSetChanged();
    }

    public void refresh(List<UA_Table> list){
        if (mList!=null){
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public List<UA_Table> getList() {
        return mList;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_articlecoll, parent, false);
        inflate.setOnClickListener(this);
        return new CViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, int position) {
        holder.itemView.setTag(position);
        UA_Table ua_table = mList.get(position);
        holder.mTvAdapterArticollTile.setText(ua_table.getA_title());
        holder.mTvAdapterArticollUser.setText(ua_table.getAu_name());
        holder.mTvAdapterArticollTime.setText(ua_table.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mList!=null?mList.size():0;
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
        private TextView mTvAdapterArticollUser;
        private TextView mTvAdapterArticollTime;
        private TextView mTvAdapterArticollTile;

        public CViewHolder(View itemView) {
            super(itemView);

            mTvAdapterArticollUser = (TextView) itemView.findViewById(R.id.tv_adapter_articoll_user);
            mTvAdapterArticollTime = (TextView) itemView.findViewById(R.id.tv_adapter_articoll_time);
            mTvAdapterArticollTile = (TextView) itemView.findViewById(R.id.tv_adapter_articoll_tile);

        }
    }
}
