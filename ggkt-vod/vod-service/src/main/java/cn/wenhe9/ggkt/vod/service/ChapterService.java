package cn.wenhe9.ggkt.vod.service;

import cn.wenhe9.ggkt.vod.entity.Chapter;
import cn.wenhe9.ggkt.vod.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 大纲列表 （章节和小节列表）
     */
    List<ChapterVo> getTreeList(long courseId);

    /**
     * 根据课程id删除章节
     */
    void removeChapterByCourseId(long id);

    /**
     * 根据id删除章节
     */
    void removeChapterById(long id);
}
