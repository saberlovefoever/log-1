package org.whh.bz.demo;

import org.junit.Test;

import static java.lang.Integer.max;

/*实现平衡树*/
public class Pinghengshu extends Chazhaoshu{
    /*继承查找树*/
    /*如何实现自平衡？
    *   要求最远路径和最近路径差距最高为1  ===>>从下往上计算深度值，根据深度值判断是否进行
    *      1、遍历计算各个节点深度  前序遍历
    *      2、从下往上根据深度，左旋、右旋操作
    * */
    public Pinghengshu(){
        super();
    }
    @Test
    public void getDeep(){
        Pinghengshu p = new Pinghengshu();      //                  0
        p.add(0);p.add(3);p.add(1);              //              -3   3
        p.add(4);p.add(-3);p.add(-2);             //              -2 1  4
        System.out.println(getTreeHeight(p.root.right)); //           2
        System.out.println(getTreeHeight(p.root.left));
        System.out.println(ifBalence(p.root));
        System.out.println(ifBalence(p.root));
    }
    void getBalence(){

    }
    boolean ifBalence(Treenode t){
        if (t==null)return true;
       int l = getTreeHeight(t.left);
       int r = getTreeHeight(t.right);
       int result = l-r;
       if(result>=-1&&result<=1)
           return ifBalence(t.left)&&ifBalence(t.right);
        System.out.println("不平衡点是 "+t.data+" 平衡因子是-> "+result);

        getbalence(t,result);

       return false;
    }
    void getbalence(Treenode t,int result){
        /*ll*/
      if (result>0){
          if(t.right==null);
          Treenode cursorP = t.parent;
          cursorP.left = t.left;
          cursorP.left.right = t;
          t.parent =  cursorP.left;
          t.left = null;
      }
        /*lr*/
        /*rl*/

        /*rr*/
    }
    int getTreeHeight(Treenode treenode){
        if(treenode==null)
            return 0 ;
        int a = max( getTreeHeight(treenode.left), getTreeHeight(treenode.right))+1;
        treenode.height = a;
        return a;
    }

}
