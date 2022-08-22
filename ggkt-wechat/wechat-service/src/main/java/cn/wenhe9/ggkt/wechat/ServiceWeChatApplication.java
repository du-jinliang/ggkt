package cn.wenhe9.ggkt.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DuJinliang
 * 2022/08/22
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "cn.wenhe9.ggkt.*")
public class ServiceWeChatApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceWeChatApplication.class, args);
    }
}
