package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by liberty on 2017/3/19.
 */

public class direction extends BaseBean implements itemType{

    private String name;
    private int id;

    public direction(){
    }

    private direction(Parcel in){
        id=in.readInt();
        name=in.readString();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }

    public static final Creator<direction> CREATOR=new Creator<direction>() {
        @Override
        public direction createFromParcel(Parcel source) {
            return new direction(source);
        }

        @Override
        public direction[] newArray(int size) {
            return new direction[size];
        }
    };
}
