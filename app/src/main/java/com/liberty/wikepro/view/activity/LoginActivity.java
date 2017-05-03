package com.liberty.wikepro.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.liberty.wikepro.MainActivity;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerLoginComponent;
import com.liberty.wikepro.contact.LoginContact;
import com.liberty.wikepro.model.AppPreferenceHelper;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.presenter.LoginPresenter;
import com.liberty.wikepro.util.DialogBoxUtil;
import com.liberty.wikepro.util.TextInputUtil;
import com.liberty.wikepro.util.ToastHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContact.View{

    @BindView(R.id.go_container)
    LinearLayout goContainer;
    @BindView(R.id.loginContainer)
    RelativeLayout loginContainer;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.registerContainer)
    RelativeLayout registerContainer;
    @BindView(R.id.registerInfoContainer1)
    LinearLayout registerInfoContainer1;
    @BindView(R.id.registerInfoContainer2)
    LinearLayout registerInfoContainer2;
    @BindView(R.id.userRegister)
    TextInputEditText userRegister;
    @BindView(R.id.loginNameRegister)
    TextInputEditText loginNameRegister;
    @BindView(R.id.passwordRegister)
    TextInputEditText passwordRegister;
    @BindView(R.id.nameRegister)
    TextInputEditText nameRegister;
    @BindView(R.id.user)
    TextInputEditText user;
    @BindView(R.id.password)
    TextInputEditText passwordEdit;
    @BindView(R.id.gender_rg)
    RadioGroup gender_rg;

    //1:male,2:female
    int gender=0;

    private Student student;

    ValueAnimator goAnim,loginAnim,registerAnim,registerBoxAnim;
    private boolean isReverse=false;

    private boolean isNext=false;

    private int isPhone=2;

    public final static String LoginPre="loginPre";

    @Inject
    LoginPresenter loginPresenter;

    @Override
    protected void initToolbar() {
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        boolean isLogin=AppPreferenceHelper.getInstance(this,LoginPre).getBoolean("isLogin",false);
        if (!isLogin){
            if (goAnim!=null){
                goAnim.start();
            }else {
                int finalIn= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,-70,getResources().getDisplayMetrics())+goContainer.getHeight()/2;
                goAnim=ValueAnimator.ofFloat(0,finalIn).setDuration(1000);
                goAnim.setTarget(goContainer);
                goAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedFraction = animation.getAnimatedFraction();
                        float alpha=animatedFraction*1.0f;
                        Log.d("xxxxx","animatedFraction="+animatedFraction+"   alpha="+alpha);
                        goContainer.setTranslationY((Float) animation.getAnimatedValue());
                        goContainer.setAlpha(alpha);
                    }
                });
                goAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        goContainer.setAlpha(0f);
                        goContainer.setVisibility(View.VISIBLE);
                    }
                });
                goAnim.setInterpolator(new AccelerateDecelerateInterpolator());
                goAnim.start();
            }
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    startOtherActivity(MainActivity.class);
                }
            },1000);
        }
    }

    @Override
    protected void initData() {
        loginPresenter.attachView(this);
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        DaggerLoginComponent.builder()
                .applicationComponent(component)
                .build()
                .inject(this);
    }

    @OnClick({
            R.id.loginBtn,
            R.id.registerBtn,
            R.id.go_login,
            R.id.go_register,
            R.id.back,
            R.id.backRegister
    })
    public void btnClick(View view){
        switch (view.getId()){
            case R.id.go_login:{
                if (loginAnim!=null){
                    loginAnim.start();
                }
            }
            break;
            case R.id.go_register:{
                if (registerAnim!=null){
                    registerAnim.start();
                }
            }
            break;
            case R.id.loginBtn:{
                String loginName=user.getText().toString();
                if (TextUtils.isEmpty(loginName)){
                    user.setError("不能为空！");
                    user.requestFocus();
                    return;
                }else if (TextInputUtil.isPhone(loginName)){
                    isPhone=0;
                }else if (TextInputUtil.isEmail(loginName)){
                    isPhone=1;
                }else {
                    user.setError("请输入正确格式的手机号码或邮箱账号");
                    user.requestFocus();
                    return;
                }
                String password=passwordEdit.getText().toString();
                if (TextUtils.isEmpty(password)){
                    passwordEdit.setError("密码不能为空");
                    passwordEdit.requestFocus();
                    return;
                }
                showDialog();
                switch (isPhone){
                    case 0:{
                        loginPresenter.loginByPhone(loginName,password);
                    }
                    break;
                    case 1:{
                        loginPresenter.loginByEmail(loginName,password);
                    }
                    break;
                }
            }
            break;
            case R.id.back:{
                if (loginAnim!=null){
                    isReverse=true;
                    loginAnim.reverse();
                }
            }
            break;
            case R.id.backRegister:{
                if (registerAnim!=null){
                    isReverse=true;
                    registerAnim.reverse();
                }
            }
            break;
            case R.id.registerBtn:{
                if (student==null){
                    student=new Student();
                }
                if (!isNext){
                    String loginName=loginNameRegister.getText().toString();
                    if (TextUtils.isEmpty(loginName)){
                        loginNameRegister.setError("不能为空！");
                        loginNameRegister.requestFocus();
                        loginNameRegister.setEnabled(true);
                        return;
                    }else if (TextInputUtil.isPhone(loginName)){
                        student.setPhone(loginName);
                        isPhone=0;
                    }else if (TextInputUtil.isEmail(loginName)){
                        student.setEmail(loginName);
                        isPhone=1;
                    }else {
                        loginNameRegister.setError("请输入正确格式的手机号码或邮箱账号");
                        loginNameRegister.requestFocus();
                        return;
                    }
                    Log.d("xxxxx","isPhone="+isPhone);
                    String nickName=userRegister.getText().toString();
                    if (nickName.equals("")){
                        userRegister.setError("用户名不能为空！");
                        userRegister.requestFocus();
//                        userRegister.setFocusable(true);
                        return;
                    }else {
                        student.setNickname(nickName);
                    }
                    String password=passwordRegister.getText().toString();
                    if (password.equals("")){
                        passwordRegister.setError("密码不能为空！");
                        passwordRegister.requestFocus();
                        return;
                    }else {
                        student.setPassword(password);
                    }
                    registerBoxAnim.start();
                    isNext=true;
                    ((AppCompatButton)findViewById(R.id.registerBtn)).setText("注册");
                }else {
                    String name=nameRegister.getText().toString();
                    Log.d("xxxxxx","name="+name);
                    if (TextUtils.isEmpty(name)){
                        nameRegister.setError("姓名不能为空！");
                        nameRegister.requestFocus();
                        return;
                    }else {
                        student.setName(name);
                    }
                    if (gender==0){
                        DialogBoxUtil.showMessage("请选择性别！",this);
                        return;
                    }else {
                        student.setGender(gender);
                    }
                    showDialog();
                    switch (isPhone){
                        case 0:{
                            loginPresenter.registerByPhone(student);
                        }
                        break;
                        case 1:{
                            loginPresenter.registerByEmail(student);
                        }
                        break;
                    }
                }

            }
            break;
        }
    }


    @Override
    protected void initViews() {
        loginContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                loginContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int screenWidth=getResources().getDisplayMetrics().widthPixels;
                int containerWidth=loginContainer.getWidth();
                float transitionX=screenWidth/2-containerWidth/2+containerWidth;
                Log.d("xxxxx","containerWidth="+containerWidth+"  screenWidth="+screenWidth+"  transitionX="+transitionX);
                loginAnim=ValueAnimator.ofFloat(0,transitionX)
                        .setDuration(1000);
                loginAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedFraction = animation.getAnimatedFraction();
                        float alpha=animatedFraction*1.0f;
                        float reverseAlpha=1.0f-animatedFraction*1.0f;
                        loginContainer.setAlpha(alpha);
                        logo.setAlpha(reverseAlpha);
                        logo.setScaleX(reverseAlpha);
                        logo.setScaleY(reverseAlpha);
                        loginContainer.setTranslationX((Float) animation.getAnimatedValue());
                    }
                });
                loginAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        loginContainer.setVisibility(View.VISIBLE);
                        loginContainer.setAlpha(0f);
                        if (isReverse){
                            goAnim.start();
                            isReverse=false;
                        }else {
                            goAnim.reverse();
                            isReverse=true;
                        }
                    }
                });
            }
        });
        registerContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                registerContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int screenWidth=getResources().getDisplayMetrics().widthPixels;
                int containerWidth=loginContainer.getWidth();
                float transitionX=-(screenWidth/2-containerWidth/2+containerWidth);
                registerAnim=ValueAnimator.ofFloat(0,transitionX)
                        .setDuration(1000);
                registerAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float animatedFraction = animation.getAnimatedFraction();
                        float alpha=animatedFraction*1.0f;
                        float reverseAlpha=1.0f-animatedFraction*1.0f;
                        registerContainer.setAlpha(alpha);
                        logo.setAlpha(reverseAlpha);
                        logo.setScaleX(reverseAlpha);
                        logo.setScaleY(reverseAlpha);
                        registerContainer.setTranslationX((Float) animation.getAnimatedValue());
                    }
                });
                registerAnim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        registerContainer.setVisibility(View.VISIBLE);
                        registerContainer.setAlpha(0f);
                        if (isReverse){
                            goAnim.start();
                            isReverse=false;
                        }else {
                            goAnim.reverse();
                            isReverse=true;
                        }
                    }
                });
            }
        });
        registerBoxAnim=ValueAnimator.ofFloat(1.0f,0.0f).setDuration(500);
        registerBoxAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                registerInfoContainer1.setAlpha((Float) animation.getAnimatedValue());
                float reverseAlpha=1.0f-(Float) animation.getAnimatedValue();
                registerInfoContainer2.setAlpha(reverseAlpha);
            }
        });
        registerBoxAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                if (!isNext){
                    registerInfoContainer2.setVisibility(View.VISIBLE);
                }else {
                    registerInfoContainer2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isNext){
                    registerInfoContainer1.setVisibility(View.VISIBLE);
                }else {
                    registerInfoContainer1.setVisibility(View.GONE);
                }
            }
        });

        gender_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.female:{
                        gender=2;
                    }
                    break;
                    case R.id.male:{
                        gender=1;
                    }
                    break;
                }
            }
        });
