package com.liberty.wikepro.model.bean;

import android.os.Parcel;

import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class CourseList extends BaseBean implements itemType{
    private List<Course> courses;

    public CourseList(){

    }

    private CourseList(Parcel in){
        in.readTypedList(courses,Course.CREATOR);
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(courses);
    }

    public static final Creator<CourseList> CREATOR=new Creator<CourseList>() {
        @Override
        public CourseList createFromParcel(Parcel source) {
            return new CourseList(source);
        }

        @Override
        public CourseList[] newArray(int size) {
            return new CourseList[size];
        }
    };
}
