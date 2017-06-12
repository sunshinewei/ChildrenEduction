package com.example.administrator.childreneduction.ui.home.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.ui.home.iview.SearchVideoUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/29.
 */

public class SearchVideoPresenter {
    private SearchVideoUI mSearchVideoUI;
    private int page=1;

    public SearchVideoPresenter(SearchVideoUI searchVideoUI){
        this.mSearchVideoUI=searchVideoUI;
    }

    public void upload_video(Context mContext,int state,String label){
        BmobQuery<VedioTable> query=new BmobQuery<>();
//        query.addWhereContains("v_label",label);
        query.addWhereEqualTo("v_label",label);
        query.setLimit(10);
        query.order("-createdAt");
        if (state==1){
            page++;
            query.setSkip(page*10+1);
        }
        query.findObjects(mContext, new FindListener<VedioTable>() {
            @Override
            public void onSuccess(List<VedioTable> list) {
                mSearchVideoUI.upload_video_ok(list);
            }
            @Override
            public void onError(int i, String s) {
                mSearchVideoUI.upload_video_fail();
            }
        });
    }
}
