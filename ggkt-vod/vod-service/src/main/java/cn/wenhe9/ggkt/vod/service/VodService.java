package cn.wenhe9.ggkt.vod.service;

/**
 * @author DuJinliang
 * 2022/08/20
 */
public interface VodService {

    /**
     * 删除视频
     */
    void removeVideoById(String id);

    String getSign();
}
