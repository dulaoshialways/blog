package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.CommentDao;
import club.dulaoshi.blog.entity.Comment;
import club.dulaoshi.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:35
 * @des 评论service实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public List<Comment> list(Map<String, Object> map) {
        return commentDao.list(map);
    }

    @Override
    public Integer add(Comment comment) {
        return commentDao.add(comment);
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return commentDao.getTotal(map);
    }

    @Override
    public Integer update(Comment comment) {
        return commentDao.update(comment);
    }

    @Override
    public Integer delete(Integer id) {
        return commentDao.delete(id);
    }
}
