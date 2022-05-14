package org.whh.bz.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.whh.bz.entity.Website;

import java.util.List;
@Mapper
@Repository
public interface WebsiteDao {
    List<Website> getAllWebsite() ;
}
