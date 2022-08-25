package cn.wenhe9.ggkt.activity.controller.api;

import cn.wenhe9.ggkt.activity.entity.CouponInfo;
import cn.wenhe9.ggkt.activity.service.CouponInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@RestController
@Api(tags = "优惠卷接口")
@RequestMapping("/api/activity/couponInfo")
public class CouponInfoApiController {
    @Resource
    private CouponInfoService couponInfoService;

    /**
     * 根据优惠卷id查询优惠卷信息
     */
    @ApiOperation("根据优惠卷id查询优惠卷信息")
    @GetMapping("/inner/{couponId}")
    public CouponInfo findCouponInfoById(@PathVariable(name = "couponId") long couponId) {
        return couponInfoService.getById(couponId);
    }

    /**
     * 更新优惠卷使用状态
     */
    @ApiOperation("更新优惠卷使用状态")
    @GetMapping("/inner/{couponUseId}/{orderId}")
    public Boolean updateCouponInfoUseStatus(
            @PathVariable(name = "couponUseId") long couponUseId,
            @PathVariable(name = "orderId") long orderId
    ) {
        couponInfoService.updateCouponInfoUseStatus(couponUseId, orderId);
        return true;
    }
}
