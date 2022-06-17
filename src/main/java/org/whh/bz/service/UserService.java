package org.whh.bz.service;

import org.whh.bz.entity.WxUser;

public interface UserService {

     WxUser findByOpenid(String s);

     int insert(WxUser wxUser);
}
