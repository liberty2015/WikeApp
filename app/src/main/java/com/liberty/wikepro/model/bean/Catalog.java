package com.liberty.wikepro.model.bean;


import android.os.Parcel;
import android.support.annotation.IdRes;

/**
 * Created by LinJinFeng on 2017/2/22.
 */

public class Catalog extends BaseBean implements itemType {

    private String title;

    private @IdRes int resId;

    private Catalog(Parcel in){
        title=in.readString();
        resId=in.readInt();
    }

    public Catalog(){

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getResId() {
        return resId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
    }

    public static final Creator<Catalog> CREATOR=new Creator<Catalog>() {
        @Override
        public Catalog createFromParcel(Parcel source) {
            return new Catalog(source);
        }

        @Override
        public Catalog[] newArray(int size) {
            return new Catalog[size];
        }
    };
}
