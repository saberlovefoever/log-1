package org.whh.bz.demo;

import org.apache.ibatis.javassist.runtime.Inner;

public  class OutClass {

    static int out =10;
    static class Inner{
        int in = 2;
        int getOut =out;
        static int staticIn = 3;
    }

    int getInner = new Inner().in;

    int getStaticInner = Inner.staticIn;
}
