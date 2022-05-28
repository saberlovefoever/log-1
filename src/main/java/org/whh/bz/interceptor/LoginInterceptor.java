package org.whh.bz.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String,String> defaultRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookie = request.getCookies();
        boolean mark = false;
        String tempLoginSession = null;
        for (Cookie s : cookie) {
            "tempLoginSession".equals(s.getName());
            tempLoginSession = s.getValue();
        }
        if (tempLoginSession == null || !(tempLoginSession.equals("123456"))){
            response.sendRedirect("/getQRCodePage");
            return false;
        }
        else {
            return true;
        }
    }
}
