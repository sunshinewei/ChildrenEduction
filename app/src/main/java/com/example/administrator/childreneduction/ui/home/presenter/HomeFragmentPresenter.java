package com.example.administrator.childreneduction.ui.home.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.ui.home.iview.HomeFragmentUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/20.
 */

public class HomeFragmentPresenter {

    private HomeFragmentUI mFragmentUI;

    public HomeFragmentPresenter(HomeFragmentUI fragmentUI){
        this.mFragmentUI=fragmentUI;
    }

    public void getArticle(Context mContext){
        BmobQuery<ArticleTable> query=new BmobQuery<>();
        query.setLimit(20);
        query.findObjects(mContext, new FindListener<ArticleTable>() {
            @Override
            public void onSuccess(List<ArticleTable> list) {
                mFragmentUI.getArticle_ok(list);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


}
