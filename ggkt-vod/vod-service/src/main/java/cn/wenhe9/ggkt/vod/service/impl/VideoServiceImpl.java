package cn.wenhe9.ggkt.vod.service.impl;

import cn.wenhe9.ggkt.vod.entity.Chapter;
import cn.wenhe9.ggkt.vod.entity.Video;
import cn.wenhe9.ggkt.vod.mapper.VideoMapper;
import cn.wenhe9.ggkt.vod.service.VideoService;
import cn.wenhe9.ggkt.vod.service.VodService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Resource
    private VodService vodService;

    @Override
    @Transactional
    public void removeVideoByCourseId(long id) {
        //根据课程id查询课程所有小节
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getCourseId, id);
        List<Video> videoList = this.list(queryWrapper);
        //遍历所有小节集合得到每个小节，获取每个小节视频id
        for (Video video : videoList) {
            this.removeVideoById(video.getId());
        }
    }

    @Override
    @Transactional
    public void removeVideoById(long id) {
        //id查询小节
        Video video = this.getById(id);
        //获取video里面的视频id
        String videoSourceId = video.getVideoSourceId();
        //判断视频id是否为空
        if (!StringUtils.isEmpty(videoSourceId)) {
            //视频id不为空，调用方法根据id删除腾讯云视频
            vodService.removeVideoById(videoSourceId);
        }
        //根据id删除小节
        this.removeById(id);
    }

    @Override
    public void removeVideoByChapterId(long chapterId) {
        LambdaQueryWrapper<Video> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getChapterId, chapterId);
        List<Video> videoList = this.list(queryWrapper);
        for (Video video : videoList) {
            this.removeVideoById(video.getId());
        }
    }
}
