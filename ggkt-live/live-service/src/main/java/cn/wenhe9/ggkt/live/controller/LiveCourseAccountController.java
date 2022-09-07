package cn.wenhe9.ggkt.live.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.live.entity.LiveCourseAccount;
import cn.wenhe9.ggkt.live.service.LiveCourseAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 直播课程账号表（受保护信息） 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Api(tags = "直播课程账号管理接口")
@RestController
@RequestMapping("/live/liveCourseAccount")
public class LiveCourseAccountController {
    @Resource
    private LiveCourseAccountService liveCourseAccountService;

    /**
     * 根据直播课程id查询账号信息
     */
    @ApiOperation(value = "根据直播课程id查询账号信息")
    @GetMapping("/{courseId}")
    public ResultResponse<LiveCourseAccount> getLiveCourseAccount(@PathVariable(name = "courseId") Long courseId) {
        LiveCourseAccount liveCourseAccount = liveCourseAccountService.getLiveCourseAccountByLiveCourseId(courseId);
        return ResultResponse.success(liveCourseAccount);
    }
}

