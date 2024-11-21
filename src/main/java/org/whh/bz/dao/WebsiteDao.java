package org.whh.bz.dao;


import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.whh.bz.entity.Website;

import java.util.List;
public interface WebsiteDao {
    @Select("select name,address,type,retired from website;")
    List<Website> getAllWebsite() ;

}
