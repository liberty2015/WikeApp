package com.liberty.wikepro.contact;

import com.liberty.libertylibrary.widget.Banner;
import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.model.bean.itemType;

import java.util.List;


/**
 * Created by LinJinFeng on 2017/2/15.
 */

public interface HomeContact {
    interface View extends BaseContact.BaseView{
        void showBanner(Banner banner);

        void showCourseList(List<itemType> courses);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void getBanner();

        void getCourseList(Student student);


    }
}
