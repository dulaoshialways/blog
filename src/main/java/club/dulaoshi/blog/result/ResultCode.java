package club.dulaoshi.blog.result;


/**
 * 返回结果枚举类
 *
 * @author djg
 */
public enum ResultCode {

    /**
     * 默认成功
     */
    SUCCESS(0, "成功"),

    /**
     * 1XXXX,是参数、数据转换相关异常
     */

    DATA_FORMAT_ERROR(10001, "数据格式有误"),
    PARAMETER_EMPTY(10002, "参数为空"),
    PARAMETER_INVALID(10003, "请求参数无效"),
    PARAMETER_PAGE(10004, "分页参数异常"),
    PARAMETER_NOT_STANDARD(10005, "参数不规范"),
    EXCEPTION_CONVERT(10006, "类型转换异常"),

    /**
     * 2XXXX，系统内业务异常，需自己定义，请依次添加
     */
    BIZ_EXCEPTION(20000, "业务层操作异常"),
    /**
     * 数据库操作相关
     */
    DB_DATA_NOT_EXIST(20001, "该数据不存在"),
    DB_ADD_FAIL(20002, "新增失败"),
    DB_UPDATE_FAIL(20003, "修改失败"),
    DB_DELETE_FAIL(20004, "删除失败"),
    DB_SELECT_FAIL(20005, "查询失败"),
    DB_DATA_EXIST(20006, "该数据已存在"),
    DATA_NOT_EXIST(20007, "无对应的返回数据"),
    UPLOAD_FAIL(20008, "上传失败"),

    /**
     * 未知异常及自定义异常
     */
    UNKNOWN_EXCEPTION(50000, "未知异常"),
    EXCEPTION_OTHER(50001, "自定义异常");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static String getMessage(int code) {
        for (ResultCode p : ResultCode.values()) {
            if (p.getCode() == code) {
                return p.getMessage();
            }
        }

        return code + "没有对应的说明信息";
    }

    public static ResultCode getEnum(int code) {
        for (ResultCode p : ResultCode.values()) {
            if (p.getCode() == code) {
                return p;
            }
        }

        return UNKNOWN_EXCEPTION;
    }
}