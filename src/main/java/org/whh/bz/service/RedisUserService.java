package org.whh.bz.service;


import org.springframework.stereotype.Service;
import org.whh.bz.entity.WxUser;

public interface RedisUserService {
    WxUser findUser(String sessionId) ;

    int addUser(WxUser wxUser,String state);

    boolean addAnonymousUser(String anonymous);
}
