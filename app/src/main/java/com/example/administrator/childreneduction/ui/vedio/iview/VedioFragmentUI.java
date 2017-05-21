package com.example.administrator.childreneduction.ui.vedio.iview;

import com.example.administrator.childreneduction.bmob.VedioTable;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 * 视频 VedioFragment
 */

public interface VedioFragmentUI {

    /**
     * 获取视频条目成功
     * @param vedioTable
     */
    void video_item_ok(List<VedioTable> vedioTable);

    void video_item_fail();


}
