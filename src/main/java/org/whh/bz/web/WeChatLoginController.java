package org.whh.bz.web;

import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.whh.bz.config.WxMappingJackson2HttpMessageConverter;
import org.whh.bz.entity.Access_token;
import org.whh.bz.entity.UserDetails;
import org.whh.bz.entity.WxUser;
import org.whh.bz.service.UserService;
import org.whh.bz.utils.WeChatUtils;
import reactor.util.annotation.Nullable;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;


@Controller
@Slf4j
public class WeChatLoginController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private UserService userService;

    @GetMapping("/checklogin")
    @ResponseBody
    private void getQRCodePage(HttpServletRequest req,@Nullable@CookieValue String s ) {
       req.setAttribute("status",1);
    }
    /**
     * 生成微信验证带参数二维码
     */
    @RequestMapping("/QRCode_login")
    private String getQRCodePage() {
       return "code";
    }

    /**
     *  图片通过流写入页面并th解析
     * @param resp
     * @throws IOException
     * @throws WriterException
     */
    @RequestMapping(value = "/showQRCode",method = RequestMethod.GET)
    private void getQRCode(HttpServletResponse resp) throws IOException, WriterException {
            String templogCodeSession = UUID.randomUUID().toString();
            Cookie cookie = new Cookie("templogCodeSession",templogCodeSession);
            cookie.setMaxAge(60);
            cookie.setPath("/");
            resp.addCookie(cookie);
            WeChatUtils.writeQRCodeToStream(templogCodeSession,resp);
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
    private String auth(
                        @RequestParam("code")String code,
                        @RequestParam("state")String state,
                        HttpServletRequest req
                        ) throws UnsupportedEncodingException {
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        Access_token obj = restTemplate.
                getForObject(WeChatUtils.getWeChatToken(code,state), Access_token.class);

        assert obj != null;
        UserDetails userDetails = restTemplate.getForObject(WeChatUtils.getWeChatUserinfo(obj.getAccess_token(),obj.getOpenid()),UserDetails.class);
        WxUser realUser  = new WxUser(userDetails.getNickname(),userDetails.getOpenid());
        //查询数据库
//        int flag = redisUserService.addUser(realUser,state);
//        如何通知浏览器用户呢？
//        1、websocket
//        2、轮询
//        3、异步ajax
//        调用消息队列
    return  null;
//            flag>0?"<script>alert('OK');this.close</script>":"<script>alert('ERROR');</script>";
    }
}
