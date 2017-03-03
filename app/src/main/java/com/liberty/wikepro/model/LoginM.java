package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;

/**
 * Created by LinJinFeng on 2017/2/21.
 */

public interface LoginM {
    void registerByPhone(Student student, OkHttpUtil.OkHttpResponseIMPL impl);

    void login(String userName, String password,OkHttpUtil.OkHttpResponseIMPL impl);

    void registerByEmail(Student student,OkHttpUtil.OkHttpResponseIMPL impl);

}
