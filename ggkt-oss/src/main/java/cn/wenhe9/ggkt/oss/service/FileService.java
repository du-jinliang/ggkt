package cn.wenhe9.ggkt.oss.service;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author DuJinliang
 * 2022/08/16
 */
public interface FileService {
    /**
     * 上传文件到七牛云oss
     * @param file MultipartFile
     * @return ResultResponse
     */
    ResultResponse upload(MultipartFile file, HttpServletRequest request);
}
