package cn.wenhe9.ggkt.gateway.auth;

import cn.wenhe9.ggkt.common.constant.SystemConfigConstant;
import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.common.utils.AuthContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author DuJinliang
 * 2022/08/25
 * 后台系统 认证策略
 */
@Component
public class AdminAuthStrategy implements AuthStrategy{
    @Override
    public void auth(String token) {
        if (!StringUtils.hasText(token)) {
            throw new GgktException(ResultResponseEnum.LOGIN_AUTH);
        }
        if (!SystemConfigConstant.ADMIN_TOKEN.equals(token)) {
            throw new GgktException(ResultResponseEnum.LOGIN_AUTH);
        }
        AuthContextHolder.setAdminId(1L);
    }
}
