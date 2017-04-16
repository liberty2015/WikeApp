package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Course;

import java.util.List;

/**
 * Created by liberty on 2017/4/4.
 */

public interface MyCourseContact {
    interface View extends BaseContact.BaseView{
        void showMyCourses(List<Course> courses);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getMyCourses(int stu_id);
    }
}
