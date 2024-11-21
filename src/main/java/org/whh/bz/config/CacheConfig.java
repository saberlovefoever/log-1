package org.whh.bz.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.whh.bz.entity.Img;
import org.whh.bz.entity.WxUser;

import java.time.Duration;

@Configuration
public class CacheConfig {

//	/*基于注解的spring-cache*/
//	@Bean("cacheManager")
//	public CacheManager cacheManager(RedisTemplate<String, Object> template) {
//	    // 基本配置
//	    RedisCacheConfiguration defaultCacheConfiguration =
//	            RedisCacheConfiguration
//	                    .defaultCacheConfig()
//	                    // 设置key为String
//	                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
//	                    // 设置value 为自动转Json的Object
//	                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class)))
//	                    // 不缓存null
//	                    .disableCachingNullValues()
//	                    // 缓存数据保存1小时
//	                    .entryTtl(Duration.ofHours(1));
//
//	    // 够着一个redis缓存管理器
//	    RedisCacheManager redisCacheManager =
//	            RedisCacheManager.RedisCacheManagerBuilder
//	                    // Redis 连接工厂
//	                    .fromConnectionFactory(template.getConnectionFactory())
//	                    // 缓存配置
//	                    .cacheDefaults(defaultCacheConfiguration)
//	                    // 配置同步修改或删除 put/evict
//	                    .transactionAware()
//	                    .build();
//
//	    return redisCacheManager;
//	}

	/*httpBean*/
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	@Bean
	public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		//默认的是JDK提供http连接，需要的话可以
		// 通过setRequestFactory方法替换为例如Apache HttpComponents、Netty或
		// OkHttp等其它HTTP library。
		factory.setReadTimeout(5000);//单位为ms
		factory.setConnectTimeout(5000);//单位为ms
		return factory;
	}

}
