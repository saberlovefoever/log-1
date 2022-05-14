package org.whh.bz.demo;

public class Fatherschild extends Father{
    @Override
    void m1(){
        super.m1();
        System.out.println("C");
    }
    void m2(){
        super.m2();
        System.out.println("D");
    }

    public static void main(String[] args) {
        Father f  = new Fatherschild();
        f.m1();
    }
}
