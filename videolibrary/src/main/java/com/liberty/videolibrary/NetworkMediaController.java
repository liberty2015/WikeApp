package com.liberty.videolibrary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import static android.R.attr.x;

/**
 * Created by LinJinFeng on 2017/2/8.
 */

public class NetworkMediaController extends FrameLayout implements
        View.OnClickListener,
        SeekBar.OnSeekBarChangeListener{

    private View rootView,loading,soundController,lightController,titleBar,mediaContainer;
    private ImageView playPause,expandShrink,back;
    private TextView title,currentProgress,totalDuration,chapter;
    private AppCompatSeekBar seekBar;
    private RelativeLayout rightPanel;

    private int rightPanelWidth;
    private int screenWidth;
    private ValueAnimator openAnim;


    private NetworkMediaControl control;

    private int currentPosition;
    private int newProgress;

    private final int MAX_PROGRESS=100;

    public final int SHOW_PROGRESS=0x11;

    public final int HIDE_CONTROLLER=0x14;

    public final int SHOW_PLAY_PAUSE=0x12;

    public final int SHOW_EXPAND_SHRINK=0x13;

    private boolean isFullScreen;

    private boolean isShowing=true;

    private AudioManager audioManager;

    private GestureDetectorCompat gestureDetector;

    private int volume=-1;

    private float brightness=-1.0f;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_PROGRESS:{
                    int pos=setProgress();
                    msg=obtainMessage(SHOW_PROGRESS);
                    sendMessageDelayed(msg,MAX_PROGRESS-(pos%MAX_PROGRESS));
                }
                break;
                case HIDE_CONTROLLER:{
                    hideController();
                    isShowing=false;
                }
            }
        }
    };

    public boolean getIsFullScreen(){
        return isFullScreen;
    }

    public void setIsFullScreen(boolean isFullScreen){
        this.isFullScreen=isFullScreen;
    }

    public NetworkMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void initView(){
        rootView=LayoutInflater.from(getContext()).inflate(R.layout.media_controller_layout,this);
        playPause= (ImageView) rootView.findViewById(R.id.play_pause);
        expandShrink= (ImageView) rootView.findViewById(R.id.expand_shrink);
        title= (TextView) rootView.findViewById(R.id.title);
        currentProgress= (TextView) rootView.findViewById(R.id.currentProgress);
        totalDuration= (TextView) rootView.findViewById(R.id.totalDuration);
        back= (ImageView) rootView.findViewById(R.id.back);
        seekBar= (AppCompatSeekBar) rootView.findViewById(R.id.progress);
//        seekBar.setEnabled(false);
        seekBar.setOnSeekBarChangeListener(this);
        seekBar.setMax(MAX_PROGRESS);
        loading=rootView.findViewById(R.id.loadingController);
        soundController=rootView.findViewById(R.id.soundController);
        lightController=rootView.findViewById(R.id.lightController);
        titleBar=rootView.findViewById(R.id.video_titleBar);
        mediaContainer=rootView.findViewById(R.id.media_controller);
        chapter= (TextView) rootView.findViewById(R.id.chapter);
        chapter.setOnClickListener(this);
        playPause.setOnClickListener(this);
        expandShrink.setOnClickListener(this);
        back.setOnClickListener(this);
        rightPanel= (RelativeLayout) findViewById(R.id.right_panel);
        rightPanelWidth= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,150,getResources().getDisplayMetrics());
        screenWidth=getResources().getDisplayMetrics().heightPixels;
        openAnim=ValueAnimator.ofInt(-rightPanelWidth,0)
                .setDuration(400);
        openAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rightPanel.getLayoutParams();
                layoutParams.rightMargin= (int) animation.getAnimatedValue();
                rightPanel.setLayoutParams(layoutParams);
            }
        });
        openAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                rightPanel.setVisibility(VISIBLE);
            }
        });
        openAnim.setTarget(rightPanel);
        chapter.setVisibility(GONE);
    }

    private void init(){
        initView();
        gestureDetector=new GestureDetectorCompat(getContext(),gestureListener);
        audioManager= (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public boolean getIsShowing(){
        return isShowing;
    }

    public void setIsShowing(boolean isShowing){
        this.isShowing=isShowing;
    }

    public void setNetworkMediaControl(NetworkMediaControl control){
        this.control=control;
    }

    public void setTitle(String titleTxt){
        if (title!=null){
            title.setText(titleTxt);
        }
    }

    public int setProgress(){
        if (control==null){
            return 0;
        }
        int position=control.getCurrentPosition();
        int duration=control.getTotalDuration();
        String timeStr=calculateProgress(position);
        String totalTimeStr=calculateProgress(duration);
        currentProgress.setText(timeStr);
        totalDuration.setText(totalTimeStr);
        if (seekBar!=null){
            if (duration>0){
                long pos=MAX_PROGRESS*position/duration;
                seekBar.setProgress((int) pos);
            }
            int bufferingPosition=control.getBufferingPosition();
            seekBar.setSecondaryProgress(bufferingPosition*MAX_PROGRESS/100);
        }
        return position;
    }


    public void showLoading(){
        loading.setVisibility(VISIBLE);
    }

    public void hideLoading(){
        loading.setVisibility(GONE);
    }

    public void showSoundController(int percent){
        Log.d("xxxxxx","showSoundController");
        soundController.setVisibility(VISIBLE);
        if (percent<=0){
            ((TextView)findViewById(R.id.soundNum)).setText(0+"%");
            ((ImageView)findViewById(R.id.sound_icon)).setImageResource(R.mipmap.sound_off);
        }else {
            ((TextView)findViewById(R.id.soundNum)).setText(percent+"%");
            ((ImageView)findViewById(R.id.sound_icon)).setImageResource(R.mipmap.sound_on);
        }
    }

    public void hideSoundController(){
        soundController.setVisibility(GONE);
    }

    public void showLightController(int percent){
        lightController.setVisibility(VISIBLE);
        if (percent<=0){
            ((TextView)findViewById(R.id.lightNum)).setText(0+"%");
            ((ImageView)findViewById(R.id.light_icon)).setImageResource(R.mipmap.light_off);
        }else {
            ((TextView)findViewById(R.id.lightNum)).setText(percent+"%");
            ((ImageView)findViewById(R.id.light_icon)).setImageResource(R.mipmap.light_on);
        }
    }

    public void hideLightController(){
        lightController.setVisibility(GONE);
    }

    /**
     * 计算时间进度
     * @param position
     * @return
     */
    private String calculateProgress(int position){
        float dprogress=position/1000;
        int minute= (int) (dprogress/60);
        int second= (int) (dprogress%60);
        String progressStr=Integer.toString(minute);
        if (second<10){
            progressStr=progressStr+":0"+second;
        }else {
            progressStr=progressStr+":"+second;
        }
//        Log.d("xxxxx","progressStr="+progressStr);
        return progressStr;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if (id==R.id.play_pause){
            if (control!=null){
                if (control.isPlaying()){
                    currentPosition=control.getCurrentPosition();
                    control.pause();
                }else {
                    if(currentPosition!=0){
                        control.seekTo(currentPosition);
                        currentPosition=0;
                    }
                    control.play();
                }
                showController();
            }
        }else if (id==R.id.expand_shrink){
            if (isFullScreen){
                control.expandShrink(false);
                isFullScreen=false;
            }else {
                control.expandShrink(true);
                isFullScreen=true;
            }
        }else if (id==R.id.back){
            control.expandShrink(false);
            isFullScreen=false;
        }else if (id==R.id.chapter){
//            control.rightBtn();
            if (rightPanelToggle){
                closeRightPanel();
                rightPanelToggle=false;
            }else {
                openRightPanel();
                rightPanelToggle=true;
            }

        }
    }

    private boolean rightPanelToggle;

    private void openRightPanel(){
        if (openAnim!=null){
            openAnim.start();
        }
    }

    private void closeRightPanel(){
        if (openAnim!=null){
            openAnim.reverse();
        }
    }

    public void seekTo(int position){
        seekBar.setProgress(position*MAX_PROGRESS/control.getTotalDuration());
    }



    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//        Log.d("xxxxxxx","-----onProgressChanged-----");
        newProgress=progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
//        Log.d("xxxxxxx","-----onStartTrackingTouch-----");

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
//        Log.d("xxxxxxx","-----onStopTrackingTouch-----");
        int newPosition=newProgress*control.getTotalDuration()/MAX_PROGRESS;
        control.seekTo(newPosition);
        currentPosition=newPosition;
    }

    public interface NetworkMediaControl{
        void seekTo(int position);
        void play();
        boolean canPause();
        void pause();
        boolean isPlaying();
        void expandShrink(boolean fullScreen);
        void expandShrink(boolean fullScreen,int screenOrientation);
        int getBufferingPosition();
        int getCurrentPosition();
        int getTotalDuration();
//        void rightPanel(View rightPanel);
    }

    public void showProgress(){
        mHandler.sendEmptyMessage(SHOW_PROGRESS);
    }

    public void setRightPanel(View rightPanel) {
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        this.rightPanel.addView(rightPanel,params);
    }

    public void hideController(){
        if (isShowing&&!rightPanelToggle){
            titleBar.setVisibility(GONE);
            mediaContainer.setVisibility(GONE);
            rightPanel.setVisibility(GONE);
        }

    }

    public void showCenterView(@IdRes int resId){
        if (resId==R.id.loadingController){
            loading.setVisibility(VISIBLE);
        }else if (resId==R.id.soundController){
            soundController.setVisibility(VISIBLE);
        }else if (resId==R.id.lightController){
            lightController.setVisibility(VISIBLE);
        }
    }

    public void showController(){
        playPause.setImageResource(control.isPlaying()?R.mipmap.pause:R.mipmap.play);
        expandShrink.setImageResource(isFullScreen?R.mipmap.shrink:R.mipmap.expand);
        titleBar.setVisibility(isFullScreen?VISIBLE:GONE);
        mediaContainer.setVisibility(VISIBLE);
        rightPanel.setVisibility(VISIBLE);
        Message msg=new Message();
        msg.what=HIDE_CONTROLLER;
        mHandler.sendMessageDelayed(msg,3*1000);
    }

    private GestureDetector.SimpleOnGestureListener gestureListener=
            new GestureDetector.SimpleOnGestureListener(){

                @Override
                public boolean onDown(MotionEvent e) {
                    /**
                     * 必须返回为true，否则后续的回调函数无法被调用
                     */
                    if (getIsShowing()){
                        hideController();
                        setIsShowing(false);
                    }else {
                        showController();
                        setIsShowing(true);
                    }
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    Log.d("xxxxx","-----onSingleTapUp-----");

                    return true;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    float oldY=e1.getY();
                    float oldX=e1.getX();
                    float deltaY=oldY-e2.getY();
                    float percent= (deltaY/NetworkMediaController.this.getHeight());
                    boolean soundControl=oldX<(0.5f*NetworkMediaController.this.getHeight());
                    Log.d("","-----onScroll-----percent="+percent);
                    if (soundControl){
                        slideSound(percent);
                        Log.d("xxxxxx","-----onScroll-----soundControl="+soundControl);
                    }else {
                        slideLight(percent);
                        Log.d("xxxxxx","-----onScroll-----soundControl="+soundControl);
                    }
                    return true;
                }

            };

    private void slideSound(float percent){
        int maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        if (volume==-1){
            volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (volume<0){
                volume=3;
            }
        }
        int realVolume= (int) (volume+maxVolume*percent);
        if (realVolume>maxVolume){
            realVolume=maxVolume;
        }else if (realVolume<0){
            realVolume=0;
        }
        int realPercent= (int) (((float)realVolume/maxVolume)*100);
        Log.d("xxxxxx","currentVolume="+volume+"  maxVolume="+maxVolume);
        Log.d("xxxxxx","realPercent="+realPercent);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,realVolume,0);
        hideLightController();
        showSoundController(realPercent);

    }

    private void slideLight(float percent){
        Window window = ((Activity) getContext()).getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        if (brightness==-1.0f){
            brightness=attributes.buttonBrightness;
            if (brightness<=0.0f){
                brightness=0.5f;
            }else if (brightness<=0.01f){
                brightness=0.01f;
            }
        }
        float realBrightness=brightness+percent;
        if (realBrightness<=0.0f){
            realBrightness=0.0f;
        }else if (realBrightness>=1.0f){
            realBrightness=1.0f;
        }
        Log.d("xxxxxx","realBrightness="+realBrightness);
        attributes.screenBrightness=realBrightness;
        window.setAttributes(attributes);
        hideSoundController();
        int realPercent= (int) (realBrightness*100);
        Log.d("xxxxxx","Brightness percent="+realPercent);
        showLightController(realPercent);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (!rightPanelToggle||(x<(screenWidth-rightPanelWidth))) return true;
//        else
        if (rightPanelToggle&&(x>(screenWidth-rightPanelWidth))) return false;
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();
//        if (!rightPanelToggle)
        if (!rightPanelToggle||(x<(screenWidth-rightPanelWidth))){
            if (gestureDetector.onTouchEvent(event)){
                Log.d("xxxxx","onTouchEvent  onScroll");
                return true;
            }
        }
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:{
                touchUp();
            }
            break;
        }
        return super.onTouchEvent(event);
    }

    public void touchUp(){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLightController();
                hideSoundController();
            }
        },1500);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

    }

    protected void onDestroy(){
//        mHandler.getLooper().quitSafely();
    }
}
