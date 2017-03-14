package com.liberty.wikepro;

import com.liberty.wikepro.net.OkHttpUtil;
import com.liberty.wikepro.net.WikeApi;

import org.junit.Test;

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
}