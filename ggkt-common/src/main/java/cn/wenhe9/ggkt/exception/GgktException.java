package cn.wenhe9.ggkt.exception;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import lombok.Getter;

/**
 * 自定义异常类
 * @author DuJinliang
 * 2022/08/16
 */
@Getter
public class GgktException extends RuntimeException{
    private ResultResponse<Void> resultResponse;

    public GgktException(ResultResponse<Void> resultResponse) {
        this.resultResponse = resultResponse;
    }

    public GgktException(ResultResponseEnum responseEnum) {
        this.resultResponse = ResultResponse.build(null, responseEnum);
    }
}
