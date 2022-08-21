package cn.wenhe9.ggkt.order.service;

import cn.wenhe9.ggkt.order.entity.OrderInfo;
import cn.wenhe9.ggkt.order.vo.OrderInfoQueryVo;
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
}
