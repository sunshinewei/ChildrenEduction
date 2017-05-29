package com.example.administrator.childreneduction.ui.home.iview;

import com.example.administrator.childreneduction.bmob.ArticleTable;

import java.util.List;

/**
 * Created by Administrator on 2017/5/29.
 */

public interface SearchArticleUI {

    /**
     * 加载文章成功
     */
    void upload_art_ok(List<ArticleTable> list);

    /**
     *
     */
    void upload_art_fail();


}
