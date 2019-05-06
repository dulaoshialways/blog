package club.dulaoshi.blog.service;

import club.dulaoshi.blog.entity.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:25
 * @des 博客servie接口
 */
public interface BlogService {
    /**
     * 根据日期年月分组查询
     * @return
     */
    List<Blog> countList();

    /**
     * 分页查询博客
     * @param map
     * @return
     */
    List<Blog> list(Map<String,Object> map);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String,Object> map);

    /**
     * 根据id查找实体
     * @param id
     * @return
     */
    Blog findById(Integer id);

    /**
     * 更新博客信息
     * @param blog
     * @return
     */
    Integer update(Blog blog);

    /**
     * 获取上一个博客
     * @param id
     * @return
     */
    Blog getlastBlog(Integer id);

    /**
     * 获取下一个博客
     * @param id
     * @return
     */
    Blog getNextBlog(Integer id);

    /**
     * 添加博客信息
     * @param blog
     * @return
     */
    Integer add(Blog blog);

    /**
     * 删除博客信息
     * @param id
     * @return
     */
    Integer delete(Integer id);

    /**
     * 查询指定博客类别下的博客数量
     * @param typeId
     * @return
     */
    Integer getBlogByTypeId(Integer typeId);
}
