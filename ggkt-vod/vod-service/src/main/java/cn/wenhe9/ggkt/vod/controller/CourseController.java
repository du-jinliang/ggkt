package cn.wenhe9.ggkt.vod.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.service.CourseService;
import cn.wenhe9.ggkt.vod.vo.CourseFormVo;
import cn.wenhe9.ggkt.vod.vo.CoursePublishVo;
import cn.wenhe9.ggkt.vod.vo.CourseQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
@RestController
@Api(tags = "课程管理接口")
@RequestMapping("/admin/vod/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    /**
     * 点播课程列表
     */
    @ApiOperation("点播课程列表")
    @GetMapping("/list/{current}/{limit}")
    public ResultResponse<Map<String , Object>> courseList(
            @PathVariable(name = "current") long current,
            @PathVariable(name = "limit") long limit,
            CourseQueryVo courseQueryVo
    ) {
        Map<String , Object> coursePage = courseService.findPageCourse(current, limit, courseQueryVo);
        return ResultResponse.success(coursePage);
    }

    /**
     * 添加课程基本信息
     */
    @ApiOperation("添加课程基本信息")
    @PostMapping
    public ResultResponse<Long> save(@RequestBody CourseFormVo courseFormVo) {
        Long courseId =  courseService.saveCourseInfo(courseFormVo);
        return ResultResponse.success(courseId);
    }

    /**
     * 根据id获取课程信息
     */
    @ApiOperation("根据id获取课程信息")
    @GetMapping("/{id}")
    public ResultResponse<CourseFormVo> getInfoById(@PathVariable(name = "id") long id) {
        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return ResultResponse.success(courseFormVo);
    }


    /**
     * 修改课程信息
     */
    @ApiOperation("修改课程信息")
    @PutMapping
    public ResultResponse<Void> updateInfoById(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseById(courseFormVo);
        return ResultResponse.success();
    }

    /**
     * 根据课程id查询发布课程信息
     */
    @ApiOperation("根据课程id查询发布课程信息")
    @GetMapping("/publish/{id}")
    public ResultResponse<CoursePublishVo> getCoursePublishVo(@PathVariable(name = "id") long id) {
        CoursePublishVo courseProgressVo =  courseService.getCoursePublishVo(id);
        return ResultResponse.success(courseProgressVo);
    }

    /**
     * 课程最终发布
     */
    @ApiOperation("课程最终发布")
    @PutMapping("/publish/{id}")
    public ResultResponse<Void> publishCourse(@PathVariable(name = "id") long id) {
        courseService.publishCourse(id);
        return ResultResponse.success();
    }

    /**
     * 根据id删除课程
     */
    @ApiOperation("删除课程")
    @DeleteMapping("/{id}")
    public ResultResponse<Void> removeById(@PathVariable(name = "id") long id) {
        courseService.removeCourseById(id);
        return ResultResponse.success();
    }
}

