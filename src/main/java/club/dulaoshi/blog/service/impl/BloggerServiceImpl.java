package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.BloggerDao;
import club.dulaoshi.blog.entity.Blogger;
import club.dulaoshi.blog.service.BloggerService;
import org.springframework.stereotype.Service;

/**
 * @author djg
 * @date 2019/5/6 16:30
 * @des 博主service实现类
 */
@Service("bloggerService")
public class BloggerServiceImpl  implements BloggerService {
    private final BloggerDao bloggerDao;

    public BloggerServiceImpl(BloggerDao bloggerDao) {
        this.bloggerDao = bloggerDao;
    }

    @Override
    public Blogger getByUserName(String userName) {
        return bloggerDao.getByUserName(userName);
    }

    @Override
    public Blogger find() {
        return bloggerDao.find();
    }

    @Override
    public Integer update(Blogger blogger) {
        return bloggerDao.update(blogger);
    }
}
