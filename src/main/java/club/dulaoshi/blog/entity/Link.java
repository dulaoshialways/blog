package club.dulaoshi.blog.entity;

import lombok.Data;

/**
 * @author djg
 * @date 2019/5/6 16:04
 * @des 友情链接实体
 */
@Data
public class Link {
    /**
     * id
     */
    private Integer id;

    /**
     * 链接名字
     */
    private String linkName;

    /**
     * 链接地址
     */
    private String linkUrl;

    /**
     * 排序序号  从小到大排序
     */
    private String orderNo;
}
