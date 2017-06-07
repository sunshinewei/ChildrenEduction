package com.example.administrator.childreneduction.ui.home.presenter;

import android.content.Context;

import com.example.administrator.childreneduction.bmob.UA_Table;
import com.example.administrator.childreneduction.ui.home.iview.LookArticleUI;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/20.
 * 查看文章
 */

public class LookArticlePresenter {

    private LookArticleUI mLookArticleUI;

    public LookArticlePresenter(LookArticleUI lookArticleUI) {
        this.mLookArticleUI = lookArticleUI;
    }

    /**
     * 添加评论
     *
     * @param mContext
     * @param ua_table
     */
    public void addComment(Context mContext, UA_Table ua_table) {
        if (ua_table != null) {
            System.out.println("添加评论");
            ua_table.save(mContext, new SaveListener() {
                @Override
                public void onSuccess() {
                    mLookArticleUI.addComment_ok();
                }

                @Override
                public void onFailure(int i, String s) {
                    mLookArticleUI.addComment_fail();
                }
            });
        }
    }

    public void query_Comment(Context mContext, String a_id) {
        BmobQuery<UA_Table> query = new BmobQuery<>();
        query.addWhereEqualTo("a_id", a_id);
        query.addWhereNotEqualTo("ua_comm", null);
        query.findObjects(mContext, new FindListener<UA_Table>() {
            @Override
            public void onSuccess(List<UA_Table> list) {
                mLookArticleUI.query_comment_ok(list);
            }

            @Override
            public void onError(int i, String s) {
                mLookArticleUI.query_comment_fail();
            }
        });
    }
}
