package cn.wenhe9.ggkt.live.service.impl;

import cn.wenhe9.ggkt.live.entity.LiveCourseConfig;
import cn.wenhe9.ggkt.live.mapper.LiveCourseConfigMapper;
import cn.wenhe9.ggkt.live.service.LiveCourseConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 直播课程配置表 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Service
public class LiveCourseConfigServiceImpl extends ServiceImpl<LiveCourseConfigMapper, LiveCourseConfig> implements LiveCourseConfigService {

    @Override
    public LiveCourseConfig getLiveCourseConfigByCourseId(Long courseId) {
        LambdaQueryWrapper<LiveCourseConfig> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseConfig::getLiveCourseId, courseId);
        return this.getOne(queryWrapper);
    }
}
