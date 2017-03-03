package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by LinJinFeng on 2017/2/17.
 */

public class Type extends BaseBean {
    private String name;
    private int did;
    private String tdev;

    public Type(){

    }

    private Type(Parcel in){
        name=in.readString();
        did=in.readInt();
        tdev=in.readString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getTdev() {
        return tdev;
    }

    public void setTdev(String tdev) {
        this.tdev = tdev;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(did);
        dest.writeString(tdev);
    }

    public static final Creator<Type> CREATOR=new Creator<Type>() {
        @Override
        public Type createFromParcel(Parcel source) {
            return new Type(source);
        }

        @Override
        public Type[] newArray(int size) {
            return new Type[size];
        }
    };


}
