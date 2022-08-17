package cn.wenhe9.ggkt.vod.service.impl;

import cn.wenhe9.ggkt.vod.entity.Teacher;
import cn.wenhe9.ggkt.vod.mapper.TeacherMapper;
import cn.wenhe9.ggkt.vod.service.TeacherService;
import cn.wenhe9.ggkt.vod.vo.TeacherQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-14
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Page<Teacher> queryByPage(long current, long limit, TeacherQueryVo teacherQueryVo) {
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        Page<Teacher> teacherPage = new Page<>(current, limit);
        if (null != teacherQueryVo) {

            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();

            queryWrapper.like(!StringUtils.isEmpty(name), Teacher::getName, name)
                    .eq(!StringUtils.isEmpty(level), Teacher::getLevel, level)
                    .ge(!StringUtils.isEmpty(joinDateBegin), Teacher::getJoinDate, joinDateBegin)
                    .le(!StringUtils.isEmpty(joinDateEnd), Teacher::getJoinDate, joinDateEnd);

        }
        return this.page(teacherPage, queryWrapper);
    }
}
