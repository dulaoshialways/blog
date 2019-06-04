package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.BlogConfDao;
import club.dulaoshi.blog.entity.BlogConf;
import club.dulaoshi.blog.entity.BlogType;
import club.dulaoshi.blog.service.BlogConfService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author djg
 * @date 2019/6/4 17:06
 * @des
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogConfServiceImpl implements BlogConfService {

    private final BlogConfDao blogConfDao;

    public BlogConfServiceImpl(BlogConfDao blogConfDao) {
        this.blogConfDao = blogConfDao;
    }

    @Override
    public Integer add(BlogConf blogConf) {
        return blogConfDao.add(blogConf);
    }

    @Override
    public Integer update(BlogConf blogConf) {
        return blogConfDao.update(blogConf);
    }

    @Override
    public BlogConf findByName(String name) {
        return blogConfDao.findByName(name);
    }
}
