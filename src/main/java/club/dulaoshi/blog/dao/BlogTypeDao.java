package club.dulaoshi.blog.dao;

import club.dulaoshi.blog.entity.BlogType;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:02
 * @des 博客类型Dao接口
 */
public interface BlogTypeDao {
    /**
     * 查询所有博客类型以及对应的博客数量
     * @return
     */
    List<BlogType> countList();

    /**
     * 根据id查找blog类型实体
     * @param id
     * @return
     */
    BlogType findById(Integer id);

    /**
     * 分页查询博客类别信息
     * @param list
     * @return
     */
    List<BlogType> list(Map<String,Object> list);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String,Object> map);

    /**
     * 添加博客类型信息
     * @param blogType
     * @return
     */
    Integer add(BlogType blogType);

    /**
     * 修改博客类别信息
     * @param blogType
     * @return
     */
    Integer update(BlogType blogType);

    /**
     * 删除博客类别信息
     * @param id
     * @return
     */
    Integer delete(Integer id);
}
