package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/19.
 */

public class HomeModel implements HomeM {

    @Inject
    public HomeModel(){

    }

    @Override
    public void getCourseList(Student student, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("id",Integer.toString(student.getId()));
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getRecommendCourses(),params,impl);
    }

    @Override
    public void getBanner(OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getBanner(), null,impl);
    }

}
