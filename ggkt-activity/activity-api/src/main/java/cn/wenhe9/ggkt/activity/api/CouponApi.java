package cn.wenhe9.ggkt.activity.api;

import cn.wenhe9.ggkt.activity.entity.CouponInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author DuJinliang
 * 2022/08/25
 */
public interface CouponApi {
    /**
     * 根据优惠卷id查询优惠卷信息
     */
    @GetMapping("/inner/{couponId}")
    CouponInfo findCouponInfoById(@PathVariable(name = "couponId") long couponId);

    /**
     * 更新优惠卷使用状态
     */
    @GetMapping("/inner/{couponUseId}/{orderId}")
    Boolean updateCouponInfoUseStatus(
            @PathVariable(name = "couponUseId") long couponUseId,
            @PathVariable(name = "orderId") long orderId
    );
}
