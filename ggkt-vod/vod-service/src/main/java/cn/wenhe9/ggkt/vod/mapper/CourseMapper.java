package cn.wenhe9.ggkt.vod.mapper;

import cn.wenhe9.ggkt.vod.entity.Course;
import cn.wenhe9.ggkt.vod.vo.CourseProgressVo;
import cn.wenhe9.ggkt.vod.vo.CoursePublishVo;
import cn.wenhe9.ggkt.vod.vo.CourseQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 根据课程id查询发布课程信息
     */
    CoursePublishVo selectCoursePublishVoById(@Param("id") long id);
}
