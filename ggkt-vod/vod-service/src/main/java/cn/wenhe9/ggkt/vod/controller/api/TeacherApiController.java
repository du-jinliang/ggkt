package cn.wenhe9.ggkt.vod.controller.api;

import cn.wenhe9.ggkt.vod.entity.Teacher;
import cn.wenhe9.ggkt.vod.service.TeacherService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author DuJinliang
 * 2022/08/27
 */
@RestController
@Api(tags = "教师信息接口")
@RequestMapping("/api/vod/teacher")
public class TeacherApiController {

    @Resource
    private TeacherService teacherService;

    @GetMapping("/inner/{id}")
    public Teacher findTeacherInfo(@PathVariable(name = "id") long id) {
        return teacherService.getById(id);
    }
}
