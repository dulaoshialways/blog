package club.dulaoshi.blog.service;

import club.dulaoshi.blog.entity.Blogger;

/**
 * @author djg
 * @date 2019/5/6 16:25
 * @des 博主service接口
 */
public interface BloggerService {
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
