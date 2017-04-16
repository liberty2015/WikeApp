package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.net.OkHttpUtil;

/**
 * Created by liberty on 2017/3/28.
 */

public interface CourseVideoM {
    void getChapterList(int cid, int stu_id, OkHttpUtil.OkHttpResponseIMPL impl);

    void getTeacher(int uid,int cid, OkHttpUtil.OkHttpResponseIMPL impl);

    void getDependentCourses(Course course, OkHttpUtil.OkHttpResponseIMPL impl);

    void getVideoTest(CVideo cVideo, OkHttpUtil.OkHttpResponseIMPL impl);

    void applyAnswer(int stu_id, int test_id, String answer, int flag, OkHttpUtil.OkHttpResponseIMPL impl);

    void stuChapterCVideo(int stu_id,int cid,int chid,int cvideo_id, OkHttpUtil.OkHttpResponseIMPL impl);

    void hasScore(int stu_id, int cid,OkHttpUtil.OkHttpResponseIMPL impl);

    void addHistory(Course course, Chapter chapter, CVideo cVideo, int stu_id);
}
