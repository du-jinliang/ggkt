package cn.wenhe9.ggkt.vod.listener;

import cn.wenhe9.ggkt.vod.entity.Subject;
import cn.wenhe9.ggkt.vod.mapper.SubjectMapper;
import cn.wenhe9.ggkt.vod.vo.SubjectEeVo;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author DuJinliang
 * 2022/08/17
 */
@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {

    private ThreadLocal<List<Subject>> subjectTL = new ThreadLocal<>();

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {
        List<Subject> subjectList = subjectTL.get();
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            subjectTL.set(subjectList);
        }
        Subject subject = new Subject();
        //SubjectEeVo -> Subject
        BeanUtils.copyProperties(subjectEeVo, subject);
        //添加
        subjectList.add(subject);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        try {
            List<Subject> subjectList = subjectTL.get();
            subjectMapper.insertBatch(subjectList);
        }finally {
            subjectTL.remove();
        }
    }
}
