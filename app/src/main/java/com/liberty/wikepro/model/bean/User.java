package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by liberty on 2017/3/27.
 */

public class User extends BaseBean {

    private int id;

    private String name;

    private String sex;

    private String job;

    private String describtion;

    public User(){

    }

    private User(Parcel in){
        id=in.readInt();
        name=in.readString();
        sex=in.readString();
        job=in.readString();
        describtion=in.readString();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    public String getDescribtion() {
        return describtion;
    }

    public String getJob() {
        return job;
    }

    public String getSex() {
        return sex;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(sex);
        dest.writeString(job);
        dest.writeString(describtion);
    }

    public static final Creator<User> CREATOR=new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
