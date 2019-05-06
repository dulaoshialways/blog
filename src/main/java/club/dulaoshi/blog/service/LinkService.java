package club.dulaoshi.blog.service;

import club.dulaoshi.blog.entity.Link;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:25
 * @des 友情链接service接口
 */
public interface LinkService {
    /**
     * 查找友情链接信息
     * @param map
     * @return
     */
    List<Link> list(Map<String,Object> map);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String,Object> map);

    /**
     * 添加友情链接信息
     * @param link
     * @return
     */
    Integer add(Link link);

    /**
     * 修改友情链接信息
     * @param link
     * @return
     */
    Integer update(Link link);

    /**
     * 删除友情链接信息
     * @param id
     * @return
     */
    Integer delete(Integer id);
}
