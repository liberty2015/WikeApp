package com.liberty.wikepro.model.bean;

import java.util.Date;

/**
 * Created by liberty on 2017/4/9.
 */

public class DateWrapper implements itemType {
    private Date date;

    public DateWrapper(Date date){
        this.date=date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }
}
