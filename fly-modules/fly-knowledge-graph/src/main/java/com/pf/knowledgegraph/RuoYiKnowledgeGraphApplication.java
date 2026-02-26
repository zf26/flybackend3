package com.pf.knowledgegraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import com.pf.common.security.annotation.EnableRyFeignClients;

/**
 * 知识图谱服务启动类
 * 专门处理用户提交的症状描述
 *
 * @author fly
 */
@EnableDiscoveryClient
@EnableRyFeignClients
@SpringBootApplication
public class RuoYiKnowledgeGraphApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuoYiKnowledgeGraphApplication.class, args);
        System.out.println("知识图谱服务启动成功");
    }
}
