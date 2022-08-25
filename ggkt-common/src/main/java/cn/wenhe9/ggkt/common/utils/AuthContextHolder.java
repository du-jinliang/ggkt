package cn.wenhe9.ggkt.common.utils;


/**
 * 获取登录用户信息类
 */
public class AuthContextHolder {

    /**
     * 后台管理用户id
     */
    private static ThreadLocal<Long> adminIdTL = new ThreadLocal<Long>();

    /**
     * 会员用户id
     */
    private static ThreadLocal<Long> userIdTL = new ThreadLocal<Long>();

    public static Long getAdminId() {
        return adminIdTL.get();
    }

    public static void setAdminId(Long adminId) {
        adminIdTL.set(adminId);
    }

    public static Long getUserId(){
        return userIdTL.get();
    }

    public static void setUserId(Long userId){
        userIdTL.set(userId);
    }

}
