package org.whh.bz.demo;

public class CompierDemo {
    volatile static int s = 1;

    public static void main(String[] args) {
        s+=2;
    }
}
