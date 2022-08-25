package cn.wenhe9.ggkt.common.constant;

/**
 * @author DuJinliang
 * 2022/08/25
 * 拦截路径常量
 */
public class SystemConfigConstant {
    /**
     * api路径
     */
    public static final String ADMIN_URL = "/api/**";

    /**
     * admin路径
     */
    public static final String API_URL = "/admin/**";

    /**
     * admin后台系统token
     */
    public static final String ADMIN_TOKEN = "admin-token";

    /**
     * http请求认证header
     */
    public static final String HTTP_AUTH_HEADER_NAME = "token";

    /**
     * 登陆url,网关拦截时需要放行
     */
    public static final String LOGIN_URL = "/**/login";

    /**
     * 微信公众号消息接口，网关拦截时需要放行
     */
    public static final String WECHAT_MESSAGE_URL = "/api/wechat/message";
}
