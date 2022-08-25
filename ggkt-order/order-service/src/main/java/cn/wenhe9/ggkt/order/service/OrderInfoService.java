package cn.wenhe9.ggkt.order.service;

import cn.wenhe9.ggkt.order.entity.OrderInfo;
import cn.wenhe9.ggkt.order.vo.OrderFormVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoQueryVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 订单列表
     */
    Map<String, Object> getOrderInfoPage(long current, long limit, OrderInfoQueryVo orderInfoQueryVo);

    /**
     * 生成订单
     */
    Long submitOrder(OrderFormVo orderFormVo);

    /**
     * 根据订单号获取订单信息
     */
    OrderInfoVo findOrderInfoById(long id);

    /**
     * 更新订单状态
     */
    void updateOrderStatus(String outTradeNo);
}
