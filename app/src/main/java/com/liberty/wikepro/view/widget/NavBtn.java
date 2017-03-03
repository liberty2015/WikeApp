package com.liberty.wikepro.view.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.liberty.wikepro.R;

/**
 * Created by LinJinFeng on 2017/2/13.
 */

public class NavBtn extends RelativeLayout {
    private AppCompatImageView navImg;
    private TextView navTxt;
    private @DrawableRes int imgId;
    private String navTitle;
    private boolean isSelect;
    private @ColorRes int tintColor;
    private ColorStateList defaultColor;

    public NavBtn(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NavBtn);
        imgId=array.getResourceId(R.styleable.NavBtn_navBtnDrawable,R.drawable.ic_mine);
        navTitle=array.getString(R.styleable.NavBtn_navBtnTxt);
        isSelect=array.getBoolean(R.styleable.NavBtn_isSelect,false);
        tintColor=array.getResourceId(R.styleable.NavBtn_tintColor,R.color.colorPrimary);
        array.recycle();
        init();
    }

    private void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.nav_btn_layout,this);
        navImg= (AppCompatImageView) findViewById(R.id.navImg);
        navTxt= (TextView) findViewById(R.id.navTxt);
        navImg.setImageResource(imgId);
        navTxt.setText(navTitle);
        defaultColor=navTxt.getTextColors();
        if (isSelect){
            updateColor();
        }
    }

    private void updateColor(){
        ColorStateList colorStateList = getResources().getColorStateList(tintColor);
        Drawable icon = navImg.getDrawable();
        icon.mutate();
        Drawable tintIcon= DrawableCompat.wrap(icon);
        DrawableCompat.setTint(tintIcon,getResources().getColor(tintColor));
//        DrawableCompat.setTintList(tintIcon,colorStateList);
        navTxt.setTextColor(getResources().getColor(tintColor));
    }

    public void setIsSelect(boolean isSelect){
        this.isSelect=isSelect;
        if (isSelect){
            updateColor();
        }else {
            Drawable icon = navImg.getDrawable();
//            DrawableCompat.clearColorFilter(icon);
//            icon.clearColorFilter();
            DrawableCompat.setTintList(icon,null);
            navTxt.setTextColor(defaultColor);
            navImg.setImageDrawable(icon);
        }
    }
}
