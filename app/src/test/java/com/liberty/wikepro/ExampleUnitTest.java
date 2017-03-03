package com.liberty.wikepro;

import org.junit.Test;

import static org.junit.Assert.*;

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
}