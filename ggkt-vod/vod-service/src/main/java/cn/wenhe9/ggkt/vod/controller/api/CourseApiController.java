package cn.wenhe9.ggkt.vod.controller.api;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.entity.Course;
import cn.wenhe9.ggkt.vod.service.CourseService;
import cn.wenhe9.ggkt.vod.vo.CourseVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/22
 */
@Api(tags = "课程信息接口")
@RestController
@RequestMapping("/api/vod/course")
public class CourseApiController {

    @Resource
    private CourseService courseService;

    /**
     * 根据课程id查询课程信息
     */
    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/inner/info/{courseId}")
    public Course findCourseById(@PathVariable(name = "courseId") long courseId) {
        return courseService.getById(courseId);
    }

    /**
     * 根据课程分类查询课程列表
     */
    @ApiOperation("根据课程分类查询课程列表")
    @GetMapping("/{subjectParentId}/{current}/{limit}")
    public ResultResponse<Page<CourseVo>> findCourseByCategory(
            @PathVariable(name = "subjectParentId") long subjectParentId,
            @PathVariable(name = "current") long current,
            @PathVariable(name = "limit") long limit
    ) {
        Page<CourseVo> courseVoPage = courseService.findCourseByCategory(subjectParentId, current, limit);
        return ResultResponse.success(courseVoPage);
    }

    /**
     * 根据课程id查询课程详情
     */
    @ApiOperation("根据课程id查询课程详情")
    @GetMapping("/{courseId}")
    public ResultResponse<Map<String, Object>> findCourseInfoByCourseId(@PathVariable(name = "courseId") long courseId) {
        Map<String, Object> map = courseService.findCourseInfoByCourseId(courseId);
        return ResultResponse.success(map);
    }

    /**
     * 根据关键字查询课程
     */
    @ApiOperation("根据关键字查询课程")
    @GetMapping("/inner/{keyword}")
    public List<Course> findCourseByKeyword(@PathVariable(name = "keyword") String keyword) {
        return courseService.findCourseByKeyword(keyword);
    }
}
