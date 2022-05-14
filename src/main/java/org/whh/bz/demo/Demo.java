package org.whh.bz.demo;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Demo {
    /**
     * 字符编码测试
     */
    @Test
    public void demo() {
        Base64.Encoder e = Base64.getEncoder();
        byte[] bytes = "12345".getBytes();
        String s = e.encodeToString(bytes);
        System.out.println(s);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] s1 = decoder.decode(s);
        System.out.println(new String(s1));
    }

    @Test
    public void demo001() throws UnsupportedEncodingException {
        byte[] bytes = "米兰".getBytes("utf-8");
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i]);
        }
    }


    @Test
    public void demo003() throws NoSuchAlgorithmException, DecoderException {
        String flag = "12345678";
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        String cookieKey = (Hex.encodeHex(messageDigest.digest((flag + "notlogin").getBytes()))).toString();
        Hex.decodeHex(cookieKey);
    }

    @Test
    public void demo004() {
        String s = "12345";
        System.out.println(System.identityHashCode(s));
        s = "12344556";
        System.out.println(System.identityHashCode(s));
    }

    @Test
    public void demo005() {
        List a = new ArrayList();
        a.add(1);
        a.add(1);
        a.add(1);
        List b = new ArrayList();
        b = a;
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));

    }

    @Test
    public void demo006() {
        int i = 0;
        System.out.println(i++);
        System.out.println(i++);
        System.out.println(i++);
    }

    @Test
    public void demo007() {
        int i = 0;
        Integer i1 = 222;
        Integer i2 = new Integer(222);
        System.out.println(System.identityHashCode(i));
        System.out.println(System.identityHashCode(i1));
    }

    @Test
    public void demo008() {
        List a = new ArrayList();
        int b = 1;
        a.add(1);
        System.out.println(System.identityHashCode(a.get(0)));
        System.out.println(System.identityHashCode(b));
        a.set(0, 2);
        System.out.println(System.identityHashCode(a.get(0)));
    }

    int a;
    int b = 0;

    @Test
    public void demo009() {
        Thread t1 = new Thread(() -> {
            Thread.currentThread().setName("AAA");
            for (int i = 0; a < 50; i++) {
                a++;
                System.out.println(Thread.currentThread().toString() + System.currentTimeMillis() + "   " + a);
            }
        });
        Thread t2 = new Thread(() -> {
            Thread.currentThread().setName("BBB");
            System.out.println(Thread.currentThread().toString() + System.currentTimeMillis() + " B中a=" + a);
        });

        t2.start();
        t1.start();

    }

    @Test
    public void demo010() {
        String s = "123";
        System.out.println(s);
        changeIndex2(s);
        System.out.println(s);
    }
    public static void changeIndex2(String s){
        s= new String(new byte[]{'a','b','c'});
        System.out.println(s);
        List a= new ArrayList();
        for (Object o:a
             ) {

        }
    }
