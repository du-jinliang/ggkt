package cn.wenhe9.ggkt.gateway.filter;

import cn.wenhe9.ggkt.common.constant.SystemConfigConstant;
import cn.wenhe9.ggkt.common.exception.GgktException;
import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import cn.wenhe9.ggkt.common.utils.AuthContextHolder;
import cn.wenhe9.ggkt.gateway.auth.AuthStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author DuJinliang
 * 2022/08/25
 */
@Slf4j
@Component
public class GlobalAuthFilter implements GlobalFilter, Ordered {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private Map<String, AuthStrategy> authStrategy;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (HttpMethod.OPTIONS.equals(request.getMethod())) {
            return chain.filter(exchange);
        }

        String token = request.getHeaders().getFirst(SystemConfigConstant.HTTP_AUTH_HEADER_NAME);

        String path = request.getURI().getPath();
        log.info("path={}", path);

        if (
                antPathMatcher.match(SystemConfigConstant.LOGIN_URL, path)
                        ||
                antPathMatcher.match(SystemConfigConstant.WECHAT_MESSAGE_URL, path)
        ) {
            return chain.filter(exchange);
        }

        String authStrategyName = path.substring(1, path.indexOf("/", 2));
        authStrategyName = String.format("%sAuthStrategy", authStrategyName);

        try {
            authStrategy.get(authStrategyName).auth(token);
            return chain.filter(exchange);
        } catch (GgktException e) {
            return loginAuth(exchange);
        }
    }

    private Mono<Void> out(ServerHttpResponse response, ResultResponseEnum resultCodeEnum) throws JsonProcessingException {
        ResultResponse<Object> result = ResultResponse.build(null, resultCodeEnum);
        byte[] bytes = objectMapper.writeValueAsString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    public Mono<Void> loginAuth(ServerWebExchange exchange){
        ServerHttpResponse response = exchange.getResponse();
        try {
            return out(response, ResultResponseEnum.LOGIN_AUTH);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new GgktException(ResultResponseEnum.FAIL);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
