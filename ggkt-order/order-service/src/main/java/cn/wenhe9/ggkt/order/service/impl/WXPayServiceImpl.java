package cn.wenhe9.ggkt.order.service.impl;

import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.order.config.HttpsClientRequestFactory;
import cn.wenhe9.ggkt.order.service.WXPayService;
import cn.wenhe9.ggkt.order.utils.ConstantWXPayPropertiesUtils;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@Service
public class WXPayServiceImpl implements WXPayService {

    @Override
    public Map<String, Object> createJsApi(String orderNo) {
        // 封装微信支付需要参数，使用map集合
        // 设置参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("appid", ConstantWXPayPropertiesUtils.APP_ID);
        paramMap.put("mch_id", ConstantWXPayPropertiesUtils.MCH_ID);
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        paramMap.put("body", "test");
        paramMap.put("out_trade_no", orderNo);
        paramMap.put("total_fee", "1");
        paramMap.put("spbill_create_ip", "127.0.0.1");
        paramMap.put("notify_url", "http://glkt.atguigu.cn/api/order/wxPay/notify");
        paramMap.put("trade_type", "JSAPI");
        /**
         * 设置参数值当前微信用户openid
         * 实现逻辑：第一步 根据订单号获取userid  第二步 根据userid获取openid
         * 因为当前使用测试号，测试号不支持支付功能，为了使用正式服务号进行测试，使用下面写法
         * 获取 正式服务号微信openid
         * 通过其他方式获取正式服务号openid，直接设置
         */
        paramMap.put("openid", "o3oBv5_WPoWj5y_1nyPejhN66EOc");
        // 通过restTemplate调用微信支付接口
        try {
            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
            HttpHeaders requestHeader = new HttpHeaders();
            requestHeader.setContentType(MediaType.APPLICATION_XML);
            
            String paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantWXPayPropertiesUtils.MCH_KEY);
            HttpEntity<String> requestEntity = new HttpEntity<>(paramXml, requestHeader);
           
            ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/unifiedorder", requestEntity, String.class);

            // 微信支付接口返回数据
            String xml = responseEntity.getBody();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            if(null != resultMap.get("result_code")  && !"SUCCESS".equals(resultMap.get("result_code"))) {
                throw new GgktException(ResultResponseEnum.PAY_ERROR);
            }

            //4、再次封装参数
            Map<String, String> parameterMap = new HashMap<>();
            String prepayId = String.valueOf(resultMap.get("prepay_id"));
            String packages = "prepay_id=" + prepayId;
            parameterMap.put("appId", ConstantWXPayPropertiesUtils.APP_ID);
            parameterMap.put("nonceStr", resultMap.get("nonce_str"));
            parameterMap.put("package", packages);
            parameterMap.put("signType", "MD5");
            parameterMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
            String sign = WXPayUtil.generateSignature(parameterMap, "o3oBv5_WPoWj5y_1nyPejhN66EOc");

            //返回结果
            Map<String, Object> result = new HashMap<>();
            result.put("appId", ConstantWXPayPropertiesUtils.APP_ID);
            result.put("timeStamp", parameterMap.get("timeStamp"));
            result.put("nonceStr", parameterMap.get("nonceStr"));
            result.put("signType", "MD5");
            result.put("paySign", sign);
            result.put("package", packages);

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装参数
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", ConstantWXPayPropertiesUtils.APP_ID);
            paramMap.put("mch_id", ConstantWXPayPropertiesUtils.MCH_ID);
            paramMap.put("out_trade_no", orderNo);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

            //2、设置请求
            RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
            HttpHeaders requestHeader = new HttpHeaders();
            requestHeader.setContentType(MediaType.APPLICATION_XML);

            String paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantWXPayPropertiesUtils.MCH_KEY);
            HttpEntity<String> requestEntity = new HttpEntity<>(paramXml, requestHeader);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity("https://api.mch.weixin.qq.com/pay/orderquery", requestEntity, String.class);
            //3、返回第三方的数据
            String xml = responseEntity.getBody();
            //6、转成Map
            //7、返回
            return WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GgktException(ResultResponseEnum.QUERY_PAY_STATUS_ERROR);
        }
    }
}
