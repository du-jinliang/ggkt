package cn.wenhe9.ggkt.gateway.auth;

import org.springframework.stereotype.Component;

/**
 * @author DuJinliang
 * 2022/08/25
 * 前台系统 认证策略
 */
@Component
public class ApiAuthStrategy implements AuthStrategy{

    @Override
    public void auth(String token) {
        authSSO(token);
    }
}
