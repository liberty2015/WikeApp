package com.liberty.wikepro.model.bean;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/28.
 */

public class Chapter extends BaseBean implements itemType{

    private int id;
    private String chname;
    private int chnum;
    private String describtion;
    private int cid;
    private List<CVideo> cvideo;
    private List<itemType> itemTypes;

    public Chapter(){

    }

    private Chapter(Parcel in){
        id=in.readInt();
        chname=in.readString();
        chnum=in.readInt();
        describtion=in.readString();
        cid=in.readInt();
        cvideo=new ArrayList<>();
        in.readTypedList(cvideo,CVideo.CREATOR);
    }

    public List<CVideo> getCvideo() {
        return cvideo;
    }

    public void setCvideo(List<CVideo> cvideo) {
        this.cvideo = cvideo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChnum() {
        return chnum;
    }

    public void setChnum(int chnum) {
        this.chnum = chnum;
    }

    public String getChname() {
        return chname;
    }

    public void setChname(String chname) {
        this.chname = chname;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public void setItemTypes(List<itemType> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public List<itemType> getItemTypes() {
        return itemTypes;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(chname);
        dest.writeInt(chnum);
        dest.writeString(describtion);
        dest.writeInt(cid);
        dest.writeTypedList(cvideo);
    }

    public static final Creator<Chapter> CREATOR=new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel source) {
            return new Chapter(source);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };
}
