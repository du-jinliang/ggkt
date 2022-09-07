package cn.wenhe9.ggkt.vod.api;

import cn.wenhe9.ggkt.vod.entity.Teacher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author DuJinliang
 * 2022/08/28
 */
public interface TeacherApi {
    @GetMapping("/inner/info/{id}")
    Teacher findTeacherInfo(@PathVariable(name = "id") long id);
}
