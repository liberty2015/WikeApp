package com.liberty.wikepro.view.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatImageView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.liberty.wikepro.R;
import com.liberty.wikepro.WikeApplication;
import com.liberty.wikepro.base.BaseFragment;
import com.liberty.wikepro.component.ApplicationComponent;
import com.liberty.wikepro.component.DaggerMainComponent;
import com.liberty.wikepro.contact.MineContact;
import com.liberty.wikepro.model.AppPreferenceHelper;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.presenter.MinePresenter;
import com.liberty.wikepro.util.DialogBoxUtil;
import com.liberty.wikepro.util.GlideCacheUtil;
import com.liberty.wikepro.util.ImageUtil;
import com.liberty.wikepro.util.LinearListCreator;
import com.liberty.wikepro.util.ToastHelper;
import com.liberty.wikepro.view.activity.AboutActivity;
import com.liberty.wikepro.view.activity.HistoryActivity;
import com.liberty.wikepro.view.activity.LoginActivity;
import com.liberty.wikepro.view.activity.MyCourseActivity;
import com.liberty.wikepro.view.activity.PersonActivity;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;
import static com.liberty.wikepro.view.activity.LoginActivity.LoginPre;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements MineContact.View{

//    @BindView(R.id.headerImg)
    ImageView headerImg;

    ImageView coverImg;

    @Inject
    MinePresenter presenter;

    private View mineHeader;

    @BindView(R.id.emptyRoot)
    ScrollView emptyRoot;

    private static final int REQUEST_SYSTEM_PIC=0x12;

    private static final int REQUEST_CAMERA_PIC=0x13;

    private static final int REQUEST_CROP_PIC=0x15;

    private static final int REQUEST_PERMISSION_CAMERA=0x14;

    private String currentImgPath;

    private String currentOutImgPath;

    private Student student;

    @Override
    protected int getLayoutResId() {
        return R.layout.empty_layout;
    }

    private void getPicFromCamera(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getHoldActivity().getPackageManager())!=null){
            File photoFile=null;
            try {
                photoFile=createImgFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile!=null){
                Uri photoURI= FileProvider.getUriForFile(getHoldActivity(),"com.liberty.wikepro.fileprovider",photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                startActivityForResult(intent,REQUEST_CAMERA_PIC);
            }
        }
    }

    private void getPicFromSystem(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,REQUEST_SYSTEM_PIC);
    }

    private File createImgFile() throws IOException {
        File storageDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imgFileName="tmp_pic_"+ SystemClock.currentThreadTimeMillis();
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

    private void zoomPicFromCamera() {
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        File tmp=new File(currentImgPath);
        Uri imgUri=FileProvider.getUriForFile(getHoldActivity(),"com.liberty.wikepro.fileprovider",tmp);
        Uri outUri=null;
        try {
            File outTmp=createOutImgFile();
            outUri=Uri.fromFile(outTmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.setDataAndType(imgUri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",0.6);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",180);
        intent.putExtra("return-data",false);
        intent.putExtra("noFaceDetection",true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent,REQUEST_CROP_PIC);
    }

    private void zoomPicFromSystem(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",0.6);
        intent.putExtra("scale",true);
        intent.putExtra("outputX",300);
        intent.putExtra("outputY",180);
        intent.putExtra("return-data",false);
        intent.putExtra("noFaceDetection",true);
        Uri outUri=null;
        try {
            File outTmp=createOutImgFile();
            outUri=Uri.fromFile(outTmp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,outUri);
        startActivityForResult(intent,REQUEST_CROP_PIC);
    }

    private void requestCameraPermission(){
        int permissionCheck= ContextCompat.checkSelfPermission(getHoldActivity(), Manifest.permission.CAMERA);
        if (permissionCheck!= PackageManager.PERMISSION_GRANTED){
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
//                DialogBoxUtil.showMessage();
            }else {
                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_PERMISSION_CAMERA);
            }
        }else {
            getPicFromCamera();
        }
    }

    @Override
    protected void initView() {
        mineHeader=LayoutInflater.from(getHoldActivity()).inflate(R.layout.mine_header,null);
        coverImg= (ImageView) mineHeader.findViewById(R.id.user_cover);
        coverImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBoxUtil.showGetPicDialog(getHoldActivity(), new LinearListCreator.OnClickCallback() {
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
        headerImg=((ImageView)mineHeader.findViewById(R.id.headerImg));
        mineHeader.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics())));
        LinearLayout.LayoutParams defLayoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,60,getResources().getDisplayMetrics()));
        View footer=LayoutInflater.from(getHoldActivity()).inflate(R.layout.mine_footer,null);
        footer.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("xxxxx","footer click");
                AppPreferenceHelper.getInstance(getHoldActivity(), LoginPre).clear();
                startActivity(new Intent(getHoldActivity(),LoginActivity.class));
                getHoldActivity().finish();
            }
        });
        new LinearListCreator.Builder(getHoldActivity(),R.layout.common_list_item)
                .addHeader(mineHeader)
                .forViews(3,defLayoutParams)
                .spaceView()
                .forViews(2,defLayoutParams)
                .addView(footer)
