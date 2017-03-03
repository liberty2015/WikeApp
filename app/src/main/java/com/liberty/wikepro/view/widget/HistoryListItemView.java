package com.liberty.wikepro.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.liberty.wikepro.R;

/**
 * Created by LinJinFeng on 2017/2/28.
 */

public class HistoryListItemView extends View {
    private Paint linePaint,pointPaint;
    private @ColorInt int pointColor;
    private boolean hasPoint;
    private int lineWidth;
    private int pointRadius;
    private @ColorInt int lineColor;
    private int height,width;
    private boolean isHeader;
    private boolean isFooter;

    public HistoryListItemView(Context context) {
        this(context,null);
    }

    public HistoryListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.HistoryListItemView);
        pointColor=array.getColor(R.styleable.HistoryListItemView_pointColor, Color.BLUE);
        int defPointRadius= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,12,getResources().getDisplayMetrics());
        pointRadius=array.getDimensionPixelSize(R.styleable.HistoryListItemView_pointRadius,defPointRadius);
        hasPoint=array.getBoolean(R.styleable.HistoryListItemView_hasPoint,false);
        int defLineWidth= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,0.5f,getResources().getDisplayMetrics());
        lineWidth=array.getDimensionPixelSize(R.styleable.HistoryListItemView_lineWidth,defLineWidth);
        lineColor=array.getColor(R.styleable.HistoryListItemView_lineColor,Color.GRAY);
        array.recycle();
        init();
    }

    private void init(){
        linePaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(lineWidth);
        linePaint.setColor(lineColor);
        pointPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint.setColor(pointColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height=h;
        width=w;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public void setFooter(boolean footer) {
        isFooter = footer;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isHeader){
            canvas.drawLine(width/2,height/2,width/2,height,linePaint);
        }else if (isFooter){
            canvas.drawLine(width/2,0,width/2,height/2,linePaint);
        }else {
            canvas.drawLine(width/2,0,width/2,height,linePaint);
        }
        int cx=width/2;
        int cy=height/2;
        if (hasPoint){
            canvas.drawCircle(cx,cy,pointRadius,pointPaint);
        }
    }
}
