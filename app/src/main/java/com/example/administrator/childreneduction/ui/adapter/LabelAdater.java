package com.example.administrator.childreneduction.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.model.LabelBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/28.
 */

public class LabelAdater extends RecyclerView.Adapter<LabelAdater.LViewHolder> {

    private ArrayList<LabelBean> mLabes;
    private Context mContext;

    public LabelAdater(Context mContext){
        this.mContext=mContext;
        initData();
    }


    /**
     * 初始化数据
     */
    private void initData() {
        if (mLabes==null){
            mLabes = new ArrayList<>();
        }
        mLabes.add(new LabelBean("0-1岁"));
        mLabes.add(new LabelBean("1-2岁"));
        mLabes.add(new LabelBean("2-3岁"));
        mLabes.add(new LabelBean("3-4岁"));
        mLabes.add(new LabelBean("4-5岁"));
        mLabes.add(new LabelBean("5-6岁"));
        mLabes.add(new LabelBean("6-7岁"));
        mLabes.add(new LabelBean("6-7岁"));
        mLabes.add(new LabelBean("8-9岁"));
        mLabes.add(new LabelBean("9-10岁"));
        mLabes.add(new LabelBean("10-11岁"));
        mLabes.add(new LabelBean("11-12岁"));
    }

    public ArrayList<LabelBean> getLabes() {
        return mLabes;
    }

    @Override
    public LViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_label, parent, false);
        return new LViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(LViewHolder holder, int position) {
        LabelBean bean = mLabes.get(position);
        holder.display(position,bean);
    }

    @Override
    public int getItemCount() {
        return mLabes != null ? mLabes.size() : 0;
    }

    class LViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvAdaLabel;
        private CheckBox mCbAdaCheck;

        public LViewHolder(View itemView) {
            super(itemView);
            mTvAdaLabel = (TextView) itemView.findViewById(R.id.tv_ada_label);
            mCbAdaCheck = (CheckBox) itemView.findViewById(R.id.cb_ada_check);
        }

        public void display(final int position, final LabelBean bean){
            mTvAdaLabel.setText(bean.getName());
            if (bean.isCheck()){
                mCbAdaCheck.setChecked(true);
            }else {
                mCbAdaCheck.setChecked(false);
            }

            mCbAdaCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCbAdaCheck.isChecked()){
                        bean.setCheck(true);
                        System.out.println("选择了是"+position);
                    }
                    if (!mCbAdaCheck.isChecked()){
                        bean.setCheck(false);
                        System.out.println("选择了否"+position);
                    }
                }
            });

        }
    }
}
