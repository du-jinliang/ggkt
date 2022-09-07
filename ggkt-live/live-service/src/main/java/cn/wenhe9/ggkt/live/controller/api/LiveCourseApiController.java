package cn.wenhe9.ggkt.live.controller.api;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.utils.AuthContextHolder;
import cn.wenhe9.ggkt.live.service.LiveCourseService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author DuJinliang
 * 2022/09/07
 */
@Api(tags = "直播课程接口")
@RestController
@RequestMapping("/api/live/liveCourse")
public class LiveCourseApiController {
    @Resource
    private LiveCourseService liveCourseService;

    /**
     * 获取用户access_token
     */
    @ApiOperation(value = "获取用户access_token")
    @GetMapping("/auth/{id}")
    public ResultResponse<JSONObject> getPlayAuth(@PathVariable(name = "id") Long id) {
        JSONObject object = liveCourseService.getPlayAuth(id, AuthContextHolder.getUserId());
        return ResultResponse.success(object);
    }

    /**
     * 根据ID查询课程
     */
    @ApiOperation("根据ID查询课程")
    @GetMapping("/info/{courseId}")
    public ResultResponse<Map<String, Object>> getInfo(@PathVariable(name = "courseId") Long courseId){
        Map<String, Object> map = liveCourseService.getInfoById(courseId);
        return ResultResponse.success(map);
    }

}
