package cn.wenhe9.ggkt.live.service.impl;

import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.live.entity.*;
import cn.wenhe9.ggkt.live.feign.TeacherFeignClient;
import cn.wenhe9.ggkt.live.feign.UserInfoFeignClient;
import cn.wenhe9.ggkt.live.mapper.LiveCourseMapper;
import cn.wenhe9.ggkt.live.mtcloud.CommonResult;
import cn.wenhe9.ggkt.live.mtcloud.MTCloud;
import cn.wenhe9.ggkt.live.service.*;
import cn.wenhe9.ggkt.live.utils.DateUtil;
import cn.wenhe9.ggkt.live.vo.LiveCourseConfigVo;
import cn.wenhe9.ggkt.live.vo.LiveCourseFormVo;
import cn.wenhe9.ggkt.live.vo.LiveCourseGoodsView;
import cn.wenhe9.ggkt.live.vo.LiveCourseVo;
import cn.wenhe9.ggkt.user.entity.UserInfo;
import cn.wenhe9.ggkt.vod.entity.Teacher;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * <p>
 * 直播课程表 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
@Slf4j
@Service
public class LiveCourseServiceImpl extends ServiceImpl<LiveCourseMapper, LiveCourse> implements LiveCourseService {

    @Resource
    private TeacherFeignClient teacherFeignClient;

    @Resource
    private LiveCourseDescriptionService liveCourseDescriptionService;

    @Resource
    private LiveCourseAccountService liveCourseAccountService;

    @Resource
    private LiveCourseConfigService liveCourseConfigService;

    @Resource
    private LiveCourseGoodsService liveCourseGoodsService;
    
    @Resource
    private UserInfoFeignClient userInfoFeignClient;

    @Resource
    private MTCloud mtCloudClient;

    @Resource
    private Executor executor;

    @Override
    public Page<LiveCourse> findLiveCourseList(long current, long limit) {
        Page<LiveCourse> coursePage = this.page(new Page<>(current, limit));

        List<LiveCourse> liveCourseList = coursePage.getRecords();

        for (LiveCourse liveCourse : liveCourseList) {
            Long teacherId = liveCourse.getTeacherId();

            Teacher teacher = teacherFeignClient.findTeacherInfo(teacherId);

            if (Objects.nonNull(teacher)) {
                liveCourse.getParam().put("teacherName", teacher.getName());
                liveCourse.getParam().put("teacherLevel", teacher.getLevel());
            }
        }
        return coursePage;
    }

