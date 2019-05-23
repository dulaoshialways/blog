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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
