package cn.wenhe9.ggkt.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/19
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "wenhe9.thread")
public class ThreadPoolConfigProperties {
    /**
     * 核心线程数量
     */
    private Integer coreSize;

    /**
     * 救急线程数量 = 最大线程数量 - 核心线程数量
     */
    private Integer maxSize;

    /**
     * 救急线程没有任务的存活时间
     */
    private Integer keepAliveTime;
}
