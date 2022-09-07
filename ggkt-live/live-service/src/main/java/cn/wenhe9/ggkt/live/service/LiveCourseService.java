package cn.wenhe9.ggkt.live.service;

import cn.wenhe9.ggkt.live.entity.LiveCourse;
import cn.wenhe9.ggkt.live.vo.LiveCourseConfigVo;
import cn.wenhe9.ggkt.live.vo.LiveCourseFormVo;
import cn.wenhe9.ggkt.live.vo.LiveCourseVo;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 直播课程表 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
public interface LiveCourseService extends IService<LiveCourse> {

    /**
     * 分页查询直播课程
     */
    Page<LiveCourse> findLiveCourseList(long current, long limit);

    /**
     * 直播课程添加
     */
    void saveLiveCourse(LiveCourseFormVo liveCourseFormVo);

    /**
     * 根据id删除直播课程
     */
    void removeLiveCourseById(long id);

    /**
     * 查询课程基本信息和描述信息
     */
    LiveCourseFormVo getLiveCourseFormVo(Long id);

    /**
     * 更新直播课程信息
     */
    void updateLiveCourseById(LiveCourseFormVo liveCourseFormVo);

    /**
     * 根据直播课程id查询配置信息
     */
    LiveCourseConfigVo getCourseConfig(Long courseId);

    /**
     * 修改直播课程配置信息
     */
    void updateLiveCourseConfig(LiveCourseConfigVo liveCourseConfigVo);

    /**
     * 获取最近的直播
     */
    List<LiveCourseVo> findLatelyLiveCourseList();

    /**
     * 获取用户access_token
     */
    JSONObject getPlayAuth(Long id, Long userId);

    /**
     * 根据ID查询课程
     */
    Map<String, Object> getInfoById(Long courseId);
}
