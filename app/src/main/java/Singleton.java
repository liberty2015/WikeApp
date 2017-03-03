/**
 * Created by LinJinFeng on 2017/3/2.
 */

public class Singleton {
    //懒汉模式
    private static Singleton instance;



    private Singleton(){

    }

    public static synchronized Singleton getInstance() {
        if (instance==null){
            instance=new Singleton();
        }
        return instance;
    }
}
