package cn.wenhe9.ggkt.exception;

import cn.wenhe9.ggkt.common.result.ResultResponse;
import cn.wenhe9.ggkt.common.result.ResultResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理类
 * @author DuJinliang
 * 2022/08/16
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常 GgktException
     */
    @ExceptionHandler(value = {GgktException.class})
    public ResultResponse<Void> handlerMemException(GgktException e){
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        //打印异常信息
        log.error(
                "在{}类的{}方法的第{}行，出现{}异常",
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                stackTraceElement.getLineNumber(),
                e.getResultResponse().getMessage()
        );
        return e.getResultResponse();
    }

    /**
     * 处理 throwable 异常
     */
    @ExceptionHandler(value = {Throwable.class})
    public ResultResponse handlerException(Throwable e) {
        StackTraceElement stackTraceElement = e.getStackTrace()[0];
        //打印异常信息
        log.error(
                "在{}类的{}方法的第{}行，出现{}异常",
                stackTraceElement.getClassName(),
                stackTraceElement.getMethodName(),
                stackTraceElement.getLineNumber(),
                e.getMessage()
        );
        return ResultResponse.fail().message(ResultResponseEnum.SERVICE_ERROR.getMessage());
    }

}
