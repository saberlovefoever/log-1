package org.whh.bz.demo;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;

public class InputStreamDemo {
    @Test
    public  void s()throws IOException {
        InputStream is = new FileInputStream(new File("C:\\Users\\梁大女神呦\\Desktop\\pwds.txt"));
        System.out.println(is.available());
//           int a =is.read();
//        System.out.println(a);
//        InputStreamReader fr = new InputStreamReader(
//                new FileInputStream(
//                        "C:\\Users\\梁大女神呦\\Desktop\\demo.txt"),"GBK"
//        );
//        BufferedReader br = new BufferedReader(fr);
//        System.out.println(fr.getEncoding());
//        String a = br.readLine();
//        System.out.println(br);
//        fr.close();
//        is.close();
    }
}
