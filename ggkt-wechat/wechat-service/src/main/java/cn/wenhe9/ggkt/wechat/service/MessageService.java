package cn.wenhe9.ggkt.wechat.service;

import me.chanjar.weixin.common.error.WxErrorException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author DuJinliang
 * 2022/08/22
 */
public interface MessageService {
    /**
     * 服务器有效性验证
     */
    String verifyToken(HttpServletRequest request);

    /**
     * 接收微信服务器发送来的消息
     */
    String receiveMessage(HttpServletRequest request) throws Exception;

    /**
     * 查询订单消息
     */
    void pushPayMessage(long id) throws WxErrorException;
}
