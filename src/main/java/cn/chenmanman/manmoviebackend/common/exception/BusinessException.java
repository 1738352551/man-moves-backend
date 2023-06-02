package cn.chenmanman.manmoviebackend.common.exception;

import lombok.Data;

/**
 * @author 陈慢慢
 * @version 1.0
 * @projectName man-moves-backend
 * @package cn.chenmanman.manmoviebackend.common.exception
 * @className BusinessException
 * @description 自定义业务异常
 * @date 2023/5/1 17:37
 */
@Data
public class BusinessException extends RuntimeException {
    /**
     *
     * 错误状态码
     * */
    private Long code;


    public BusinessException(String message, Long code) {
        super(message);
        this.code = code;
    }


    public BusinessException(ErrorEnum errorEnum) {
        super(errorEnum.getMsg());
        this.code = errorEnum.getCode();
    }


    public BusinessException() {}
}
