package com.liberty.wikepro.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerPersonComponent;
import com.liberty.wikepro.contact.PersonContact;
import com.liberty.wikepro.model.AppPreferenceHelper;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.net.WikeApi;
import com.liberty.wikepro.presenter.PersonPresenter;
import com.liberty.wikepro.util.DialogBoxUtil;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.util.LinearListCreator;
import com.liberty.wikepro.util.TextInputUtil;
import com.liberty.wikepro.util.ToastHelper;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import butterknife.BindView;

import static com.liberty.wikepro.view.activity.LoginActivity.LoginPre;

/**
 * Created by LinJinFeng on 2017/2/23.
 */

public class PersonActivity extends BaseActivity implements PersonContact.View{

    @BindView(R.id.emptyRoot)
    ScrollView rootView;

    @Inject
    PersonPresenter personPresenter;

    private RelativeLayout info_header;
    private ImageView headerImg;
    private TextView name,gender,job,password,phone,email,self_describe,nickName;

    private static final int REQUEST_SYSTEM_PIC=0x12;

    private static final int REQUEST_CAMERA_PIC=0x13;

    private static final int REQUEST_CROP_PIC=0x15;

    private static final int REQUEST_PERMISSION_CAMERA=0x14;

    private File tmpFile;

    private Student student;

    private String currentImgPath;

    private String currentOutImgPath;

    @Override
    protected void initToolbar() {
        mCommonToolbar.setTitle("个人信息");
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
        personPresenter.attachView(this);
        tmpFile=new File(Environment.getExternalStorageDirectory(),getTmpFileName());
        if (!tmpFile.getParentFile().exists()) tmpFile.getParentFile().mkdirs();
    }

    private String getTmpFileName(){
        String tmpName= "/temp/"+"tmp_pic"+SystemClock.currentThreadTimeMillis()+".jpg";
        return tmpName;
    }

    private File createImgFile() throws IOException {
        File storageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imgFileName="tmp_pic_"+SystemClock.currentThreadTimeMillis();
        File img=File.createTempFile(imgFileName,".jpg",storageDir);
        currentImgPath=img.getAbsolutePath();
        Log.d("xxxxx","currentImgPath="+currentImgPath);
        return img;
    }

    private File createOutImgFile() throws IOException {
        File storageDir=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imgFileName="tmp_pic_"+SystemClock.currentThreadTimeMillis();
        File img=File.createTempFile(imgFileName,".jpg",storageDir);
        currentOutImgPath=img.getAbsolutePath();
        Log.d("xxxxx","currentOutImgPath="+currentOutImgPath);
        return img;
    }

