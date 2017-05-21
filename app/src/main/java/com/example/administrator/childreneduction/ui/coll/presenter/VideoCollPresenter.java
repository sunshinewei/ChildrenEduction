package com.example.administrator.childreneduction.ui.coll.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.UV_Table;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.coll.iview.VideoCollUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/21.
 *
 * 视频收集
 */

public class VideoCollPresenter {
    private VideoCollUI mVideoCollUI;
    private int page = 1;

    public VideoCollPresenter(VideoCollUI videoCollUI) {
        this.mVideoCollUI = videoCollUI;
    }

    /**
     * 查看收集的视频
     * @param mContext
     * @param state
     * @param loginInfo
     */
    public void coll_video(Context mContext, final int state, LoginInfo loginInfo) {
        final BmobQuery<UV_Table> query = new BmobQuery<>();
        query.addWhereEqualTo("u_id", loginInfo.getId());
        query.setLimit(10);
        if (state == 1) {
            page++;
            query.setSkip(10 * page + 1);
        }
        query.findObjects(mContext, new FindListener<UV_Table>() {
            @Override
            public void onSuccess(List<UV_Table> list) {
                mVideoCollUI.video_coll_data_ok(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
