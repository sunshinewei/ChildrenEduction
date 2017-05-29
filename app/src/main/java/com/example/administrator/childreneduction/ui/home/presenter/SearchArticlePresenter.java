package com.example.administrator.childreneduction.ui.home.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.ui.home.iview.SearchArticleUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/29.
 */

public class SearchArticlePresenter {

    private SearchArticleUI mArticleUI;
    private int page=1;

    public SearchArticlePresenter(SearchArticleUI searchArticleUI){
        this.mArticleUI=searchArticleUI;
    }

    /**
     * 查询在某个年龄端的文章
     * @param mContext
     * @param label
     * @param state
     */
    public void upload_art(Context mContext, String label, int state){
        BmobQuery<ArticleTable> query=new BmobQuery<>();
//        query.addWhereContains("a_label",label);
        query.addWhereEqualTo("a_label",label);
        query.setLimit(10);
        if (state==1){
            page++;
            query.setSkip(page*10+1);
        }
        query.findObjects(mContext, new FindListener<ArticleTable>() {
            @Override
            public void onSuccess(List<ArticleTable> list) {
                mArticleUI.upload_art_ok(list);
            }

            @Override
            public void onError(int i, String s) {
                mArticleUI.upload_art_fail();
            }
        });

    }

}
