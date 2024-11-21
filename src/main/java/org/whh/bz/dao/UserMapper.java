package org.whh.bz.dao;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.whh.bz.entity.WxUser;
public interface UserMapper {
    @Select("SELECT wx_name FROM wx_user WHERE wx_openid =#{openid};")
    WxUser findByOpenid(String openid);

    @Insert("insert into wx_user value (#{wxUser.wxName},#{wxUser.wxOpenid})")
    int add(WxUser wxUser);
}
