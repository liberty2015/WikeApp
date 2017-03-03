package com.liberty.libertylibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.liberty.libertylibrary.R;
import com.liberty.libertylibrary.util.CommonUtil;
import com.squareup.picasso.Picasso;




/**
 * Created by Administrator on 2016/4/16.
 */
public class CircleImageView extends ImageView {
    private Paint mPaint;
    private Context mContext;
    private int mWidth;
    private String imgUrl;
    private boolean attachedToWindow=false;
    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        mContext=context;
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(context.getResources().getColor(R.color.colorPrimary));
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width= Math.min(getMeasuredWidth(),getMeasuredHeight());
        mWidth=width;
        setMeasuredDimension(width, width);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable=getDrawable();
        Bitmap bitmap;
        if (drawable!=null){
            if(drawable instanceof BitmapDrawable){
                bitmap=((BitmapDrawable) drawable).getBitmap();
            }else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicWidth(), Bitmap.Config.ARGB_8888);
                Canvas canvas1 = new Canvas(bitmap);
                drawable.setBounds(0, 0, mWidth, mWidth);
                drawable.draw(canvas1);
            }
            float cx=(mWidth/2);
            float cy=(mWidth/2);
            float radius=mWidth/2-10;
            canvas.drawCircle(cx,cy,radius,mPaint);
            canvas.drawBitmap(toRoundDrawable(bitmap, mWidth-20), 10, 10, mPaint);
        }else {
//            float width=common.applyDimension(common.COMPLEX_UNIT_DIP,100,mContext.getResources().getDisplayMetrics());

            float cx=mWidth/2;
            float cy=mWidth/2;
            float radius=mWidth/2-10;
            canvas.drawCircle(cx,cy,radius,mPaint);
        }
//            float width=common.applyDimension(common.COMPLEX_UNIT_DIP,100,mContext.getResources().getDisplayMetrics());
//            float cx=width/2;
//            float cy=width/2;
//            float radius=width/2;
//            Paint paint=new Paint();
//            paint.setAntiAlias(true);
//            paint.setColor(Color.BLUE);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setStrokeWidth(3);
//            canvas.drawCircle(cx,cy,radius,paint);

    }

    public static Bitmap toRoundDrawable(Bitmap bitmap, int circleWidth){
        int width=bitmap.getWidth();
        int height=bitmap.getHeight();
        final float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        Log.i("height", Integer.toString(circleWidth));
        if (width<=height){
            roundPx=circleWidth/2;
            left=0;
            top=0;
            right=width;
            bottom=width;
            height=width;
            dst_left=0;
            dst_top=0;
            dst_right=circleWidth;
            dst_bottom=circleWidth;
        }else{
            roundPx=circleWidth/2;
            float clip=(width-height)/2;
            left=clip;
            right=width-clip;
            top=0;
            bottom=height;
            width=height;
            dst_left=0;
            dst_top=0;
            dst_right=circleWidth;
            dst_bottom=circleWidth;
        }
        Bitmap output= Bitmap.createBitmap(circleWidth,circleWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(output);
        final int color=0xff424242;
        final Paint paint=new Paint();
        final Rect src=new Rect((int)left,(int)top,(int)right,(int)bottom);
        final Rect dst=new Rect((int)dst_left,(int)dst_top,(int)dst_right,(int)dst_bottom);
        final RectF rectF=new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(roundPx, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    public void setImgUrl(String imgUrl){
        if (attachedToWindow){
            if (!TextUtils.isEmpty(imgUrl)&& imgUrl.equals(CommonUtil.matchUrl(imgUrl))){
                Picasso.with(getContext()).load(imgUrl).into(this);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.attachedToWindow=true;
        setImgUrl(imgUrl);
    }
}
