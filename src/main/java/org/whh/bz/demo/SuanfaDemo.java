package org.whh.bz.demo;

import org.junit.Test;

import java.util.Arrays;

public class SuanfaDemo {

    public void so (int demo[]) {
        for (int i = 0; i < demo.length; i++) {
            if (i == demo.length - 1) {
                System.out.print(demo[i] + "");
            } else {
                System.out.print(demo[i] + ",");
            }
        }
    }
    /*算法测试  一次买卖股票
     * 解法   动态规划
     * */
    @Test
    public void demo001() {
        int[] price = {1, 7, 3, 4, 6, 5};
        int[][] temp = new int[price.length][2];
        temp[0][0] = 0;
        temp[0][1] = -price[0];
        for (int i = 1; i < price.length; i++) {
            temp[i][0] = Math.max(temp[i - 1][0], temp[i - 1][1] + price[i]);
            temp[i][1] = Math.max(temp[i - 1][1], -price[i]);
        }
        System.out.println(temp[price.length - 1][0]);
    }

    //    给出给定数字下一个比它大的数字。 例子：1234 -> 1243    1243 ->1324
//    有思路可以先讲清楚思路，考虑完善边界条件，面试官确定思路没问题后写完并测试：
    @Test
    public void s002() {
//        123 132  312 321 213  231
        int a = 100;
        int count;
        int b;
        int[] num;  //数字转换成的数组
        int[] temp; //计算后的数组

        if (a < 10) {
            System.out.println("小于10无法交换");
            return;
        } else {
//            计算数字位数
            b = a;
            count = 1;
            while (true) {
                if ((b / 10) > 0) {
                    b = b / 10;
                    count++;
                } else {
                    System.out.println("是" + count + "位数");
                    break;
                }
            }


            num = new int[count];
            b = a;
            int tep;
            for (int i = 1; i < count + 1; ++i) {
                tep = b % 10;
                b = b / 10;
                num[count - i] = tep;
            }

//               temp=num; 不能这样赋值 这样写意思是双引用指向同一个数组
            temp = new int[count];
            for (int i = 0; i < num.length; i++) {
                temp[i] = num[i];
            }
            System.out.print("num赋值给temp=[");
            for (int i = 0; ; i++) {
                if (i == temp.length - 1) {
                    System.out.print(temp[i] + "]");
                    break;
                } else {
                    System.out.print(temp[i] + ",");
                }
            }

//            判断交换
            boolean flag = false;
            for (int i = temp.length - 1; i > 0; i--) {
                if (!flag) {
                    for (int j = i - 1; j >= 0; j--) {
                        if (temp[i] > temp[j]) {
                            int tmp= temp[i];
                            temp[i] = temp[j];
                            temp[j] = tmp;
                            flag = true;
                            break;
                        }
                    }
                } else {
                    break;
                }
            }

//            输出 数组
            System.out.println("");
            System.out.print("temp=[");
            for (int i = 0; ; i++) {
                if (i == temp.length - 1) {
                    System.out.print(temp[i] + "]");
                    break;
                } else {
                    System.out.print(temp[i] + ",");
                }
            }
            System.out.println();
            System.out.println();
            if (Arrays.equals(num, temp)) {//省事儿了、、、
                System.out.println("已经是最大值");
            } else {
                int finalnumber = 0;
//                计算数字
                for (int i = 0; i < temp.length; i++) {//5次  0-4
                    for (int j = temp.length - 1 - i; j > 0; j--) {
                        temp[i] *= 10;
                    }
                    finalnumber += temp[i];
                }
                System.out.println("交换完毕==》" + finalnumber);

            }
        }

    }
    @Test
    public void s003(){
        int[] a = {1,4,6},b={2,3};
        int[] temp = new int[a.length+b.length-1];
        int astart=0,bstart=0;
        for (int i = 0; i < a.length+b.length-1; i++) {


        }
    }

    /*归并排序*/
    @Test
    public void mergeSort(){
        int[] temp = new int[demo.length];
        mergeSort(temp,demo,0,demo.length-1);
        so(demo);
    }

