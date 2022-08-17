package cn.wenhe9.ggkt.vod.mapper;

import cn.wenhe9.ggkt.vod.entity.Subject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-17
 */
public interface SubjectMapper extends BaseMapper<Subject> {
    /**
     * 批量插入数据
     */
    void insertBatch(@Param("subjectList") List<Subject> subjectList);
}
