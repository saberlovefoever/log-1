package org.whh.bz.utils;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;
import reactor.util.annotation.Nullable;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
@Configuration
public class ImageUtils {



    public  static void writeToReq(HttpServletResponse resp,int id,double factor,String url) throws IOException {
    BufferedImage target =  ImageIO.read(new File(url+id+".jpg"));
    BufferedImage bufferedImage = new BufferedImage((int)(target.getWidth()*factor),(int)(target.getHeight()*factor),BufferedImage.TYPE_INT_RGB);
    bufferedImage.getGraphics().drawImage(target,0,0,(int)(target.getWidth()*factor),(int)(target.getHeight()*factor),null);
    OutputStream o = resp.getOutputStream();
    resp.setContentType("image/jpg");
    resp.setCharacterEncoding("utf-8");
    resp.setHeader("Content-Disposition","attachment;filename="+id+".jpg");
    ImageIO.write(bufferedImage,"jpg",o);
}

    /**
     * dhash
     * 步骤：缩略图  计算灰度  比较差值
     * @return img hash
     */
    public  static  String imgHash(File f) throws IOException {
        Assert.notNull(f,"图片文件为空");
        BufferedImage img = new BufferedImage(9,8,BufferedImage.TYPE_INT_RGB);
        img.getGraphics().drawImage( ImageIO.read(f),0,0,9,8,null);
        int s []=new int[9*8];
        int index = 0;
        for (int i = 0; i < 9; i++) {//count gray
            for (int j = 0; j < 8; j++) {
                int piexl = img.getRGB(i,j);
                int gray = getGray(piexl);
                s[index++] = gray;
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {//height
            for (int j = 0; j < 8; j++) {//width
                if(s[i*9+j]>s[i*9+j+1]){
                    builder.append(1);
                }else {
                    builder.append(0);
                }
            }

        }
        String bin =builder.toString();
        String k = binTOHexHash(bin);
        return k.toUpperCase();
    }

    /**
     *由于没有bit这个类所以没有找到 bintoHexString  这类的方法，
     * 所以麻烦一点转成int在做转换咯。。
     * @param in target string
     * @return
     */

    public static String binTOHexHash(String in){
        String oneByte [] = new String[8];
        int[] a = new int[8];
        for (int i = 0; i <oneByte.length ; i++) {
            oneByte[i] = in.substring(i*8,(i*8+8));
            a[i] = Integer.parseInt(oneByte[i],2);
            oneByte[i] =  Integer.toHexString(a[i]);
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < oneByte.length; i++) {
            buffer.append(oneByte[i]);
        }
        return buffer.toString();
    }
    /**
     * r*77+g*151+b*28
     * r*0.3+g*0.59+b*0.11
     * @param rgb
     * @return
     */
    public static int getGray(int rgb){
        int a = rgb & 0xff000000;
        int r = (rgb>>16) & 0xff;
        int g = (rgb>>8) & 0xff;
        int b = rgb & 0xff;
        rgb = (r*77+g*151+b*28)>>8;
        return a|(rgb<<16)|(rgb<<8)|rgb;
    }

    public static void imageHashIntoDB() throws IOException {
        List<String>  l = new ArrayList();
        List<String>  lm = new ArrayList();
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariDataSource.setUsername("root");
        hikariDataSource.setPassword("123456");
        hikariDataSource.setJdbcUrl("jdbc:mysql://192.168.1.15:3306/whh?characterEncoding=UTF-8&serverTimezone=UTC");
        JdbcTemplate j = new JdbcTemplate(hikariDataSource);
        for (int i = 1; i < 29; i++) {
            System.out.println("pics-"+i+"->");
            String s = imgHash(new File("E:\\ideaProject\\log\\src\\main\\resources\\static\\pics\\"+i+".jpg"));
//            System.out.println(s);
            l.add(s);
            String sa = "update img set img_hash =? where img_id = "+i+";";
            j.update(sa,s);
        }
        for (int i = 1; i < 29; i++) {
            System.out.println("mpics-"+i+"->");
            String s = imgHash(new File("E:\\ideaProject\\log\\src\\main\\resources\\static\\mpics\\"+i+".jpg"));
//            System.out.println(s);
            lm.add(s);
        }
        for (int i = 0; i < l.size(); i++) {
            System.out.println(l.get(i).equals(lm.get(i)));;
        }

            hikariDataSource.close();

    }
}
