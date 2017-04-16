/**
 * Created by LinJinFeng on 2017/3/2.
 */

public class ThreadLocalTest {

    volatile double i;

    Object object=new Object();

    public void addI(){
        i++;

    }

    public static void main(String[] args) throws InterruptedException {
//        String str1=new String("haha");
//        String str2="haha";
//        System.out.println(str1==str2);
        final ThreadLocalTest test=new ThreadLocalTest();
        for (int n=0;n<1000;n++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    test.addI();
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println(test.i);
    }
}
