package cn.wenhe9.ggkt.live.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/28
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "mtcloud")
public class MTCloudAccountProperties {
    private String openId;

    private String openToken;
}
