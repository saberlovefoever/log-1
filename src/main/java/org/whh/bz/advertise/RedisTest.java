package org.whh.bz.advertise;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring/spring-dao.xml"})
public class RedisTest {
    @Autowired
    private RedisTemplate<String,Advertisement> redisTemplate;
    @Test
    public void saveAdTest(){
      redisTemplate.opsForHash().put("test",1,  this.initAdvertisement());
        System.out.println(redisTemplate.opsForHash().get("test",1));
    }

    private Advertisement initAdvertisement() {
        Template template = new Template(20,"轮播模版","alert('轮播')");
        AdContent adContent_1 = new AdContent(1,"新年图书大促销","books.com","*.jpg",1);
        AdContent adContent_2 = new AdContent(2,"手机专场满2000反60","mobbileephone.com","x.jpg",2);
        List<AdContent> adContentList = new ArrayList<AdContent>();
        adContentList.add(adContent_1);
        adContentList.add(adContent_2);
        Advertisement advertisement = new Advertisement(10001,"home-01",template.getId(),adContentList);
        return advertisement;
    }

}
