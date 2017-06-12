package com.example.administrator.childreneduction.ui.vedio.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.ui.vedio.iview.VedioFragmentUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/21.
 *
 * 视频 presenter
 */

public class VedioFragmentPresenter {

    private VedioFragmentUI mFragmentUI;
    private int page=1;
    public VedioFragmentPresenter(VedioFragmentUI fragmentUI){
        this.mFragmentUI=fragmentUI;
    }


    /**
     * 获取视频条目
     * @param mContext
     */
    public void video_item(Context mContext,int state){
        BmobQuery<VedioTable> query=new BmobQuery<>();
        query.order("-createdAt");
        if (state==1){
            page++;
            query.setSkip(page*10+1);
        }
        query.findObjects(mContext, new FindListener<VedioTable>() {
            @Override
            public void onSuccess(List<VedioTable> list) {
                mFragmentUI.video_item_ok(list);
            }

            @Override
            public void onError(int i, String s) {
                mFragmentUI.video_item_fail();
            }
        });
    }


}
