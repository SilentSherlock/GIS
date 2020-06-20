package com.programs.gis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.programs.gis.dao")
public class GisApplication extends SpringBootServletInitializer {

    /*在线部署访问*/
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(GisApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GisApplication.class, args);
    }

}
