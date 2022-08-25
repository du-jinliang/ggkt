package cn.wenhe9.ggkt.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/20
 */
@Component
public class ConstantVodProperties implements InitializingBean {

    @Value("${tecent.vod.appId}")
    private String appId;

    @Value("${tecent.vod.accessKey}")
    private String accessKey;

    @Value("${tecent.vod.secretKey}")
    private String secretKey;

    @Value("${tecent.vod.procedure}")
    private String procedure;

    @Value("${tecent.vod.region}")
    private String region;

    public static String APP_ID;

    public static String ACCESS_KEY;

    public static String SECRET_KEY;

    public static String PROCEDURE;

    public static String REGION;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        ACCESS_KEY = this.accessKey;
        SECRET_KEY = this.secretKey;
        PROCEDURE = this.procedure;
        REGION = this.region;
    }
}
