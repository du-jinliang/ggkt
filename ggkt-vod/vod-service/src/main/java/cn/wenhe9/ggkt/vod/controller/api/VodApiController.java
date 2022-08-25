package cn.wenhe9.ggkt.vod.controller.api;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@RestController
@Api(tags = "腾讯视频点播接口")
@RequestMapping("/api/vod/")
public class VodApiController {
    @Resource
    private VodService vodService;

    /**
     * 获取视频播放参数
     */
    @ApiOperation("获取视频播放参数")
    @GetMapping("/palyAuth/{courseId}/{videoId}")
    public ResultResponse<Map<String, Object>> getPlayAuth(
            @PathVariable(name = "courseId") long courseId,
            @PathVariable(name = "videoId") long videoId
    ) {
        Map<String, Object> map = vodService.getPlayAuth(courseId, videoId);
        return ResultResponse.success(map);
    }
}
