package cn.wenhe9.ggkt.live.service;

import cn.wenhe9.ggkt.live.entity.LiveCourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
public interface LiveCourseDescriptionService extends IService<LiveCourseDescription> {
    /**
     * 根据课程id删除直播课程描述信息
     */
    void removeLiveCourseDescriptionByLiveCourseId(Long courseId);

    /**
     * 根据直播课程id查询直播课程描述信息
     */
    LiveCourseDescription getByLiveCourseId(Long courseId);
}
