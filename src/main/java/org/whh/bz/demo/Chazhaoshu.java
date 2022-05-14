package org.whh.bz.demo;

import org.junit.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/*构建二叉查找树*/
public class Chazhaoshu {
    @Test
    public void  demo(){
        Chazhaoshu a = new Chazhaoshu();
        a.add(0);    //           0
        a.add(1);     //       -2   1
        a.add(-2);      //   -3  -1
        a.add(-3);//
        a.add(-1);//
    /*    System.out.println(a.root.right.toString());
        List c = getAll(a.root,new ArrayList());
        System.out.println(c.toString());*/
        System.out.println(demo2(a.root).toString());
    }
    /*层序遍历*/
    public List demo1(Treenode t){
        if(t==null) return null;
        List l = new ArrayList();
        Queue<Treenode> q = new ArrayDeque<Treenode>();
        q.add(t);
        while (!q.isEmpty()){
           Treenode s = q.poll();
           l.add(s.data);
           if(s.left!=null){
               q.add(s.left);
           }
            if(s.right!=null){
                q.add(s.right);
            }
        }
        return l;
    }
    /*层序遍历变种->每行单独存储*/
    public List demo2(Treenode input){
        if(input==null) return null;
        List l = new ArrayList();
        Queue<Treenode> q = new ArrayDeque<Treenode>();
        q.add(input);
        while (!q.isEmpty()){
            int currentFloor = q.size();
            List l2  = new ArrayList();
            for (int i = 0 ; i<currentFloor ; i ++ ){
                /* 出列操作在这*/
                Treenode s = q.poll();
                l2.add(s.data);
                if(s.left!=null){
                    q.add(s.left);
                }
                if(s.right!=null){
                    q.add(s.right);
                }
            }
            l.add(l2);
        }
        return l;

    }
    Treenode root;

    List getAll(Treenode s, ArrayList arralist){
        if(s==null){
            return null;
        }
        getAll(s.left,arralist);
        arralist.add(s.data);
        getAll(s.right,arralist);
        return  arralist;
    }

    void add(int input){
        Treenode a = new Treenode(input);
        Treenode cursor = this.root;
        if(this.root==null) {
           this.root = a;
            System.out.println("根节点新增指向。=>"+input);
           return;
       }
        int b = insert(cursor,a);
        System.out.println(b>0?a+"->成功":a+"->失败");
    }
    /*0 未插入已存在
    * 1 插入成功*/
    int insert(Treenode cursor,Treenode a){
        if (a.data==cursor.data){
            return 0;
        }
        else if(a.data<cursor.data){
           if(cursor.left==null){
               cursor.left = a;
               a.parent = cursor;
               return 1;
           }
           return insert(cursor.left,a);
        }
        else {
            if(cursor.right==null){
                cursor.right = a;
                a.parent = cursor;
                return 1;
            }
           return insert(cursor.right,a);
        }
    }
       static  class  Treenode{
        int data;
        int height;
        Treenode parent;
        Treenode left;
        Treenode right;
        private Treenode(int input){
            this.data = input;
        }

        @Override
        public String toString() {
            return "Treenode{" +
                    "data=" + data +
                    '}';
        }
    }

}
