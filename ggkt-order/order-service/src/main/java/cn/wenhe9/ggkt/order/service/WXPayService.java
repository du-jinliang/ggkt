package cn.wenhe9.ggkt.order.service;

import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/25
 */
public interface WXPayService {
    /**
     * 下单小程序支付
     */
    Map<String, Object> createJsApi(String orderNo);

    /**
     * 查询支付状态
     */
    Map<String, String> queryPayStatus(String orderNo);
}
