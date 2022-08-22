package cn.wenhe9.ggkt.wechat.config;

import cn.wenhe9.ggkt.wechat.utils.ConstantWechatProperties;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author DuJinliang
 * 2022/08/22
 */
@Component
public class WeChatMpConfig {

    @Resource
    private ConstantWechatProperties constantWechatProperties;

    @Bean
    public WxMpService wxMpService(WxMpConfigStorage wxMpConfigStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage);
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage() {
        WxMpDefaultConfigImpl wxMpDefaultConfig = new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAppId(ConstantWechatProperties.APP_ID);
        wxMpDefaultConfig.setSecret(ConstantWechatProperties.APP_SECRET);
        return wxMpDefaultConfig;
    }
}
