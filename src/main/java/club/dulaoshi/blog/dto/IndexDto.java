package club.dulaoshi.blog.dto;

import lombok.Data;

/**
 * @author djg
 * @date 2019/5/7 13:53
 * @des 列表参数
 */
@Data
public class IndexDto {
    /**
     * 当前页
     */
    private String page;

    /**
     * 分类id
     */
    private String typeId;

    /**
     * 时间分类字符串
     */
    private String releaseDateStr;
}
