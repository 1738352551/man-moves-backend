package cn.chenmanman.manmoviebackend.common.exception;



public enum ErrorEnum {
    FAIL(500L, "系统异常!"),
    SUCCESS(200L, "成功!");
    private Long code;

    private String msg;

    ErrorEnum(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
