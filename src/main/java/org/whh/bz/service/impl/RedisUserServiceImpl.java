package org.whh.bz.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.whh.bz.entity.WxUser;
import org.whh.bz.service.RedisUserService;

import javax.annotation.Resource;
import java.time.Duration;

@Service
public class RedisUserServiceImpl implements RedisUserService {

    @Resource
    private RedisTemplate<String,WxUser> redisTemplate;

    @Override
    public WxUser findUser(String sessionID) {
           return (WxUser) redisTemplate.opsForHash().get(sessionID, sessionID);
    }
    @Override
    public int addUser(WxUser wxUser,String state) {
        try {
            redisTemplate.opsForHash().put(state,state,wxUser);
            redisTemplate.expire(state, Duration.ofSeconds(60*5));
        }catch (Exception e){
            System.err.println("wxUser存入失败");
            e.printStackTrace();
            return 0;
        }
        return 1;
    }
}
