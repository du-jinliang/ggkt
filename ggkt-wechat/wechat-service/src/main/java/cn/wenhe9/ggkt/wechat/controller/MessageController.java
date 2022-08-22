package cn.wenhe9.ggkt.wechat.controller;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.wechat.service.MessageService;
import cn.wenhe9.ggkt.wechat.utils.SHA1;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/22
 */

@Slf4j
@RestController
@RequestMapping("/api/wechat/message")
public class MessageController {

    @Resource
    private MessageService messageService;

    /**
     * 查询订单消息
     */
    @GetMapping("/pushPayMessage")
    public ResultResponse<Void> pushPayMessage() throws WxErrorException {
        messageService.pushPayMessage(1L);
        return ResultResponse.success();
    }

    /**
     * 服务器有效性验证
     */
    @GetMapping
    public String verifyToken(HttpServletRequest request) {
        return messageService.verifyToken(request);
    }

    /**
     * 接收微信服务器发送来的消息
     */
    @PostMapping
    public String receiveMessage(HttpServletRequest request) throws Exception {
        return messageService.receiveMessage(request);
    }

}