    public void mergeSort(int[] temp,int[] demo,int start,int end){
        if(start>=end){
          return;
        }
        int mid = ((end-start)>>1)+start;
        mergeSort(temp,demo,start,mid);
        mergeSort(temp,demo,mid+1,end);
        merge(temp,demo,start,mid,end);
    }
    private void merge(int[]temp,int[] demo,int start,int mid,int end){
        int endStartIndex = mid+1;
        int startStartIndex = start;
        int k =start;
        while (startStartIndex<=mid&&endStartIndex<=end){
            temp[k++] = demo[startStartIndex]>=demo[endStartIndex]?demo[startStartIndex++]:demo[endStartIndex++];
        }
        while (startStartIndex<=mid){
            temp[k++] = demo[startStartIndex++];
        }
        while (endStartIndex<=end){
            temp[k++] = demo[endStartIndex++];
        }
        for (int i = start;i<=end;i++){
            demo[i] = temp[i];
        }
    }
    /*heapSort 堆排序*/
    @Test
   public void heapSort(){
        /*  第一步：将数组堆化
               *  beginIndex =  。
         *  从第一个非叶子节点开始即可。无需从最后一个叶子节点开始。
         *  叶子节点可以看作已符合堆要求的节点，根节点就是它自己且自己以下值为最大。
         */
       int len = demo.length - 1;
       int beginIndex = (len - 1) >> 1;
       for(int i = beginIndex; i >= 0; i--){
           everyAtomicHeap(i, len);
       }
       /*
        * 第二步：对堆化数据排序
        * 每次都是移出最顶层的根节点A[0]，与最尾部节点位置调换，同时遍历长度 - 1。
        * 然后从新整理被换到根节点的末尾元素，使其符合堆的特性。
        * 直至未排序的堆长度为 0。
        */
       for(int i = len; i > 0; i--){
           swap(0, i);
           everyAtomicHeap(0, i - 1);
       }
       so(demo);
    }
    private void swap(int i,int j){
        int temp = demo[i];
        demo[i] = demo[j];
        demo[j] = temp;
    }

    public void everyAtomicHeap( int index,int len){
        int li = (index << 1) + 1; // 左子节点索引
        int ri = li + 1;           // 右子节点索引
        int cMax = li;             // 子节点值最大索引，默认左子节点。
        if(li > len) return;       // 左子节点索引超出计算范围，直接返回。
        if(ri <= len && demo[ri] > demo[li]) // 先判断左右子节点，哪个较大。
            cMax = ri;
        if(demo[cMax] > demo[index]){  //               8                8                  12                8
                                      //               / \      ==>     / \           ==>  /  \        ==>   / \  ==> return;
                                        //       (max)9  12            9   12(max)        9    8(max)       x   y
           swap(cMax,index);      // 如果父节点被子节点调换，// 则需要继续判断换下后的父节点是否符合堆的特性。
            everyAtomicHeap(cMax, len);
        }
    }
   /* bubbleSort  冒泡排序*/
    /*1 2 3 5 7*/
    @Test
    public void  bubbleSort(){
        for (int i = 1; i < demo.length; i++) { //要比较n-1次
            for (int j = 0; j < demo.length-i; j++) {
                if (demo[j]>demo[j+1]){  // > 把大的往后冒泡
                    int temp = demo[j];
                    demo[j] = demo[j+1];
                    demo[j+1] = temp;
                }
            }
        }
        so(demo);
    }
   /* 插入排序*/
    @Test
   public void insertSort(){

       for (int i = 1; i < demo.length; i++) {//从 index = 1 开始 第一个默认有序
           for (int j =0; j < i; j++) {//  负责0到i-1 的角标
               // 1  2  3  4  <-(3) 5  7  8
             /*  这样解释： < 只有遇到比 i大的才会停下 需要让序列递增
                         > 只有遇到比 i小的才会停下 需要让序列递减才能遇到*/
               if (demo[i]<demo[j]){
                   int temp = demo[i];
                    for(int k=i;k>j;k--){
                       demo[k]=demo[k-1];
                    }
                   demo[j]=temp;
               }
           }
       }
       so(demo);
   }


    /*简单选择排序*/
    private int[] demo = new int[]{2,2,0, 4, 6, 7, 9, 1, 6,0};

    /*快速排序  挖坑填坑算法*/

    public void quickSort(int[] demo,int start,int end ) {
        if (start>=end){
            return;
        }
        int pivot = getPivot(demo,start,end);
        quickSort(demo,start,pivot);
        quickSort(demo,pivot+1,end);
    }
    @Test
    public void quickSortStarter (){
        quickSort(demo,0,demo.length-1);
        for (int i = 0; i < demo.length; i++) {
            System.out.print(demo[i]+"  ");
        }
    }
    static int getPivot(int demo[], int front, int back) {
        int pivot = front;
        while (front!=back) {
            while (front < back && demo[front] <= demo[pivot])
                front++;
            while (front < back && demo[back] > demo[pivot])
                back--;
            if(front<back){
                int temp = demo[front];
                demo[front] = demo[back];
                demo[back] = temp;
            }
        }
        int t = demo[front-1];
        demo[front-1] = demo[pivot];
        demo[pivot] = t;
        return  front-1;
    }
}