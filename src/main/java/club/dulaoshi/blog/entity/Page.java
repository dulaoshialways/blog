package club.dulaoshi.blog.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author djg
 * @date 2019/5/7 13:58
 * @des  分页参数
 */
@Data
public class Page<T> {

    public Page(){
        this.page = 0;
        this.pageSize = 10;
    }
    /**
     * 当前页
     */
    private int page;

    /**
     * 每页条数
     */
    private int pageSize;

    /**
     * 起始页
     */
    private int start;

    /**
     * 总条数
     */
    private Long total;

    /**
     * 分页总数
     */
    private Long pageTotal;

    private List<T> list = new ArrayList<T>();
}
