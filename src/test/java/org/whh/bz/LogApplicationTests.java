package org.whh.bz;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.whh.bz.dao.ImgDao;
import org.whh.bz.dao.UserMapper;
import org.whh.bz.dao.WebsiteDao;
import org.whh.bz.entity.Img;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LogApplicationTests {
    @Resource
    ImgDao imgDao;
    @Resource
    DataSource dataSource;
    @Resource
    UserMapper userMapper;
    @Resource
    WebsiteDao websiteDao;
    @Test
    public void contextLoads() {
        List img = websiteDao.getAllWebsite();
        log.error(img.toString());
    }
}
