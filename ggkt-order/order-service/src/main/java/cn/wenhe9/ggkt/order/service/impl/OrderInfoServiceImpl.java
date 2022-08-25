package cn.wenhe9.ggkt.order.service.impl;

import cn.wenhe9.ggkt.activity.entity.CouponInfo;
import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.common.utils.AuthContextHolder;
import cn.wenhe9.ggkt.common.utils.OrderNoUtils;
import cn.wenhe9.ggkt.order.entity.OrderDetail;
import cn.wenhe9.ggkt.order.entity.OrderInfo;
import cn.wenhe9.ggkt.order.feign.ActivityFeignClient;
import cn.wenhe9.ggkt.order.feign.CourseFeignClient;
import cn.wenhe9.ggkt.order.feign.UserFeignClient;
import cn.wenhe9.ggkt.order.mapper.OrderInfoMapper;
import cn.wenhe9.ggkt.order.service.OrderDetailService;
import cn.wenhe9.ggkt.order.service.OrderInfoService;
import cn.wenhe9.ggkt.order.vo.OrderFormVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoListVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoQueryVo;
import cn.wenhe9.ggkt.order.vo.OrderInfoVo;
import cn.wenhe9.ggkt.user.entity.UserInfo;
import cn.wenhe9.ggkt.vod.entity.Course;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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

    @Resource
    private CourseFeignClient courseFeignClient;

    @Resource
    private ActivityFeignClient activityFeignClient;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private Executor executor;

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

    @Override
    public Long submitOrder(OrderFormVo orderFormVo) {
        try {
            // 获取生成订单条件值
            Long courseId = orderFormVo.getCourseId();
            Long couponId = orderFormVo.getCouponId();
            Long userId = AuthContextHolder.getUserId();
            CompletableFuture<OrderDetail> orderDetailFuture = CompletableFuture.supplyAsync(() -> {
                // 判断当前用户, 针对当前课程是否已经生成了订单
                LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper
                        .eq(OrderDetail::getCourseId, courseId)
                        .eq(OrderDetail::getUserId, userId);
                return orderDetailService.getOne(queryWrapper);
            }, executor);

            OrderDetail orderDetailExist = orderDetailFuture.get();
            if (Objects.nonNull(orderDetailExist)) {
                //订单已经存在，直接返回订单id
                return orderDetailExist.getId();
            }

            // 根据课程id查询课程信息
            CompletableFuture<Course> courseFuture = CompletableFuture.supplyAsync(() -> {
                Course course = courseFeignClient.findCourseById(courseId);
                if (Objects.isNull(course)) {
                    throw new GgktException(ResultResponseEnum.COURSE_NOT_FOUND);
                }
                return course;
            }, executor);

            // 根据用户id查询用户信息
            CompletableFuture<UserInfo> userInfoFuture = CompletableFuture.supplyAsync(() -> {
                UserInfo userInfo = userFeignClient.getUserInfoById(userId);
                if (Objects.isNull(userInfo)) {
                    throw new GgktException(ResultResponseEnum.USER_NOT_EXIST);
                }
                return userInfo;
            }, executor);

            // 根据优惠卷id查询优惠卷信息
            CompletableFuture<BigDecimal> couponFuture = CompletableFuture.supplyAsync(() -> {
                BigDecimal couponReduce = new BigDecimal(0);
                if (Objects.nonNull(couponId)) {
                    CouponInfo couponInfo = activityFeignClient.findCouponInfoById(couponId);
                    if (Objects.isNull(couponInfo)) {
                        throw new GgktException(ResultResponseEnum.COUPON_NOT_FOUND);
                    }
                    couponReduce = couponInfo.getAmount();
                }
                return couponReduce;
            }, executor);

            // 多任务组合
            CompletableFuture<Void> allFuture = CompletableFuture.allOf(courseFuture, userInfoFuture, courseFuture);

            // 统一异常处理
            allFuture.exceptionally(throwable -> {throw new RuntimeException(throwable);});
            // 等待所有任务结束
            allFuture.get();

            Course course = courseFuture.get();
            UserInfo userInfo = userInfoFuture.get();
            BigDecimal couponReduce = couponFuture.get();
            // 封装订单生成需要的数据到对象，完成添加订单
            // 封装数据到orderInfo对象里面，添加订单基本信息表
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserId(userId);
            orderInfo.setNickName(userInfo.getNickName());
            orderInfo.setPhone(userInfo.getPhone());
            orderInfo.setProvince(userInfo.getProvince());
            orderInfo.setOriginAmount(course.getPrice());
            orderInfo.setCouponReduce(couponReduce);
            orderInfo.setFinalAmount(orderInfo.getOriginAmount().subtract(orderInfo.getCouponReduce()));
            orderInfo.setOutTradeNo(OrderNoUtils.getOrderNo());
            orderInfo.setTradeBody(course.getTitle());
            orderInfo.setOrderStatus("0");
            this.save(orderInfo);
            // 封装数据到orderDetail对象里面，添加订单基本详情表
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderInfo.getId());
            orderDetail.setUserId(userId);
            orderDetail.setCourseId(courseId);
            orderDetail.setCourseName(course.getTitle());
            orderDetail.setCover(course.getCover());
            orderDetail.setOriginAmount(course.getPrice());
            orderDetail.setCouponReduce(new BigDecimal(0));
            orderDetail.setFinalAmount(orderDetail.getOriginAmount().subtract(orderDetail.getCouponReduce()));
            orderDetailService.save(orderDetail);
            // 更新优惠卷状态
            if(Objects.nonNull(orderFormVo.getCouponUseId())) {
                activityFeignClient.updateCouponInfoUseStatus(orderFormVo.getCouponUseId(), orderInfo.getId());
            }
            // 返回订单id
            return orderInfo.getId();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GgktException(ResultResponseEnum.SUBMIT_ORDER_ERROR);
        }
    }

    @Override
    public OrderInfoVo findOrderInfoById(long id) {
        // 根据订单id查询订单基本信息和详情信息
        OrderInfo orderInfo = this.getById(id);
        OrderDetail orderDetail = orderDetailService.getById(id);
        // 封装orderInfoVo
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        orderInfoVo.setCourseId(orderDetail.getCourseId());
        orderInfoVo.setCourseName(orderDetail.getCourseName());
        return orderInfoVo;
    }

    @Override
    public void updateOrderStatus(String outTradeNo) {
        //根据out_trade_no查询订单
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderInfo::getOutTradeNo,outTradeNo);
        OrderInfo orderInfo = this.getOne((queryWrapper));
        //更新订单状态 1 已经支付
        orderInfo.setOrderStatus("1");
        this.updateById(orderInfo);
    }
}
