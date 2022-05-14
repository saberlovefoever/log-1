package org.whh.bz.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.whh.bz.entity.WxUser;
import org.whh.bz.enums.UserStatus;
import org.whh.bz.exception.NPException;
import org.whh.bz.service.RedisUserService;
import org.whh.bz.service.UserService;
import org.whh.bz.utils.UUIDGenerator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WeChatLoginController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUserService redisUserService;

    private  static final String APPSECRET = "0f21d5a489c6395b3f6a33dedd73721e";
    private  static final String APPID = "wx1de4bce0ab328e44";
    private  static final String SCOPE = "snsapi_userinfo";
    private  static final String REDIRECT_URI ="https://42s4204l85.goho.co/wxTest";
    private static final String QR_CODE_AUTH_PATH = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

    /**
     * 生成带微信验证带参数二维码
     *
     */
//    https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
    @RequestMapping(value = "/getQRcode/{flag}",method = RequestMethod.GET)
    private void getQRcode(HttpServletResponse resp, HttpServletRequest req,
                           @PathVariable(value = "flag",required = false) String flag
                           ) throws IOException, WriterException, NoSuchAlgorithmException {
        if (flag ==null)
            throw new NPException();
        resp.setHeader("Pragma", "No-cache"); // 设置响应头信息，告诉浏览器不要缓存此内容
        resp.setHeader("Cache-Control", "no-cache");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("image/*,text/*");
         /*加密放入cookie*/

        /*"google ZXing"*/
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+APPID+"&redirect_uri="+REDIRECT_URI+"&response_type=code&scope=snsapi_userinfo&state="+flag+"#wechat_redirect";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, 350, 350);
        MatrixToImageWriter.writeToStream(bitMatrix,"png",resp.getOutputStream());
    }
    /**
     *  扫码登录认证回调地址
     *  带了用户参数和二维码的state参数
     *  存入db、缓存 通过页面js轮询？checkLogin到结果后返回
    */
    @RequestMapping(value = {"/wxTest"},method = RequestMethod.GET)
    @ResponseBody
    private String auth (@RequestParam("code")String code,
                         @RequestParam("state")String state   /*getQRcode方法提交的参数*/){
        String use_code_getAccessToken_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx1de4bce0ab328e44&secret=0f21d5a489c6395b3f6a33dedd73721e&code="+code+"&grant_type=authorization_code";
        String str = restTemplate.getForEntity(use_code_getAccessToken_url,String.class).getBody();
        JSONObject jsonObject= JSON.parseObject(str);
        String url_getUserDetails = "https://api.weixin.qq.com/sns/userinfo?access_token="+jsonObject.getString("access_token")+"&openid="+jsonObject.getString("openid")+"&lang=zh_CN";
        String str_getUserDetails = restTemplate.getForEntity(url_getUserDetails,String.class).getBody();
        JSONObject jsonObject_getUserDetails= JSON.parseObject(str_getUserDetails);
        System.out.println(jsonObject_getUserDetails);
        WxUser realUser  = new WxUser(jsonObject_getUserDetails.getString("nickname"), jsonObject_getUserDetails.getString("openid"));
        //查询数据库
        WxUser ifHaveUser =null;
        int flag = 0;
        ifHaveUser = redisUserService.findUser(state);
        if (ifHaveUser==null) {
            flag = redisUserService.addUser(realUser,state);
        }
        return ifHaveUser!=null||flag>0?"<script>alert(\"OK\")</script>":"<script>alert(\"ERROR\")</script>";
    }

    /*载入页面  登录检查*/
    @RequestMapping(value = "/checkLogin",method = RequestMethod.GET)
    @ResponseBody
    private  String  checkLogin(@CookieValue(value = "sessionID",required = false,defaultValue = "0")String sessionID,
                                HttpServletResponse resp,
                                @RequestParam(value = "ssid",required = false,defaultValue = "0")String ssid){
        Map<String,Object> map = new  HashMap();
        if ("0".equals(sessionID)){
            sessionID = UUIDGenerator.getUUIDStr();
           Cookie cookie = new Cookie("sessionID", sessionID);
           cookie.setMaxAge(1000*60*60*24);
           resp.addCookie(cookie);

       }
        WxUser wxUser = redisUserService.findUser(sessionID);
        map.put("sessionID",sessionID);
        if(wxUser==null){
            map.put("status", UserStatus.NOT_LOGIN.getCode());
            map.put("msg", UserStatus.NOT_LOGIN.getMsg());
            return JSON.toJSONString(map);
        }
        //会员业务。。会员等级。。是否到期。。省略
        map.put("status", UserStatus.NORMAL_USER.getCode());
        map.put("msg", UserStatus.NORMAL_USER.getMsg());
        map.put("user", wxUser);

        return JSON.toJSONString(map);
    }


    /*   ↓↓↓  not use*/
    private String  getCodeSrc() throws UnsupportedEncodingException {
        String get_code_request_url ="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+URLEncoder.encode(this.getTicket(), "utf-8");
        String o = restTemplate.getForEntity(get_code_request_url, String.class).getBody();
        System.out.println(o);
        return o;
    }
    private String  getTicket(){
        String get_code_url ="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+ this.get_access_token();
        String body = "{\"expire_seconds\": 604800, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": 123}}}";
        HttpHeaders httpHeaders = new HttpHeaders();
        MediaType mediaType = new MediaType("json","utf-8");
        httpHeaders.setContentType(mediaType);
        HttpEntity httpEntity = new HttpEntity(body,httpHeaders);
        Map  getCodeMap = restTemplate.postForEntity(get_code_url,httpEntity,Map.class).getBody();
        System.out.println(getCodeMap.get("url"));
        return getCodeMap.get("ticket").toString();
    }
    private  String  get_access_token(){
        //可以的话用@Value 导入appid secret
        //或者用 restTemplate自带方法（带map的）
        //access_token 有过期时间可以考虑放入redis，腾讯端和redis同时失R效
        String get_access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx1de4bce0ab328e44&secret=0f21d5a489c6395b3f6a33dedd73721e";
        //就不做空处理了、、
        Map map = restTemplate.getForEntity(get_access_token_url,Map.class).getBody();
        return  map.get("access_token").toString();
    }
}
