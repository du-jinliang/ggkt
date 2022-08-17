package cn.wenhe9.ggkt.vod.service;

import cn.wenhe9.ggkt.vod.entity.Teacher;
import cn.wenhe9.ggkt.vod.vo.TeacherQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-14
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 分页查询讲师
     */
    Page<Teacher> queryByPage(long current, long limit, TeacherQueryVo teacherQueryVo);
}
