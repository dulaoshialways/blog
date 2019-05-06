package club.dulaoshi.blog.entity;

import lombok.Data;

/**
 * @author djg
 * @date 2019/5/6 16:04
 * @des 博客类型实体
 */
@Data
public class BlogType {
    /**
     * id
     */
    private Integer id;

    /**
     * 博客类型名称
     */
    private String typeName;

    /**
     * 排序序号 从小到大排序
     */
    private Integer orderNo;

    /**
     * 数量
     */
    private Integer blogCount;
}
