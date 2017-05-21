package com.example.administrator.childreneduction.ui.coll.iview;

import com.example.administrator.childreneduction.bmob.UV_Table;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface VideoCollUI {

    /**
     * 成功
     * @param list
     */
    void video_coll_data_ok(List<UV_Table> list);

    /**
     *
     */
    void video_coll_data_fail();

}
