package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by LinJinFeng on 2017/2/15.
 */

public class Course extends BaseBean implements itemType{

    private int id;
    private String cname;
    private int uid;
    private int count;
    private int type;
    private String direction;
    private int clevel;
    private int unum;
    private String describtion;
    private String pdev;
    private int spanCount;
    private int homeType;
    private int hasStudy;

    public static final int RECOMMAND=0x11;
    public static final int NEW=0x12;

    public Course(){

    }

    private Course(Parcel in){
        id=in.readInt();
        cname=in.readString();
        uid=in.readInt();
        count=in.readInt();
        type=in.readInt();
        direction=in.readString();
        clevel=in.readInt();
        unum=in.readInt();
        describtion=in.readString();
        pdev=in.readString();
        spanCount=in.readInt();
        homeType=in.readInt();
        hasStudy=in.readInt();
    }

    public void setNEW() {
        this.homeType = NEW;
    }

    public void setRECOMMAND() {
        this.homeType = RECOMMAND;
    }

    public int getHomeType() {
        return homeType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCname() {
        return cname;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public void setClevel(int clevel) {
        this.clevel = clevel;
    }

    public int getClevel() {
        return clevel;
    }

    public void setUnum(int unum) {
        this.unum = unum;
    }

    public int getUnum() {
        return unum;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getDescribtion() {
        return describtion;
    }

    public String getPdev() {
        return pdev;
    }

    public void setPdev(String pdev) {
        this.pdev = pdev;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public int getHasStudy() {
        return hasStudy;
    }

    public void setHasStudy(int hasStudy) {
        this.hasStudy = hasStudy;
    }

    public void setHomeType(int homeType) {
        this.homeType = homeType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(cname);
        dest.writeInt(uid);
        dest.writeInt(count);
        dest.writeInt(type);
        dest.writeString(describtion);
        dest.writeInt(clevel);
        dest.writeInt(unum);
        dest.writeString(describtion);
        dest.writeString(pdev);
        dest.writeInt(spanCount);
        dest.writeInt(homeType);
        dest.writeInt(hasStudy);
    }

    public static final Creator<Course> CREATOR=new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel source) {
            return new Course(source);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };
}