    private void requestCameraPermission(){
        int permissionCheck= ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
//                DialogBoxUtil.showMessage();
            }else {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
            }
        }else {
            getPicFromCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_PERMISSION_CAMERA){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getPicFromCamera();
            }
        }
    }

    @Override
    protected void setActivityComponent(ApplicationComponent component) {
        DaggerPersonComponent.builder().applicationComponent(component).build().inject(this);
    }

    @Override
    protected void initViews() {
        student= WikeApplication.getInstance().getStudent();
        info_header= (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.info_header,null);
        info_header.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,100,getResources().getDisplayMetrics())));
        info_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBoxUtil.showGetPicDialog(PersonActivity.this, new LinearListCreator.OnClickCallback() {
                    @Override
                    public void onClick(int position, View v) {
                        switch (position){
                            case 0:{
                                requestCameraPermission();
                            }
                            break;
                            case 1:{
                                getPicFromSystem();
                            }
                            break;
                        }
                    }
                });
            }
        });

        final LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics()));
        new LinearListCreator.Builder(this,R.layout.info_item)
                .withBaseView(rootView)
                .addHeader(info_header)
                .forViews(8,params)
                .needLine(true)
                .inflater(new LinearListCreator.OnInflaterCallback() {
                    @Override
                    public void onInflater(int position, View v) {
                        switch (position){
                            case 0:{
                                ((TextView)v.findViewById(R.id.item)).setText("姓名");
                                name=((TextView)v.findViewById(R.id.name));
                                name.setText(student.getName());
                            }
                            break;
                            case 1:{
                                ((TextView)v.findViewById(R.id.item)).setText("昵称");
                                nickName=((TextView)v.findViewById(R.id.name));
                                nickName.setText(student.getNickname());
                            }
                            break;
                            case 2:{
                                ((TextView)v.findViewById(R.id.item)).setText("性别");
                                gender=((TextView)v.findViewById(R.id.name));
                                gender.setText(student.getGender()==1?"男":"女");
                            }
                            break;
                            case 3:{
                                ((TextView)v.findViewById(R.id.item)).setText("密码");
                                password=((TextView)v.findViewById(R.id.name));
                                password.setText("");
                            }
                            break;
                            case 4:{
                                ((TextView)v.findViewById(R.id.item)).setText("电话");
                                phone=((TextView)v.findViewById(R.id.name));
                                phone.setText(student.getPhone());
                            }
                            break;
                            case 5:{
                                ((TextView)v.findViewById(R.id.item)).setText("邮箱");
                                email=((TextView)v.findViewById(R.id.name));
                                email.setText(student.getEmail());
                            }
                            break;
                            case 6:{
                                ((TextView)v.findViewById(R.id.item)).setText("自我介绍");
                                self_describe=((TextView)v.findViewById(R.id.name));
                                self_describe.setText(student.getSelf_describe());
                            }
                            break;
                            case 7:{
                                ((TextView)v.findViewById(R.id.item)).setText("职业");
                                job=((TextView)v.findViewById(R.id.name));
                                job.setText(student.getJob());
                            }
                            break;
                        }
                    }
                })
                .withClickCallback(new LinearListCreator.OnClickCallback() {
                    @Override
                    public void onClick(int position, View v) {
                        switch (position){
                            case 0:{
                                Log.d("xxxxxxx","0");
                                DialogBoxUtil.showEditDialog(PersonActivity.this, "修改姓名", new DialogBoxUtil.onEditTextCallback() {
                                    @Override
                                    public void onEditTextCallback(String editText) {
                                        student.setName(editText);
                                        showDialog();
                                        personPresenter.editUserInfo(student);
                                    }
                                });
                            }
                            break;
                            case 1:{
                                Log.d("xxxxxxx","1");
                                DialogBoxUtil.showEditDialog(PersonActivity.this, "修改昵称", new DialogBoxUtil.onEditTextCallback() {
                                    @Override
                                    public void onEditTextCallback(String editText) {
                                        student.setNickname(editText);
                                        showDialog();
                                        personPresenter.editUserInfo(student);
                                    }
                                });
                            }
                            break;
                            case 2:{
                                Log.d("xxxxxxx","2");
                                ViewGroup contentView= (ViewGroup) LayoutInflater.from(PersonActivity.this).inflate(R.layout.gender_dialog,null);
                                RadioGroup radioGroup= (RadioGroup) contentView.findViewById(R.id.gender_rg);
                                RadioButton female= (RadioButton) contentView.findViewById(R.id.female);
                                RadioButton male= (RadioButton) contentView.findViewById(R.id.male);
                                if (student.getGender()==1){
                                    male.setChecked(true);
                                }else if (student.getGender()==2){
                                    female.setChecked(true);
                                }
                                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                        switch (checkedId){
                                            case R.id.female:{
                                                student.setGender(2);
                                            }
                                            break;
                                            case R.id.male:{
                                                student.setGender(1);
                                            }
                                            break;
                                        }
                                    }
                                });
                                final AlertDialog dialog=new AlertDialog.Builder(PersonActivity.this)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                personPresenter.editUserGender(student.getId(),student.getGender());
                                                showDialog();
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setView(contentView).create();
                                dialog.show();
                            }
                            break;
                            case 3:{
                                Log.d("xxxxxxx","3");
                                ViewGroup contentView= (ViewGroup) LayoutInflater.from(PersonActivity.this).inflate(R.layout.pass_dialog,null);
                                final EditText password= (EditText) contentView.findViewById(R.id.password);
                                password.setHint("请输入密码。");
                                final EditText passwordInput= (EditText) contentView.findViewById(R.id.passwordInput);
                                passwordInput.setHint("请确认密码。");
                                final AlertDialog dialog=new AlertDialog.Builder(PersonActivity.this)
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String pass=password.getText().toString();
                                                String pass1=passwordInput.getText().toString();
                                                if (TextUtils.isEmpty(pass)){
                                                    ToastHelper.showToast(PersonActivity.this,"请输入密码。");
                                                    return;
                                                }
                                                if (TextUtils.isEmpty(pass1)){
                                                    ToastHelper.showToast(PersonActivity.this,"请确认密码。");
                                                    return;
                                                }
                                                if (pass.equals(pass1)){
                                                    try {
                                                        student.setPassword(TextInputUtil.md5(pass));
                                                    } catch (NoSuchAlgorithmException e) {
                                                        e.printStackTrace();
                                                    }
                                                    personPresenter.editUserPass(student.getId(),pass);
                                                }else {
                                                    ToastHelper.showToast(PersonActivity.this,"第二次密码必须和第一次密码相同！");
                                                }
                                            }
                                        })
                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).setView(contentView).create();
                                dialog.show();
                            }
                            break;
                            case 4:{
                                Log.d("xxxxxxx","4");
                                DialogBoxUtil.showEditDialog(PersonActivity.this, "修改电话", new DialogBoxUtil.onEditTextCallback() {
                                    @Override
                                    public void onEditTextCallback(String editText) {
                                        student.setPhone(editText);
                                        showDialog();
                                        personPresenter.editUserInfo(student);
                                    }
                                });
                            }
                            break;
                            case 5:{
                                Log.d("xxxxxxx","5");
                                DialogBoxUtil.showEditDialog(PersonActivity.this, "修改邮箱", new DialogBoxUtil.onEditTextCallback() {
                                    @Override
                                    public void onEditTextCallback(String editText) {
                                        student.setEmail(editText);
                                        showDialog();
                                        personPresenter.editUserInfo(student);
                                    }
                                });
                            }
                            break;
                            case 6:{
                                Log.d("xxxxxxx","6");
                                DialogBoxUtil.showEditDialog(PersonActivity.this, "修改自我介绍", new DialogBoxUtil.onEditTextCallback() {
                                    @Override
                                    public void onEditTextCallback(String editText) {
                                        student.setSelf_describe(editText);
                                        showDialog();
                                        personPresenter.editUserInfo(student);
                                    }
                                });
                            }
                            break;
                            case 7:{
                                Log.d("xxxxxxx","7");
                                DialogBoxUtil.showEditDialog(PersonActivity.this, "修改职业", new DialogBoxUtil.onEditTextCallback() {
                                    @Override
                                    public void onEditTextCallback(String editText) {
                                        student.setJob(editText);
                                        showDialog();
                                        personPresenter.editUserInfo(student);
                                    }
                                });
                            }
                            break;
                        }
                    }
                })
                .build();
        headerImg = (ImageView) info_header.findViewById(R.id.userImg);
        if (!TextUtils.isEmpty(student.getHead_img())){
            ImageUtil.getCircleImageIntoImageView(this,
                headerImg, WikeApi.getInstance().getImageUrl(student.getHead_img()),true);
        }else {
            if (student.getGender()==1){
                headerImg.setImageResource(R.drawable.ic_male);
            }else if (student.getGender()==2){
                headerImg.setImageResource(R.drawable.ic_female);
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.empty_activity_layout;
    }

    private void getPicFromSystem(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_SYSTEM_PIC);
    }

    private void getPicFromCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager())!=null){
            File photoFile=null;
            try {
                photoFile=createImgFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile!=null){
                Uri photoURI= FileProvider.getUriForFile(this,"com.liberty.wikepro.fileprovider",photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(intent,REQUEST_CAMERA_PIC);
            }
        }
//        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
//            Uri imgUri= FileProvider.getUriForFile(this,"com.liberty.wikepro.fileprovider",tmpFile);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
//        }else {
//            intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tmpFile));
//        }
//        startActivityForResult(intent,REQUEST_CAMERA_PIC);
    }

    private void zoomPicFromCamera() {
            Intent intent=new Intent("com.android.camera.action.CROP");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            File tmp=new File(currentImgPath);
            Uri imgUri=FileProvider.getUriForFile(this,"com.liberty.wikepro.fileprovider",tmp);
            Uri outUri=null;
            try {
                File outTmp=createOutImgFile();
//                outUri=FileProvider.getUriForFile(this,"com.liberty.wikepro.fileprovider",outTmp);
                outUri=Uri.fromFile(outTmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        intent.setDataAndType(imgUri,"image/*");
            intent.putExtra("crop","true");
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            intent.putExtra("outputX",300);
            intent.putExtra("outputY",300);
            intent.putExtra("return-data",false);
            intent.putExtra("noFaceDetection",true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,outUri);
            intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
            startActivityForResult(intent,REQUEST_CROP_PIC);
//        Uri imgUri=FileProvider.getUriForFile(this,"com.liberty.wikepro.fileprovider",tmpFile);
//        File tmp=new File(Environment.getExternalStorageDirectory(),"/temp/"+System.currentTimeMillis()+".jpg");
//        Uri outUri=FileProvider.getUriForFile(this,"com.liberty.wikepro.fileprovider",tmp);
//        Intent intent=new Intent("com.android.camera.action.CROP");
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setDataAndType(imgUri,"image/*");
//        intent.putExtra("crop","true");
//        intent.putExtra("aspectX",1);
//        intent.putExtra("aspectY",1);
//        intent.putExtra("scale",true);
//        intent.putExtra("outputX",300);
//        intent.putExtra("outputY",300);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT,outUri);
//        intent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("return-data",true);
//        intent.putExtra("noFaceDetection",true);
//        startActivityForResult(intent,REQUEST_CROP_PIC);
    }

    private void zoomPicFromSystem(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("scale",true);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        intent.putExtra("return-data",false);
        intent.putExtra("noFaceDetection",true);
        Uri outUri=null;
        try {
            File outTmp=createOutImgFile();
//            outUri=FileProvider.getUriForFile(this,"com.liberty.wikepro.fileprovider",outTmp);
            outUri=Uri.fromFile(outTmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outUri);
        startActivityForResult(intent,REQUEST_CROP_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_SYSTEM_PIC:{
                    if (data!=null){
                        zoomPicFromSystem(data.getData());
                    }
                }
                break;
                case REQUEST_CAMERA_PIC:{
                    zoomPicFromCamera();
                }
                break;
                case REQUEST_CROP_PIC:{
                    showDialog();
                    setImageView();
                }
                break;
            }
        }
    }

    private void setImageView(){
        File file=new File(currentOutImgPath);
        if (file.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(currentOutImgPath);
            headerImg.setImageBitmap(ImageUtil.getCircleBitmap(this,bitmap));
            personPresenter.editUserHead(student,file);
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        hideDialog();
    }

    @Override
    public void editUserInfoSuccess() {
        ToastHelper.showToast(this,"修改成功！");
//        Student student= MainActivity.getStudent();
        AppPreferenceHelper.getInstance(this,LoginPre)
                .putBoolean("isLogin",true)
                .putInt("id",student.getId())
                .putString("name",student.getName())
                .putString("nickName",student.getNickname())
                .putString("phone",student.getPhone())
                .putString("email",student.getEmail())
                .putInt("gender",student.getGender())
                .putString("self_describe",student.getSelf_describe())
                .putString("head_img",student.getHead_img())
                .putString("page_img",student.getPage_img())
                .putString("job",student.getJob());
        name.setText(student.getName());
        nickName.setText(student.getNickname());
        gender.setText(student.getGender()==1?"男":"女");
        phone.setText(student.getPhone());
        email.setText(student.getEmail());
        self_describe.setText(student.getSelf_describe());
        job.setText(student.getJob());
    }

    @Override
    public void editUserInfoFail() {
        ToastHelper.showToast(this,"修改失败！");
    }

    @Override
    public void editUserPassSuccess() {
        ToastHelper.showToast(this,"修改成功！");
        AppPreferenceHelper.getInstance(this,LoginPre)
                .putString("password",student.getPassword());
    }

    @Override
    public void editUserPassFail() {
        ToastHelper.showToast(this,"修改失败！");
    }

    @Override
    public void editUserGenderSuccess() {
        ToastHelper.showToast(this,"修改成功！");
        AppPreferenceHelper.getInstance(this,LoginPre)
                .putInt("gender",student.getGender());
        gender.setText(student.getGender()==1?"男":"女");
    }

    @Override
    public void editUserGenderFail() {
        ToastHelper.showToast(this,"修改失败！");
        student.setGender(student.getGender()==1?2:1);
    }

    @Override
    public void editUserHeadSuccess() {
        AppPreferenceHelper.getInstance(this,LoginPre).putString("head_img",student.getHead_img());
        ToastHelper.showToast(this,"上传成功！");
    }

    @Override
    public void editUserHeadFail() {
        ToastHelper.showToast(this,"上传失败！");
    }

}
