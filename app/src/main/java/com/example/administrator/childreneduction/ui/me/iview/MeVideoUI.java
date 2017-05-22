package com.example.administrator.childreneduction.ui.me.iview;

import com.example.administrator.childreneduction.bmob.VedioTable;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public interface MeVideoUI {

    void load_mevideo_ok(List<VedioTable> vedioTable);
    void load_mevideo_fail();
}
