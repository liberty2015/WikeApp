package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;

import java.io.File;

/**
 * Created by liberty on 2017/3/16.
 */

public interface PersonM {
    void editUserInfo(Student student,OkHttpUtil.OkHttpResponseIMPL impl);

    void editUserPass(int id,String password,OkHttpUtil.OkHttpResponseIMPL impl);

    void editUserGender(int id,int gender,OkHttpUtil.OkHttpResponseIMPL impl);

    void editUserHead(int id, File head, OkHttpUtil.OkHttpResponseIMPL impl);

}
