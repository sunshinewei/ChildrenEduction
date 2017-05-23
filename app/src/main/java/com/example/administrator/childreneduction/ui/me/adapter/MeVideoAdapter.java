package com.example.administrator.childreneduction.ui.me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MeVideoAdapter extends RecyclerView.Adapter<MeVideoAdapter.CViewHolder> implements View.OnClickListener {
    private Context mContext;
    private OnClickListener mOnClickListener;
    private List<VedioTable> mList;

    public MeVideoAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    /**
     * 加载更多
     *
     * @param list
     */
    public void setAddData(List<VedioTable> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void refresh(List<VedioTable> list) {
        if (mList != null) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public List<VedioTable> getList() {
        return mList;
    }

    @Override
    public CViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_videocoll, parent, false);
        inflate.setOnClickListener(this);
        return new CViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final CViewHolder holder, int position) {
        holder.itemView.setTag(position);
        VedioTable table = mList.get(position);

        holder.mTvAdapterHomeTile.setText(table.getV_title());
        holder.mTvAdapterHomeUser.setText(table.getU_name());
        holder.mTvAdapterHomeTime.setText(table.getCreatedAt());
        String url = table.getV_url();
        if (url != null) {
            Observable.just(url)
                    .map(new Function<String, Bitmap>() {
                        @Override
                        public Bitmap apply(@NonNull String s) throws Exception {
                            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                            mmr.setDataSource(s, new HashMap<String, String>());
                            Bitmap time = mmr.getFrameAtTime();
                            return time;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Bitmap>() {
                        @Override
                        public void accept(@NonNull Bitmap s) throws Exception {
                            holder.mImgAdapterVideo.setImageBitmap(s);
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public void onClick(View v) {
        if (mOnClickListener != null) {
            mOnClickListener.setOnClickListener(v, (Integer) v.getTag());
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    class CViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvAdapterHomeUser;
        private TextView mTvAdapterHomeTime;
        private TextView mTvAdapterHomeTile;
        private ImageView mImgAdapterVideo;

        public CViewHolder(View itemView) {
            super(itemView);
            mImgAdapterVideo = (ImageView) itemView.findViewById(R.id.img_adapter_video);
            mTvAdapterHomeUser = (TextView) itemView.findViewById(R.id.tv_adapter_home_user);
            mTvAdapterHomeTime = (TextView) itemView.findViewById(R.id.tv_adapter_home_time);
            mTvAdapterHomeTile = (TextView) itemView.findViewById(R.id.tv_adapter_home_tile);
        }
    }
}
