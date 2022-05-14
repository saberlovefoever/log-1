package org.whh.bz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.whh.bz.dao.UserMapper;
import org.whh.bz.entity.WxUser;
import org.whh.bz.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userDao;
    @Override
    public WxUser findByOpenid(String s) {
        return userDao.findByOpenid(s);
    }

    @Override
    public int insert(WxUser wxUser) { return userDao.add(wxUser); }

}
