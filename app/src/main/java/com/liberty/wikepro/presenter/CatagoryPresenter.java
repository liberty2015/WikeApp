package com.liberty.wikepro.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.CatagoryContact;
import com.liberty.wikepro.model.CatagoryModel;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.net.OkHttpUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/4/3.
 */

public class CatagoryPresenter extends BasePresenter<CatagoryContact.View> implements CatagoryContact.Presenter {

    @Inject
    CatagoryModel model;

    @Inject
    public CatagoryPresenter(){}

    @Override
    public void refresh(int stu_id,int id) {
        model.refresh(stu_id,id, new OkHttpUtil.OkHttpResponseIMPL() {
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
                mView.refresh(courses);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void loadMore(int stu_id,int id, int limit, int offset) {
        model.loadMore(stu_id,id, limit, offset, new OkHttpUtil.OkHttpResponseIMPL() {
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
                mView.loadMore(courses);
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }
}
