package cn.wenhe9.ggkt.live.service;

import cn.wenhe9.ggkt.live.entity.LiveCourseGoods;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 直播课程关联推荐表 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
public interface LiveCourseGoodsService extends IService<LiveCourseGoods> {

    /**
     * 根据课程id查询直播课程关联推荐
     */
    List<LiveCourseGoods> getLiveCourseGoodsByLiveCourseId(Long courseId);
}
