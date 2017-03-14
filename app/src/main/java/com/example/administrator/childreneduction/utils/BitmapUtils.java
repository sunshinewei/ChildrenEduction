package com.example.administrator.childreneduction.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.example.administrator.childreneduction.utils.picasso.CircleTransformation;
import com.example.administrator.childreneduction.utils.picasso.RoundedTransformation;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class BitmapUtils {

    /**
     * @param mContext
     * @param mUrl
     * @param mImageView
     */
    public static void setBitMap(Context mContext, Uri mUrl, ImageView mImageView){
        Picasso.with(mContext)
                .load(mUrl)
                .into(mImageView);
    }

    public static void setBitMap(Context mContext, String mPath, ImageView mImageView){
        Picasso.with(mContext)
                .load(mPath)
                .into(mImageView);
    }

    public static void setBitMap(Context mContext, int mResouseID, ImageView mImageView){
        Picasso.with(mContext)
                .load(mResouseID)
                .into(mImageView);
    }
    /**
     * 加载圆形图片
     * @param mContext
     * @param mUrl
     * @param mImageView
     */
    public void setCircleBitMap(Context mContext, Uri mUrl, ImageView mImageView){
        Picasso.with(mContext)
                .load(mUrl)
                .transform(new CircleTransformation())
                .into(mImageView);
    }
    public void setCircleBitMap(Context mContext, String mPath, ImageView mImageView){
        Picasso.with(mContext)
                .load(mPath)
                .transform(new CircleTransformation())
                .into(mImageView);
    }

    public void setCircleBitMap(Context mContext, int mResouceID, ImageView mImageView){
        Picasso.with(mContext)
                .load(mResouceID)
                .transform(new CircleTransformation())
                .into(mImageView);
    }

    /**
     * 加载圆角图片
     * @param mContext
     * @param mUrl
     * @param mImageView
     */
    public void setRoundBitMap(Context mContext, Uri mUrl, ImageView mImageView,int radius, int margin) {
        Picasso.with(mContext)
                .load(mUrl)
                .transform(new RoundedTransformation(radius, margin))
                .into(mImageView);

    }
    public void setRoundBitMap(Context mContext, String mPath, ImageView mImageView,int radius, int margin) {
        Picasso.with(mContext)
                .load(mPath)
                .transform(new RoundedTransformation(radius, margin))
                .into(mImageView);
    }
    public void setRoundBitMap(Context mContext, int mResouceID, ImageView mImageView,int radius, int margin) {
        Picasso.with(mContext)
                .load(mResouceID)
                .transform(new RoundedTransformation(radius, margin))
                .into(mImageView);
    }
}
