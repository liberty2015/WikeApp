package com.liberty.wikepro.model.bean;

import android.os.Parcel;

import java.util.Date;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class history extends BaseBean implements itemType{

    private int _id;
    private int course_id;
    private String course;
    private int chapter_id;
    private String chapter;
    private String cvideo;
    private int cvideo_id;
    private Date time;
    private int stu_id;

    private history(Parcel in){
        _id=in.readInt();
        course_id=in.readInt();
        course=in.readString();
        chapter_id=in.readInt();
        chapter=in.readString();
        cvideo=in.readString();
        cvideo_id=in.readInt();
        time= (Date) in.readSerializable();
        stu_id=in.readInt();
    }

    public history(){}

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_id() {
        return _id;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public Date getTime() {
        return time;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getChapter() {
        return chapter;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCourse() {
        return course;
    }

    public int getCvideo_id() {
        return cvideo_id;
    }

    public void setCvideo(String cvideo) {
        this.cvideo = cvideo;
    }

    public String getCvideo() {
        return cvideo;
    }

    public void setCvideo_id(int cvideo_id) {
        this.cvideo_id = cvideo_id;
    }

    public static Creator<history> getCREATOR() {
        return CREATOR;
    }

    public static final Creator<history> CREATOR=new Creator<history>() {
        @Override
        public history createFromParcel(Parcel source) {
            return new history(source);
        }

        @Override
        public history[] newArray(int size) {
            return new history[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeInt(course_id);
        dest.writeString(course);
        dest.writeInt(chapter_id);
        dest.writeString(chapter);
        dest.writeString(cvideo);
        dest.writeInt(cvideo_id);
        dest.writeSerializable(time);
        dest.writeInt(stu_id);
    }
}
