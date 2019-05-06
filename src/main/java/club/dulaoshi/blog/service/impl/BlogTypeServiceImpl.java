package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.BlogTypeDao;
import club.dulaoshi.blog.entity.BlogType;
import club.dulaoshi.blog.service.BlogTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:34
 * @des 博客类型service实现类
 */
@Service
public class BlogTypeServiceImpl implements BlogTypeService {

    private final BlogTypeDao blogTypeDao;

    public BlogTypeServiceImpl(BlogTypeDao blogTypeDao) {
        this.blogTypeDao = blogTypeDao;
    }

    @Override
    public List<BlogType> countList() {
        return blogTypeDao.countList();
    }

    @Override
    public List<BlogType> list(Map<String, Object> list) {
        return blogTypeDao.list(list);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogTypeDao.getTotal(map);
    }

    @Override
    public Integer add(BlogType blogType) {
        return blogTypeDao.add(blogType);
    }

    @Override
    public Integer update(BlogType blogType) {
        return blogTypeDao.update(blogType);
    }

    @Override
    public Integer delete(Integer id) {
        return blogTypeDao.delete(id);
    }
}
