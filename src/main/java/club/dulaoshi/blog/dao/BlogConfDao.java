package club.dulaoshi.blog.dao;

import club.dulaoshi.blog.entity.BlogConf;
import club.dulaoshi.blog.entity.BlogType;

/**
 * @author djg
 * @date 2019/6/4 16:02
 * @des 博客配置dao接口
 */
public interface BlogConfDao {
    /**
     * 新增博客配置
     * @param blogConf
     * @return
     */
   Integer add(BlogConf blogConf);


    /**
     * 修改博客配置
     * @param blogConf
     * @return
     */
   Integer update(BlogConf blogConf);

    /**
     * 查询博客配置
     * @param name
     * @return
     */
    BlogConf findByName(String name);
}
