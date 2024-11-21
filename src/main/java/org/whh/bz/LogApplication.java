package org.whh.bz;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.Arrays;

@SpringBootApplication
@MapperScan("org.whh.bz.dao")
public class LogApplication {


    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class, args);

    }

    @Bean
    public CommandLineRunner c(ApplicationContext a) {
        return  args -> {
            String[] names = a.getBeanDefinitionNames();
            Arrays.sort(names);
            for(String s : names) {
                System.out.println(s);
            }
        };
    }

}

