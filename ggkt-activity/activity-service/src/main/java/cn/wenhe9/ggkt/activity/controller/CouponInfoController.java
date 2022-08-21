package cn.wenhe9.ggkt.activity.controller;


import cn.wenhe9.ggkt.activity.entity.CouponInfo;
import cn.wenhe9.ggkt.activity.entity.CouponUse;
import cn.wenhe9.ggkt.activity.service.CouponInfoService;
import cn.wenhe9.ggkt.activity.vo.CouponUseQueryVo;
import cn.wenhe9.ggkt.common.result.ResultResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 优惠券信息 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-21
 */
@Api(tags = "优惠券信息管理接口")
@RestController
@RequestMapping("/admin/activity/couponInfo")
public class CouponInfoController {
    @Resource
    private CouponInfoService couponInfoService;


    /**
     * 获取优惠券
     */
    @ApiOperation(value = "获取优惠券")
    @GetMapping("/{id}")
    public ResultResponse<CouponInfo> getCouponInfo(@PathVariable(name = "id") String id) {
        CouponInfo couponInfo = couponInfoService.getById(id);
        return ResultResponse.success(couponInfo);
    }

    /**
     * 新增优惠券
     */
    @ApiOperation(value = "新增优惠券")
    @PostMapping
    public ResultResponse<Void> saveCouponInfo(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return ResultResponse.success();
    }

    /**
     * 修改优惠券
     */
    @ApiOperation(value = "修改优惠券")
    @PutMapping
    public ResultResponse<Void> updateCouponInfo(@RequestBody CouponInfo couponInfo) {
        couponInfoService.updateById(couponInfo);
        return ResultResponse.success();
    }

    /**
     * 删除优惠券
     */
    @ApiOperation(value = "删除优惠券")
    @DeleteMapping("/{id}")
    public ResultResponse<Void> removeCouponInfo(@PathVariable(name = "id") String id) {
        couponInfoService.removeById(id);
        return ResultResponse.success();
    }

    /**
     * 根据id列表删除优惠券
     */
    @ApiOperation(value="根据id列表删除优惠券")
    @DeleteMapping("/batch")
    public ResultResponse<Void> batchRemoveCouponInfo(@RequestBody List<String> idList){
        couponInfoService.removeByIds(idList);
        return ResultResponse.success();
    }

    /**
     * 获取优惠卷分页列表
     */
    @ApiOperation(value = "获取优惠卷分页列表")
    @GetMapping("/list/{current}/{limit}")
    public ResultResponse<Page<CouponInfo>> getCouponList (
            @PathVariable(name = "current") Long current,
            @PathVariable(name = "limit") Long limit
    ) {
        Page<CouponInfo> couponInfoPage = couponInfoService.page(new Page<>(current, limit));
        return ResultResponse.success(couponInfoPage);
    }

    /**
     * 获取已经使用优惠卷分页列表
     */
    @ApiOperation(value = "获取已经使用优惠卷分页列表")
    @GetMapping("/list/used/{current}/{limit}")
    public ResultResponse<Page<CouponUse>> getUsedCouponList (
            @PathVariable(name = "current") Long current,
            @PathVariable(name = "limit") Long limit,
            CouponUseQueryVo couponUseQueryVo
    ) {
        Page<CouponUse> couponUsePage = couponInfoService.selectCouponUsePage(current, limit, couponUseQueryVo);
        return ResultResponse.success(couponUsePage);
    }
}

