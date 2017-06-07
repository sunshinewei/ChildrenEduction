package com.example.administrator.childreneduction.ui.coll.iview;

import com.example.administrator.childreneduction.bmob.ArticleTable;
import com.example.administrator.childreneduction.bmob.UA_Table;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface ArticleFragmentUI {

    /**
     * @param list
     */
    void article_collect_data_ok(List<UA_Table> list);

    void article_collect_data_fail();

    void article_data_ok(ArticleTable articleTable);

    void article_data_fail();
}
