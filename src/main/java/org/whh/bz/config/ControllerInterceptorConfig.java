package org.whh.bz.config;

import org.springframework.boot.web.servlet.filter.OrderedHiddenHttpMethodFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.whh.bz.interceptor.LoginInterceptor;


import javax.annotation.Resource;

@Configuration
public class ControllerInterceptorConfig extends WebMvcConfigurationSupport {
    @Resource
    private LoginInterceptor l;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(l).addPathPatterns("/download/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**","/js/**")
                .addResourceLocations("classpath:/static/","classpath:/static/js");

    }
    @Bean
     public OrderedHiddenHttpMethodFilter hiddenHttpMethodFilter(){
        return  new OrderedHiddenHttpMethodFilter();
    }

}
