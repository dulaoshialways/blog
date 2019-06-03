package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.BlogDao;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.lucene.BlogIndex;
import club.dulaoshi.blog.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:32
 * @des 博客service实现类
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl implements BlogService {
    private final BlogDao blogDao;
    private final BlogIndex blogIndex;

    public BlogServiceImpl(BlogDao blogDao, BlogIndex blogIndex) {
        this.blogDao = blogDao;
        this.blogIndex = blogIndex;
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
    public Integer update(Blog blog) throws Exception{
        Integer flag = blogDao.update(blog);
        blogIndex.updateIndex(blog);
        return flag;
    }

    @Override
    public Blog getLastBlog(Integer id) {
        return blogDao.getLastBlog(id);
    }

    @Override
    public Blog getNextBlog(Integer id) {
        return blogDao.getNextBlog(id);
    }

    @Override
    public Integer add(Blog blog) throws Exception{
        Integer flag = blogDao.add(blog);
        blogIndex.addIndex(blog);
        return flag;
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
