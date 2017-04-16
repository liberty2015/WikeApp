package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/27.
 */

public class CourseDetailModel implements CourseDetailM {

    @Inject
    public CourseDetailModel(){

    }

    @Override
    public void getTeacherAndChapters(int uid,int cid, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("uid",Integer.toString(uid));
        params.add("cid",Integer.toString(cid));
        OkHttpUtil.getInstance().get(WikeApi.getInstance().getGetCourseUser(),params,impl);
    }

    @Override
    public void firstCourse(int cid, int id, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("cid",Integer.toString(cid));
        params.add("id",Integer.toString(id));
        OkHttpUtil.getInstance().get(WikeApi.getInstance().firstCourse(),params,impl);
    }


}
