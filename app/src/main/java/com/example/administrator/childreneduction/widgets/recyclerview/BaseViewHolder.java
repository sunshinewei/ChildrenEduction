package com.example.administrator.childreneduction.widgets.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mSparseArray;
    private Context mContext;
    private View convertView;

    /**
     * @param parent
     * @param resId
     */
//    public BaseViewHolder(ViewGroup parent, @LayoutRes int resId) {
//        super(LayoutInflater.from(parent.getContext()).inflate(resId,parent,false));
//        mSparseArray=new SparseArray<>();
//    }

    /**
     * @param mView
     */
    public BaseViewHolder(View mView,Context mConetxt){
        super(mView);
        mSparseArray=new SparseArray<>();
        this.mContext=mConetxt;
        this.convertView=mView;
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
            view=convertView.findViewById(viewId);
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

    public BaseViewHolder setText(int viewId, CharSequence value) {
        TextView view = getView(viewId);
        view.setText(value);
        return this;
    }
    public BaseViewHolder setImageUrl(int viewId, String imageUrl) {
        ImageView view = getView(viewId);
        Picasso.with(mContext).load(imageUrl).into(view);
        return this;
    }
    public BaseViewHolder setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }
    public BaseViewHolder linkify(int viewId) {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

}
