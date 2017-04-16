package com.liberty.wikepro.model;

import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

/**
 * Created by liberty on 2017/3/16.
 */

public class PersonModel implements PersonM {

    @Inject
    public PersonModel(){}

    @Override
    public void editUserInfo(Student student, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("id",Integer.toString(student.getId()));
        params.add("email",student.getEmail());
        params.add("phone",student.getPhone());
        params.add("password",student.getPassword());
        params.add("gender",Integer.toString(student.getGender()));
        params.add("nickName",student.getNickname());
        params.add("self_describe",student.getSelf_describe());
        params.add("head_img",student.getHead_img());
        params.add("page_img",student.getPage_img());
        params.add("name",student.getName());
        params.add("job",student.getJob());
        OkHttpUtil.getInstance().post(WikeApi.getInstance().editUserInfo(), params,impl);
    }

    @Override
    public void editUserPass(int id, String password, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("id",Integer.toString(id));
        params.add("password",password);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().editUserPass(), params,impl);
    }

    @Override
    public void editUserGender(int id,int gender, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("gender",Integer.toString(gender));
        params.add("id",Integer.toString(id));
        OkHttpUtil.getInstance().post(WikeApi.getInstance().editUserGender(), params,impl);
    }

    @Override
    public void editUserHead(int id, File head, OkHttpUtil.OkHttpResponseIMPL impl) {
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("id",Integer.toString(id));
        String[] keys={"image"};
        File[] files={head};
        try {
            OkHttpUtil.getInstance().postFile(WikeApi.getInstance().editUserHead(),files,keys,params,impl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
