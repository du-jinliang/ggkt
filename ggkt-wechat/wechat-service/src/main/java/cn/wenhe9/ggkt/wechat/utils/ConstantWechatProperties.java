package cn.wenhe9.ggkt.wechat.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/22
 */
@Component
public class ConstantWechatProperties implements InitializingBean {

    @Value("${wechat.mpAppId}")
    private String appId;

    @Value("${wechat.mpAppSecret}")
    private String appSecret;

    public static String APP_ID;

    public static String APP_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        APP_SECRET = this.appSecret;
    }
}
