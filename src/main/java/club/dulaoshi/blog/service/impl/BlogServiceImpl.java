package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.BlogDao;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.service.BlogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:32
 * @des 博客service实现类
 */
@Service
public class BlogServiceImpl implements BlogService {
    private final BlogDao blogDao;

    public BlogServiceImpl(BlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Override
    public List<Blog> countList() {
        return blogDao.countList();
    }

    @Override
    public List<Blog> list(Map<String, Object> map) {
        return blogDao.list(map);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return blogDao.getTotal(map);
    }

    @Override
    public Blog findById(Integer id) {
        return blogDao.findById(id);
    }

    @Override
    public Integer update(Blog blog) {
        return blogDao.update(blog);
    }

    @Override
    public Blog getlastBlog(Integer id) {
        return blogDao.getlastBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return blogDao.getNextBlog(id);
    }

    @Override
    public Integer add(Blog blog) {
        return blogDao.add(blog);
    }

    @Override
    public Integer delete(Integer id) {
        return blogDao.delete(id);
    }

    @Override
    public Integer getBlogByTypeId(Integer typeId) {
        return blogDao.getBlogByTypeId(typeId);
    }
}
