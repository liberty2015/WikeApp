package com.liberty.wikepro;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;
import com.liberty.wikepro.util.TextInputUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void instanceTest(){
        String  str="fasfasfa";
        if (str instanceof String){
            System.out.println("hahaha");
        }
    }

    @Test
    public void hashSetTest(){
        int hashSet=0x00100000;
        int nextHash=hashSet|0x00001000;
        System.out.println(Integer.toHexString(nextHash));
        System.out.println(Integer.toHexString((nextHash&hashSet)));
        if ((nextHash&hashSet)==hashSet){
            System.out.println("haha");
        }
    }

    @Test
    public void testRequestParam(){
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("loginName","123");
        params.add("loginPassword","123");
        params.add("gender","1");
    }

    @Test
    public void testPost(){
        OkHttpUtil.RequestParams params=new OkHttpUtil.RequestParams();
        params.add("loginName","13750050322");
        params.add("loginPassword","123456");
        params.add("gender","1");
        params.add("nickName","Liberty");
        params.add("self_describe",null);
        params.add("head_img",null);
        params.add("page_img",null);
        OkHttpUtil.getInstance().post(WikeApi.getInstance().registerByPhone(), params,null);
    }

    @Test
    public void testMd5(){
        try {
            System.out.printf(TextInputUtil.md5("1357924680"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGson(){
//        String json="[{\"id\":\"15\",\"chname\":\"Activity\\u7684\\u751f\\u547d\\u5468\\u671f\"," +
//                "\"chnum\":null,\"describtion\":\"\",\"cid\":\"4\"},{\"id\":\"16\",\"chname\":" +
//                "\"Service\\u5168\\u89e3\\u6790\",\"chnum\":null,\"describtion\":\"\",\"cid\":\"4\"}," +
//                "{\"id\":\"17\",\"chname\":\"\\u642d\\u5efaAndroid\\u5f00\\u53d1\\u73af\\u5883\",\"chnum\"" +
//                ":null,\"describtion\":\"\",\"cid\":\"4\"},{\"id\":\"18\",\"chname\":\"\\u4f7f\\u7528Intent\\" +
//                "u5b9e\\u73b0\\u9875\\u9762\\u8df3\\u8f6c\",\"chnum\":null,\"describtion\":\"\",\"cid\":\"4\"}," +
//                "{\"id\":\"19\",\"chname\":\"Fragment\\u57fa\\u7840\\u6982\\u8ff0\",\"chnum\":null,\"describtion\":" +
//                "\"\",\"cid\":\"4\"},{\"id\":\"20\",\"chname\":\"Android \\u5e38\\u7528\\u5e03\\u5c40\\u8be6\\u89e3\"," +
//                "\"chnum\":null,\"describtion\":\"\",\"cid\":\"4\"},{\"id\":\"21\",\"chname\":\"Android\\u6570\\u636e\\u5b58" +
//                "\\u50a8\\u4e4bSQLite\",\"chnum\":null,\"describtion\":\"\",\"cid\":\"4\"},{\"id\":\"22\",\"chname\":\"\\u56db\\" +
//                "u5927\\u7ec4\\u4ef6\\u4e4bBroadcastReceiver\",\"chnum\":null,\"describtion\":\"\",\"cid\":\"4\"},{\"id\":\"23\"," +
//                "\"chname\":\"\\u56db\\u5927\\u7ec4\\u4ef6\\u4e4bContentProvider\",\"chnum\":null,\"describtion\":\"\",\"cid\":\"4\"}]";
//        Gson gson=new Gson();
//        List<Chapter> chapters = gson.fromJson(json, new TypeToken<List<Chapter>>() {
//        }.getType());
//        System.out.println(chapters.size());
        try {
            JSONObject object=new JSONObject("{\"success\":true,\"data\":{\"id\":\"1\",\"name\":\"\\u6797\\u9526\\u4e30\",\"sex\":\"\\u7537\",\"job\":" +
                    "\"\\u5b89\\u5353\\u5de5\\u7a0b\\u5e08\",\"describtion\":\"\\u6bd5\\u4e1a\\u4e8e\\u5317\\u7406\\u5de5\"}}");
            JSONObject data=object.getJSONObject("data");
            System.out.println(data.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class Chapter{
        private int id;
        private String chname;
        private int chnum;

        public Chapter(){

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getChnum() {
            return chnum;
        }

        public void setChnum(int chnum) {
            this.chnum = chnum;
        }

        public String getChname() {
            return chname;
        }

        public void setChname(String chname) {
            this.chname = chname;
        }
    }
}