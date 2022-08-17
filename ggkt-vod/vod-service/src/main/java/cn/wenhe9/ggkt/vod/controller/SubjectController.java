package cn.wenhe9.ggkt.vod.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.entity.Subject;
import cn.wenhe9.ggkt.vod.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-17
 */
@Slf4j
@RestController
@Api(tags = "课程科目管理接口")
@RequestMapping("/admin/vod/subject")
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    /**
     * 课程分类列表
     * 懒加载 每次查询一层数据
     */
    @ApiOperation("课程分类列表")
    @GetMapping("/child/{id}")
    public ResultResponse<List<Subject>> getChildSubject(@PathVariable(name = "id") long id) {
        List<Subject> list = subjectService.selectSubjectList(id);
        return ResultResponse.success(list);
    }

    /**
     * 课程分类导出
     */
    @ApiOperation("课程分类导出")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        subjectService.exportData(response);
    }

    /**
     * 课程分类导入
     */
    @ApiOperation("课程分类导入")
    @PostMapping("/import")
    public ResultResponse<Void> importData(MultipartFile file) {
        long start = System.currentTimeMillis();
        subjectService.importData(file);
        long end = System.currentTimeMillis();
        log.info("花费时间为{}", end - start);
        return ResultResponse.success();
    }
}

