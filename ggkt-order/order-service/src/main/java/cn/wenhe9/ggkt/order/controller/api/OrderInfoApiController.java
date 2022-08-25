package cn.wenhe9.ggkt.order.controller.api;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.order.service.OrderInfoService;
import cn.wenhe9.ggkt.order.vo.OrderFormVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@Api(tags = "创建订单接口")
@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoApiController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 生成订单
     */
    @ApiOperation("生成订单")
    @PostMapping("/submit")
    public ResultResponse<Long> submitOrder(@RequestBody OrderFormVo orderFormVo) {
        Long orderId = orderInfoService.submitOrder(orderFormVo);
        return ResultResponse.success(orderId);
    }

}
