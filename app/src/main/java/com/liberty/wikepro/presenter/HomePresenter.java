package com.liberty.wikepro.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.liberty.wikepro.R;
import com.liberty.wikepro.base.BasePresenter;
import com.liberty.wikepro.contact.HomeContact;
import com.liberty.wikepro.model.HomeModel;
import com.liberty.libertylibrary.widget.Banner;
import com.liberty.wikepro.model.bean.Catalog;
import com.liberty.wikepro.model.bean.Course;
import com.liberty.wikepro.model.bean.Student;
import com.liberty.wikepro.model.bean.itemType;
import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by LinJinFeng on 2017/2/21.
 */

public class HomePresenter extends BasePresenter<HomeContact.View> implements HomeContact.Presenter {

    @Inject
    HomeModel homeModel;

    @Inject
    public HomePresenter(){

    }

    @Override
    public void getBanner() {
        homeModel.getBanner(new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                try {
                    JSONArray jsonArray=new JSONArray(result);
                    Banner banner=new Banner();
                    List<Banner.photos> photos=new ArrayList<Banner.photos>();
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject object=jsonArray.getJSONObject(i);
                        Banner.photos photo=new Banner.photos();
                        photo.setName(object.isNull("title")?"":object.getString("title"));
                        photo.setContentUrl(object.isNull("url")?"":object.getString("url"));
                        photo.setPhotoUrl(object.isNull("img_dev")?"": WikeApi.getInstance().getImageUrl(object.getString("img_dev")));
                        photos.add(photo);
                    }
                    banner.setPhotos(photos);
                    mView.showBanner(banner);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void getCourseList(Student student) {
        homeModel.getCourseList(student, new OkHttpUtil.OkHttpResponseIMPL() {
            @Override
            public void onSuccess(String result) {
                mView.complete();
            }

            @Override
            public void onError(String error) {

            }

            @Override
            public void onAnalyseDataSuccess(String result) {
                mView.showCourseList(convertToItemTypes(result));

            }

            @Override
            public void onAnalyseDataError(String result) {

            }
        });
    }

    @Override
    public void detachView() {

    }

    private List<itemType> convertToItemTypes(String json){
        List<itemType> courseList=new ArrayList<itemType>();
        try {
            JSONObject object=new JSONObject(json);
            String recommendJson=object.getJSONArray("recommend").toString();
            Log.d("xxxxx","recommendJson="+recommendJson);
            Gson gson=new Gson();
            List<Course> recommendCourses=gson.fromJson(recommendJson,new TypeToken<List<Course>>(){}.getType());
            String newJson=object.getJSONArray("new").toString();
            Log.d("xxxxx","newJson="+newJson);
            List<Course> newCourses=gson.fromJson(newJson,new TypeToken<List<Course>>(){}.getType());
            Catalog catalog=new Catalog();
            catalog.setTitle("为您推荐");
            catalog.setResId(R.drawable.ic_star);
            courseList.add(catalog);
            for (Course course:recommendCourses){
                course.setSpanCount(1);
                courseList.add(course);
            }
            Catalog catalog1=new Catalog();
            catalog1.setTitle("新课推荐");
            catalog1.setResId(R.drawable.ic_up);
            courseList.add(catalog1);
            for (Course course:newCourses){
                course.setSpanCount(2);
                courseList.add(course);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return courseList;

    }

}