//        goAnim.start();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void loginSuccess(Student student) {
        ToastHelper.showToast(this,"登录成功！");
        AppPreferenceHelper.getInstance(this,LoginPre)
                .putBoolean("isLogin",true)
                .putInt("id",student.getId())
                .putString("name",student.getName())
                .putString("nickName",student.getNickname())
                .putString("password",student.getPassword())
                .putString("phone",student.getPhone())
                .putString("email",student.getEmail())
                .putInt("gender",student.getGender())
                .putString("self_describe",student.getSelf_describe())
                .putString("head_img",student.getHead_img())
                .putString("page_img",student.getPage_img())
                .putString("job",student.getJob());
        dismissDialog();
        finish();
        startOtherActivity(MainActivity.class);
    }

    @Override
    public void loginFail() {
        ToastHelper.showToast(this,"登录失败！密码错误或用户名不存在。");
    }

    @Override
    public void registerSuccess(Student student) {
        ToastHelper.showToast(this,"注册成功！");
        AppPreferenceHelper.getInstance(this,LoginPre)
                .putBoolean("isLogin",true)
                .putInt("id",student.getId())
                .putString("name",student.getName())
                .putString("nickName",student.getNickname())
                .putString("password",student.getPassword())
                .putString("phone",student.getPhone())
                .putString("email",student.getEmail())
                .putInt("gender",student.getGender())
                .putString("self_describe",student.getSelf_describe())
                .putString("head_img",student.getHead_img())
                .putString("page_img",student.getPage_img())
                .putString("job",student.getJob());
        dismissDialog();
        finish();
        startOtherActivity(RecommendActivity.class);
    }

    @Override
    public void registerFail(String result) {
        ToastHelper.showToast(this,result);
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        hideDialog();
    }
}
