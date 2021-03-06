package com.liberty.wikepro.net;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.JavaNetCookieJar;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LinJinFeng on 2017/2/19.
 */

public class OkHttpUtil {

    public interface OkHttpResponseIMPL{
        void onSuccess(String result);

        void onError(String error);

        void onAnalyseDataSuccess(String result);

        void onAnalyseDataError(String result);
    }

    private OkHttpClient mOkHttpClient;

    private Handler mDelivery;
    private Gson mGson;

    private volatile static OkHttpUtil instance;
    private final static int CONNECTTIMEOUT=300;
    private final static int READTIMEOUT=200;
    private final static int WRITETIMEOUT=200;

    private OkHttpUtil(){
        CookieManager cookieManager=new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        mOkHttpClient=new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                /**
                 * 设置超时
                 */
                .connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READTIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(WRITETIMEOUT,TimeUnit.SECONDS)
                /**
                 * 错误重连
                 */
                .retryOnConnectionFailure(true)
                .addInterceptor(new LogInterceptor("Wike",true))
                .build();
        mDelivery=new Handler(Looper.getMainLooper());
        mGson=new Gson();
    }

    /**
     * DCL单例模式
     * @return
     */
    public static OkHttpUtil getInstance(){
        if (instance==null){
            synchronized (OkHttpUtil.class){
                if (instance==null){
                    instance=new OkHttpUtil();
                }
            }
        }
        return instance;
    }

    public void get(String url, RequestParams params, final OkHttpResponseIMPL impl){
        mOkHttpClient.newCall(buildGetRequest(url,params))
                .enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(impl,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessResultCallback(impl,parseResponse(response));
            }
        });
    }

    public void post(String url, RequestParams params, final OkHttpResponseIMPL impl){
        mOkHttpClient.newCall(buildPostRequest(url,params))
                .enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(impl,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessResultCallback(impl,parseResponse(response));
            }
        });
    }

    public void postFile(String url, File[] files, String[] keys, RequestParams params, final OkHttpResponseIMPL impl) throws UnsupportedEncodingException {
        mOkHttpClient.newCall(buildMultipartRequest(url, files, keys, params))
                .enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                sendFailResultCallback(impl,e);
            }

            @Override
            public void onResponse(Call call, Response response){
                try {
                    sendSuccessResultCallback(impl,parseResponse(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendFailResultCallback(final OkHttpResponseIMPL impl,final IOException e){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                impl.onError(e.toString());
            }
        });
    }

    private String parseResponse(Response response) throws IOException {
        return response.body().string();
    }

    private void sendSuccessResultCallback(final OkHttpResponseIMPL impl,final String result){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                try {
//                    String result=response1.body().string();
                    Log.d("xxxxx","ResultCallback="+new String(result.getBytes("UTF-8"),"Utf-8"));
                    impl.onSuccess(result);
                    JSONObject jsonObject=new JSONObject(result);
                    if (jsonObject.getBoolean("success")){
                        if (!jsonObject.isNull("data")){
                            JSONObject dataJson=jsonObject.optJSONObject("data");
                            if (dataJson!=null){
                                impl.onAnalyseDataSuccess(dataJson.toString());
                            }else {
                                JSONArray dataJsonArr=jsonObject.optJSONArray("data");
                                if (dataJsonArr!=null){
                                    impl.onAnalyseDataSuccess(dataJsonArr.toString());
                                }
                            }
                        }else {
                            impl.onAnalyseDataSuccess(result);
                        }
                    }else {
                        impl.onAnalyseDataError(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Request buildMultipartRequest(String url, File[] files,String[] keys,RequestParams params) throws UnsupportedEncodingException {
        Request.Builder builder = new Request.Builder().url(url);
        MultipartBody.Builder builder1=new MultipartBody
                .Builder();
        builder1.setType(MultipartBody.FORM);
        if (params!=null&&params.size()>0){
            ArrayList<String> keys1 = params.getKeys();
            for (String key:keys1){
                builder1.addFormDataPart(key,params.getValue(key));
//                builder1.addPart(Headers.of("Content-Disposition","form-data;name=\""
//                        +key+"\""),RequestBody.create(null,params.getValue(key)));
            }
        }
        if (files!=null&&files.length>0){
            RequestBody fileBody=null;
            for (int i=0;i<files.length;i++){
                File file=files[i];
                String fileName=file.getName();
                okhttp3.MediaType mediaType= okhttp3.MediaType.parse("image/jpg");
                fileBody=RequestBody.create(mediaType,file);
//                builder1.addPart(Headers.of("Content-Disposition","form-data;name=\""
//                        +keys[i]+"\";filename=\""+fileName+"\""),fileBody);
                builder1.addFormDataPart(keys[i], URLEncoder.encode(fileName,"UTF-8"),fileBody);
            }
        }
        return builder.post(builder1.build()).build();
    }

    private String guessMimeType(String path){
        FileNameMap fileNameMap= URLConnection.getFileNameMap();
        String contentTypeFor=fileNameMap.getContentTypeFor(path);
        if (contentTypeFor==null){
            contentTypeFor="application/octet-stream";
        }
        return contentTypeFor;
    }

    private Request buildPostRequest(String url,RequestParams params){
        Request.Builder builder = new Request.Builder()
                .url(url);
        if (params!=null&&params.size()>0){
            FormBody.Builder builder1 = new FormBody.Builder();
            ArrayList<String> keys = params.getKeys();
            for (String key:keys){
                builder1.add(key,params.getValue(key));
            }
            builder.post(builder1.build());
        }
        return builder.build();
    }

    private Request buildGetRequest(String url,RequestParams params){
        if (params!=null&&params.size()>0){
            url=encodeUrl(url,params);
        }
        return new Request.Builder().url(url).build();
    }

    private String encodeUrl(String url,RequestParams params){
        StringBuilder builder=new StringBuilder(url);
        List<String> keys = params.getKeys();
        builder.append("?");
        for (int i=0;i<params.size();i++){
            builder.append(keys.get(i))
                    .append("=")
                    .append(params
                            .getValue(keys.get(i)
                            ));
            if (i<params.size()-1){
                builder.append("&");
            }
        }
        return builder.toString();
    }

    public static class RequestParams{
        ArrayMap<String,String> params=new ArrayMap<>();

        public RequestParams add(String key,String value){
            if (value==null){
                value="";
            }
            params.put(key,value);
            return this;
        }

        public RequestParams add(String key,int value){
            params.put(key,Integer.toString(value));
            return this;
        }

        public int size(){
            return params.size();
        }

        public String getValue(String key){
            return params.get(key);
        }

        public ArrayList<String> getKeys(){
            Set<String> stringSet = params.keySet();
            ArrayList<String> keyList=new ArrayList<>(stringSet);
            return keyList;
        }
    }

}
