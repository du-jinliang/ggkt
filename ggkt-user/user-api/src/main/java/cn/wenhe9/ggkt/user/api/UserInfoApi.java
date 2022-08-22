package cn.wenhe9.ggkt.user.api;

import cn.wenhe9.ggkt.user.entity.UserInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author DuJinliang
 * 2022/08/21
 */
public interface UserInfoApi {
    /**
     * 根据id获取用户信息
     */
    @GetMapping("/inner/{id}")
    public UserInfo getUserInfoById(@PathVariable(name = "id") long id);
}
