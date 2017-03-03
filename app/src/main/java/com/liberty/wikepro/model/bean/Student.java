package com.liberty.wikepro.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LinJinFeng on 2017/2/15.
 */

public class Student extends BaseBean {
    private int id;
    private int gender;
    private String nickName;
    private String name;
    private String password;
    private String phone;
    private String email;
    private String self_describe;
    private String head_img;
    private String page_img;
    private String job;

    public Student(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getGender() {
        return gender;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSelf_describe(String self_describe) {
        this.self_describe = self_describe;
    }

    public String getSelf_describe() {
        return self_describe;
    }

    public void setHead_img(String head_img) {
        this.head_img = head_img;
    }

    public String getHead_img() {
        return head_img;
    }

    public void setPage_img(String page_img) {
        this.page_img = page_img;
    }

    public String getPage_img() {
        return page_img;
    }

    private Student(Parcel in){
        id=in.readInt();
        gender=in.readInt();
        nickName=in.readString();
        name=in.readString();
        password=in.readString();
        phone=in.readString();
        email=in.readString();
        self_describe=in.readString();
        head_img=in.readString();
        page_img=in.readString();
        job=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(gender);
        dest.writeString(nickName);
        dest.writeString(name);
        dest.writeString(password);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(self_describe);
        dest.writeString(head_img);
        dest.writeString(page_img);
        dest.writeString(job);
    }

    public final static Parcelable.Creator<Student> CREATOR=new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
