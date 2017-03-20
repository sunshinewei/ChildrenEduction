package com.example.administrator.childreneduction.baseAdapter.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mSparseArray;

    /**
     * @param parent
     * @param resId
     */
    public BaseViewHolder(ViewGroup parent, @LayoutRes int resId) {
        super(LayoutInflater.from(parent.getContext()).inflate(resId,parent,false));
        mSparseArray=new SparseArray<>();
    }

    /**
     * @param mView
     */
    public BaseViewHolder(View mView){
        super(mView);
        mSparseArray=new SparseArray<>();
    }

    /**
     * 获取布局中的View
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getView(@IdRes int viewId){
        View view=mSparseArray.get(viewId);
        if (view==null){
            view=itemView.findViewById(viewId);
            mSparseArray.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 获取Context
     * @return
     */
    public Context getContext(){
        return itemView.getContext();
    }

}
