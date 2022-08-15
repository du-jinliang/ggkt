package cn.wenhe9.ggkt.vod.controller;


import cn.wenhe9.ggkt.vod.entity.Teacher;
import cn.wenhe9.ggkt.vod.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wenhe9
 * @since 2022-08-14
 */
@RestController
@RequestMapping("/admin/vod/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    /**
     * 查询所有讲师
     */
    @GetMapping("/list")
    public List<Teacher> findAllTeacher() {
        return teacherService.list();
    }

    /**
     * 根据id删除指定讲师
     */
    @DeleteMapping("{id}")
    public boolean removeTeacher(@PathVariable(name = "id") long id) {
        return teacherService.removeById(id);
    }
}

