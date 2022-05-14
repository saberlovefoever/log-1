package org.whh.bz.service.impl;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

public class ShortMessageServiceImpl {
    /**
     *
     * @param phoneNumber  电话号码
     * @param title  公众号名字
     * @param code 验证码
     */
   public  boolean sendSms(String phoneNumber,String title,String code[]){
       try{
           SmsSingleSenderResult result = new SmsSingleSender(1400563753, "f2d1c4fac5932cb538be55481afa5de3")
                   .sendWithParam("86", phoneNumber,1089709, code,"桃礼满天下公众号","","");
            System.out.println(result) ;
            return  result.result==0;
       }
       catch (HTTPException e) {
            e.printStackTrace();
       } catch (JSONException e) {
            e.printStackTrace();
       } catch (IOException e) {
            e.printStackTrace();}
       return false;
   }
}


