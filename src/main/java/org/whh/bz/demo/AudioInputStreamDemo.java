package org.whh.bz.demo;

import lombok.SneakyThrows;
import org.junit.Test;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.util.Properties;

public class AudioInputStreamDemo {
    @SneakyThrows
    @Test
    public  void test() {
        AudioInputStream ai = AudioSystem.getAudioInputStream(
                new File("C:\\Users\\梁大女神呦\\Downloads\\紗霧＿起きなさいよ.wav"));
        System.out.println( ai.getFormat());
        ai.close();
        Properties s = System.getProperties();
        System.out.println(s);
    }

}
