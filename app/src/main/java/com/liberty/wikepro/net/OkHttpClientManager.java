package com.liberty.wikepro.net;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.internal.$Gson$Types;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LinJinFeng on 2017/2/14.
 */

public class OkHttpClientManager {
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;
    private int CONNECTTIMEOUT=30;
    private int READTIMEOUT=20;
    private int WRITETIMEOUT=20;

    private static final String TAG="OkHttpClientManager";

    private OkHttpClientManager(){
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
                .build();
        mDelivery=new Handler(Looper.getMainLooper());
        mGson=new Gson();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance==null){
            synchronized (OkHttpClientManager.class){
                if (mInstance==null){
                    mInstance=new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public static Response get(String url) throws IOException {
        return getInstance()._get(url);
    }

    private Response _get(String url) throws IOException {
        final Request request=new Request.Builder()
                .url(url)
                .build();
        Call call=mOkHttpClient.newCall(request);
        Response response=call.execute();
        return response;
    }

    public static String getAsString(String url) throws IOException {
        return getInstance()._getAsString(url);
    }

    private String _getAsString(String url) throws IOException {
        Response response=get(url);
        return response.body().string();
    }

    public static void getAsync(String url, final ResultCallback callback){
        getInstance()._getAsync(url,callback);
    }

    private void _getAsync(String url, final ResultCallback callback){
        final Request request=new Request.Builder()
                .url(url)
                .build();
        deliveryResult(callback,request);
    }

    public static Response post(String url,Param... params) throws IOException {
        return getInstance()._post(url,params);
    }

    private Response _post(String url,Param... params) throws IOException {
        Request request=buildPostRequest(url,params);
        Response response=mOkHttpClient.newCall(request).execute();
        return response;
    }

    public static String postAsString(String url,Param... params) throws IOException {
        return getInstance()._postAsString(url,params);
    }

    private String _postAsString(String url,Param... params) throws IOException {
        Response response=post(url,params);
        return response.body().string();
    }

    public static void postAsync(String url,final ResultCallback callback,Param...params){
        getInstance()._postAsync(url, callback, params);
    }

    private void _postAsync(String url,final ResultCallback callback,Param...params){
        Request request=buildPostRequest(url, params);
        deliveryResult(callback,request);
    }

    public static <T>void postAsync(String url, final ResultCallback<T> callback, Map<String,String> paramMap){
        getInstance()._postAsync(url, callback, paramMap);
    }

    private <T>void _postAsync(String url, final ResultCallback<T> callback, Map<String,String> paramMap){
        Param[] paramsArr=map2Params(paramMap);
        Request request=buildPostRequest(url,paramsArr);
        deliveryResult(callback,request);
    }

    public static Response post(String url,File[]files,String[] fileKeys,Param[] params) throws IOException {
        return getInstance()._post(url, files, fileKeys, params);
    }

    private Response _post(String url,File[]files,String[] fileKeys,Param[] params) throws IOException {
        Request request=buildMultipartFormRequest(url,files,fileKeys,params);
        return mOkHttpClient.newCall(request).execute();
    }

    public static Response post(String url, File file,String fileKey) throws IOException {
        return getInstance()._post(url, file, fileKey);
    }

    private Response _post(String url, File file,String fileKey) throws IOException {
        Request request=buildMultipartFormRequest(url,new File[]{file},new String[]{fileKey},null);
        return mOkHttpClient.newCall(request).execute();
    }

    public static Response post(String url,File file,String fileKey,Param...params) throws IOException {
        return getInstance()._post(url, file, fileKey, params);
    }

    private Response _post(String url,File file,String fileKey,Param...params) throws IOException {
        Request request=buildMultipartFormRequest(url,new File[]{file},new String[]{fileKey},params);
        return mOkHttpClient.newCall(request).execute();
    }

    public static void postAsync(String url,ResultCallback callback,File[] files,String[] fileKeys,Param...params){
        getInstance()._postAsync(url, callback, files, fileKeys, params);
    }

    private void _postAsync(String url,ResultCallback callback,File[] files,String[] fileKeys,Param...params){
        Request request=buildMultipartFormRequest(url,files,fileKeys,params);
        deliveryResult(callback,request);
    }

    public static void postAsync(String url,ResultCallback callback,File file,String fileKey){
        getInstance()._postAsync(url, callback, file, fileKey);
    }

    private void _postAsync(String url,ResultCallback callback,File file,String fileKey){
        Request request=buildMultipartFormRequest(url,new File[]{file},new String[]{fileKey},null);
        deliveryResult(callback,request);
    }

    public static void postAsync(String url,ResultCallback callback,File file,String fileKey,Param...params){
        getInstance()._postAsync(url, callback, file, fileKey, params);
    }

    private void _postAsync(String url,ResultCallback callback,File file,String fileKey,Param...params){
        Request request=buildMultipartFormRequest(url,new File[]{file},new String[]{fileKey},params);
        deliveryResult(callback,request);
    }

    public static void downloadAsync(final String url, final String destFileDir, final ResultCallback callback){
        getInstance()._downloadAsync(url, destFileDir, callback);
    }

    private void _downloadAsync(final String url, final String destFileDir, final ResultCallback callback){
        Request request=new Request.Builder()
                .url(url)
                .build();
        Call call=mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is=null;
                byte[] bytes=new byte[2048];
                int len=0;
                FileOutputStream fos=null;
                try {
                    is=response.body().byteStream();
                    File file=new File(destFileDir,getFileName(url));
                    fos=new FileOutputStream(file);
                    while ((len=is.read(bytes))!=-1){
                        fos.write(bytes,0,len);
                    }
                    fos.flush();
                    sendSuccessResultCallback(file.getAbsoluteFile(),callback);
                }catch (IOException e){
                    sendFailStringCallback(response.request(),e,callback);
                }finally {
                    try {
                        if (is!=null)is.close();
                        if (fos!=null)fos.close();
                    }catch (IOException e){

                    }
                }

            }
        });
    }

