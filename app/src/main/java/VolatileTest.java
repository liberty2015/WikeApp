import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by liberty on 2017/4/2.
 */

public class VolatileTest {

    static volatile List<bean> beans;

    static Thread thread=new Thread(){
        @Override
        public void run() {
            super.run();
            beans=new ArrayList<>();
            Random random=new Random(System.currentTimeMillis());
            for (int i = 0; i < 5; i++) {
                int num=random.nextInt(10);
                bean b=new bean();
                b.x=num;
                beans.add(b);
            }
        }
    };

    static Thread thread1=new Thread(){
        @Override
        public void run() {
            super.run();
            while (true){
                if (beans!=null){
                    for (bean b:beans){
                        System.out.println(b.x);
                    }
                }
            }
        }
    };

    public static void main(String[] args) {
        thread1.start();
        thread.start();
    }

    static class bean{
        int x;
    }
}
