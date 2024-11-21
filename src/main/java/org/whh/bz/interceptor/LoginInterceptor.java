package org.whh.bz.interceptor;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class LoginInterceptor implements HandlerInterceptor {
//    @Resource
//    private RedisUserServiceImpl redisUserService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Cookie[] cookie = request.getCookies();
//        boolean mark = false;
//        String tempLoginSession = null;
//        if (cookie==null){
//            response.sendRedirect("/QRCode_login");
//            return false;}
//        for (Cookie s : cookie) {
//            if("templogCodeSession".equals(s.getName()))
//            tempLoginSession = s.getValue();
//        }
//        if (redisUserService.findUser(tempLoginSession)==null){
//            response.sendRedirect("/QRCode_login");
//            return false;
//        }
//        else {
//            return true;

//        }
        return true;
    }
}
