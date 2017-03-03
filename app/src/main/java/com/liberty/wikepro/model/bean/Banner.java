package com.liberty.wikepro.model.bean;

import android.os.Parcel;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/9/26.
 */

public class Banner extends BaseBean {
    private String name;
    private Date date;
    private List<photo > photos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<photo> photos) {
        this.photos = photos;
    }

    public Banner(){

    }

    private Banner(Parcel in){
        name=in.readString();
        date= (Date) in.readSerializable();
        in.readTypedList(photos,photo.CREATOR);
    }

    public static final Creator<Banner>CREATOR =new Creator<Banner>() {
        @Override
        public Banner createFromParcel(Parcel source) {
            return new Banner(source);
        }

        @Override
        public Banner[] newArray(int size) {
            return new Banner[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeSerializable(date);
        dest.writeTypedList(photos);
    }

    public static class photo extends BaseBean{
        private String contentUrl;
        private String name;
        private String photoUrl;

        public photo(){

        }

        private photo(Parcel in){
            contentUrl=in.readString();
            name=in.readString();
            photoUrl=in.readString();
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public String getName() {
            return name;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(contentUrl);
            dest.writeString(name);
            dest.writeString(photoUrl);
        }

        public static final Creator<photo> CREATOR=new Creator<photo>() {
            @Override
            public photo createFromParcel(Parcel source) {
                return new photo(source);
            }

            @Override
            public photo[] newArray(int size) {
                return new photo[size];
            }
        };
    }
}
