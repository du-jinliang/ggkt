package cn.wenhe9.ggkt.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 统一的返回结果
 * @author DuJinliang
 * 2022/08/16
 */
@Setter
@Getter
public class ResultResponse<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 返回状态信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    public ResultResponse() {

    }

    protected static <T> ResultResponse<T> build(T data) {
        ResultResponse<T> resultResponse = new ResultResponse<>();
        resultResponse.setData(data);
        return resultResponse;
    }

    public static <T> ResultResponse<T> build(T data, ResultResponseEnum responseEnum) {
        ResultResponse<T> resultResponse = build(data);
        resultResponse.setCode(responseEnum.getCode());
        resultResponse.setMessage(responseEnum.getMessage());
        return resultResponse;
    }

    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> resultResponse = build(data, ResultResponseEnum.SUCCESS);
        return resultResponse;
    }

    public static <T> ResultResponse<T> success() {
        ResultResponse<T> resultResponse = build(null, ResultResponseEnum.SUCCESS);
        return resultResponse;
    }

    public static <T> ResultResponse<T> fail(T data){
        ResultResponse<T> resultResponse = build(data, ResultResponseEnum.FAIL);
        return resultResponse;
    }

    public static <T> ResultResponse<T> fail(){
        ResultResponse<T> resultResponse = build(null, ResultResponseEnum.FAIL);
        return resultResponse;
    }

    public ResultResponse<T> code(Integer code){
        this.code = code;
        return this;
    }

    public ResultResponse<T> message(String message){
        this.message = message;
        return this;
    }
}
