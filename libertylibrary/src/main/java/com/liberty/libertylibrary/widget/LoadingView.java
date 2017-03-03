package com.liberty.libertylibrary.widget;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.liberty.libertylibrary.R;


/**
 * Created by LinJinFeng on 2017/2/6.
 */

public class LoadingView extends View{

    private Bitmap bitmap;
    private Paint paint;
    private Path path;
    private int width,height;
    private PorterDuffXfermode xfermode;
    private int waveLength;
    private int waveCount;
    private ColorMatrixColorFilter matrixColorFilter;
    private BitmapShader shader;
    private AnimatorSet set;
    private float offset1,offset2;
    private int resId;
    private int cx,cy;

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        resId=array.getResourceId(R.styleable.LoadingView_showingView,R.mipmap.ic_launcher);
        bitmap= BitmapFactory.decodeResource(getResources(),resId);
        array.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("xxxxx","-----onMeasure-----");
//        if (!hasMeasure){
            int widthMode=MeasureSpec.getMode(widthMeasureSpec);
            int heightMode=MeasureSpec.getMode(heightMeasureSpec);
            int width;
            int height;
            if (widthMode==MeasureSpec.AT_MOST){
                width=bitmap.getWidth();
            }else {
                width=MeasureSpec.getSize(widthMeasureSpec);
            }
            if (heightMode==MeasureSpec.AT_MOST){
                height=bitmap.getHeight();
            }else {
                height=MeasureSpec.getSize(heightMeasureSpec);
            }
            setMeasuredDimension(width,height);
//        }else {
//            super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        }

    }

    private void resizeBitmap(){
//        BitmapFactory.Options options=new BitmapFactory.Options();
//        options.inSampleSize=(bitmap.getWidth()/width);
//        options.inJustDecodeBounds=false;
//        bitmap= BitmapFactory.decodeResource(getResources(),resId,options);
        Matrix matrix=new Matrix();
        matrix.postScale(width/bitmap.getWidth(),height/bitmap.getHeight());
        bitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight() ,matrix,false);
        cx=width/2-bitmap.getWidth()/2;
        cy=height/2-bitmap.getHeight()/2;
        Matrix matrix1=new Matrix();
        matrix1.setTranslate(cx,cy);
        shader=new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        shader.setLocalMatrix(matrix1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("xxxxx","-----onSizeChanged-----");
        width=w;
        height=h;
        resizeBitmap();
        waveLength=w/2;
        Log.d("xxxxx","size="+width+","+height);
        Log.d("xxxxx","waveLength="+waveLength);
        waveCount=(int)Math.round(width/waveLength+1.5f);
        Log.d("xxxxx","waveCount="+waveCount);
        final ValueAnimator animator1=ValueAnimator.ofFloat(0,waveLength);
        animator1.setDuration(3000);
//        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setRepeatCount(ValueAnimator.INFINITE);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset1= (float) animator1.getAnimatedValue();
                Log.d("xxxxx","offset1="+offset1);
                invalidate();
            }
        });
        final ValueAnimator animator2=ValueAnimator.ofFloat(0,-height);
        animator2.setDuration(7000);
        animator2.setRepeatCount(ValueAnimator.INFINITE);
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset2= (float) animator2.getAnimatedValue();
                Log.d("xxxxx","offset2="+offset2);
                invalidate();
            }
        });
        set=new AnimatorSet();
        set.play(animator1).with(animator2);

//        set.start();
    }

    private void init(){
        Log.d("xxxxx","-----init-----");
        path=new Path();
        paint=new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setAntiAlias(true);
        matrixColorFilter=new ColorMatrixColorFilter(new float[]{
                0.33f,0.59f,0.11f,0,0,
                0.33f,0.59f,0.11f,0,0,
                0.33f,0.59f,0.11f,0,0,
                0,0,0,1,0
        });
//        shader=new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        xfermode=new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("xxxxx","-----onLayout-----");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("xxxxx","-----onDraw-----");
        Log.d("xxxxx","width="+bitmap.getWidth()+"  height="+bitmap.getHeight()+"  cx="+cx+"  cy="+cy);
        path.reset();
        path.moveTo(-waveLength+offset1,height+offset2);
        for(int i=0;i<waveCount;i++){
            path.quadTo(-waveLength*3/4+i*waveLength+offset1,height+20+offset2,-waveLength/2+i*waveLength+offset1,height+offset2);
            path.quadTo(-waveLength/4+i*waveLength+offset1,height-20+offset2,i*waveLength+offset1,height+offset2);
        }
        path.lineTo(width,height);
        path.lineTo(0,height);
        path.close();
        int i = canvas.saveLayer(0, 0, width, height, paint, Canvas.ALL_SAVE_FLAG);
        paint.setColorFilter(matrixColorFilter);
        canvas.drawBitmap(bitmap,cx,cy,paint);
        paint.setColorFilter(null);
        paint.setShader(shader);
        paint.setXfermode(xfermode);
        canvas.drawPath(path,paint);
        paint.setXfermode(null);
        paint.setShader(null);
        canvas.restoreToCount(i);
        /**
         * isRunning  返回是否是运行状态，在start(),或设置的startDelay时间运行完到没有end()之间
         * isStarted  判断动画是否开始
         */
        if (!set.isRunning()){
            set.start();
        }
    }



    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("xxxxx","-----onAttachedToWindow-----");
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("xxxxx","-----onDetachedFromWindow-----");
        /**
         *end() 结束动画，跳到结束完成的状态，属性值也会跟随变为完成时的值，必须运行在调用动画运行的线程上
         */
        if (set!=null)
            set.end();
    }
}
