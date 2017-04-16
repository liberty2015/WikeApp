package com.liberty.wikepro.contact;

import com.liberty.wikepro.base.BaseContact;
import com.liberty.wikepro.model.bean.Student;

import java.io.File;

/**
 * Created by liberty on 2017/3/18.
 */

public interface MineContact {
    interface View extends BaseContact.BaseView{
        void editUserCoverSuccess();
        void editUserCoverFail();
    }

    interface Presenter extends BaseContact.BasePresenter<View>{
        void editUserCover(Student student, File head);
    }
}