//                .needLine(true)
                .withBaseView(emptyRoot)
                .inflater(new LinearListCreator.OnInflaterCallback() {
                    @Override
                    public void onInflater(int position, View v) {
                        switch (position){
                            case 0:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_person);
                                ((TextView)v.findViewById(R.id.itemName)).setText("个人信息");
                            }
                            break;
                            case 1:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_history);
                                ((TextView)v.findViewById(R.id.itemName)).setText("历史记录");
                            }
                            break;
                            case 2:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_book);
                                ((TextView)v.findViewById(R.id.itemName)).setText("我的课程");
                            }
                            break;
                            case 4:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_about);
                                ((TextView)v.findViewById(R.id.itemName)).setText("关于");
                            }
                            break;
                            case 5:{
                                ((AppCompatImageView)v.findViewById(R.id.itemImg)).setImageResource(R.drawable.ic_clean);
                                String txt ="清除缓存("+ GlideCacheUtil.getInstance().getCacheSize(getHoldActivity())+")";
                                SpannableStringBuilder stringBuilder=new SpannableStringBuilder(txt);
                                stringBuilder.setSpan(new TextAppearanceSpan(getHoldActivity(),android.R.style.TextAppearance_DeviceDefault_Small),
                                        4,txt.length()-1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                                ((TextView)v.findViewById(R.id.itemName)).setText(stringBuilder);
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
                                startActivity(new Intent(getHoldActivity(), PersonActivity.class));
                            }
                            break;
                            case 1:{
                                startActivity(new Intent(getHoldActivity(), HistoryActivity.class));
                            }
                            break;
                            case 2:{
                                startActivity(new Intent(getHoldActivity(), MyCourseActivity.class));
                            }
                            break;
                            case 4:{
                                startActivity(new Intent(getHoldActivity(), AboutActivity.class));
                            }
                            break;
                            case 5:{
                                ToastHelper.showToast(getHoldActivity(),"成功清除"+GlideCacheUtil.getInstance().getCacheSize(getHoldActivity())
                                        +"缓存");
                                GlideCacheUtil.getInstance().cleanImageAllCache(getHoldActivity());
                                ((TextView)v.findViewById(R.id.itemName)).setText("清除缓存");
                            }
                            break;
                        }
                    }
                }).build();
//        ImageUtil.getCircleImageIntoImageView(getHoldActivity(),
//                headerImg,"http://www.popoho.com/uploads/allimg/121225/2-121225153517.jpg",true);
        mineHeader.findViewById(R.id.userName).setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mineHeader.findViewById(R.id.userJob).setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mineHeader.findViewById(R.id.userDescription).setLayerType(View.LAYER_TYPE_SOFTWARE,null);
    }

    @Override
    protected void initData() {
        presenter.attachView(this);
        student= WikeApplication.getInstance().getStudent();
        Log.d("xxxxx","gender="+student.getGender());
        if (student!=null){
            if (!TextUtils.isEmpty(student.getHead_img())){
                ImageUtil.getCircleImageIntoImageView(getHoldActivity(),
                headerImg, student.getHead_img(),true);
            }else {
                if (student.getGender()==1){
                    headerImg.setImageResource(R.drawable.ic_male);
                }else if (student.getGender()==2){
                    headerImg.setImageResource(R.drawable.ic_female);
                }
            }
            AppCompatImageView gender= (AppCompatImageView) mineHeader.findViewById(R.id.gender);
            if (student.getGender()==1){
                gender.setImageResource(R.drawable.ic_malehead);
            }else if (student.getGender()==2){
                gender.setImageResource(R.drawable.ic_femalehead);
            }
            ((TextView)mineHeader.findViewById(R.id.userName)).setText(student.getNickname());
            if (!TextUtils.isEmpty(student.getJob())){
                ((TextView)mineHeader.findViewById(R.id.userJob)).setText(student.getJob());
            }
            if (!TextUtils.isEmpty(student.getSelf_describe())){
                ((TextView)mineHeader.findViewById(R.id.userDescription)).setText(student.getSelf_describe());
            }
            if (!TextUtils.isEmpty(student.getPage_img())){
                ImageUtil.getCircleImageIntoImageView(getHoldActivity(),(ImageView) mineHeader.findViewById(R.id.user_cover),student.getPage_img(),false);
            }
        }

    }

    @Override
    public void attachView() {

    }

    private void setImageView(){
        File file=new File(currentOutImgPath);
        if (file.exists()){
            Bitmap bitmap= BitmapFactory.decodeFile(currentOutImgPath);
            coverImg.setImageBitmap(bitmap);
            presenter.editUserCover(student,file);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

    @Override
    public void showError() {

    }

    @Override
    public void complete() {
        hideDialog();
    }

    @Override
    public void editUserCoverSuccess() {
        AppPreferenceHelper.getInstance(getHoldActivity(),LoginPre).putString("page_img",student.getPage_img());
        ToastHelper.showToast(getHoldActivity(),"图片上传成功！");
    }

    @Override
    public void editUserCoverFail() {
        ToastHelper.showToast(getHoldActivity(),"图片上传失败！");
    }

    @Override
    protected void setFragmentComponent(ApplicationComponent component) {
        super.setFragmentComponent(component);
        DaggerMainComponent.builder()
//                .applicationModule(new ApplicationModule(WikeApplication.getInstance()))
                .applicationComponent(component).build().inject(this);
    }
}
