package com.liberty.wikepro;

import android.app.Application;

import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerApplicationComponent;
import com.liberty.wikepro.model.AppPreferenceHelper;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.module.ApplicationModule;
import com.liberty.wikepro.util.NetStateReceiver;
import com.liberty.wikepro.view.activity.LoginActivity;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class WikeApplication extends Application {

    private static WikeApplication instance;
    public ApplicationComponent mAppComponent;

    private Student student;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mAppComponent= DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(instance))
                .build();
        NetStateReceiver.registerNetworkStateReceiver(instance);
    }

    public void initStudent(){
        student=new Student();
        student.setId(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getInt("id",0));
        student.setName(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("name",""));
        student.setNickname(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("nickName",""));
        student.setPhone(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("phone",""));
        student.setPassword(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("password",""));
        student.setEmail(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("email",""));
        student.setSelf_describe(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("self_describe",""));
        student.setHead_img(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("head_img",""));
        student.setPage_img(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("page_img",""));
        student.setJob(AppPreferenceHelper.getInstance(this, LoginActivity.LoginPre).getString("job",""));
        student.setGender(AppPreferenceHelper.getInstance(this,LoginActivity.LoginPre).getInt("gender",1));
        setStudent(student);
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        if (student==null) initStudent();
        return student;
    }

    @Override
    public void onLowMemory() {
        if (instance!=null){
            NetStateReceiver.unRegisterNetworkStateReceiver(instance);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        super.onLowMemory();
    }

    public static WikeApplication getInstance() {
        return instance;
    }
}
