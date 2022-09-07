package cn.wenhe9.ggkt.live.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.live.service.LiveCourseService;
import cn.wenhe9.ggkt.live.vo.LiveCourseConfigVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 直播课程配置表 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Api(tags = "直播课程管理接口")
@RestController
@RequestMapping("/live/liveCourseConfig")
public class LiveCourseConfigController {

    @Resource
    private LiveCourseService liveCourseService;

    /**
     * 根据直播课程id查询配置信息
     */
    @ApiOperation(value = "根据直播课程id查询配置信息")
    @GetMapping("/{courseId}")
    public ResultResponse<LiveCourseConfigVo> getCourseConfig(@PathVariable(name = "courseId") Long courseId) {
        LiveCourseConfigVo liveCourseConfigVo = liveCourseService.getCourseConfig(courseId);
        return ResultResponse.success(liveCourseConfigVo);
    }

    /**
     * 修改直播课程配置信息
     */
    @ApiOperation(value = "修改直播课程配置信息")
    @PutMapping
    public ResultResponse<Void> updateConfig(@RequestBody LiveCourseConfigVo liveCourseConfigVo) {
        liveCourseService.updateLiveCourseConfig(liveCourseConfigVo);
        return ResultResponse.success();
    }
}

