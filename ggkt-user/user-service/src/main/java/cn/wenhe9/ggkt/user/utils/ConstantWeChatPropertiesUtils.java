package cn.wenhe9.ggkt.user.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/**
 * @author DuJinliang
 * 2022/08/24
 */
@Component
public class ConstantWeChatPropertiesUtils implements InitializingBean {

    @Value("${wechat.mpAppId}")
    private String appId;

    @Value("${wechat.mpAppSecret}")
    private String appSecret;

    @Value("${wechat.userInfoUrl}")
    private String userInfoUrl;

    public static String ACCESS_KEY_ID;

    public static String ACCESS_KEY_SECRET;

    public static String USER_INFO_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = this.appId;
        ACCESS_KEY_SECRET = this.appSecret;
        USER_INFO_URL = this.userInfoUrl;
    }
}
