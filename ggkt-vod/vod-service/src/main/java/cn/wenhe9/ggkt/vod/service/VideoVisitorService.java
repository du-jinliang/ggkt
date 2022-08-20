package cn.wenhe9.ggkt.vod.service;

import cn.wenhe9.ggkt.vod.entity.VideoVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-20
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    /**
     * 课程统计接口
     */
    Map<String, Object> getCount(long courseId, String startDate, String endDate);
}
