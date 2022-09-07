package cn.wenhe9.ggkt.live.service.impl;

import cn.wenhe9.ggkt.live.entity.LiveCourseAccount;
import cn.wenhe9.ggkt.live.mapper.LiveCourseAccountMapper;
import cn.wenhe9.ggkt.live.service.LiveCourseAccountService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 直播课程账号表（受保护信息） 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Service
public class LiveCourseAccountServiceImpl extends ServiceImpl<LiveCourseAccountMapper, LiveCourseAccount> implements LiveCourseAccountService {

    @Override
    public LiveCourseAccount getLiveCourseAccountByLiveCourseId(Long courseId) {
        LambdaQueryWrapper<LiveCourseAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseAccount::getLiveCourseId, courseId);
        return this.getOne(queryWrapper);
    }
}
