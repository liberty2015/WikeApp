package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by liberty on 2017/4/8.
 */

public class Score extends BaseBean {
    private int id;
    private int stu_id;
    private int course_id;
    private int score;

    private Score(Parcel in){
        id=in.readInt();
        stu_id=in.readInt();
        course_id=in.readInt();
        score=in.readInt();
    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(stu_id);
        dest.writeInt(course_id);
        dest.writeInt(score);
    }

    public static final Creator<Score> CREATOR=new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel source) {
            return new Score(source);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}
