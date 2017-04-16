package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Student;

/**
 * Created by LinJinFeng on 2017/2/13.
 */

public interface LoginContact {
    interface View extends BaseContact.BaseView{
        void loginSuccess(Student student);
        void loginFail();

        void registerSuccess(Student student);
        void registerFail(String result);
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void loginByPhone(String userName,String password);
        void loginByEmail(String userName, String password);

        void registerByPhone(Student student);

        void registerByEmail(Student student);
    }
}
