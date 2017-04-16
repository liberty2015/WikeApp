package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Student;

import java.io.File;

/**
 * Created by liberty on 2017/3/15.
 */

public interface PersonContact {
    interface View extends BaseContact.BaseView{
        void editUserInfoSuccess();
        void editUserInfoFail();

        void editUserPassSuccess();
        void editUserPassFail();

        void editUserGenderSuccess();
        void editUserGenderFail();

        void editUserHeadSuccess();
        void editUserHeadFail();

    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void editUserInfo(Student student);

        void editUserPass(int id,String password);

        void editUserGender(int id,int gender);

        void editUserHead(Student student,File head);

    }
}
