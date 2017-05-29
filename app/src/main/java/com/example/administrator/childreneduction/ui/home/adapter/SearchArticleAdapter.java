package com.example.administrator.childreneduction.ui.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/5/29.
 */

public class SearchArticleAdapter  extends RecyclerView.Adapter<SearchArticleAdapter.SAViewHolder>{
    private Context mContext;
    public SearchArticleAdapter(Context mContext){
        this.mContext=mContext;
    }

    @Override
    public SAViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SAViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class SAViewHolder extends RecyclerView.ViewHolder{

        public SAViewHolder(View itemView) {
            super(itemView);
        }
    }
}
