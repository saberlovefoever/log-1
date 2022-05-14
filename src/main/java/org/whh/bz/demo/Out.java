package org.whh.bz.demo;

import java.util.HashMap;

public class Out {
    static int inner;
    int s;
    public static void main(String[] args) {
        Out a = new Out();
       Object n = a.getClass().getClassLoader();
        Object o = a.getClass().getClassLoader();
        System.out.println(n.hashCode());
        System.out.println(o.hashCode());
        HashMap s =new HashMap();
        s.put("1","2");
        System.out.println(s.getClass().getClassLoader());
    }
}
