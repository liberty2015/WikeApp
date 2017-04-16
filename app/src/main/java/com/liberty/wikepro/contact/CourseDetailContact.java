package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Chapter;
import com.liberty.wikepro.model.bean.User;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public interface CourseDetailContact {

    interface View extends BaseContact.BaseView{
        void setTeacherAndChapters(User user, List<Chapter> chapters);

//        void showChapters(List<Chapter> chapters);

        void firstCourse();
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getTeacherAndChapters(int uid,int cid);

//        void getChapters(int cid);
        void firstCourse(int cid,int id);
    }
}
