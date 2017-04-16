import com.liberty.wikepro.net.OkHttpUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by LinJinFeng on 2017/2/16.
 */

public class TestNum {

//    private static ThreadLocal<TestSum> seqNum=new ThreadLocal<TestSum>(){
//        @Override
//        protected TestSum initialValue() {
//            return new TestSum(0);
//        }
//    };

//    public int getNextNum(){
//
//    }

    static char label;

    public static char test1() {

        try {

            System.out.println('A');

            return label = 'A';

            }

        finally {

            System.out.println('B');

            label = 'B';

            }
        }


    private static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static class Solution {
        public static ListNode ReverseList(ListNode head) {
            if(head==null||head.next==null){
                return head;
            }
            ListNode p1,p2,p3;
            p2=head.next;
            p1=head;
            while(p2!=null){
                p3=p2.next;
                p2.next=p1;
                p1=p2;
                p2=p3;
            }
            head.next=null;
            head=p1;
            return head;
        }
    }

    private static void quickSort(int left,int right,int[]arr){
        if (left>right)return;
        int i,j,tmp;
        i=left;
        j=right;
        tmp=arr[left];
        while (i<j){
            while (arr[j]>=tmp&&i<j){
                j--;
            }
            while (arr[i]<=tmp&&i<j){
                i++;
            }
            if (i<j){
                int t=arr[i];
                arr[i]=arr[j];
                arr[j]=t;
            }
        }
        arr[left]=arr[i];
        arr[i]=tmp;
        quickSort(left,i-1,arr);
        quickSort(j+1,right,arr);
    }

    private static int testTry(){
        try {
            return 1;
        }catch (Exception e){
            return 0;
        }finally {
            System.out.println("aa");
        }
    }

    public static void main(String[] args) {
//        int[] arr={1,2,3,4,5,6};
//        ListNode head=new ListNode(arr[0]);
//        ListNode node=head;
//        for (int i=1;i<arr.length;i++){
//            ListNode next=new ListNode(arr[i]);
//            node.next=next;
//            node=next;
//        }
//        ListNode reverse=Solution.ReverseList(head);
//        while (reverse!=null){
//            System.out.println(reverse.val);
//            reverse=reverse.next;
//        }
//        int []arr={34,12,53,1,24,16,12,76};
//        quickSort(0,arr.length-1,arr);
//        for (int i:arr){
//            System.out.println(i);
//        }
//        System.out.println(new Integer(null));
//        System.out.println(test1());
//        System.out.println(label);
//        TestSum sn=new TestSum(0);
//        TestClient t1=new TestClient(sn);
//        TestClient t2=new TestClient(sn);
//        TestClient t3=new TestClient(sn);
//        t1.start();
//        t2.start();
//        t3.start();
//        int [] arr={2,1,5,0,9,4,7};
//        maoPaoBetter(arr);
//        int[] arr={0,3,45,67,89,104,567,1023,2056};
//        System.out.println(binarySearch(arr,1023));
//        for (int i:arr){
//            System.out.print(i+"  ");
//        }
//        B b=new B();
        File f=new File("F:\\install package\\scene.jpg");
        OkHttpUtil.RequestParams params = new OkHttpUtil.RequestParams();
        params.add("id","1");
        String url="http://119.29.175.247/WikeServer/Home/User/editUserHead";
        OkHttpClient client=new OkHttpClient.Builder()
                .readTimeout(100000, TimeUnit.SECONDS)
                .writeTimeout(200000,TimeUnit.SECONDS)
                .readTimeout(300000,TimeUnit.SECONDS)
                .build();
        RequestBody imgBody=RequestBody.create(MediaType.parse("image/jpeg"),f);
        MultipartBody.Builder builder1=new MultipartBody.Builder().setType(MultipartBody.FORM);
        builder1.addFormDataPart("image",f.getName(),imgBody);
        builder1.addFormDataPart("id","1");
        Request.Builder builder=new Request.Builder().url(url).post(builder1.build());
//        try {
//            Response response=client.newCall(builder.build()).execute();
//            if (response.isSuccessful()){
//                System.out.println(new String (response.body().string().getBytes(),"utf-8"));
//            }

//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        client.newCall(builder.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result=new String(response.body().string().getBytes(),"UTF-8");
                System.out.println(result);
            }
        });
    }

    static class A{
        A(){
            System.out.printf("A");
        }
    }

    static class B extends A{
        B(){
            System.out.printf("B");
        }
    }

    private static void maoPao(int[] arr){
        for (int i=0;i<arr.length-1;i++){
            for (int j=0;j<arr.length-i-1;j++){
                if (arr[j]>arr[j+1]){
                    int tmp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=tmp;
                }
            }
        }
        for (int i:arr){
            System.out.print(i+"  ");
        }
    }

    private static void maoPaoBetter(int[] arr){
        int n=arr.length-1;
        while (n>0){
            int pos=0;
            for (int j=0;j<n;j++){
                if (arr[j]>arr[j+1]){
                    int tmp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=tmp;
                    pos=j;
                }
            }
            n=pos;
            for (int i:arr){
                System.out.print(i+"  ");
            }
            System.out.println();
        }
        System.out.println();
        for (int i:arr){
            System.out.print(i+"  ");
        }
    }

    private static int binarySearch(int[] arr,int e){
        int left=0;
        int right=arr.length;
        while (left<right){
            int mid=(left+right)>>1;
            if (arr[mid]>e){
                right=mid-1;
            }else if (arr[mid]<e){
                left=mid+1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    private static class TestSum{
        private static ThreadLocal<TestSum> seqNum=new ThreadLocal<TestSum>(){
            @Override
            protected TestSum initialValue() {
                return new TestSum(0);
            }
        };

        int num=0;
        public TestSum(int num){
            this.num=num;
        }

        public int getNum() {
            return num++;
        }

        public TestSum getTestSum(){
            return seqNum.get();
        }
    }

    private static class TestClient extends Thread{
        private TestSum sn;

        public TestClient(TestSum sn){
            this.sn=sn;
        }

        @Override
        public void run() {
            for (int i=0;i<3;i++){
                System.out.println("thread["+Thread.currentThread().getName()+"]-->sn["+sn.getTestSum().getNum()+"]");
            }
        }
    }
}
