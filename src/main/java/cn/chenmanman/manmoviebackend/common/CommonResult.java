package cn.chenmanman.manmoviebackend.common;

import cn.chenmanman.manmoviebackend.common.exception.ErrorEnum;
import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.common
 * @className BaseResult
 * @description 统一响应体
 * @date 2023/5/1 17:46
 */
@Data
public class CommonResult<T> {
    /**
     * 响应码
     * */
    private Long code;

    /**
     * 响应消息
     * */
    private String message;

    /**
     * 响应数据
     * */
    private T data;


    public CommonResult(Long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }


    public CommonResult(Long code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(ErrorEnum errorEnum) {
        this.code = errorEnum.getCode();
        this.message = errorEnum.getMsg();
    }

    public static  <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>(ErrorEnum.SUCCESS.getCode(), message, data);
    }

    public static  <T> CommonResult<T> success() {
        return new CommonResult<>(ErrorEnum.SUCCESS.getCode(), "请求成功", null);
    }

    public static  <T> CommonResult<T> success( T data) {
        return new CommonResult<>(ErrorEnum.SUCCESS.getCode(), ErrorEnum.SUCCESS.getMsg(), data);
    }

    public static  <T> CommonResult<T> success(String message) {
        return new CommonResult<>(ErrorEnum.SUCCESS.getCode(), message, null);
    }

    public static <T> CommonResult<T> fail(String message, Long code) {
        return new CommonResult<>(code, message, null);
    }

    public static  <T> CommonResult<T> fail(String message) {
        return new CommonResult<>(ErrorEnum.FAIL.getCode(), message, null);
    }

    public static  <T> CommonResult<T> fail(ErrorEnum errorEnum) {
        return new CommonResult<>(errorEnum.getCode(), errorEnum.getMsg(), null);
    }
}
