package club.dulaoshi.blog.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author djg
 * @date 2019/5/7 17:31
 * @des
 */
@Data
public class Result<T> implements Serializable {
    /**
     * 错误码,默认值0位成功
     */
    protected int code = 0;

    /**
     * 错误消息
     */
    protected String msg;

    /**
     * 返回的实体类
     */
    private T data;

    public Result() {
        super();
    }

    public Result(int code, String errorMsg, T data) {
        this.code = code;
        this.msg = errorMsg;
        this.data = data;
    }

    /**
     * 重写toString方法
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Result { \n");
        sb.append("        errorCode    = ").append(code).append(",\n");
        sb.append("        errorMsg = ").append(msg);
        sb.append("\n}");
        return sb.toString();
    }

    public static <T> Result<T> success() {
        return new Result<>();
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.data = data;
        return result;
    }

    public static <T> Result<T> fail(int code) {
        Result<T> result = new Result<>();
        result.code = code;
        result.msg = ResultCode.getMessage(code);
        return result;
    }

    public static <T> Result<T> fail(int code, String errorMsg) {
        Result<T> result = new Result<>();
        result.code = code;
        result.msg = errorMsg;
        return result;
    }
}
