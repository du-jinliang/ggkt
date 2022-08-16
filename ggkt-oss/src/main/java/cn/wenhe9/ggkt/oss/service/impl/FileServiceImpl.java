package cn.wenhe9.ggkt.oss.service.impl;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.oss.service.FileService;
import cn.wenhe9.ggkt.oss.utils.ConstantOssPropertiesUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author DuJinliang
 * 2022/08/16
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public ResultResponse upload(MultipartFile file, HttpServletRequest request) {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region1());

        UploadManager uploadManager = new UploadManager(cfg);
        //生成上传凭证，然后准备上传
        String accessKey = ConstantOssPropertiesUtils.ACCESS_KEY;
        String secretKey = ConstantOssPropertiesUtils.SECRET_KEY;
        String bucket = ConstantOssPropertiesUtils.BUCKET;

        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String filename = file.getOriginalFilename();
        String substring = filename.substring(filename.lastIndexOf("."));
        String timeUrl = new DateTime().toString("yyyy/MM/dd");
        String key = timeUrl + "/image-" + System.currentTimeMillis() + substring;

        try {
            byte[] uploadBytes = file.getBytes();
            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = objectMapper.readValue(response.bodyString(), DefaultPutRet.class);
                String url = "http://tuchuang.wenhe9.cn/" + putRet.key;

                return ResultResponse.success(url);

            } catch (QiniuException ex) {
                Response r = ex.response;
                log.error(r.toString());
                try {
                    log.error(r.bodyString());
                } catch (QiniuException ex2) {
                    log.error(ex2.getMessage());
                }
            }
        } catch (UnsupportedEncodingException ex) {
            log.error(ex.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultResponse.fail();
    }
}
