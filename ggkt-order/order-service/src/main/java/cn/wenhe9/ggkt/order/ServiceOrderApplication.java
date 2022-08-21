package cn.wenhe9.ggkt.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DuJinliang
 * 2022/08/21
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = "cn.wenhe9.ggkt.*")
public class ServiceOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}
