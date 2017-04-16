package com.liberty.wikepro.model;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/4.
 */

public class MyCourseModel implements MyCourseM {

    @Inject
    public MyCourseModel(){}

    @Override
    public void getMyCourses(int stu_id, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("stu_id",stu_id);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().getMyCourse(),params,impl);
    }
}
