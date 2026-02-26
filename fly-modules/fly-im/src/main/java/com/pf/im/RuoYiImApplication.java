package com.pf.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.pf.common.security.annotation.EnableCustomConfig;
import com.pf.common.security.annotation.EnableRyFeignClients;

/**
 * 实时通信(IM)模块启动类
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiImApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiImApplication.class, args);
    }
}
