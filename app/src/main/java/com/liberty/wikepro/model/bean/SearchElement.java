package com.liberty.wikepro.model.bean;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by liberty on 2017/4/17.
 */

public class SearchElement {
    String query;

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }

    public SpannableString getSearchSpannable(String str){
        SpannableString string=new SpannableString(str);
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(Color.parseColor("#FF4081"));
        for (int i=0;i<str.length();i++){
            int index=str.indexOf(query,i);
            if (index>=0&&index<str.length()){
                string.setSpan(colorSpan,index,index+query.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                i=i+(index+query.length());
            }else {
                break;
            }
        }
        return string;
    }
}
