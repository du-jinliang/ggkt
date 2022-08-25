package cn.wenhe9.ggkt.vod.service;

import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/20
 */
public interface VodService {

    /**
     * 删除视频
     */
    void removeVideoById(String id);

    /**
     * 返回客户端上传视频签名
     */
    String getSign();

    /**
     * 获取视频播放参数
     */
    Map<String, Object> getPlayAuth(long courseId, long videoId);
}
