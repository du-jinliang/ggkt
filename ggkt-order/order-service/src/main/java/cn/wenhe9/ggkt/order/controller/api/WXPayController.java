package cn.wenhe9.ggkt.order.controller.api;

import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.order.service.OrderInfoService;
import cn.wenhe9.ggkt.order.service.WXPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@RestController
@Api(tags = "微信支付接口")
@RequestMapping("/api/order/wxPay")
public class WXPayController {

    @Resource
    private WXPayService wxPayService;

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 下单小程序支付
     */
    @ApiOperation("下单小程序支付")
    @GetMapping("/createJsApi/{orderNo}")
    public ResultResponse<Map<String, Object>> createJsApi(@PathVariable(name = "orderNo") String orderNo) {
        Map<String, Object> map =  wxPayService.createJsApi(orderNo);
        return ResultResponse.success(map);
    }

    /**
     * 查询支付状态
     */
    @ApiOperation(value = "查询支付状态")
    @GetMapping("/status/{orderNo}")
    public ResultResponse<Object> queryPayStatus(@PathVariable("orderNo") String orderNo) {
        // 根据订单号调用微信接口查询支付状态
        Map<String, String> resultMap =  wxPayService.queryPayStatus(orderNo);
        //出错
        if (resultMap == null) {
            throw new GgktException(ResultResponseEnum.PAY_ERROR);
        }
        //如果成功
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {
            //更改订单状态，处理支付结果
            String outTradeNo = resultMap.get("out_trade_no");
            orderInfoService.updateOrderStatus(outTradeNo);
            return ResultResponse.success().message("支付成功");
        }
        return ResultResponse.success().message("支付中");
    }
}
