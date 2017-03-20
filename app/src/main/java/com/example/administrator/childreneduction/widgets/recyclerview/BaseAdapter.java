package com.example.administrator.childreneduction.widgets.recyclerview;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public abstract class BaseAdapter<M,VH extends BaseViewHolder> extends AbsAdapter<M,VH> {
    private List<M> mDataList;

    public BaseAdapter(Context context) {
        super(context);
        this.mDataList = new ArrayList<>();
    }

    public BaseAdapter(Context context, List<M> list) {
        super(context);
        this.mDataList = new ArrayList<>();
        this.mDataList.addAll(list);
    }
    /**
     * 填充数据,此操作会清除原来的数据
     * @param list 要填充的数据
     * @return true:填充成功并调用刷新数据
     */
    public boolean fillList(List<M> list) {
        mDataList.clear();
        boolean result = mDataList.addAll(list);
        if (result) {
            notifyDataSetChanged();
        }
        return result;
    }

    /**
     * 追加一条数据
     *
     * @param data 要追加的数据
     * @return true:追加成功并刷新界面
     */
    public boolean appendItem(M data) {
        boolean result = mDataList.add(data);
        if (result) {
            if (getHeaderExtraViewCount() == 0) {
                notifyItemInserted(mDataList.size() - 1);
            } else {
                notifyItemInserted(mDataList.size());
            }
        }
        return result;
    }

    /**
     * 追加集合数据
     *
     * @param list 要追加的集合数据
     * @return 追加成功并刷新
     */
    public boolean appendList(List<M> list) {
        boolean result = mDataList.addAll(list);
        if (result) {
            notifyDataSetChanged();
        }
        return result;
    }

    /**
     * 在最顶部前置数据
     *
     * @param data 要前置的数据
     */
    public void proposeItem(M data) {
        mDataList.add(0, data);
        if (getHeaderExtraViewCount() == 0) {
            notifyItemInserted(0);
        } else {
            notifyItemInserted(getHeaderExtraViewCount());
        }
    }

    /**
     * 在顶部前置数据集合
     *
     * @param list 要前置的数据集合
     */
    public void proposeList(List<M> list) {
        mDataList.addAll(0, list);
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public final int getItemViewType(int position) {
        if (headerView != null && position == 0) {
            return VIEW_TYPE_HEADER;
        } else if (footerView != null && position == mDataList.size() + getHeaderExtraViewCount()) {
            return VIEW_TYPE_FOOTER;
        } else {
            return getCustomViewType(position);
        }
    }

    /**
     * 获取自定义View的类型
     *
     * @param position 位置
     * @return View的类型
     */
    public abstract int getCustomViewType(int position);

    @Override
    public int getItemCount() {
        return mDataList.size() + getExtraViewCount();
    }

    /**
     * 根据位置获取一条数据
     *
     * @param position View的位置
     * @return 数据
     */
    public M getItem(int position) {
        if (headerView != null && position == 0
                || position >= mDataList.size() + getHeaderExtraViewCount()) {
            return null;
        }
        return headerView == null ? mDataList.get(position) : mDataList.get(position - 1);
    }

    /**
     * 根据ViewHolder获取数据
     *
     * @param holder ViewHolder
     * @return 数据
     */
    public M getItem(VH holder) {
        return getItem(holder.getAdapterPosition());
    }

    public void updateItem(M data) {
        int index = mDataList.indexOf(data);
        if (index < 0) {
            return;
        }
        mDataList.set(index, data);
        if (headerView == null) {
            notifyItemChanged(index);
        } else {
            notifyItemChanged(index + 1);
        }
    }

    /**
     * 移除一条数据,根据索引值
     *
     * @param position 位置
     */
    public void removeItem(int position) {
        if (headerView == null) {
            mDataList.remove(position);
        } else {
            mDataList.remove(position - 1);
        }
        notifyItemRemoved(position);
    }

    /**
     * 移除一条数据，根据数据
     *
     * @param data 要移除的数据
     */
    public void removeItem(M data) {
        int index = mDataList.indexOf(data);
        if (index < 0) {
            return;
        }
        mDataList.remove(index);
        if (headerView == null) {
            notifyItemRemoved(index);
        } else {
            notifyItemRemoved(index + 1);
        }
    }
}
