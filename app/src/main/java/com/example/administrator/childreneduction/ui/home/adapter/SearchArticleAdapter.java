package com.example.administrator.childreneduction.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/5/29.
 */

public class SearchArticleAdapter extends RecyclerView.Adapter<SearchArticleAdapter.SAViewHolder> implements View.OnClickListener {
    private Context mContext;
    private List<ArticleTable> mTables;
    private OnClickListener mOnClickListener;

    public SearchArticleAdapter(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 刷新
     *
     * @param list
     */
    public void refresh(List<ArticleTable> list) {
        if (mTables != null) {
            mTables.clear();
        }
        this.mTables = list;
    }

    /**
     * 添加
     *
     * @param list
     */
    public void addData(List<ArticleTable> list) {
        this.mTables.addAll(list);
    }

    @Override
    public SAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_search_artcle, parent, false);
        inflate.setOnClickListener(this);
        return new SAViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SAViewHolder holder, int position) {
        holder.itemView.setTag(position);

        ArticleTable articleTable = mTables.get(position);
        holder.mTvAdaUser.setText(articleTable.getU_name());
        holder.mTvAdaTitle.setText(articleTable.getA_title());
        holder.mTvAdaTime.setText("时间："+articleTable.getCreatedAt());
        holder.mTvAdaLabel.setText("标签："+articleTable.getA_label());

        Glide.with(mContext)
                .load(articleTable.getU_url())
                .into(holder.mCivAdaSeararHead);
    }

    @Override
    public int getItemCount() {
        return mTables != null ? mTables.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener!=null){
            mOnClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    /**
     * @param onClickListener
     */
    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    class SAViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCivAdaSeararHead;
        private TextView mTvAdaUser;
        private TextView mTvAdaTitle;
        private TextView mTvAdaLabel;
        private TextView mTvAdaTime;

        public SAViewHolder(View itemView) {
            super(itemView);
            mCivAdaSeararHead = (CircleImageView) itemView.findViewById(R.id.civ_ada_searar_head);
            mTvAdaUser = (TextView) itemView.findViewById(R.id.tv_ada_user);
            mTvAdaTitle = (TextView) itemView.findViewById(R.id.tv_ada_title);
            mTvAdaLabel = (TextView) itemView.findViewById(R.id.tv_ada_label);
            mTvAdaTime = (TextView) itemView.findViewById(R.id.tv_ada_time);
        }
    }
}
