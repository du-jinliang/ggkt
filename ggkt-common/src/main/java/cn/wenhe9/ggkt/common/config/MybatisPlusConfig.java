package cn.wenhe9.ggkt.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author DuJinliang
 * 2022/08/14
 */
@Configuration
@MapperScan("cn.wenhe9.ggkt.*.mapper")
public class MybatisPlusConfig {
}
