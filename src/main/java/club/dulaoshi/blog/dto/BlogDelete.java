package club.dulaoshi.blog.dto;

import lombok.Data;

import java.util.List;

/**
 * @author djg
 * @date 2019/6/4 11:06
 * @des
 */
@Data
public class BlogDelete {
    /**
     * id集合
     */
    private List<Integer> ids;

    /**
     * flag字段 判断0删除和放入1回收站
     */
    private Integer status;


}
