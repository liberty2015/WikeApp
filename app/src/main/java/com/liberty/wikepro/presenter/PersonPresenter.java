package com.liberty.wikepro.presenter;

import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.PersonContact;
import com.liberty.wikepro.model.PersonModel;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/15.
 */

public class PersonPresenter extends BasePresenter<PersonContact.View> implements PersonContact.Presenter {

    @Inject
    PersonModel personModel;

    @Inject
    public PersonPresenter(){}

    @Override
    public void attachView(PersonContact.View view) {
        mView=view;
    }

    @Override
    public void detachView() {

    }

    @Override
    public void editUserInfo(Student student) {
        personModel.editUserInfo(student, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {
                mView.editUserInfoFail();
            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                mView.editUserInfoSuccess();
            }

            @Override
            public void onAnalyseDataError(String result) {
                mView.editUserInfoFail();
            }
        });
    }

    @Override
    public void editUserPass(int id, String password) {
        personModel.editUserPass(id, password, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                mView.editUserPassSuccess();
            }

            @Override
            public void onAnalyseDataError(String result) {
                mView.editUserPassFail();
            }
        });
    }

    @Override
    public void editUserGender(int id,int gender) {
        personModel.editUserGender(id,gender, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                mView.editUserGenderSuccess();
            }

            @Override
            public void onAnalyseDataError(String result) {
                mView.editUserGenderFail();
            }
        });
    }

    @Override
    public void editUserHead(final Student student, File head) {
        personModel.editUserHead(student.getId(), head, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    if (object.isNull("dev")){
                        WikeApplication.getInstance().getStudent().setHead_img(object.getString("dev"));
                    }
                    mView.editUserHeadSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnalyseDataError(String result) {
                mView.editUserHeadFail();
            }
        });
    }

}
