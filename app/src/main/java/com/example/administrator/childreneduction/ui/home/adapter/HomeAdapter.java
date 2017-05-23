package com.example.administrator.childreneduction.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.model.Content;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;
import com.example.administrator.childreneduction.ui.listener.OnLongClickListener;
import com.example.administrator.childreneduction.utils.SharePrefernceUtils;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/5/14.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HViewHolder> implements View.OnClickListener, View.OnLongClickListener {
    private Context mContext;
    private List<ArticleTable> list;
    private OnClickListener mOnClickListener;
    private OnLongClickListener mLongClickListener;
    private SharePrefernceUtils mPrefernceUtils;

    private LoginInfo loginInfo;
    public HomeAdapter(Context mContext) {
        this.mContext = mContext;
        mPrefernceUtils=new SharePrefernceUtils(mContext, Content.SP_NAME);
        Gson gson = new Gson();
        String string = mPrefernceUtils.getString(Content.SP_NAME);
        loginInfo = gson.fromJson(string, LoginInfo.class);
    }

    public void refresh(List<ArticleTable> lis){
        if (list!=null){
            list.clear();
        }
        this.list=lis;
    }

    public void addData(List<ArticleTable> lis){
        list.addAll(lis);
    }

    public List<ArticleTable> getList() {
        return list;
    }

    @Override
    public HViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_home, parent, false);
        inflate.setOnClickListener(this);
        inflate.setOnLongClickListener(this);
        return new HViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(HViewHolder holder, int position) {
        holder.itemView.setTag(position);
        final ArticleTable articleTable = list.get(position);
//        holder.mTvAdapterHomeContent.setText(articleTable.getA_content());
        holder.mTvAdapterHomeTile.setText(articleTable.getA_title());
        holder.mTvAdapterHomeTime.setText(articleTable.getCreatedAt());
        holder.mTvAdapterHomeUser.setText(articleTable.getU_name());

        //分享
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    /**
     * 设置条目点击监听
     *
     * @param mOnClickListener
     */
    public void setOnClickListener(OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    /**
     * 长按点击监听
     * @param clickListener
     */
    public void setLongClickListener(OnLongClickListener clickListener){
        this.mLongClickListener=clickListener;
    }

    @Override
    public boolean onLongClick(View v) {
        if (mLongClickListener!=null){
            mLongClickListener.setLongClickListener(v, (Integer) v.getTag());
        }
        return true;
    }

    class HViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvAdapterHomeTile;
        private TextView mTvAdapterHomeContent;
        private TextView mTvAdapterHomeUser;
        private TextView mTvAdapterHomeTime;
        private ImageView mImgAdapterHomeColl;
        private ImageView mImgAdapterHomeShare;

        public HViewHolder(View itemView) {
            super(itemView);
            mTvAdapterHomeTile = (TextView) itemView.findViewById(R.id.tv_adapter_home_tile);
            mTvAdapterHomeContent = (TextView) itemView.findViewById(R.id.tv_adapter_home_content);
            mTvAdapterHomeUser = (TextView) itemView.findViewById(R.id.tv_adapter_home_user);
            mTvAdapterHomeTime = (TextView) itemView.findViewById(R.id.tv_adapter_home_time);
            mImgAdapterHomeColl = (ImageView) itemView.findViewById(R.id.img_adapter_home_coll);
            mImgAdapterHomeShare = (ImageView) itemView.findViewById(R.id.img_adapter_home_share);
        }
    }
}
