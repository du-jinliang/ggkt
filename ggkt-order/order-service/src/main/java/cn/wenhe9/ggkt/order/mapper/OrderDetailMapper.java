package cn.wenhe9.ggkt.order.mapper;

import cn.wenhe9.ggkt.order.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单明细 订单明细 Mapper 接口
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
    /**
     * 根据订单id查询课程名称
     */
    String queryCourseNameByOrderId(@Param("id") long id);
}
