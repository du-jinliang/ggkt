package cn.wenhe9.ggkt.live.feign;

import cn.wenhe9.ggkt.vod.api.TeacherApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/28
 */
@Component
@FeignClient(name = "ggkt-vod", path = "/api/vod/teacher")
public interface TeacherFeignClient extends TeacherApi {
}
