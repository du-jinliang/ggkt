package cn.wenhe9.ggkt.order.service.impl;

import cn.wenhe9.ggkt.order.entity.OrderInfo;
import cn.wenhe9.ggkt.order.mapper.OrderInfoMapper;
import cn.wenhe9.ggkt.order.service.OrderDetailService;
import cn.wenhe9.ggkt.order.service.OrderInfoService;
import cn.wenhe9.ggkt.order.vo.OrderInfoListVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 订单表 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Resource
    private OrderDetailService orderDetailService;

    @Override
    public Map<String, Object> getOrderInfoPage(long current, long limit, OrderInfoQueryVo orderInfoQueryVo) {

        Page<OrderInfoListVo> pageParam = new Page<>(current, limit);

        Page<OrderInfoListVo> orderInfoPage = baseMapper.queryAllByPage(pageParam, orderInfoQueryVo);

        long totalCount = orderInfoPage.getTotal();
        long pageCount = orderInfoPage.getPages();
        List<OrderInfoListVo> records = orderInfoPage.getRecords();

        Map<String, Object> map = new HashMap<>();
        map.put("total", totalCount);
        map.put("pageCount", pageCount);
        map.put("records", records);

        return map;
    }
}
