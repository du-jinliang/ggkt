package cn.wenhe9.ggkt.vod.mapper;

import cn.wenhe9.ggkt.vod.entity.VideoVisitor;
import cn.wenhe9.ggkt.vod.vo.VideoVisitorCountVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频来访者记录 Mapper 接口
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-20
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    /**
     * 课程统计接口
     */
    List<VideoVisitorCountVo> getCount(
            @Param("courseId") long courseId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );
}




