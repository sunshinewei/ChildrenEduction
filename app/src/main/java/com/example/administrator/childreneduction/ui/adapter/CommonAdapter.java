package com.example.administrator.childreneduction.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.UA_Table;
import com.example.administrator.childreneduction.bmob.UV_Table;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/6/8.
 * 评论列表
 */

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.CViewHolder> {

    private Context mContext;
    private String mType;
    private List<UV_Table> mUV_tables = new ArrayList<>();
    private List<UA_Table> mUATables = new ArrayList<>();
//    private LoginInfo mLoginInfo;

    public CommonAdapter(Context mContext, String type) {
        this.mContext = mContext;
        this.mType = type;
    }

    /**
     * 添加数据
     */
    public void addData(List mlist) {
        if ("1".equals(mType)) {
            mUV_tables.addAll(mlist);
        }
        if ("0".equals(mType)) {
            mUATables.addAll(mlist);
        }
        notifyDataSetChanged();
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_common, parent, false);
        return new CViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(CViewHolder holder, int position) {
        if ("1".equals(mType)) {
            UV_Table uv_table = mUV_tables.get(position);
            holder.mTvAdaCommComm.setText(uv_table.getUv_comm());
            System.out.println("评论为" + uv_table.getUv_comm());
            holder.mTvAdaCommName.setText(uv_table.getVu_name());
            holder.mTvAdaCommTime.setText(uv_table.getCreatedAt());
            Glide.with(mContext)
                    .load(uv_table.getU_url())
                    .into(holder.mCivAdaComHead);
        }
        if ("0".equals(mType)) {
            UA_Table ua_table = mUATables.get(position);
            holder.mTvAdaCommComm.setText(ua_table.getUa_comm());
            holder.mTvAdaCommName.setText(ua_table.getAu_name());
            holder.mTvAdaCommTime.setText(ua_table.getCreatedAt());
            Glide.with(mContext)
                    .load(ua_table.getU_url())
                    .into(holder.mCivAdaComHead);
        }
    }

    @Override
    public int getItemCount() {
        if ("1".equals(mType)) {
            return mUV_tables != null ? mUV_tables.size() : 0;
        }
        return mUATables != null ? mUATables.size() : 0;
    }

    class CViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mCivAdaComHead;
        private TextView mTvAdaCommName;
        private TextView mTvAdaCommComm;
        private TextView mTvAdaCommTime;

        public CViewHolder(View itemView) {
            super(itemView);
            mCivAdaComHead = (CircleImageView) itemView.findViewById(R.id.civ_ada_com_head);
            mTvAdaCommName = (TextView) itemView.findViewById(R.id.tv_ada_comm_name);
            mTvAdaCommComm = (TextView) itemView.findViewById(R.id.tv_ada_comm_comm);
            mTvAdaCommTime = (TextView) itemView.findViewById(R.id.tv_ada_comm_time);
        }
    }
}
