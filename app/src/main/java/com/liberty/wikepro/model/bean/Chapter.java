package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by LinJinFeng on 2017/2/28.
 */

public class Chapter extends BaseBean implements itemType{

    private int id;
    private String chname;
    private int chnum;

    public Chapter(){

    }

    private Chapter(Parcel in){
        id=in.readInt();
        chname=in.readString();
        chnum=in.readInt();
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
