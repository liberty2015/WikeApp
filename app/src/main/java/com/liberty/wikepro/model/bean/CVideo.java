package com.liberty.wikepro.model.bean;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public class CVideo extends BaseBean implements itemType{

    private int id;
    private String vname;
    private int chid;
    private int cid;
    private String vdev;
    private List<CTest> ctest;
    private Chapter chapter;

    public CVideo(){

    }

    private CVideo(Parcel in){
        id=in.readInt();
        vname=in.readString();
        chid=in.readInt();
        cid=in.readInt();
        vdev=in.readString();
        ctest=new ArrayList<>();
        in.readTypedList(ctest,CTest.CREATOR);
        chapter=in.readParcelable(Chapter.class.getClassLoader());
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public int getId() {
        return id;
    }

    public int getChid() {
        return chid;
    }

    public int getCid() {
        return cid;
    }

    public String getVname() {
        return vname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setChid(int chid) {
        this.chid = chid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    public void setVdev(String vdev) {
        this.vdev = vdev;
    }

    public String getVdev() {
        return vdev;
    }

    public List<CTest> getCtest() {
        return ctest;
    }

    public void setCtest(List<CTest> ctest) {
        this.ctest = ctest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(vname);
        dest.writeInt(chid);
        dest.writeInt(cid);
        dest.writeString(vdev);
        dest.writeTypedList(ctest);
    }

    public static final Creator<CVideo> CREATOR=new Creator<CVideo>() {
        @Override
        public CVideo createFromParcel(Parcel source) {
            return new CVideo(source);
        }

        @Override
        public CVideo[] newArray(int size) {
            return new CVideo[size];
        }
    };
}
