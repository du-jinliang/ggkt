package cn.wenhe9.ggkt.vod.service.impl;

import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.vod.entity.Chapter;
import cn.wenhe9.ggkt.vod.entity.Video;
import cn.wenhe9.ggkt.vod.mapper.ChapterMapper;
import cn.wenhe9.ggkt.vod.service.ChapterService;
import cn.wenhe9.ggkt.vod.service.VideoService;
import cn.wenhe9.ggkt.vod.vo.ChapterVo;
import cn.wenhe9.ggkt.vod.vo.VideoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Resource
    private ThreadPoolExecutor executor;

    @Resource
    private VideoService videoService;

    @Override
    public List<ChapterVo> getTreeList(long courseId)  {
        try {
            List<ChapterVo> chapterVoList = new ArrayList<>();

            CompletableFuture<List<Chapter>> chapterFuture = CompletableFuture.supplyAsync(() -> {
                LambdaQueryWrapper<Chapter> wrapperChapter = new LambdaQueryWrapper<>();
                wrapperChapter.eq(Chapter::getCourseId, courseId);
                return this.list(wrapperChapter);
            }, executor);

            CompletableFuture<List<Video>> videoFuture = CompletableFuture.supplyAsync(() -> {
                LambdaQueryWrapper<Video> wrapperVideo = new LambdaQueryWrapper<>();
                wrapperVideo.eq(Video::getCourseId, courseId);
                return videoService.list(wrapperVideo);
            }, executor);

            CompletableFuture<Void> allOfFuture = CompletableFuture.allOf(chapterFuture, videoFuture);
            allOfFuture.get();

            List<Chapter> chapterList = chapterFuture.get();
            List<Video> videoList = videoFuture.get();

            chapterList.forEach(item -> {
                ChapterVo chapterVo = new ChapterVo();
                BeanUtils.copyProperties(item, chapterVo);

                chapterVoList.add(chapterVo);

                List<VideoVo> videoVoList = videoList.stream()
                        .filter(video -> video.getChapterId().equals(item.getId()))
                        .map(temp -> {
                            VideoVo videoVo = new VideoVo();
                            BeanUtils.copyProperties(temp, videoVo);
                            return videoVo;
                        })
                        .collect(Collectors.toList());

                chapterVo.setChildren(videoVoList);
            });

            return chapterVoList;
        } catch (Exception e) {
            throw new GgktException(ResultResponseEnum.CHAPTER_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void removeChapterByCourseId(long id) {
        LambdaQueryWrapper<Chapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Chapter::getCourseId, id);
        videoService.removeVideoByChapterId(id);
        this.remove(queryWrapper);
    }

    @Override
    @Transactional
    public void removeChapterById(long id) {
        videoService.removeVideoByChapterId(id);
        this.removeById(id);
    }
}
