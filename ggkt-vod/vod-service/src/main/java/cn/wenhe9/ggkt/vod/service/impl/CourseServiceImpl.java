package cn.wenhe9.ggkt.vod.service.impl;

import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.vod.entity.Course;
import cn.wenhe9.ggkt.vod.entity.CourseDescription;
import cn.wenhe9.ggkt.vod.entity.Subject;
import cn.wenhe9.ggkt.vod.entity.Teacher;
import cn.wenhe9.ggkt.vod.mapper.CourseMapper;
import cn.wenhe9.ggkt.vod.service.*;
import cn.wenhe9.ggkt.vod.vo.CourseFormVo;
import cn.wenhe9.ggkt.vod.vo.CoursePublishVo;
import cn.wenhe9.ggkt.vod.vo.CourseQueryVo;
import cn.wenhe9.ggkt.vod.vo.CourseVo;
import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-18
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Resource
    private TeacherService teacherService;

    @Resource
    private SubjectService subjectService;

    @Resource
    private CourseDescriptionService descriptionService;

    @Resource
    private ChapterService chapterService;

    @Resource
    private VideoService videoService;

    @Resource
    private ThreadPoolExecutor executor;

    @Override
    public Map<String , Object> findPageCourse(long current, long limit, CourseQueryVo courseQueryVo) {
        Page<CourseVo> courseVoPage = baseMapper.selectCourseVoById(new Page<>(current, limit), courseQueryVo);

        long totalCount = courseVoPage.getTotal();
        long totalPage = courseVoPage.getPages();
        List<CourseVo> list = courseVoPage.getRecords();

        Map<String , Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", list);

        return map;
    }

    @Override
    @Transactional
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);

        //添加课程基本信息
        this.save(course);

        //添加课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        //设置课程id
        courseDescription.setId(course.getId());

        descriptionService.save(courseDescription);

        return course.getId();
    }

    @Override
    public CourseFormVo getCourseInfoById(long id) {
        Course course = this.getById(id);

        if (null == course) {
            throw new GgktException(ResultResponseEnum.COURSE_NOT_FOUND);
        }

        CourseDescription courseDescription = descriptionService.getById(id);

        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);

        if (null != courseDescription) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }

        return courseFormVo;
    }

    @Override
    @Transactional
    public void updateCourseById(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);

        this.updateById(course);

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseFormVo.getId());
        String description = courseFormVo.getDescription();
        if (null == description) {
            courseDescription.setDescription("");
        }else {
            courseDescription.setDescription(description);
        }

        descriptionService.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishVo(long id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public void publishCourse(long id) {
        Course course = this.getById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        this.updateById(course);
    }

    @Override
    @Transactional
    public void removeCourseById(long id) {
        CompletableFuture.runAsync(() -> {
            //根据课程id删除小节
            videoService.removeVideoByCourseId(id);
        }, executor).thenRunAsync(() -> {
            //根据课程id删除章节
            chapterService.removeChapterByCourseId(id);
        }, executor).thenRunAsync(() -> {
            //根据课程id删除课程描述
            descriptionService.removeById(id);
        }, executor).thenRunAsync(() -> {
            //根据课程id删除课程
            this.removeById(id);
        }, executor);
    }
}
