package cn.wenhe9.ggkt.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/16
 */
@Component
public class ConstantOssPropertiesUtils implements InitializingBean {
    @Value("${qiniu.oss.access_key}")
    private String accessKey;

    @Value("${qiniu.oss.secret_key}")
    private String secretKey;

    @Value("${qiniu.oss.bucket}")
    private String bucket;

    public static String ACCESS_KEY;

    public static String SECRET_KEY;

    public static String BUCKET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY = accessKey;
        SECRET_KEY = secretKey;
        BUCKET = bucket;
    }
}