    private String getFileName(String url){
        int separatorIndex=url.lastIndexOf("/");
        return (separatorIndex<0)?url:url.substring(separatorIndex+1,url.length());
    }

    private Request buildMultipartFormRequest(String url,File[] files,String[] fileKeys,Param[] params){
        params=validateParam(params);
        MultipartBody.Builder builder=new MultipartBody
                .Builder();
        builder.setType(MultipartBody.FORM);
        for (Param param:params){
            builder.addFormDataPart(param.key,param.value);
        }
        if (files!=null){
            RequestBody fileBody=null;
            for (int i = 0; i < files.length; i++) {
                File file=files[i];
                String fileName=file.getName();
                fileBody=RequestBody.create(MediaType.parse(guessMimeType(fileName)),file);
                builder.addPart(Headers.of("Content-Disposition","form-data;name=\""
                        +fileKeys[i]+"\";filename=\""+fileName+"\""),fileBody);
            }
        }
        RequestBody requestBody=builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private String guessMimeType(String path){
        FileNameMap fileNameMap= URLConnection.getFileNameMap();
        String contentTypeFor=fileNameMap.getContentTypeFor(path);
        if (contentTypeFor==null){
            contentTypeFor="application/octet-stream";
        }
        return contentTypeFor;
    }

    private Param[] validateParam(Param[] params){
        if (params==null){
            return new Param[0];
        }else return params;
    }

    private Param[] map2Params(Map<String,String> paramMap){
        if (paramMap==null)return new Param[0];
        int size=paramMap.size();
        Param[] params=new Param[size];
        Set<Map.Entry<String, String>> entries = paramMap.entrySet();
        int i=0;
        for (Map.Entry<String,String> entry:entries){
            params[i++]=new Param(entry.getKey(),entry.getValue());
        }
        return params;
    }

    private Request buildPostRequest(String url,Param...params){
        if (params==null){
            params=new Param[0];
        }
        FormBody.Builder builder=new FormBody.Builder();
        for (Param param:params){
            builder.add(param.key,param.value);
        }
        RequestBody requestBody=builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    private <T>void deliveryResult(final ResultCallback<T> callback, Request request){
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailStringCallback(call.request(),e,callback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String s=response.body().string();
                    if (callback.mType==String.class){
                        T t= (T) s;
                        sendSuccessResultCallback(t,callback);
                    }else {
                        T t=mGson.fromJson(s,callback.mType);
                        sendSuccessResultCallback(t,callback);
                    }
                }catch (IOException e){
                    sendFailStringCallback(response.request(),e,callback);
                }catch (JsonParseException e){//Json 解析错误
                    sendFailStringCallback(response.request(),e,callback);
                }

            }
        });
    }

    private void sendFailStringCallback(final Request request,final Exception e,final ResultCallback callback){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback!=null){
                    callback.onError(request,e);
                }
            }
        });
    }

    private <T> void sendSuccessResultCallback(final T t,final ResultCallback<T> callback){
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                if (callback!=null){
                    callback.onResponse(t);
                }
            }
        });
    }

    public static abstract class ResultCallback<T>{
        Type mType;

        public ResultCallback(){
            mType=getSuperClassTypeParameter(getClass());
        }

        static Type getSuperClassTypeParameter(Class<?> subClass){
            Type superClass=subClass.getGenericSuperclass();
            if (superClass instanceof Class){
                throw new RuntimeException("Missing type parameter.");
            }
            /**
             *获取泛型参数的实际类型
             */
            ParameterizedType parameterized= (ParameterizedType) superClass;
            return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
        }

        public abstract void onError(Request request,Exception e);

        public abstract void onResponse(T response);
    }

    public static class Param{

        String key;
        String value;

        public Param(){

        }

        public Param(String key,String value){
            this.key=key;
            this.value=value;
        }

    }

}
