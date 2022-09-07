package cn.wenhe9.ggkt.live.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.live.entity.LiveCourse;
import cn.wenhe9.ggkt.live.service.LiveCourseService;
import cn.wenhe9.ggkt.live.vo.LiveCourseConfigVo;
import cn.wenhe9.ggkt.live.vo.LiveCourseFormVo;
import cn.wenhe9.ggkt.live.vo.LiveCourseVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.beans.Encoder;
import java.util.Base64;
import java.util.List;

/**
 * <p>
 * 直播课程表 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Api(tags = "直播课程管理接口")
@RestController
@RequestMapping("/live/admin/liveCourse")
public class LiveCourseController {

    @Resource
    private LiveCourseService liveCourseService;

    /**
     * 分页查询直播课程
     */
    @ApiOperation("分页查询直播课程")
    @GetMapping("/list/{current}/{limit}")
    public ResultResponse<Page<LiveCourse>> findLiveCourseList(
            @PathVariable(name = "current") long current,
            @PathVariable(name = "limit") long limit
    ) {
        Page<LiveCourse> liveCoursePage = liveCourseService.findLiveCourseList(current, limit);
        return ResultResponse.success(liveCoursePage);
    }

    /**
     * 直播课程添加
     */
    @ApiOperation("直播课程添加")
    @PostMapping
    public ResultResponse<Void> saveLiveCourse(@RequestBody LiveCourseFormVo liveCourseFormVo) {
        liveCourseService.saveLiveCourse(liveCourseFormVo);
        return ResultResponse.success();
    }

    /**
     * 根据id删除直播课程
     */
    @ApiOperation("根据id删除直播课程")
    @DeleteMapping("/{id}")
    public ResultResponse<Void> removeLiveCourse(@PathVariable(name = "id") long id) {
        liveCourseService.removeLiveCourseById(id);
        return ResultResponse.success();
    }

    /**
     * 查询课程基本信息
     */
    @ApiOperation("查询课程基本信息")
    @GetMapping("/{id}")
    public ResultResponse<LiveCourse> getLiveCourseInfo(@PathVariable(name = "id") Long id) {
        LiveCourse liveCourse = liveCourseService.getById(id);
        return ResultResponse.success(liveCourse);
    }

    /**
     * 查询课程基本信息和描述信息
     */
    @ApiOperation(value = "查询课程基本信息和描述信息")
    @GetMapping("/info/{id}")
    public ResultResponse<LiveCourseFormVo> getInfo(@PathVariable(name = "id") Long id) {
        LiveCourseFormVo liveCourseFormVo = liveCourseService.getLiveCourseFormVo(id);
        return ResultResponse.success(liveCourseFormVo);
    }

    /**
     * 更新直播课程信息
     */
    @ApiOperation(value = "更新直播课程信息")
    @PutMapping
    public ResultResponse<Void> updateById(@RequestBody LiveCourseFormVo liveCourseFormVo) {
        liveCourseService.updateLiveCourseById(liveCourseFormVo);
        return ResultResponse.success();
    }

    /**
     * 获取最近的直播
     */
    @ApiOperation(value = "获取最近的直播")
    @GetMapping("findLatelyList")
    public ResultResponse<List<LiveCourseVo>> findLatelyList() {
        List<LiveCourseVo> courseVoList = liveCourseService.findLatelyLiveCourseList();
        return ResultResponse.success(courseVoList);
    }
}

