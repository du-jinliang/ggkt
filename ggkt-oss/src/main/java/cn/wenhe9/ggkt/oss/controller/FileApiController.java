package cn.wenhe9.ggkt.oss.controller;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author DuJinliang
 * 2022/08/16
 */
@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {

    @Resource
    private FileService fileService;

    /**
     * 上传文件到七牛云oss
     * @param file MultipartFile
     * @return ResultResponse
     */
    @ApiOperation("上传文件到七牛云oss")
    @PostMapping("/fileUpload")
    public ResultResponse fileUpload(MultipartFile file, HttpServletRequest request){
        //获取上传文件
        return fileService.upload(file, request);
    }
}
