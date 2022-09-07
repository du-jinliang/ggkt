package cn.wenhe9.ggkt.live.service.impl;

import cn.wenhe9.ggkt.live.entity.LiveCourseDescription;
import cn.wenhe9.ggkt.live.mapper.LiveCourseDescriptionMapper;
import cn.wenhe9.ggkt.live.service.LiveCourseDescriptionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Service
public class LiveCourseDescriptionServiceImpl extends ServiceImpl<LiveCourseDescriptionMapper, LiveCourseDescription> implements LiveCourseDescriptionService {

    @Override
    public void removeLiveCourseDescriptionByLiveCourseId(Long courseId) {
        LambdaQueryWrapper<LiveCourseDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseDescription::getLiveCourseId, courseId);
        this.remove(queryWrapper);
    }

    @Override
    public LiveCourseDescription getByLiveCourseId(Long courseId) {
        LambdaQueryWrapper<LiveCourseDescription> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseDescription::getLiveCourseId, courseId);
        return this.getOne(queryWrapper);
    }
}
