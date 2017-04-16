package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;

/**
 * Created by liberty on 2017/3/19.
 */

public interface HomeM {

    void getCourseList(Student student, OkHttpUtil.OkHttpResponseIMPL impl);

    void getBanner(OkHttpUtil.OkHttpResponseIMPL impl);
}
