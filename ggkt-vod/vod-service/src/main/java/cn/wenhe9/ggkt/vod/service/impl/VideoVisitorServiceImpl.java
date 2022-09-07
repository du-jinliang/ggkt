package cn.wenhe9.ggkt.vod.service.impl;

import cn.wenhe9.ggkt.vod.entity.VideoVisitor;
import cn.wenhe9.ggkt.vod.mapper.VideoVisitorMapper;
import cn.wenhe9.ggkt.vod.service.VideoVisitorService;
import cn.wenhe9.ggkt.vod.vo.VideoVisitorCountVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-20
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor>
    implements VideoVisitorService {

    @Override
    public Map<String, Object> getCount(long courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo> videoVisitorVoList = baseMapper.getCount(courseId, startDate, endDate);

        Map<String, Object> map = new HashMap<>();

        List<String> dateList = new ArrayList<>();
        List<Integer> countList = new ArrayList<>();

        dateList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());
        countList = videoVisitorVoList.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());

        map.put("xData", dateList);
        map.put("yData", countList);

        return map;
    }
}




