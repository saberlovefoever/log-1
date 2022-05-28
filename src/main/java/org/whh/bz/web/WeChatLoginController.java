package org.whh.bz.web;


import com.alibaba.fastjson.JSON;
import com.google.zxing.WriterException;
import lombok.NonNull;
import org.json.JSONObject;
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
import org.whh.bz.exceptions.UserSessionException;
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
    @RequestMapping("/QRCode_login")
    private String getQRCodePage(HttpServletResponse resp, HttpServletRequest req,
                                 @Nullable@CookieValue("tempLoginSession")String tempLoginSession
                           ) {
        if (tempLoginSession ==null)
            throw new UserSessionException();
       return "code";
    }

    /**
     *  图片通过流写入页面并th解析
     * @param resp
     * @param sessionID
     * @throws IOException
     * @throws WriterException
     */
    @RequestMapping(value = "/showQRCode",method = RequestMethod.GET)
    private void getQRCode(HttpServletResponse resp,
                             @Nullable @CookieValue(name = "tempLoginSession")String sessionID
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
}
