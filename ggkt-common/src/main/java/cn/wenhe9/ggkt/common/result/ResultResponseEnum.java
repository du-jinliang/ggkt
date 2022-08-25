package cn.wenhe9.ggkt.common.result;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * 全局统一返回结果类
 * @author DuJinliang
 * 2022/08/16
 */
@Getter
@ApiModel("全局统一返回结果类")
public enum ResultResponseEnum {
    SUCCESS(20000,"成功"),
    FAIL(20001, "失败"),

    SERVICE_ERROR(2012, "服务异常"),

    DATA_ERROR(204, "数据异常"),

    ILLEGAL_REQUEST(205, "非法请求"),

    REPEAT_SUBMIT(206, "重复提交"),

    LOGIN_AUTH(208, "未登陆"),

    PERMISSION(209, "没有权限"),

    PHONE_CODE_ERROR(211, "手机验证码错误"),

    MTCLOUD_ERROR(210, "直播接口异常"),

    COUPON_GET(220, "优惠券已经领取"),

    COUPON_LIMIT_GET(221, "优惠券已发放完毕"),

    EXPORT_DATA_ERROR(21002, "导出失败"),
    IMPORT_DATA_ERROR(21003, "导入失败"),

    FILE_UPLOAD_ERROR( 21004, "文件上传错误"),
    FILE_DELETE_ERROR( 21005, "文件刪除错误"),

    COURSE_NOT_FOUND(21006, "课程没有找到"),
    SIGN_GET_ERROR(21007, "获取签名失败"),
    GET_ACCESS_TOKEN_ERROR(21008, "获取accessToken失败"),

    SYNC_MENU_ERROR(21009, "同步菜单失败"),

    CHAPTER_NOT_FOUND(21010, "获取章节失败"),

    VIDEO_NOT_FOUND(21011, "获取小节失败"),

    VOD_PALY_ERROR(209, "请购买后观看");


    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回状态信息
     */
    private String message;

    ResultResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
