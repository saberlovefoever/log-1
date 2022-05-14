package org.whh.bz.service;


import org.whh.bz.entity.WxUser;

public interface RedisUserService {
    WxUser findUser(String sessionId) ;

    int addUser(WxUser wxUser,String state);
}
