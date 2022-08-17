package cn.wenhe9.ggkt.vod.service.impl;

import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.exception.GgktException;
import cn.wenhe9.ggkt.vod.entity.Subject;
import cn.wenhe9.ggkt.vod.listener.SubjectListener;
import cn.wenhe9.ggkt.vod.mapper.SubjectMapper;
import cn.wenhe9.ggkt.vod.service.SubjectService;
import cn.wenhe9.ggkt.vod.vo.SubjectEeVo;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author DuJinliang
 * @since 2022-08-17
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Resource
    private SubjectListener subjectListener;

    @Override
    public List<Subject> selectSubjectList(long id) {
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subject::getParentId, id);
        List<Subject> subjectList = this.list(queryWrapper);
        subjectList.forEach(item -> {
            Long subjectId = item.getId();
            boolean hasChildren = this.hasChildren(subjectId);
            item.setHasChildren(hasChildren);
        });
        return subjectList;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //防止中文乱码
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            //查询课程分类所有数据
            List<Subject> subjectList = this.list();

            // List<Subject> -> List<SubjectEeVo>
            List<SubjectEeVo> subjectEeVoList = subjectList.stream().map(subject -> {
                SubjectEeVo subjectEeVo = new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
                return subjectEeVo;
            }).collect(Collectors.toList());

            //EasyExcel 写操作
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVoList);
        }catch (Exception e) {
            throw new GgktException(ResultResponseEnum.EXPORT_DATA_ERROR);
        }
    }

    @Override
    public void importData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class, subjectListener).sheet().doRead();
        } catch (IOException e) {
            throw new GgktException(ResultResponseEnum.IMPORT_DATA_ERROR);
        }
    }


    private boolean hasChildren(long id){

        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Subject::getParentId, id);

        int count = this.count(queryWrapper);

        return count > 0;
    }
}
