package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by liberty on 2017/4/16.
 */

public class Search extends BaseBean {

    private int _id;
    private String query;

    public Search(){

    }

    private Search(Parcel in){
        _id=in.readInt();
        query=in.readString();
    }

    public int get_id() {
        return _id;
    }

    public String getQuery() {
        return query;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(query);
    }

    public static final Creator<Search> CREATOR=new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel source) {
            return new Search(source);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };
}
