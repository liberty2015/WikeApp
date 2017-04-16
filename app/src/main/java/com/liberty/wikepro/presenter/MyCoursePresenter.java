package com.liberty.wikepro.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.MyCourseContact;
import com.liberty.wikepro.model.MyCourseModel;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.net.OkHttpUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/4.
 */

public class MyCoursePresenter extends BasePresenter<MyCourseContact.View> implements MyCourseContact.Presenter {

    @Inject
    MyCourseModel model;

    @Inject
    public MyCoursePresenter(){}

    @Override
    public void getMyCourses(int stu_id) {
        model.getMyCourses(stu_id, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                List<Course> courses=new Gson().fromJson(result,new TypeToken<List<Course>>(){}.getType());
                mView.showMyCourses(courses);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }
}
