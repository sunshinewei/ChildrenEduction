package com.example.administrator.childreneduction.ui.me.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.ui.me.iview.MeArticleUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MeArticlePresenter {

    private MeArticleUI mArticleUI;
    private int page=1;

    public MeArticlePresenter(MeArticleUI meArticleUI){
        this.mArticleUI=meArticleUI;
    }

    public void load_mearticle(Context mContext,String uid, int state){
        BmobQuery<ArticleTable> query=new BmobQuery<>();
        query.addWhereEqualTo("u_id",uid);
        query.setLimit(10);

        if (state==1){
            page++;
            query.setSkip(10*page+1);
        }
        query.findObjects(mContext, new FindListener<ArticleTable>() {
            @Override
            public void onSuccess(List<ArticleTable> list) {
                mArticleUI.load_mearticle_ok(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }
}
