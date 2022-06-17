package org.whh.bz.service.impl;

import io.lettuce.core.RedisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.whh.bz.entity.WxUser;
import org.whh.bz.enums.UserStatus;
import org.whh.bz.service.RedisUserService;

import javax.annotation.Resource;
import java.time.Duration;

@Slf4j
@Service
public class RedisUserServiceImpl implements RedisUserService {

    @Resource
    private RedisTemplate<String,WxUser> redisTemplate;

    @Override
    public WxUser findUser(String sessionID) {

        return (WxUser) redisTemplate.opsForHash().get(sessionID+"User", (sessionID+"User").hashCode());
    }
    @Override
    public int addUser(WxUser wxUser,String state) {
        try {
            redisTemplate.opsForHash().put(state+"User",(state+"User".hashCode()),wxUser);
            redisTemplate.expire(state, Duration.ofMinutes(2));
        }catch (Exception e){
            System.err.println("wxUser存入失败");
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public boolean addAnonymousUser(String anonymous) {
        try {
            redisTemplate.opsForHash().put(anonymous,anonymous.hashCode(), UserStatus.NOT_LOGIN.getCode());
            //2min超时  2*60*1000
            redisTemplate.expire(anonymous,Duration.ofMinutes(1));
        }catch (RedisException e){
            log.error("redis 存入匿名用户失败");
            return false;
        }
        return true;
    }
}
