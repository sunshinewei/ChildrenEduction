package com.example.administrator.childreneduction.ui.me.iview;

import com.example.administrator.childreneduction.bmob.ArticleTable;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public interface MeArticleUI {

    void load_mearticle_ok(List<ArticleTable> list);
    void load_mearticle_fail();
}
