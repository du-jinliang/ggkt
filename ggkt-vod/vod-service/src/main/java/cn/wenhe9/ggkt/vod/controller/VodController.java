package cn.wenhe9.ggkt.vod.controller;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author DuJinliang
 * 2022/08/20
 */
@RestController
@Api(tags = "腾讯云点播")
@RequestMapping("/admin/vod")
public class VodController {
    @Resource
    private VodService vodService;

    /**
     * 返回客户端上传视频签名
     */
    @ApiOperation("返回客户端上传视频签名")
    @PostMapping("/sign")
    public ResultResponse<String> getSign() {
        String sign = vodService.getSign();
        return ResultResponse.success(sign);
    }

    /**
     * 删除视频
     */
    @ApiOperation("删除视频")
    @DeleteMapping("/{id}")
    public ResultResponse<Void> removeVideoById(@PathVariable(name = "id") String id) {
        vodService.removeVideoById(id);
        return ResultResponse.success();
    }
}
