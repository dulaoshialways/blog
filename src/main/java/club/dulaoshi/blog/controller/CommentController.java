package club.dulaoshi.blog.controller;

import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.entity.Comment;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.service.CommentService;
import club.dulaoshi.blog.utils.AddressUtils;
import club.dulaoshi.blog.utils.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author djg
 * @date 2019/5/9 14:39
 * @des 评论相关接口
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final BlogService blogService;

    public CommentController(CommentService commentService, BlogService blogService) {
        this.commentService = commentService;
        this.blogService = blogService;
    }

    /**
     * 博客评论
     * @param comment
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    public Object save(@RequestBody Comment comment, HttpServletRequest request) throws Exception{
        int resultTotal = 0;
        // 获取用户ip
        String userIp = request.getRemoteAddr();
        userIp = AddressUtils.getAddresses("ip=" + userIp);
        comment.setUserIp(userIp);
        if (comment.getId() == null) {
            resultTotal = commentService.add(comment);
            // 博客回复次数加一
            Blog blog = blogService.findById(comment.getBlog().getId());
            blog.setReplyHit(blog.getReplyHit() + 1);
            blogService.update(blog);
        }

        if(resultTotal >0){
            return Result.success("回复成功，等待管理员审核！");
        }
        return Result.fail(ResultCode.DB_ADD_FAIL.getCode());
    }
}
