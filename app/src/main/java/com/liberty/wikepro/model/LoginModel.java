package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import javax.inject.Inject;

/**
 * Created by LinJinFeng on 2017/2/21.
 */

public class LoginModel implements LoginM {

    @Inject
    public LoginModel(){}

    @Override
    public void registerByPhone(Student student,OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("loginName",student.getPhone());
        params.add("loginPassword",student.getPassword());
        params.add("gender",Integer.toString(student.getGender()));
        params.add("nickName",student.getNickName());
        params.add("self_describe",student.getSelf_describe());
        params.add("head_img",student.getHead_img());
        params.add("page_img",student.getPage_img());
        params.add("name",student.getName());
        OkHttpUtil.getInstance().post(WikeApi.getInstance().registerByPhone(), params,impl);
    }

    @Override
    public void login(String userName, String password,OkHttpUtil.OkHttpResponseIMPL impl) {

    }

    @Override
    public void registerByEmail(Student student,OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("loginName",student.getEmail());
        params.add("loginPassword",student.getPassword());
        params.add("gender",Integer.toString(student.getGender()));
        params.add("nickName",student.getNickName());
        params.add("self_describe",student.getSelf_describe());
        params.add("head_img",student.getHead_img());
        params.add("page_img",student.getPage_img());
        params.add("name",student.getName());
        OkHttpUtil.getInstance().post(WikeApi.getInstance().registerByEmail(), params,impl);
    }
}
