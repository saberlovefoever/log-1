package org.whh.bz.demo;

import com.google.zxing.Writer;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public class ReaderDemo {
    @Test
    public void ReaderDemo_01() throws IOException {
        Reader r = new BufferedReader(new FileReader("C:\\Users\\梁大女神呦\\Desktop\\demo.txt"));
        BufferedWriter w = new BufferedWriter(new FileWriter("C:\\Users\\梁大女神呦\\Desktop\\demo_copy.txt"));
        char[] buffer = new char[1024];
        System.out.println(buffer);
        r.transferTo(w);
        r.close();
        w.close();
    }
}
