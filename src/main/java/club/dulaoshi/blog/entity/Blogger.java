package club.dulaoshi.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author djg
 * @date 2019/5/6 16:04
 * @des 博主实体
 */
@Data
public class Blogger{
    /**
     * 编号
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 关于博主
     */
    private String profile;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 个性签名
     */
    private String sign;

    /**
     * 头像路径
     */
    private String imageName;
}
