package com.liberty.wikepro.view.activity;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.liberty.videolibrary.NetworkVideoView;
import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.base.BaseFragment;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerCourseComponent;
import com.liberty.wikepro.contact.CourseVideoContact;
import com.liberty.wikepro.model.bean.CTest;
import com.liberty.wikepro.model.bean.CVideo;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Score;
import com.liberty.wikepro.model.bean.User;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.presenter.CourseVideoPresenter;
import com.liberty.wikepro.view.fragment.ChapterListFragment;
import com.liberty.wikepro.view.fragment.CourseDetailFragment;
import com.liberty.wikepro.view.fragment.CourseVideoFragmentCallback;
import com.liberty.wikepro.view.widget.adapter.RightPanelAdapter;
import com.liberty.wikepro.view.widget.adapter.ViewPagerAdapter;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class CourseVideoActivity extends BaseActivity
        implements CourseVideoContact.View,CourseVideoFragmentCallback{

    @BindView(R.id.videoView)
    NetworkVideoView videoView;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.appBar)
    AppBarLayout appBarLayout;
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.playBtn)
    LinearLayout playBtn;
    @BindView(R.id.score_card_viewStub)
    ViewStub scoreViewStub;

    View scoreCardView;

    RecyclerView rightView;

    ChapterListFragment chapterListFragment;
    CourseDetailFragment detailFragment;
    RightPanelAdapter pannelAdapter;

    DialogHandler handler=new DialogHandler(this);

    /**
     * 判断进度监听线程是否运行
     */
    private volatile boolean isRunning;

    private volatile List<CTest> cTests;

    private Course course;

    private final int PLAY=0x11;
    private final int PAUSE=0x12;
    private final int EXPAND=0x13;
    private final int SHRINK=0x14;

    private final static int CType_1=1;
    private final static int CType_2=2;
    private final static int CType_3=3;


    private DialogThread thread=new DialogThread();

    @Inject
    CourseVideoPresenter courseVideoPresenter;

    @Override
    protected void initToolbar() {
//        mCommonToolbar.setTitle("hahaha");
        mCommonToolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCommonToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        course=getIntent().getParcelableExtra("course");
        videoView.setOnCompleteListener(new NetworkVideoView.onCompleteListener() {
            @Override
            public void onComplete() {
                chapterListFragment.nextVideo();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.setPauseScreenPosition();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d("xxxxxxx","-----onAttachedToWindow-----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("xxxxxxx","-----onResume-----");
    }

    @Override
    protected void initViews() {
        courseVideoPresenter.attachView(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset==0){
                    playBtn.setVisibility(View.GONE);
                }else if (Math.abs(verticalOffset)>=appBarLayout.getTotalScrollRange()){
                    playBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        List<BaseFragment> fragments=new ArrayList<>();
        chapterListFragment=new ChapterListFragment();
        detailFragment=new CourseDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("course",course);
        detailFragment.setArguments(bundle);
        fragments.add(chapterListFragment);
        fragments.add(detailFragment);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments,new String[]{"列表","详情"}));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        videoView.setOnFullScreenListener(new NetworkVideoView.onFullScreenListener() {
            @Override
            public void onFullScreen(boolean isFullScreen, View videoView) {
                if (isFullScreen){
                    tabLayout.setVisibility(View.GONE);
                    viewPager.setVisibility(View.GONE);
                    collapsingToolbarLayout.getLayoutParams().height= ViewGroup.LayoutParams.MATCH_PARENT;
                }else {
                    viewPager.setVisibility(View.VISIBLE);
                    tabLayout.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.getLayoutParams().height=
                            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250,getResources().getDisplayMetrics());
                }
            }
        });
        videoView.setOnPlayPauseListener(new NetworkVideoView.onPlayPauseListener() {
            @Override
            public void onPlay() {
                if (currentState!=EXPAND){
                    getSupportActionBar().hide();
                }
                configCollapsingLayout(PLAY);
            }

            @Override
            public void onPause() {
                if (currentState!=EXPAND){
                    getSupportActionBar().show();
                }
                ((TextView)playBtn.findViewById(R.id.clickTxt)).setText("继续播放");
                configCollapsingLayout(PAUSE);
            }
        });
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appBarLayout.setExpanded(true,true);
                videoView.play();
            }
        });
        courseVideoPresenter.getTeacher(course.getUid(),course.getId());
        if (WikeApplication.getInstance().getStudent()==null){
            WikeApplication.getInstance().initStudent();
        }
        courseVideoPresenter.getChapterList(course.getId(), WikeApplication.getInstance().getStudent().getId());
        courseVideoPresenter.getDependentCourses(course);
        rightView=new RecyclerView(this);
        rightView.setLayoutManager(new LinearLayoutManager(this));
