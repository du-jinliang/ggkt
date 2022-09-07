package cn.wenhe9.ggkt.live.service.impl;

import cn.wenhe9.ggkt.live.entity.LiveCourseConfig;
import cn.wenhe9.ggkt.live.entity.LiveCourseGoods;
import cn.wenhe9.ggkt.live.mapper.LiveCourseGoodsMapper;
import cn.wenhe9.ggkt.live.service.LiveCourseGoodsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Service
public class LiveCourseGoodsServiceImpl extends ServiceImpl<LiveCourseGoodsMapper, LiveCourseGoods> implements LiveCourseGoodsService {

    @Override
    public List<LiveCourseGoods> getLiveCourseGoodsByLiveCourseId(Long courseId) {
        LambdaQueryWrapper<LiveCourseGoods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LiveCourseGoods::getLiveCourseId, courseId);
        return this.list(queryWrapper);
    }
}
