package org.whh.bz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class MyRedisMessageListener implements MessageListener {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println(redisTemplate.getValueSerializer().deserialize(message.getBody()));
        System.out.println(redisTemplate.getStringSerializer().deserialize(message.getChannel()));
        System.out.println(new String(pattern));
    }



}
