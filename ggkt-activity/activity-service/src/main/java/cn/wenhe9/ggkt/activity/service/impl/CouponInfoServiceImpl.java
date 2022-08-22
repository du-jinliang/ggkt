package cn.wenhe9.ggkt.activity.service.impl;

import cn.wenhe9.ggkt.activity.entity.CouponInfo;
import cn.wenhe9.ggkt.activity.entity.CouponUse;
import cn.wenhe9.ggkt.activity.feign.UserInfoFeignClient;
import cn.wenhe9.ggkt.activity.mapper.CouponInfoMapper;
import cn.wenhe9.ggkt.activity.service.CouponInfoService;
import cn.wenhe9.ggkt.activity.service.CouponUseService;
import cn.wenhe9.ggkt.activity.vo.CouponUseQueryVo;
import cn.wenhe9.ggkt.user.entity.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 优惠券信息 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
@Service
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Resource
    private CouponUseService couponUseService;

    @Resource
    private UserInfoFeignClient userFeignClient;

    @Override
    public Page<CouponUse> selectCouponUsePage(Long current, Long limit, CouponUseQueryVo couponUseQueryVo) {

        //获取条件
        Long couponId = couponUseQueryVo.getCouponId();
        String couponStatus = couponUseQueryVo.getCouponStatus();
        String getTimeBegin = couponUseQueryVo.getGetTimeBegin();
        String getTimeEnd = couponUseQueryVo.getGetTimeEnd();
        //封装条件
        LambdaQueryWrapper<CouponUse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(!StringUtils.isEmpty(couponId), CouponUse::getCouponId, couponId)
                .eq(!StringUtils.isEmpty(couponStatus), CouponUse::getCouponStatus, couponStatus)
                .ge(!StringUtils.isEmpty(getTimeBegin), CouponUse::getGetTime, getTimeBegin)
                .le(!StringUtils.isEmpty(getTimeEnd), CouponUse::getGetTime, getTimeEnd);
        //调用方法查询
        Page<CouponUse> couponUsePage = couponUseService.page(new Page<>(current, limit), queryWrapper);
        //封装用户昵称和手机号
        List<CouponUse> couponUseList = couponUsePage.getRecords();
        couponUseList.forEach(this::getUserInfoByCouponUse);

        return couponUsePage;

    }

    /**
     * 封装用户昵称和手机号
     */
    private void getUserInfoByCouponUse(CouponUse couponUse) {
        Long userId = couponUse.getUserId();
        if(!StringUtils.isEmpty(userId)) {
            UserInfo userInfo = userFeignClient.getUserInfoById(userId);
            if(userInfo != null) {
                couponUse.getParam().put("nickName", userInfo.getNickName());
                couponUse.getParam().put("phone", userInfo.getPhone());
            }
        }
    }
}
