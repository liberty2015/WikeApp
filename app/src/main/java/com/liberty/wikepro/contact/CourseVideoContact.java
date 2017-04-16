package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.CTest;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Score;
import com.liberty.wikepro.model.bean.User;
import com.liberty.wikepro.model.bean.itemType;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public interface CourseVideoContact {
    interface View extends BaseContact.BaseView{
        void showChapterList(List<itemType> chapters);

        void showTeacher(User user);

        void showDependentCourses(List<Course> courses);

        void getVideoTestList(List<CTest> cTests);

        void applyAnswerRight();

        void applyAnswerWrong();

        void hasScore(Score score);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getChapterList(int cid,int stu_id);

        void getTeacher(int uid,int cid);

        void getDependentCourses(Course course);

        void getVideoTest(CVideo cVideo);

        void applyAnswer(int stu_id,int test_id,String answer,int flag);

        void stuChapterCVideo(int stu_id,int cid,int chid,int cvideo_id);

        void hasScore(int stu_id,int cid);

        void addHistory(Course course, Chapter chapter,CVideo cVideo,int stu_id);
    }
}
