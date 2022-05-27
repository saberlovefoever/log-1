package org.whh.bz.web;


import com.alibaba.fastjson.JSON;
import com.google.zxing.WriterException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.whh.bz.entity.WxUser;
import org.whh.bz.enums.UserStatus;
import org.whh.bz.exceptionController.UserSessionException;
import org.whh.bz.service.RedisUserService;
import org.whh.bz.service.UserService;
import org.whh.bz.utils.UUIDGenerator;
import org.whh.bz.utils.WeChatUtils;
import reactor.util.annotation.Nullable;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WeChatLoginController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private UserService userService;
    @Resource
    private RedisUserService redisUserService;

    /**
     * 生成微信验证带参数二维码
     */
    @RequestMapping(value = "/getQRCodePage",method = RequestMethod.GET)
    private String getQRCodePage(HttpServletResponse resp, HttpServletRequest req,
                          @Nullable @CookieValue(name = "sessionID")String sessionID
                           ) throws IOException, WriterException {
        if (sessionID ==null)
            throw new UserSessionException();
       return "code";
    }


    @RequestMapping(value = "/getQRCode",method = RequestMethod.GET)
    private void getQRCode(HttpServletResponse resp, HttpServletRequest req,
                             @Nullable @CookieValue(name = "sessionID")String sessionID
    ) throws IOException, WriterException {
        WeChatUtils.writeQRCodeToStream(sessionID,resp);
    }
    /**
     *  扫码登录认证第一次回调地址
     *  redirect_uri/?code=CODE&state=STATE。
     *  code说明 ： code作为换取access_token的票据，
     *  每次用户授权带上的 code 将不一样，
     *  code只能使用一次，5分钟未被使用自动过期。
    */
    @RequestMapping(value = {"/wxTest"},method = RequestMethod.GET)
    @ResponseBody
    private String auth(@RequestParam("code")String code,
                        @RequestParam("state")String state,
                        HttpServletRequest req
                        ) throws UnsupportedEncodingException {
        JSONObject tokenJSonObject = WeChatUtils.getWeChatToken(code,state,req);
        JSONObject jsonObject = WeChatUtils.getWeChatUserinfo(tokenJSonObject.getString("access_token"),
                tokenJSonObject.getString("openid"),req);
        WxUser realUser  = new WxUser(
                new String(jsonObject.getString("nickname").
                        getBytes("ISO-8859-1"),"utf-8"),
                jsonObject.getString("openid"));
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
    private  String  checkLogin(@CookieValue(value = "sessionID",required = false,defaultValue = "0" )String sessionID,
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
        map.put("userName", wxUser.getWxName());
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
