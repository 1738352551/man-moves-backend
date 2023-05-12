package cn.chenmanman.manmoviebackend.common.exception;

import cn.chenmanman.manmoviebackend.common.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.common.exception
 * @className GlobalExceptionHandler
 * @description 全局异常处理器，捕获项目中出现的错误。
 * @date 2023/5/13 1:02
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public CommonResult<?> businessExceptionHandle(BusinessException businessException) {
        log.debug("业务异常: {} - {}", businessException.getMessage(), businessException.getCause());
        return CommonResult.fail(businessException.getMessage(), businessException.getCode());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<?> exceptionHandle(Exception exception) {
        log.debug("未知异常: {} - {}", exception.getMessage(), exception.getCause());
        return CommonResult.fail(exception.getMessage());
    }
}
