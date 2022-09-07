package cn.wenhe9.ggkt.vod.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.entity.Teacher;
import cn.wenhe9.ggkt.vod.service.TeacherService;
import cn.wenhe9.ggkt.vod.vo.TeacherQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-14
 */
@RestController
@Api(tags = "讲师管理接口")
@RequestMapping("/admin/vod/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    /**
     * 查询所有讲师
     */
    @GetMapping("/list")
    @ApiOperation("查询所有讲师")
    public ResultResponse<List<Teacher>> findAllTeacher() {
        List<Teacher> list = teacherService.list();
        return ResultResponse.success(list);
    }

    /**
     * 根据id删除指定讲师
     */
    @DeleteMapping("/{id}")
    @ApiOperation("逻辑删除讲师")
    public ResultResponse<Void> removeTeacher(
            @PathVariable(name = "id") long id
    ) {
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess) {
            return ResultResponse.success();
        }
        return ResultResponse.fail();
    }

    /**
     * 分页查询讲师
     */
    @ApiOperation("分页查询讲师")
    @GetMapping("/list/{current}/{limit}")
    public ResultResponse<Page<Teacher>> queryByPage(
            @PathVariable(name = "current") long current,
            @PathVariable(name = "limit") long limit,
            TeacherQueryVo teacherQueryVo
    ) {
        Page<Teacher> teacherPage = teacherService.queryByPage(current, limit, teacherQueryVo);
        return ResultResponse.success(teacherPage);
    }

    /**
     * 添加讲师
     */
    @PostMapping
    @ApiOperation("添加讲师")
    public ResultResponse<Void> saveTeacher(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return ResultResponse.success();
    }

    /**
     * 根据id查询讲师
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询讲师")
    public ResultResponse<Teacher> getTeacher(
            @PathVariable(name = "id") long id
    ) {
        Teacher teacher = teacherService.getById(id);
        return ResultResponse.success(teacher);
    }


    /**
     * 更新讲师信息
     */
    @PutMapping
    @ApiOperation("更新讲师信息")
    public ResultResponse<Void> updateTeacher(@RequestBody Teacher teacher) {
        boolean isSuccess = teacherService.updateById(teacher);
        if (isSuccess) {
            return ResultResponse.success();
        }
        return ResultResponse.fail();
    }

    /**
     * 根据id批量删除
     */
    @DeleteMapping("/batch")
    @ApiOperation("根据id批量删除")
    public ResultResponse<Void> removeBatch(@RequestBody Set<Long> ids) {
        boolean isSuccess = teacherService.removeByIds(ids);
        if (isSuccess) {
            return ResultResponse.success();
        }
        return ResultResponse.fail();
    }
}

