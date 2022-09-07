package cn.wenhe9.ggkt.vod.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.entity.Chapter;
import cn.wenhe9.ggkt.vod.service.ChapterService;
import cn.wenhe9.ggkt.vod.vo.ChapterVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
@RestController
@Api(tags = "章节管理接口")
@RequestMapping("/admin/vod/chapter")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    /**
     * 大纲列表 （章节和小节列表）
     */
    @ApiOperation("大纲列表")
    @GetMapping("/treeList/{courseId}")
    public ResultResponse<List<ChapterVo>> getTreeList(@PathVariable(name = "courseId") long courseId) {
        List<ChapterVo> list = chapterService.getTreeList(courseId);
        return ResultResponse.success(list);
    }

    /**
     * 添加章节
     */
    @ApiOperation("添加章节")
    @PostMapping
    public ResultResponse<Void> saveChapter(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return ResultResponse.success();
    }


    /**
     * 根据id查询
     */
    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public ResultResponse<Chapter> getChapterById(@PathVariable(name = "id") long id) {
        Chapter chapter = chapterService.getById(id);
        return ResultResponse.success(chapter);
    }

    /**
     * 修改章节
     */
    @ApiOperation("修改章节")
    @PutMapping
    public ResultResponse<Void> updateChapterById(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return ResultResponse.success();
    }


    /**
     * 删除章节
     */
    @ApiOperation("根据id删除章节")
    @DeleteMapping("/{id}")
    public ResultResponse<Void> removeChapterById(@PathVariable(name = "id") long id) {
        chapterService.removeChapterById(id);
        return ResultResponse.success();
    }
}

