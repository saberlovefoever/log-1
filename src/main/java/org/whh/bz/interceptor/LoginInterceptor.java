package org.whh.bz.interceptor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String,Object> defaultRedisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      /* 从cookie获取用户记录*/
        Cookie[] cookies = request.getCookies();
        String s = cookies[0].getValue();
        /*去redis中查找 token redis业务要做空指针处理*/
        Object si = defaultRedisTemplate.opsForHash().get(s,s);
       /* 写法要写的讲究些，null不可能有equals方法*/
        /*自定义处理器  ==>   Object handler */
        if(!s.equals(si)){
            response.sendRedirect("WEB-INF/jsp/error.jsp");
            return false;
        }
        return  true;
    }
}
