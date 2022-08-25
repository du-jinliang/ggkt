package cn.wenhe9.ggkt.order.feign;

import cn.wenhe9.ggkt.activity.api.CouponApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@Component
@FeignClient(value = "ggkt-activity", path = "/api/activity/couponInfo")
public interface ActivityFeignClient extends CouponApi {
}
