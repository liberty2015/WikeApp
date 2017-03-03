package com.liberty.libertylibrary.widget;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by LinJinFeng on 2016/9/26.
 */

public class Banner implements Serializable {
    private String name;
    private Date date;
    private List<photos > photos;

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

    public List<photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<photos> photos) {
        this.photos = photos;
    }

    public static class photos{
        private String contentUrl;
        private String name;
        private String photoUrl;

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
    }
}