//        videoView.setRightPanelView(rightView);
        pannelAdapter=new RightPanelAdapter(this);
        rightView.setAdapter(pannelAdapter);
//        scoreCardView=scoreViewStub.inflate();
//        scoreCardView.getLayoutParams().height=0;
//        scoreCardView.setLayoutParams(scoreCardView.getLayoutParams());
//        scoreViewStub.setVisibility(View.VISIBLE);
//        ValueAnimator animator=ValueAnimator.ofInt(0,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,40,getResources().getDisplayMetrics())).setDuration(1000);
//        animator.setTarget(scoreCardView);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                ViewGroup.LayoutParams layoutParams = scoreCardView.getLayoutParams();
//                layoutParams.height= (int) animation.getAnimatedValue();
//                scoreCardView.setLayoutParams(layoutParams);
//            }
//        });
//        animator.start();
    }

    private int currentState;

    private void configCollapsingLayout(int state){
        currentState=state;
        switch (state){
            case PLAY:
            case EXPAND:{
                AppBarLayout.LayoutParams layoutParams =
                        (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
                layoutParams.setScrollFlags(0);
            }
            break;
            case PAUSE:
            case SHRINK:{
                AppBarLayout.LayoutParams layoutParams =
                        (AppBarLayout.LayoutParams) collapsingToolbarLayout.getLayoutParams();
                layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL| AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            getSupportActionBar().hide();
            configCollapsingLayout(EXPAND);
        }else if (newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().show();
            configCollapsingLayout(SHRINK);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            if (videoView.getIsFullScreen()){
                videoView.expandShrink(false);
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_course_video;
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        hideDialog();
    }

    @Override
    public void showChapterList(List<itemType> chapters) {
        chapterListFragment.fillChapters(chapters);
        pannelAdapter.addAll(chapters);
        courseVideoPresenter.hasScore(WikeApplication.getInstance().getStudent().getId(),course.getId());
    }

    @Override
    public void showTeacher(User user) {
        detailFragment.showTeacher(user);
    }

    @Override
    public void showDependentCourses(List<Course> courses) {
        detailFragment.showDependentCourses(courses);
    }

    @Override
    public void getVideoTestList(List<CTest> cTests) {
        this.cTests=cTests;
        if (!thread.isAlive()){
            isRunning=true;
            thread.start();
        }
    }

    @Override
    public void applyAnswerRight() {
        View view= LayoutInflater.from(this).inflate(R.layout.answer_success,null);
        showCenterDialog(this,view);
        videoView.play();
//        isShowingDialog=false;
    }

    @Override
    public void applyAnswerWrong() {
        View view= LayoutInflater.from(this).inflate(R.layout.answer_success,null);
        AppCompatImageView img= (AppCompatImageView) view.findViewById(R.id.img);
        img.setImageResource(R.drawable.ic_sad);
        TextView txt= (TextView) view.findViewById(R.id.txt);
        txt.setText("回答错误，请继续加油！");
        showCenterDialog(this,view);
        videoView.play();
    }

    @Override
    public void hasScore(final Score score) {
        scoreCardView=scoreViewStub.inflate();
        scoreCardView.getLayoutParams().height=0;
        scoreCardView.setLayoutParams(scoreCardView.getLayoutParams());
        ValueAnimator animator=ValueAnimator.ofInt(0,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,40,getResources().getDisplayMetrics())).setDuration(1000);
        animator.setTarget(scoreCardView);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ViewGroup.LayoutParams layoutParams = scoreCardView.getLayoutParams();
                layoutParams.height= (int) animation.getAnimatedValue();
                scoreCardView.setLayoutParams(layoutParams);
            }
        });
        animator.start();
        scoreCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CourseVideoActivity.this,ScoreActivity.class);
                intent.putExtra("score",score);
                intent.putExtra("course",course);
                startActivity(intent);
            }
        });
    }

    public void showCenterDialog(Context context, View view){
        Dialog dialog=new Dialog(context);
        dialog.setContentView(view);
        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        if (attributes!=null){
            attributes.gravity= Gravity.CENTER;
            int size= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,150,getResources().getDisplayMetrics());
            attributes.width=size;
            attributes.height=size;
            dialog.getWindow().setAttributes(attributes);
        }