    @Override
    @Transactional
    public void saveLiveCourse(LiveCourseFormVo liveCourseFormVo) {
        LiveCourse liveCourse = new LiveCourse();
        BeanUtils.copyProperties(liveCourseFormVo, liveCourse);

        // 获取讲师信息
        Teacher teacher = teacherFeignClient.findTeacherInfo(liveCourse.getTeacherId());

        // 调用方法添加直播课程
        // 创建map集合，封装直播课程其他参数
        HashMap<Object, Object> options = new HashMap<>();
        //直播类型。1: 教育直播，2: 生活直播。默认 1，说明：根据平台开通的直播类型填写
        options.put("scenes", 2);
        options.put("password", liveCourseFormVo.getPassword());
        try {
            String res = mtCloudClient.courseAdd(
                    liveCourse.getCourseName(),
                    teacher.getId().toString(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getIntro(),
                    options
            );

            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                // 添加直播基本信息
                CompletableFuture<Void> liveCourseFuture = CompletableFuture.runAsync(() -> {
                    liveCourse.setCourseId(object.getLong("course_id"));
                    baseMapper.insert(liveCourse);
                }, executor);

                // 添加直播描述信息
                CompletableFuture<Void> liveCourseDescriptionFuture = CompletableFuture.runAsync(() -> {
                    LiveCourseDescription liveCourseDescription = LiveCourseDescription
                            .builder()
                            .description(liveCourseFormVo.getDescription())
                            .liveCourseId(liveCourse.getId())
                            .build();
                    liveCourseDescriptionService.save(liveCourseDescription);
                }, executor);

                //保存课程账号信息
                CompletableFuture<Void> liveCourseAccountFuture = CompletableFuture.runAsync(() -> {
                    LiveCourseAccount liveCourseAccount = LiveCourseAccount
                            .builder()
                            .liveCourseId(liveCourse.getId())
                            .zhuboAccount(object.getString("bid"))
                            .zhuboPassword(liveCourseFormVo.getPassword())
                            .adminKey(object.getString("admin_key"))
                            .userKey(object.getString("user_key"))
                            .zhuboKey(object.getString("zhubo_key"))
                            .build();
                    liveCourseAccountService.save(liveCourseAccount);
                }, executor);

                // 统一异常处理
                CompletableFuture
                        .allOf(liveCourseFuture, liveCourseDescriptionFuture, liveCourseAccountFuture)
                        .exceptionally(e -> {
                            log.error(e.getMessage());
                            throw new GgktException(ResultResponseEnum.LIVE_SAVE_ERROR);
                        });
            } else {
                log.error(commonResult.getmsg());
                throw new GgktException(ResultResponseEnum.LIVE_SAVE_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GgktException(ResultResponseEnum.LIVE_SAVE_ERROR);
        }
    }

    @Override
    @Transactional
    public void removeLiveCourseById(long id) {
        // 根据id查询直播课程信息
        LiveCourse liveCourse = this.getById(id);
        if (Objects.isNull(liveCourse)) {
            throw new GgktException(ResultResponseEnum.COURSE_NOT_FOUND);
        }
        // 获取直播 courseId
        Long courseId = liveCourse.getCourseId();
        // 调用方法删除直播课程
        CompletableFuture<Void> cloudCourseFuture = CompletableFuture.runAsync(() -> {
            try {
                mtCloudClient.courseDelete(courseId.toString());
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new GgktException(ResultResponseEnum.LIVE_COURSE_REMOVE_ERROR);
            }
        }, executor);
        // 删除直播课程表数据
        CompletableFuture<Void> liveCourseFuture = CompletableFuture.runAsync(() -> {
            this.removeById(id);
        }, executor);
        // 删除直播课程描述数据
        CompletableFuture<Void> liveCourseDescriptionFuture = CompletableFuture.runAsync(() -> {
            liveCourseDescriptionService.removeLiveCourseDescriptionByLiveCourseId(courseId);
        }, executor);

        CompletableFuture
                .allOf(cloudCourseFuture, liveCourseFuture, liveCourseDescriptionFuture)
                .exceptionally(e -> {
                    throw new GgktException(ResultResponseEnum.LIVE_COURSE_REMOVE_ERROR);
                });
    }

    @Override
    public LiveCourseFormVo getLiveCourseFormVo(Long id) {
        LiveCourse liveCourse = this.getById(id);
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getByLiveCourseId(id);

        LiveCourseFormVo liveCourseFormVo = new LiveCourseFormVo();
        BeanUtils.copyProperties(liveCourse, liveCourseFormVo);
        liveCourseFormVo.setDescription(liveCourseDescription.getDescription());
        return liveCourseFormVo;
    }

    @Override
    public void updateLiveCourseById(LiveCourseFormVo liveCourseFormVo) {
        //根据id获取直播课程基本信息
        LiveCourse liveCourse = this.getById(liveCourseFormVo.getId());
        BeanUtils.copyProperties(liveCourseFormVo,liveCourse);
        //讲师
        Teacher teacher = teacherFeignClient.findTeacherInfo(liveCourseFormVo.getTeacherId());

        HashMap<Object, Object> options = new HashMap<>();
        try {
            String res = mtCloudClient.courseUpdate(
                    liveCourse.getCourseId().toString(),
                    teacher.getId().toString(),
                    liveCourse.getCourseName(),
                    new DateTime(liveCourse.getStartTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    new DateTime(liveCourse.getEndTime()).toString("yyyy-MM-dd HH:mm:ss"),
                    teacher.getName(),
                    teacher.getIntro(),
                    options
            );
            //返回结果转换，判断是否成功
            CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
            if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
                JSONObject object = commonResult.getData();
                //更新直播课程基本信息
                liveCourse.setCourseId(object.getLong("course_id"));
                this.updateById(liveCourse);
                //直播课程描述信息更新
                LiveCourseDescription liveCourseDescription =
                        liveCourseDescriptionService.getByLiveCourseId(liveCourse.getId());
                liveCourseDescription.setDescription(liveCourseFormVo.getDescription());
                liveCourseDescriptionService.updateById(liveCourseDescription);
            } else {
                throw new GgktException(ResultResponseEnum.COURSE_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public LiveCourseConfigVo getCourseConfig(Long courseId) {
        LiveCourseConfigVo liveCourseConfigVo = new LiveCourseConfigVo();
        LiveCourseConfig liveCourseConfig = liveCourseConfigService.getLiveCourseConfigByCourseId(courseId);
        if(Objects.nonNull(liveCourseConfig)) {
            List<LiveCourseGoods> liveCourseGoodsList = liveCourseGoodsService.getLiveCourseGoodsByLiveCourseId(courseId);
            BeanUtils.copyProperties(liveCourseConfig, liveCourseConfigVo);
            liveCourseConfigVo.setLiveCourseGoodsList(liveCourseGoodsList);
        }
        return liveCourseConfigVo;
    }

    @Override
    public void updateLiveCourseConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourseConfig liveCourseConfigUpt = new LiveCourseConfig();
        BeanUtils.copyProperties(liveCourseConfigVo, liveCourseConfigUpt);
        if(Objects.isNull(liveCourseConfigVo.getId())) {
            liveCourseConfigService.save(liveCourseConfigUpt);
        } else {
            liveCourseConfigService.updateById(liveCourseConfigUpt);
        }
        LambdaQueryWrapper<LiveCourseGoods> queryWrapper = new LambdaQueryWrapper<LiveCourseGoods>();
        queryWrapper.eq(LiveCourseGoods::getLiveCourseId, liveCourseConfigVo.getLiveCourseId());
        liveCourseGoodsService.remove(queryWrapper);
        if(!CollectionUtils.isEmpty(liveCourseConfigVo.getLiveCourseGoodsList())) {
            liveCourseGoodsService.saveBatch(liveCourseConfigVo.getLiveCourseGoodsList());
        }
        this.updateLifeConfig(liveCourseConfigVo);
    }

    @Override
    public List<LiveCourseVo> findLatelyLiveCourseList() {
        List<LiveCourseVo> liveCourseVoList = baseMapper.findLatelyLiveCourseList();

        liveCourseVoList.forEach(item -> {
            Long teacherId = item.getTeacherId();
            Teacher teacher = teacherFeignClient.findTeacherInfo(teacherId);
            item.setTeacher(teacher);
        });

        return liveCourseVoList;
    }

    @SneakyThrows
    @Override
    public JSONObject getPlayAuth(Long id, Long userId) {
        LiveCourse liveCourse = this.getById(id);
        UserInfo userInfo = userInfoFeignClient.getUserInfoById(userId);
        HashMap<Object,Object> options = new HashMap<Object, Object>();
        String res = mtCloudClient.courseAccess(liveCourse.getCourseId().toString(), userId.toString(), userInfo.getNickName(), MTCloud.ROLE_USER, 80*80*80, options);
        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if(Integer.parseInt(commonResult.getCode()) == MTCloud.CODE_SUCCESS) {
            JSONObject object = commonResult.getData();
            System.out.println("access::"+object.getString("access_token"));
            return object;
        } else {
            throw new GgktException(ResultResponseEnum.GET_ACCESS_TOKEN_ERROR);
        }
    }

    @Override
    public Map<String, Object> getInfoById(Long courseId) {
        LiveCourse liveCourse = this.getById(courseId);
        liveCourse.getParam().put("startTimeString", new DateTime(liveCourse.getStartTime()).toString("yyyy年MM月dd HH:mm"));
        liveCourse.getParam().put("endTimeString", new DateTime(liveCourse.getEndTime()).toString("yyyy年MM月dd HH:mm"));
        Teacher teacher = teacherFeignClient.findTeacherInfo(liveCourse.getTeacherId());
        LiveCourseDescription liveCourseDescription = liveCourseDescriptionService.getByLiveCourseId(courseId);

        Map<String, Object> map = new HashMap<>();
        map.put("liveCourse", liveCourse);
        map.put("liveStatus", this.getLiveStatus(liveCourse));
        map.put("teacher", teacher);
        if(null != liveCourseDescription) {
            map.put("description", liveCourseDescription.getDescription());
        } else {
            map.put("description", "");
        }
        return map;
    }



    /**
     * 直播状态 0：未开始 1：直播中 2：直播结束
     */
    private int getLiveStatus(LiveCourse liveCourse) {
        // 直播状态 0：未开始 1：直播中 2：直播结束
        int liveStatus = 0;
        Date curTime = new Date();
        if(DateUtil.dateCompare(curTime, liveCourse.getStartTime())) {
            liveStatus = 0;
        } else if(DateUtil.dateCompare(curTime, liveCourse.getEndTime())) {
            liveStatus = 1;
        } else {
            liveStatus = 2;
        }
        return liveStatus;
    }

    /**
     * 上传直播配置
     */
    @SneakyThrows
    private void updateLifeConfig(LiveCourseConfigVo liveCourseConfigVo) {
        LiveCourse liveCourse = this.getById(liveCourseConfigVo.getLiveCourseId());

        //参数设置
        HashMap<Object,Object> options = new HashMap<Object, Object>();
        //界面模式
        options.put("pageViewMode", liveCourseConfigVo.getPageViewMode());
        //观看人数开关
        JSONObject number = new JSONObject();
        number.put("enable", liveCourseConfigVo.getNumberEnable());
        options.put("number", number.toJSONString());
        //观看人数开关
        JSONObject store = new JSONObject();
        number.put("enable", liveCourseConfigVo.getStoreEnable());
        number.put("type", liveCourseConfigVo.getStoreType());
        options.put("store", number.toJSONString());
        //商城列表
        List<LiveCourseGoods> liveCourseGoodsList = liveCourseConfigVo.getLiveCourseGoodsList();
        if(!CollectionUtils.isEmpty(liveCourseGoodsList)) {
            List<LiveCourseGoodsView> liveCourseGoodsViewList = new ArrayList<>();
            for(LiveCourseGoods liveCourseGoods : liveCourseGoodsList) {
                LiveCourseGoodsView liveCourseGoodsView = new LiveCourseGoodsView();
                BeanUtils.copyProperties(liveCourseGoods, liveCourseGoodsView);
                liveCourseGoodsViewList.add(liveCourseGoodsView);
            }
            JSONObject goodsListEdit = new JSONObject();
            goodsListEdit.put("status", "0");
            options.put("goodsListEdit ", goodsListEdit.toJSONString());
            options.put("goodsList", JSON.toJSONString(liveCourseGoodsViewList));
        }

        String res = mtCloudClient.courseUpdateLifeConfig(liveCourse.getCourseId().toString(), options);

        CommonResult<JSONObject> commonResult = JSON.parseObject(res, CommonResult.class);
        if(Integer.parseInt(commonResult.getCode()) != MTCloud.CODE_SUCCESS) {
            throw new GgktException(ResultResponseEnum.LIVE_COURSE_CONFIG_UPDATE_ERROR);
        }
    }
}
