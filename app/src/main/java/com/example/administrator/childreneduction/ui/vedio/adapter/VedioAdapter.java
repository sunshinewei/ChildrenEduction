package com.example.administrator.childreneduction.ui.vedio.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.childreneduction.R;
import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.ui.listener.OnClickListener;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/13.
 */

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.VViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<VedioTable> mList;

    private OnClickListener mOnClickListener;

    public VedioAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void refresh(List<VedioTable> vedioTable) {
        if (mList != null) {
            mList.clear();
        }
        mList = vedioTable;
        notifyDataSetChanged();
    }

    public void addData(List<VedioTable> vedioTable){
        mList.addAll(vedioTable);
    }

    public List<VedioTable> getList() {
        return mList;
    }

    @Override
    public VViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.adapter_vedio, parent, false);
        inflate.setOnClickListener(this);
        return new VViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(final VViewHolder holder, int position) {
        holder.itemView.setTag(position);
        VedioTable vedioTable = mList.get(position);
        holder.mTvAdapterVideoUser.setText(vedioTable.getU_name());
        holder.mTvAdapterVideoTime.setText(vedioTable.getCreatedAt());
        holder.mTvAdapterVideoTile.setText(vedioTable.getV_title());
        holder.mTvAdapterVideoContent.setText(vedioTable.getV_content());
        Glide.with(mContext).load(vedioTable.getU_url())
                .into(holder.mTvAdapterVideoHead);

        String url = vedioTable.getV_url();
        if (url!=null){
            Observable.just(url)
                    .map(new Function<String, Bitmap>() {
                        @Override
                        public Bitmap apply(@NonNull String s) throws Exception {
                            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                            mmr.setDataSource(s,new HashMap<String, String>());
                            Bitmap time = mmr.getFrameAtTime();
                            return time;
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Bitmap>() {
                        @Override
                        public void accept(@NonNull Bitmap s) throws Exception {
                            holder.mImgAdapterVideoBackground.setImageBitmap(s);
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

    /**
     * 点击监听
     * @param onClickListener
     */
    public void setOnClickListener(OnClickListener onClickListener){
        this.mOnClickListener=onClickListener;
    }

    class VViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mUserInfo;
        private TextView mTvAdapterVideoUser;
        private TextView mTvAdapterVideoTime;
        private TextView mTvAdapterVideoTile;
        private TextView mTvAdapterVideoContent;
        private TextView mTvAdapterVideoLabel;
        private ImageView mImgAdapterVideoBackground;
        private CircleImageView mTvAdapterVideoHead;


        public VViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mUserInfo = (LinearLayout) itemView.findViewById(R.id.user_info);
            mTvAdapterVideoUser = (TextView) itemView.findViewById(R.id.tv_adapter_video_user);
            mTvAdapterVideoTime = (TextView) itemView.findViewById(R.id.tv_adapter_video_time);
            mTvAdapterVideoTile = (TextView) itemView.findViewById(R.id.tv_adapter_video_tile);
            mTvAdapterVideoContent = (TextView) itemView.findViewById(R.id.tv_adapter_video_content);
            mTvAdapterVideoLabel = (TextView) itemView.findViewById(R.id.tv_adapter_video_label);
            mImgAdapterVideoBackground = (ImageView) itemView.findViewById(R.id.img_adapter_video_background);
            mTvAdapterVideoHead = (CircleImageView) itemView.findViewById(R.id.tv_adapter_video_head);
        }
    }


}
