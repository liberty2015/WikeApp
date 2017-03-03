package com.liberty.wikepro.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BaseActivity;
import com.liberty.wikepro.util.DialogBoxUtil;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.util.LinearListCreator;

import java.io.File;

import butterknife.BindView;

/**
 * Created by LinJinFeng on 2017/2/23.
 */

public class PersonActivity extends BaseActivity {

    @BindView(R.id.emptyRoot)
    ScrollView rootView;
    private RelativeLayout info_header;
    private ImageView headerImg;
    private TextView name,gender,job,password,phone,email,self_describe,nickName;

    private static final int REQUEST_SYSTEM_PIC=0x12;

    private static final int REQUEST_CAMERA_PIC=0x13;

    private static final int REQUEST_CROP_PIC=0x15;

    private static final int REQUEST_PERMISSION_CAMERA=0x14;

    private File tmpFile;

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
        tmpFile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),getTmpFileName());
    }

    private String getTmpFileName(){
        String tmpName= "tmp_pic"+SystemClock.currentThreadTimeMillis()+".jpg";
        return tmpName;
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
    protected void initViews() {
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
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
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
                            }
                            break;
                            case 1:{
                                ((TextView)v.findViewById(R.id.item)).setText("昵称");
                                nickName=((TextView)v.findViewById(R.id.name));
                            }
                            break;
                            case 2:{
                                ((TextView)v.findViewById(R.id.item)).setText("性别");
                                gender=((TextView)v.findViewById(R.id.name));
                            }
                            break;
                            case 3:{
                                ((TextView)v.findViewById(R.id.item)).setText("密码");
                                password=((TextView)v.findViewById(R.id.name));
                            }
                            break;
                            case 4:{
                                ((TextView)v.findViewById(R.id.item)).setText("电话");
                                phone=((TextView)v.findViewById(R.id.name));
                            }
                            break;
                            case 5:{
                                ((TextView)v.findViewById(R.id.item)).setText("邮箱");
                                email=((TextView)v.findViewById(R.id.name));
                            }
                            break;
                            case 6:{
                                ((TextView)v.findViewById(R.id.item)).setText("自我介绍");
                                self_describe=((TextView)v.findViewById(R.id.name));
                            }
                            break;
                            case 7:{
                                ((TextView)v.findViewById(R.id.item)).setText("职业");
                                job=((TextView)v.findViewById(R.id.name));
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

                            }
                            break;
                            case 1:{

                            }
                            break;
                            case 2:{

                            }
                            break;
                            case 3:{

                            }
                            break;
                            case 4:{

                            }
                            break;
                            case 5:{

                            }
                            break;
                            case 6:{

                            }
                            break;
                            case 7:{

                            }
                            break;
                        }
                    }
                })
                .build();
        headerImg = (ImageView) info_header.findViewById(R.id.userImg);
//        Glide.with(this).load("http://www.popoho.com/uploads/allimg/121225/2-121225153517.jpg")
//                .transform(new GlideCircleTransform(this))
//                .into(headerImg);
        ImageUtil.getCircleImageIntoImageView(this,
                headerImg,"http://www.popoho.com/uploads/allimg/121225/2-121225153517.jpg",true);
        name.setText("Liberty");
        nickName.setText("2333");
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(tmpFile));
        startActivityForResult(intent,REQUEST_CAMERA_PIC);
    }

    private void zoomPic(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);

        intent.putExtra("outputX",300);
        intent.putExtra("outputY",300);
        intent.putExtra("return-data",true);
        intent.putExtra("noFaceDetection",true);
        startActivityForResult(intent,REQUEST_CROP_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_SYSTEM_PIC:{
                    if (data!=null){
                        zoomPic(data.getData());
                    }
                }
                break;
                case REQUEST_CAMERA_PIC:{
//                if (data!=null){
                    zoomPic(Uri.fromFile(tmpFile));
//                }
                }
                break;
                case REQUEST_CROP_PIC:{
                    if (data!=null){
                        Bitmap bitmap=data.getParcelableExtra("data");
                        headerImg.setImageBitmap(ImageUtil.getCircleBitmap(this,bitmap));
                    }
                }
                break;
            }
        }
    }
}
