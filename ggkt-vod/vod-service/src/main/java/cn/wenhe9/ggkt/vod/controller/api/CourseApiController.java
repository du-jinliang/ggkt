package cn.wenhe9.ggkt.vod.controller.api;

import cn.wenhe9.ggkt.vod.entity.Course;
import cn.wenhe9.ggkt.vod.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author DuJinliang
 * 2022/08/22
 */
@Api(tags = "课程内部管理接口")
@RestController
@RequestMapping("/api/void/course")
public class CourseApiController {

    @Resource
    private CourseService courseService;

    /**
     * 根据关键字查询课程
     */
    @ApiOperation("根据关键字查询课程")
    @GetMapping("/inner/{keyword}")
    public List<Course> findCourseByKeyword(@PathVariable(name = "keyword") String keyword) {
        return courseService.findCourseByKeyword(keyword);
    }
}
