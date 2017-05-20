package com.example.administrator.childreneduction.ui.me.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.ui.me.iview.VideoPublishUI;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by Administrator on 2017/5/21.
 */

public class VideoPublishPresenter {

    private VideoPublishUI mPublishUI;

    public VideoPublishPresenter(VideoPublishUI publishUI) {
        this.mPublishUI = publishUI;
    }


    /**
     * 上传视频
     *
     * @param mContext
     * @param path
     */
    public void upload_video(final Context mContext, String path) {
        final BmobFile bmobFile = new BmobFile(new File(path));
        bmobFile.upload(mContext, new UploadFileListener() {
            @Override
            public void onSuccess() {
                mPublishUI.upload_video_ok(bmobFile.getUrl());
            }

            @Override
            public void onFailure(int i, String s) {
                mPublishUI.upload_video_fail();
            }
        });
    }

    /**
     * 发布视频
     *
     * @param mContext
     * @param vedioTable
     */
    public void pub_video(Context mContext, final VedioTable vedioTable) {
        vedioTable.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                mPublishUI.pub_video_ok();
            }

            @Override
            public void onFailure(int i, String s) {
                mPublishUI.upload_video_fail();
            }
        });
    }
}
