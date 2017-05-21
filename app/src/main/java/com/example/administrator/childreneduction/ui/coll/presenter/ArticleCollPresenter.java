package com.example.administrator.childreneduction.ui.coll.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.UA_Table;
import com.example.administrator.childreneduction.model.LoginInfo;
import com.example.administrator.childreneduction.ui.coll.iview.ArticleFragmentUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/21.
 */

public class ArticleCollPresenter {

    private ArticleFragmentUI mFragmentUI;
    private int page = 1;

    public ArticleCollPresenter(ArticleFragmentUI fragmentUI) {

        this.mFragmentUI = fragmentUI;
    }

    /**
     * 收藏的文章
     *
     * @param mContext
     */
    public void coll_article(Context mContext, final int state, LoginInfo loginInfo) {
        final BmobQuery<UA_Table> query = new BmobQuery<>();
        query.addWhereEqualTo("u_id", loginInfo.getId());
        query.setLimit(10);
        if (state==1){
            page++;
            query.setSkip(10 * page + 1);
        }
        query.findObjects(mContext, new FindListener<UA_Table>() {
            @Override
            public void onSuccess(List<UA_Table> list) {
                if (state == 0) {
                    mFragmentUI.article_collect_data_ok(list);
                }
                if (state == 1) {
                    mFragmentUI.article_collect_data_ok(list);
                }
            }

            @Override
            public void onError(int i, String s) {
                mFragmentUI.article_collect_data_fail();
            }
        });
    }


}
