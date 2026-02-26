package com.pf.consultation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.pf.common.security.annotation.EnableCustomConfig;
import com.pf.common.security.annotation.EnableRyFeignClients;

/**
 * 远程医疗咨询模块启动类
 */
@EnableCustomConfig
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiConsultationApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(RuoYiConsultationApplication.class, args);
    }
}
