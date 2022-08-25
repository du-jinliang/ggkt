package cn.wenhe9.ggkt.gateway.auth;

import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.common.utils.AuthContextHolder;
import cn.wenhe9.ggkt.common.utils.JwtHelper;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author DuJinliang
 * 2022/08/25
 * 策略模式实现用户认证功能
 */
public interface AuthStrategy {
    /**
     * 用户请求验证
     */
    void auth(String token);


    /**
     * 默认验证方法
     */
    default Long authSSO(String token) {
        if (!StringUtils.hasText(token)) {
            // token 为空
            throw new GgktException(ResultResponseEnum.LOGIN_AUTH);
        }
        Long userId = JwtHelper.getUserId(token);
        if (Objects.isNull(userId)) {
            // token 解析失败
            throw new GgktException(ResultResponseEnum.LOGIN_AUTH);
        }
        // 设置 userId 到当前线程
        AuthContextHolder.setUserId(userId);
        // 返回 userId
        return userId;
    }
}
