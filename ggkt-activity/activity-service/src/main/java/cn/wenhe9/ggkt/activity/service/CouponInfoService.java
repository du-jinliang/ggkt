package cn.wenhe9.ggkt.activity.service;

import cn.wenhe9.ggkt.activity.entity.CouponInfo;
import cn.wenhe9.ggkt.activity.entity.CouponUse;
import cn.wenhe9.ggkt.activity.vo.CouponUseQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
public interface CouponInfoService extends IService<CouponInfo> {

    /**
     * 获取已经使用优惠卷分页列表
     */
    Page<CouponUse> selectCouponUsePage(Long current, Long limit, CouponUseQueryVo couponUseQueryVo);
}
