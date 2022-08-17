package cn.wenhe9.ggkt.vod.service;

import cn.wenhe9.ggkt.vod.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-17
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 课程分类列表
     */
    List<Subject> selectSubjectList(long id);

    /**
     * 课程分类导出
     */
    void exportData(HttpServletResponse response);

    /**
     * 课程分类导入
     */
    void importData(MultipartFile file);
}
