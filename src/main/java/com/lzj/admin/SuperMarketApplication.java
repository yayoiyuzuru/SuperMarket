package com.lzj.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动器
 * @author TianTian
 * @date 2022/1/5 23:01
 */

@SpringBootApplication

@MapperScan("com.lzj.admin.mapper")
public class SuperMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuperMarketApplication.class,args);


    }
}
