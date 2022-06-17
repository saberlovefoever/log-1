package org.whh.bz.dao;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.whh.bz.entity.Website;

import java.util.List;
@Repository
public interface WebsiteDao {
    List<Website> getAllWebsite() ;

    List addObject();
}
