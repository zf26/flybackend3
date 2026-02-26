package com.pf.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.pf.common.security.annotation.EnableCustomConfig;
import com.pf.common.security.annotation.EnableRyFeignClients;

/**
 * 搜索服务启动类
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class FlySearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlySearchApplication.class, args);
        System.out.println("(♥◠‿◠)ノ゛  搜索服务启动成功   ლ(´ڡ`ლ)゛");
    }
}

