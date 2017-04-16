package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;

/**
 * Created by liberty on 2017/4/4.
 */

public interface MyCourseM {
    void getMyCourses(int stu_id, OkHttpUtil.OkHttpResponseIMPL impl);
}
