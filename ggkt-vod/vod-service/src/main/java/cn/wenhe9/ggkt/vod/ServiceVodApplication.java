package cn.wenhe9.ggkt.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author DuJinliang
 * 2022/08/14
 */
@SpringBootApplication
@ComponentScan(basePackages = "cn.wenhe9.ggkt.*")
public class ServiceVodApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceVodApplication.class, args);
    }
}
