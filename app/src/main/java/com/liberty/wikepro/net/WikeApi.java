package com.liberty.wikepro.net;

import android.util.Log;

import com.liberty.wikepro.BuildConfig;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class WikeApi {

    private static WikeApi instance;

    private boolean hasPort=false;

    private final String base_url= BuildConfig.serverUrl+BuildConfig.appUrl;

    private final String port=":";

    private final String login_url="";

    private static final String registerByPhone_url="/Home/User/registerByPhone";

    private static final String registerByEmail_url="/Home/User/registerByEmail";

    private static final String loginByPhone_url="/Home/User/loginByPhone";

    private static final String loginByEmail_url="/Home/User/loginByEmail";

    private static final String editUserInfo_url="/Home/User/editUserInfo";

    private static final String editPass_url="/Home/User/editUserPass";

    private static final String editUserGender_url="/Home/User/editUserGender";

    private static final String editUserHead_url="/Home/User/editUserHead";

    private static final String editUserCover_url="/Home/User/editUserCover";

    private static final String getTypeList_url="/Home/Course/getTypeList";

    private static final String updateRecommend_url="/Home/Course/updateRecommendList";

    private static final String getRecommendCourses_url="/Home/Course/getRecommendCourses";

    private static final String getCourseUser_url="/Home/Course/getCourseUser";

    private static final String firstCourse_url="/Home/Course/firstCourse";

    private static final String getCourseVideo_url="/Home/Course/getCourseVideo";

    private static final String getTeacher_url="/Home/Course/getTeacher";

    private static final String getDependentCourses_url="/Home/Course/getDependentCourses";

    private static final String getVideoTest_url="/Home/Course/getVideoTest";

    private static final String applyAnswer_url="/Home/Course/applyAnswer";

    private static final String getTypeCourse_url="/Home/Course/getTypeCourse";

    private static final String stuChapterCVideo_url="/Home/Course/stuChapterCVideo";

    private static final String getMyCourses_url="/Home/Course/myCourse";

    private static final String getBanner_url="/Home/Banner/getBanner";

    private static final String hasScore_url="/Home/Course/hasScore";

    private static final String particularScore_url="/Home/Course/particularScore";

    private static final String search_url="/Home/Search/search";

    public static WikeApi getInstance(){
        if (instance==null){
            instance=new WikeApi();
        }
        return instance;
    }

    public void setHasPort(boolean hasPort) {
        this.hasPort = hasPort;
    }

    public String registerByPhone(){
        return base_url+(hasPort?port:"")+registerByPhone_url;
    }

    public String registerByEmail(){
        return base_url+(hasPort?port:"")+registerByEmail_url;
    }

    public String loginByPhone(){
        return base_url+(hasPort?port:"")+loginByPhone_url;
    }

    public String loginByEmail(){
        return base_url+(hasPort?port:"")+loginByEmail_url;
    }

    public String editUserInfo(){
        return base_url+(hasPort?port:"")+editUserInfo_url;
    }

    public String editUserPass(){
        return base_url+(hasPort?port:"")+editPass_url;
    }

    public String editUserGender(){
        return base_url+(hasPort?port:"")+editUserGender_url;
    }

    public String editUserHead(){
        return base_url+(hasPort?port:"")+editUserHead_url;
    }

    public String editUserCover(){
        return base_url+(hasPort?port:"")+editUserCover_url;
    }

    public String getImageUrl(String imgUrl){
        Log.d("xxxxx","ImageUrl"+base_url+(hasPort?port:"")+"/wike/"+imgUrl);
        return base_url+(hasPort?port:"")+"/wike/"+imgUrl;
    }

    public String getTypeList(){
        return base_url+(hasPort?port:"")+getTypeList_url;
    }

    public String updateRecommend(){
        return base_url+(hasPort?port:"")+updateRecommend_url;
    }

    public String getRecommendCourses(){
        return base_url+(hasPort?port:"")+getRecommendCourses_url;
    }

    public String getGetCourseUser(){
        return base_url+(hasPort?port:"")+getCourseUser_url;
    }

    public String firstCourse(){
        return base_url+(hasPort?port:"")+firstCourse_url;
    }

    public String getCourseVideo(){
        return base_url+(hasPort?port:"")+getCourseVideo_url;
    }

    public String getTeacher(){
        return base_url+(hasPort?port:"")+getTeacher_url;
    }

    public String getDependentCourses(){
        return base_url+(hasPort?port:"")+getDependentCourses_url;
    }

    public String getVideoTest(){
        return base_url+(hasPort?port:"")+getVideoTest_url;
    }

    public String applyAnswer(){
        return base_url+(hasPort?port:"")+applyAnswer_url;
    }

    public String getTypeCourse(){
        return base_url+(hasPort?port:"")+getTypeCourse_url;
    }

    public String stuChapterCVideo(){
        return base_url+(hasPort?port:"")+stuChapterCVideo_url;
    }

    public String getMyCourse(){
        return base_url+(hasPort?port:"")+getMyCourses_url;
    }

    public String getBanner(){
        return base_url+(hasPort?port:"")+getBanner_url;
    }

    public String hasScore(){
        return base_url+(hasPort?port:"")+hasScore_url;
    }

    public String particularScore(){
        return base_url+(hasPort?port:"")+particularScore_url;
    }

    public String search(){
        return base_url+(hasPort?port:"")+search_url;
    }
}
