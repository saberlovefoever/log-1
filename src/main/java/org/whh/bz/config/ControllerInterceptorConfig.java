package org.whh.bz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.whh.bz.interceptor.LoginInterceptor;

import javax.annotation.Resource;

@Configuration
public class ControllerInterceptorConfig implements WebMvcConfigurer {
@Resource
    private LoginInterceptor l;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(l).addPathPatterns("/abc");
//    }

//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("index");
//    }

 /*   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:resources/static/");
    }*/
 /*   @Bean
    public InternalResourceViewResolver demo(){
        InternalResourceViewResolver a = new InternalResourceViewResolver();
        a.setPrefix("/WEB-INF/templates/");
        a.setSuffix(".html");
        a.setViewClass(JstlView.class);
        return a;
    }*/


}
