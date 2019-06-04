package club.dulaoshi.blog.entity;

import lombok.Data;

/**
 * @author djg
 * @date 2019/6/4 16:59
 * @des
 */
@Data
public class BlogConf {

    /**
     * id
     */
    private Integer id;

    /**
     * 配置项名称
     */
    private String name;

    /**
     * 配置项值
     */
    private String value;
}
