import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by liberty on 2017/3/24.
 */

public class testJava {

    private final static int CONNECTTIMEOUT=300;
    private final static int READTIMEOUT=200;
    private final static int WRITETIMEOUT=200;

    public static void main(String[] args) {
//        CookieManager cookieManager=new CookieManager();
//        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
//        OkHttpClient okHttpClient=new OkHttpClient.Builder()
//                .cookieJar(new JavaNetCookieJar(cookieManager))
//                /**
//                 * 设置超时
//                 */
//                .connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READTIMEOUT,TimeUnit.SECONDS)
//                .writeTimeout(WRITETIMEOUT,TimeUnit.SECONDS)
//                /**
//                 * 错误重连
//                 */
//                .retryOnConnectionFailure(true)
//                .build();
//        final Request request=new Request
//                .Builder()
//                .url("http://119.29.175.247/WikeServer/Home/Course/getRecommendCourses?id=1")
//                .build();
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String result=response.body().string();
//                System.out.println(result);
//            }
//        });
        try {
            JSONObject object=new JSONObject("{\"success\":true,\"data\":{\"id\":\"1\",\"name\":\"\\u6797\\u9526\\u4e30\",\"sex\":\"\\u7537\",\"job\":" +
                    "\"\\u5b89\\u5353\\u5de5\\u7a0b\\u5e08\",\"describtion\":\"\\u6bd5\\u4e1a\\u4e8e\\u5317\\u7406\\u5de5\"}}");
            JSONObject data=object.getJSONObject("data");
            System.out.println(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
