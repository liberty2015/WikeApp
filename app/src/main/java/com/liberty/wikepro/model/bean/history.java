package com.liberty.wikepro.model.bean;

import android.os.Parcel;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class history extends BaseBean {



    public static final Creator<history> CREATOR=new Creator<history>() {
        @Override
        public history createFromParcel(Parcel source) {
            return null;
        }

        @Override
        public history[] newArray(int size) {
            return new history[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
