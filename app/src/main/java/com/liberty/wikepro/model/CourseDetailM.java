package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;

/**
 * Created by liberty on 2017/3/27.
 */

public interface CourseDetailM {
    void getTeacherAndChapters(int uid,int cid, OkHttpUtil.OkHttpResponseIMPL impl);

    void firstCourse(int cid,int id,OkHttpUtil.OkHttpResponseIMPL impl);
}
