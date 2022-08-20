package cn.wenhe9.ggkt.vod.service;

import cn.wenhe9.ggkt.vod.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
public interface VideoService extends IService<Video> {

    /**
     * 根据课程id删除小节
     */
    void removeVideoByCourseId(long id);

    /**
     * 根据id删除小节
     */
    void removeVideoById(long id);

    /**
     * 根据chapterId删除小节
     */
    void removeVideoByChapterId(long chapterId);
}
