package com.example.administrator.childreneduction.ui.me.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.VedioTable;
import com.example.administrator.childreneduction.ui.me.iview.MeVideoUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MeVideoPresenter {

    private MeVideoUI mMeVideoUI;
    private int page=1;

    public MeVideoPresenter(MeVideoUI meVideoUI){
        this.mMeVideoUI=meVideoUI;
    }

    public void load_mearticle(Context mContext, String uid, int state){
        BmobQuery<VedioTable> query=new BmobQuery<>();
        query.addWhereEqualTo("u_id",uid);
        query.setLimit(10);

        if (state==1){
            page++;
            query.setSkip(10*page+1);
        }
        query.findObjects(mContext, new FindListener<VedioTable>() {
            @Override
            public void onSuccess(List<VedioTable> list) {
                mMeVideoUI.load_mevideo_ok(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

}
