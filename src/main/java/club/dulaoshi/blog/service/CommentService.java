package club.dulaoshi.blog.service;

import club.dulaoshi.blog.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:25
 * @des 评论service接口
 */
public interface CommentService {
    /**
     * 查询用户评论信息
     * @param map
     * @return
     */
    List<Comment> list(Map<String,Object> map);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    Integer add(Comment comment);

    /**
     * 获取总记录数
     * @param map
     * @return
     */
    Long getTotal(Map<String,Object> map);

    /**
     * 更新评论
     * @param comment
     * @return
     */
    Integer update(Comment comment);

    /**
     * 删除评论信息
     * @param id
     * @return
     */
    Integer delete(Integer id);
}
