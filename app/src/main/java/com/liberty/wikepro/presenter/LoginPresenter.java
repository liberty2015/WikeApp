package com.liberty.wikepro.presenter;

import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.LoginContact;
import com.liberty.wikepro.model.LoginModel;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

/**
 * Created by LinJinFeng on 2017/2/13.
 */

public class LoginPresenter extends BasePresenter<LoginContact.View> implements LoginContact.Presenter {

    @Inject
    LoginModel loginModel;

    @Inject
    public LoginPresenter(){
    }

    @Override
    public void login(String userName, String password) {
        loginModel.login(userName, password, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.loginSuccess();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {

            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void registerByPhone(Student student) {
        loginModel.registerByPhone(student, new OkHttpUtil.OkHttpResponseIMPL() {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void registerByEmail(Student student) {
        loginModel.registerByEmail(student, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {

            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }
}
