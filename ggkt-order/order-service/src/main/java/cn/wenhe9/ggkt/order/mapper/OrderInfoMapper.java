package cn.wenhe9.ggkt.order.mapper;

import cn.wenhe9.ggkt.order.entity.OrderInfo;
import cn.wenhe9.ggkt.order.vo.OrderInfoListVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单表 订单表 Mapper 接口
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    Page<OrderInfoListVo> queryAllByPage(Page<OrderInfoListVo> page, @Param("queryVo") OrderInfoQueryVo queryVo);
}
