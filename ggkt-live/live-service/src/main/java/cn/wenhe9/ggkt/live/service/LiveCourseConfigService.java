package cn.wenhe9.ggkt.live.service;

import cn.wenhe9.ggkt.live.entity.LiveCourseConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 直播课程配置表 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
public interface LiveCourseConfigService extends IService<LiveCourseConfig> {

    /**
     * 根据课程id查询直播课程配置
     */
    LiveCourseConfig getLiveCourseConfigByCourseId(Long courseId);
}
