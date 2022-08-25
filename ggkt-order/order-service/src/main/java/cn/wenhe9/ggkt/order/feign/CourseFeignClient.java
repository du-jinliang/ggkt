package cn.wenhe9.ggkt.order.feign;

import cn.wenhe9.ggkt.vod.api.CourseApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@Component
@FeignClient(value = "ggkt-vod", path = "/api/vod/course")
public interface CourseFeignClient extends CourseApi {
}
