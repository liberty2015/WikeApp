package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Student;

/**
 * Created by LinJinFeng on 2017/2/13.
 */

public interface LoginContact {
    interface View extends BaseContact.BaseView{
        void loginSuccess();
        void loginFail();

        void registerSuccess();
        void registerFail();
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void login(String userName,String password);

        void registerByPhone(Student student);

        void registerByEmail(Student student);
    }
}
