package com.example.administrator.childreneduction.ui.home.iview;

import com.example.administrator.childreneduction.bmob.VedioTable;

import java.util.List;

/**
 * Created by Administrator on 2017/5/29.
 *
 */

public interface SearchVideoUI {

    void upload_video_ok(List<VedioTable> list);

    void upload_video_fail();

}
