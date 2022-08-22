package cn.wenhe9.ggkt.vod.api;

import cn.wenhe9.ggkt.vod.entity.Course;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author DuJinliang
 * 2022/08/22
 */
public interface CourseApi {
    /**
     * 根据关键字查询课程
     */
    @GetMapping("/inner/{keyword}")
    List<Course> findCourseByKeyword(@PathVariable(name = "keyword") String keyword);
}
