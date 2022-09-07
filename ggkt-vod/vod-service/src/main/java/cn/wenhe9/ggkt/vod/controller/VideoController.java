package cn.wenhe9.ggkt.vod.controller;


import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.entity.Video;
import cn.wenhe9.ggkt.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
@RestController
@Api(tags = "课程小节(课时)管理接口")
@RequestMapping("/admin/vod/video")
public class VideoController {

    @Resource
    private VideoService videoService;

    /**
     * 添加小节
     */
    @ApiOperation("添加小节")
    @PostMapping
    public ResultResponse<Void> saveVideo(@RequestBody Video video) {
        videoService.save(video);
        return ResultResponse.success();
    }


    /**
     * 根据id查询
     */
    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public ResultResponse<Video> getVideoById(@PathVariable(name = "id") long id) {
        Video video = videoService.getById(id);
        return ResultResponse.success(video);
    }

    /**
     * 修改小节
     */
    @ApiOperation("修改小节")
    @PutMapping
    public ResultResponse<Void> updateVideoById(@RequestBody Video video) {
        videoService.updateById(video);
        return ResultResponse.success();
    }


    /**
     * 删除小节
     */
    @ApiOperation("根据id删除小节")
    @DeleteMapping("/{id}")
    public ResultResponse<Void> removeVideoById(@PathVariable(name = "id") long id) {
        videoService.removeVideoById(id);
        return ResultResponse.success();
    }
}

