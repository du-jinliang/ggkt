package cn.wenhe9.ggkt.live.service;

import cn.wenhe9.ggkt.live.entity.LiveCourseAccount;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 直播课程账号表（受保护信息） 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-27
 */
public interface LiveCourseAccountService extends IService<LiveCourseAccount> {

    /**
     * 根据直播课程id查询账号信息
     */
    LiveCourseAccount getLiveCourseAccountByLiveCourseId(Long courseId);
}
