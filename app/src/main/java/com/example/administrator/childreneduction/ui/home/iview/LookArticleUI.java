package com.example.administrator.childreneduction.ui.home.iview;

import com.example.administrator.childreneduction.bmob.UA_Table;

import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public interface LookArticleUI {

    void addComment_ok();

    void addComment_fail();

    void query_comment_ok(List<UA_Table> list);

    void query_comment_fail();
}
