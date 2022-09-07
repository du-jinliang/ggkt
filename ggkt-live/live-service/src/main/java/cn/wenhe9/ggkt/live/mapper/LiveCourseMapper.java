package cn.wenhe9.ggkt.live.mapper;

import cn.wenhe9.ggkt.live.entity.LiveCourse;
import cn.wenhe9.ggkt.live.vo.LiveCourseVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 直播课程表 Mapper 接口
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
public interface LiveCourseMapper extends BaseMapper<LiveCourse> {

    /**
     * 获取最近直播课程
     */
    List<LiveCourseVo> findLatelyLiveCourseList();
}