//实现链表
    @Test
    public  void  demo011(){
       Node node = new Node(1);
        Node newNode = new Node(2);
        node.next=newNode;

        Node[] nodes = new Node[5];
        System.out.println(nodes[3]);
        int i = 0;
        Node cur = node;
        Node next  ;
        while (cur!=null){
            next = cur.next;
            nodes[i]=cur;
            cur = cur.next;
            i++;
        }
        for (int j = 0; j < nodes.length; j++) {
            System.out.println(nodes[j].data);
        }
    }
    private class Node{
        Object data;
        Node next;
        public Node(Object data){
            this.data = data;
        }
    }

    private int[] demo = new int[]{2,2,0, 4, 6, 7, 9, 1, 6,0};
    /*堆排序*/
    @Test
    public  void  heeapSort(){
        int first = 0;
        int last = demo.length-1;
        if (first>=last)return;
        int firstNoneLeafNode = (last-1)>>2;
        for (int i = firstNoneLeafNode; i >=0 ; i--) {
            sort(demo,i,last);
        }
        for (int i = demo.length-1; i >0 ; i--) {
            swap(demo,0,i);
            sort(demo,0,i-1);
        }
        for (int i = 0; i <demo.length ; i++) {
            System.out.print(demo[i]+" ");
        }
    }
    private  void sort(int[] demo,int index,int length){
        int left = index*2+1;
        int right = left+1;
        int maxInThree = left;
        if(left>length)return;
        if (right<=length&&demo[right]>demo[left]){
            maxInThree  = right;
        }
        if (demo[maxInThree]>demo[index]){
            swap(demo,maxInThree,index);
            sort(demo,maxInThree,length);
        }
    }
    void swap(int[] demo,int a,int b){
        int temp = demo[a];
        demo[a] = demo[b];
        demo[b] = temp;
    }

    /*基数排序*/
    @Test
    public void de(){
        radixsort(demo,demo.length);
        for (int i = 0; i <demo.length ; i++) {
            System.out.print(demo[i]+" ");
        }
    }


    int maxbit(int data[], int n) //辅助函数，求数据的最大位数
    {
        int maxData = data[0];      ///< 最大数
        /// 先求出最大数，再求其位数，这样有原先依次每个数判断其位数，稍微优化点。
        for (int i = 1; i < n; ++i)
        {
            if (maxData < data[i])
                maxData = data[i];
        }
        int d = 1;
        int p = 10;
        while (maxData >= p)
        {
            //p *= 10; // Maybe overflow
            maxData /= 10;
            ++d;
        }
        return d;
/*    int d = 1; //保存最大的位数
    int p = 10;
    for(int i = 0; i < n; ++i)
    {
        while(data[i] >= p)
        {
            p *= 10;
            ++d;
        }
    }
    return d;*/
    }
    void radixsort(int data[], int n) //基数排序
    {
        int d = maxbit(data, n);
        int[] tmp = new int[n];
        int[] count = new int[10]; //计数器
        int i, j, k;
        int radix = 1;
        for(i = 1; i <= d; i++) //进行d次排序
        {
            for(j = 0; j < 10; j++)
                count[j] = 0; //每次分配前清空计数器
            for(j = 0; j < n; j++)
            {
                k = (data[j] / radix) % 10; //统计每个桶中的记录数
                count[k]++;
            }
            for(j = 1; j < 10; j++)
                count[j] = count[j - 1] + count[j]; //将tmp中的位置依次分配给每个桶
            for(j = n - 1; j >= 0; j--) //将所有桶中记录依次收集到tmp中
            {
                k = (data[j] / radix) % 10;
                tmp[count[k] - 1] = data[j];
                count[k]--;
            }
            for(j = 0; j < n; j++) //将临时数组的内容复制到data中
                data[j] = tmp[j];
            radix = radix * 10;
        }
    }
    /*基数排序*/
    @Test
     public void radixSortDemo(){
         int max = demo[0];
         /*找出最大值*/
         for (int i = 1; i <  demo.length; i++) {
            if (max<demo[i])max=demo[i];
         }
        /*计算最大位数来确定排序的次数*/
         int numSize = 1;
         while (true) {
             if ((max / 10) > 0) {
                 max = max / 10;
                 numSize++;
             } else {
                 System.out.println("是" + numSize + "位数"+"--"+max);
                 break;
             }
         }
         /*基数排序*/
         /*创建临时temp*/
         int [] temp = new int[demo.length];
         int [] count = new int[10];
         int  s = 0;       //桶子编号index
         int baseNum = 1;  // 1*10*10*10*10  个位十位百位千位
         for (int i = 0; i < numSize; i++) {
             /*桶子计数*/
             for (int j = 0; j < demo.length; j++) {
                count[demo[j] / baseNum%10]++;
             }
             /*确定每个桶的界限*/
             for (int j = 1; j < 10; j++) {
                 count[j] = count[j-1]+count[j];
             }
             for (int j = 0; j < demo.length; j++) {
                 temp[count[demo[j]/baseNum%10]-1] = demo[j];
                 count[demo[j]/baseNum%10]--;
             }
             for (int j = 0; j < demo.length; j++) {
                demo[j] = temp[j];
             }
             baseNum*=10;   // 1*10*10*10*10  个位十位百位千位
         }
        for (int i = 0; i < demo.length; i++) {
            System.out.print(demo[i]+" ");
        }
    }
    @Test
    public void demo_F_CH(){
        F f = new Ch();
        f.check();
    }
}
class F{
    int age = 30;
    public void check (){
        System.out.println(30);
    }
}
class Ch extends F{
    int age = 10;
    public void check (){
        System.out.println(10);
    }
}
