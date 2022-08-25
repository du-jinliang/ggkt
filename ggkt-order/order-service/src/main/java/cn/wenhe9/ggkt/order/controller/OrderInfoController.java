package cn.wenhe9.ggkt.order.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.order.entity.OrderInfo;
import cn.wenhe9.ggkt.order.service.OrderDetailService;
import cn.wenhe9.ggkt.order.service.OrderInfoService;
import cn.wenhe9.ggkt.order.vo.OrderInfoQueryVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
@Api(tags = "订单管理接口")
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 订单列表
     */
    @ApiOperation("订单列表")
    @GetMapping("/list/{current}/{limit}")
    public ResultResponse<Map<String, Object>> listOrderInfo(
        @PathVariable(name = "current") long current,
        @PathVariable(name = "limit") long limit,
        OrderInfoQueryVo orderInfoQueryVo
    ) {
        Map<String, Object> map = orderInfoService.getOrderInfoPage(current, limit, orderInfoQueryVo);
        return ResultResponse.success(map);
    }

    /**
     * 根据订单号获取订单信息
     */
    @ApiOperation("根据订单号获取订单信息")
    @GetMapping("/info/{id}")
    public ResultResponse<OrderInfoVo> findOrderInfoById(@PathVariable(name = "id") long id) {
        OrderInfoVo orderInfoVo = orderInfoService.findOrderInfoById(id);
        return ResultResponse.success(orderInfoVo);
    }
}

