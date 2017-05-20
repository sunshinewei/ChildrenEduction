package com.example.administrator.childreneduction.ui.me.iview;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface VideoPublishUI {
    /**
     * 上传文件成功
     * @param link
     */
    void upload_video_ok(String link);

    /**
     * 上传文件失败
     */
    void upload_video_fail();

    /**
     * 发布失败
     */
    void pub_video_ok();

    /**
     * 发布成功
     */
    void pub_video_fail();
}
