package cn.wenhe9.ggkt.order.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@Component
public class ConstantWXPayPropertiesUtils implements InitializingBean {

    @Value("${wx.pay.app_id}")
    private String appId;

    @Value("${wx.pay.mch_id}")
    private String mchId;

    @Value("${wx.pay,mch_key}")
    private String mchKey;

    public static String APP_ID;

    public static String MCH_ID;

    public static String MCH_KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        MCH_ID = this.mchId;
        MCH_KEY = this.mchKey;
    }
}