//        AlertDialog dialog=new AlertDialog.Builder(context).setView(view).create();
        Message msg=handler.obtainMessage(1,dialog);
        handler.sendMessageDelayed(msg,1000);
        dialog.show();

    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        super.setActivityComponent(component);
        DaggerCourseComponent.builder()
                .applicationComponent(component)
                .build()
                .inject(this);
    }

    @Override
    public void setCVideo(CVideo cVideo) {
        try {
            videoView.setPlayUrl(cVideo.getVdev());
            videoView.play();
            videoView.setTitle(cVideo.getVname());
            courseVideoPresenter.getVideoTest(cVideo);
            courseVideoPresenter.stuChapterCVideo(WikeApplication.getInstance().getStudent().getId(),
                    cVideo.getCid(),cVideo.getChid(),cVideo.getId());
            courseVideoPresenter.addHistory(course,cVideo.getChapter(),
                    cVideo,WikeApplication.getInstance().getStudent().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
//        videoView.destroy();
        isRunning=false;
        super.onDestroy();

    }

    /**
     * 判断是否处于对话框显示阶段
     */
    private volatile boolean isShowingDialog;

    private class DialogThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                while (isRunning){
                    if (!isShowingDialog){
                        int currentPosition = videoView.getCurrentPosition()/1000;
                        for (CTest test:cTests){
                            int cvtimepoint = test.getCvtimepoint();
                            if (cvtimepoint==(currentPosition)){
                                Message message = handler.obtainMessage();
                                message.obj=test;
                                handler.sendMessage(message);
                                isShowingDialog=true;
                            }
                        }
                    }
                    Thread.sleep(300);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private static class DialogHandler extends Handler{
        private WeakReference<CourseVideoActivity> weakReference;

        DialogHandler(CourseVideoActivity context){
            weakReference=new WeakReference<CourseVideoActivity>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:{
                    CTest cTest= (CTest) msg.obj;
                    weakReference.get().showCTestDialog(cTest);
                    weakReference.get().videoView.pause();
                }
                break;
                case 1:{
                    Dialog dialog= (Dialog) msg.obj;
                    weakReference.get().isShowingDialog=false;
                    dialog.dismiss();
                }
                break;
            }

        }
    }

    private int chance=2;

    private void CType1(final CTest test){
        LinearLayout container=new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        String[] options=test.getCvoptions().split("///");
        final RadioGroup rg=new RadioGroup(this);
        rg.setOrientation(LinearLayout.VERTICAL);
        char c='A';
        ViewGroup.LayoutParams params1=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i=0;i<options.length;i++){
            RadioButton radioButton=new RadioButton(this);
            radioButton.setText(c+" "+options[i]);
            radioButton.setId(c);
            c++;
            rg.addView(radioButton,i,params1);
        }
        container.addView(rg,params1);
        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setView(container)
                .setPositiveButton("确定",null)
                .setTitle(test.getCvcontent())
                .setCancelable(false)
                .create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkId=rg.getCheckedRadioButtonId();
                char a= (char) checkId;
                String answer=new String(new char[]{a});
                if (answer.equals(test.getCvanswer())){
                    alertDialog.dismiss();
                    CourseVideoActivity.this.showDialog();
                    courseVideoPresenter.applyAnswer(
                            WikeApplication.getInstance().getStudent().getId(),
                            test.getId(),answer,0);
                }else {
                    if (chance>0){
                        alertDialog.setTitle(test.getCvcontent()+"\n"+"您还有"+chance--+"次机会。");
                    }else {
                        alertDialog.dismiss();
                        CourseVideoActivity.this.showDialog();
                        courseVideoPresenter.applyAnswer(WikeApplication.getInstance().getStudent().getId(),
                                test.getId(),answer,1);
                        chance=2;
                    }
                }
            }
        });
    }

    private void CType2(final CTest test){
        LinearLayout container=new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        String[] options=test.getCvoptions().split("///");
        char c='A';
        ViewGroup.LayoutParams params1=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final List<CheckBox> checkBoxes=new ArrayList<>();
        for (int i=0;i<options.length;i++){
            CheckBox checkBox=new CheckBox(this);
            checkBox.setText(c+" "+options[i]);
            checkBox.setId(c);
            c++;
            checkBoxes.add(checkBox);
            container.addView(checkBox,i,params1);
        }
        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setView(container)
                .setPositiveButton("确定",null)
                .setTitle(test.getCvcontent())
                .setCancelable(false)
                .create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb=new StringBuilder();
//                if (checkBoxes.get(0).isChecked()){
//                    char a= (char) checkBoxes.get(0).getId();
//                    sb.append(a);
//                }
                for (int i=0;i<checkBoxes.size();i++){
                    CheckBox checkBox=checkBoxes.get(i);
                    if (checkBox.isChecked()){
                        char a= (char) checkBox.getId();
                        sb.append(a);
//                        sb.append(i<checkBoxes.size()?"-":"");
                    }
                }
                String answer=sb.toString();
                answer=answer.replace("","-");
                answer=answer.substring(1,answer.length()-1);
                if (answer.equals(test.getCvanswer())){
                    alertDialog.dismiss();
                    CourseVideoActivity.this.showDialog();
                    courseVideoPresenter.applyAnswer(
                            WikeApplication.getInstance().getStudent().getId(),
                            test.getId(),answer,0);
                }else {
                    if (chance>0){
                        alertDialog.setTitle(test.getCvcontent()+"\n"+"您还有"+chance--+"次机会。");
                    }else {
                        alertDialog.dismiss();
                        CourseVideoActivity.this.showDialog();
                        courseVideoPresenter.applyAnswer(WikeApplication.getInstance().getStudent().getId(),
                                test.getId(),answer,1);
                        chance=2;
                    }
                }
            }
        });
    }

    private void CType3(final CTest test){
        LinearLayout container=new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        final EditText text=new EditText(this);

        ViewGroup.LayoutParams params1=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        container.addView(text,params1);
        final AlertDialog alertDialog=new AlertDialog.Builder(this)
                .setView(container)
                .setPositiveButton("确定",null)
                .setTitle(test.getCvcontent())
                .setCancelable(false)
                .create();
        alertDialog.show();
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer=text.getText().toString();
                alertDialog.dismiss();
                CourseVideoActivity.this.showDialog();
                courseVideoPresenter.applyAnswer(WikeApplication.getInstance().getStudent().getId(),
                            test.getId(),answer,0);
            }
        });
    }

    private void showCTestDialog(final CTest test){

        switch (test.getCvtype()){
            case CType_1:{
                CType1(test);
            }
            break;
            case CType_2:{
                CType2(test);
            }
            break;
            case CType_3:{
                CType3(test);
            }
            break;
        }
    }
}
