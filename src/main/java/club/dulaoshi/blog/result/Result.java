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
    protected int success = 0;

    /**
     * 错误消息
     */
    protected String errorMsg;

    /**
     * 返回的实体类
     */
    private T data;

    public Result() {
        super();
    }

    public Result(int errorCode, String errorMsg, T data) {
        this.success = errorCode;
        this.errorMsg = errorMsg;
        this.data = data;
    }

    /**
     * 重写toString方法
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Result { \n");
        sb.append("        errorCode    = ").append(success).append(",\n");
        sb.append("        errorMsg = ").append(errorMsg);
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

    public static <T> Result<T> fail(int errorCode) {
        Result<T> result = new Result<>();
        result.success = errorCode;
        result.errorMsg = ResultCode.getMessage(errorCode);
        return result;
    }

    public static <T> Result<T> fail(int errorCode, String errorMsg) {
        Result<T> result = new Result<>();
        result.success = errorCode;
        result.errorMsg = errorMsg;
        return result;
    }
}
