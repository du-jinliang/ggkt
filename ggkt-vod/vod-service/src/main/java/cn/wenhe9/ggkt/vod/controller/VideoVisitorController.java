package cn.wenhe9.ggkt.vod.controller;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.service.VideoVisitorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 视频来访者记录 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-20
 */
@RestController
@Api(tags = "视频来访者记录 管理接口")
@RequestMapping("/admin/vod/videoVisitor")
public class VideoVisitorController {
    @Resource
    private VideoVisitorService videoVisitorService;

    /**
     * 课程统计接口
     */
    @ApiOperation("课程统计接口")
    @GetMapping("/count/{courseId}/{startDate}/{endDate}")
    public ResultResponse<Map<String, Object>> getCount(
            @PathVariable(name = "courseId") long courseId,
            @PathVariable(name = "startDate") String startDate,
            @PathVariable(name = "endDate") String endDate
    ) {
        Map<String, Object> map = videoVisitorService.getCount(courseId, startDate, endDate);
        return ResultResponse.success(map);
    }
}
