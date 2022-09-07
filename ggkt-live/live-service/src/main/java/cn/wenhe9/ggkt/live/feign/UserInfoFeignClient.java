package cn.wenhe9.ggkt.live.feign;

import cn.wenhe9.ggkt.user.api.UserInfoApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/09/07
 */
@Component
@FeignClient(name = "ggkt-user", path = "/api/user/userInfo")
public interface UserInfoFeignClient extends UserInfoApi {
}
