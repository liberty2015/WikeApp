package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Course;

import java.util.List;

/**
 * Created by liberty on 2017/4/3.
 */

public interface CatagoryContact {
    interface View extends BaseContact.BaseView{
        void refresh(List<Course> courses);

        void loadMore(List<Course> courses);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void refresh(int stu_id,int id);

        void loadMore(int stu_id,int id,int limit,int offset);
    }
}
