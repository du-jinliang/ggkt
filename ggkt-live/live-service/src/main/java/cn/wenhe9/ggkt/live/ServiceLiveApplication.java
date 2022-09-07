package cn.wenhe9.ggkt.live;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author DuJinliang
 * 2022/08/27
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages = "cn.wenhe9.ggkt.*")
public class ServiceLiveApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceLiveApplication.class, args);
    }
}
