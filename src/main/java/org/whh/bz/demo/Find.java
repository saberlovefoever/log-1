package org.whh.bz.demo;

import org.junit.Test;

public class Find {
    @Test
    public void demo(){
        int[]  a = {1,2,4,67,8,998,443,45,25,2,542,5,6};
//        normal(a,5);
        new SuanfaDemo().quickSort(a,0,a.length-1);
//        half(a,2,0,a.length-1);
        o(a);
        fbnq(a,2,0,a.length-1);
    }
    public void o(int[] a){
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+" ");
        }
        System.out.println();
    }
    /*普通查找*/
    public void normal(int[] a,int b){
        for (int i=0 ;i<a.length;i++){
            if (b == a[i])
            {
                System.out.println(i+"-");
                break;
            }
        }
        System.out.println("没找到");
    }
    /*二分查找*/
    public void  half(int[] a,int obj,int start,int end){
        int mid =0;
        if (start>end)return;
        if (start==end){
            if (a[start]==obj){
                System.out.println("finded。Index = "+start);
            }
            else {
                System.out.println("无匹配");
            }
        }
        if(a[mid = (start+end)/2]==obj){
            System.out.println("finded。Index = "+mid);
        }else if (obj>a[mid]){
            half(a,obj,mid+1,end);
        }else{
            half(a,obj,start,mid-1);
        }
    }
    /*差值查找*/
    /*斐波那契查找*/
    /*index = 1 1 2 3 5 8 13 21 34 55*/
    /*斐波那契数列来表示index*/
    public int[] getFib(int length){
        int[] f = new int[length];
        f[0] = 1;  f[1] = 1;
        for (int i = 2; i < length; i++) {
            f[i]=f[i-1]+f[i-2];
        }
        return f;
    }
    public void fbnq(int[] a,int key,int low,int high){
    }
    /*二叉搜索树*/
    /*  二叉查找树特性
    1、左子树<根节点<右子树
    2、无重复结点    */
     private final static class MyTreeNode{
        Object data;
        MyTreeNode left;
        MyTreeNode right;
    }
    public void bianli(MyTreeNode input,boolean flag){
        if(input==null){
            return;
        }
        if (flag)return;
        bianli(input.left,flag);
        System.out.println(input.data);
        if (input.data=="left"){flag=true;return;}
        bianli(input.right,flag);
    }
    @Test
    public void test(){
        MyTreeNode node = new MyTreeNode();
        MyTreeNode leftnode = new MyTreeNode();
        MyTreeNode rightnode = new MyTreeNode();
        MyTreeNode leftleftnode = new MyTreeNode();

        node.left=leftnode;
        leftleftnode.data = "leftleftnode";
        node.right=rightnode;
        node.data = "root";
        leftnode.data = "left";
        rightnode.data = "right";
        leftnode.left = leftleftnode;
        bianli(node,false);

    }
}
