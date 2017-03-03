package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by LinJinFeng on 2017/3/1.
 */

public class CVideo extends BaseBean implements itemType{

    private int id;
    private String vname;
    private int chid;
    private int cid;

    public CVideo(){

    }

    private CVideo(Parcel in){
        id=in.readInt();
        vname=in.readString();
        chid=in.readInt();
        cid=in.readInt();
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
