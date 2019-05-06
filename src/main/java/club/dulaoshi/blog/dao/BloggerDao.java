package club.dulaoshi.blog.dao;

import club.dulaoshi.blog.entity.Blogger;

/**
 * @author djg
 * @date 2019/5/6 16:02
 * @des 博主DAO接口
 */
public interface BloggerDao {
    /**
     * 根据用户名查找用户
     * @param userName
     * @return
     */
    Blogger getByUserName(String userName);

    /**
     * 查询博主信息
     * @return
     */
    Blogger find();

    /**
     * 更新博主信息
     * @param blogger
     * @return
     */
    Integer update(Blogger blogger);
}
