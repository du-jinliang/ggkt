package cn.wenhe9.ggkt.live.config;

import cn.wenhe9.ggkt.live.mtcloud.MTCloud;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author DuJinliang
 * 2022/08/28
 */
@Configuration
public class MTCloudConfig {

    @Bean
    public MTCloud mtCloudClient(MTCloudAccountProperties cloudAccountProperties) {
        return new MTCloud(cloudAccountProperties.getOpenId(), cloudAccountProperties.getOpenToken());
    }

}
