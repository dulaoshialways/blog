package club.dulaoshi.blog.controller;

import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.service.CommentService;
import club.dulaoshi.blog.utils.StringUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: djg
 * @date: 2019/5/7 23:41
 * @des: 博客详情controller
 */
@RestController
@RequestMapping("/blog")
public class BlogController {
    private final BlogService blogService;
    private final CommentService commentService;

    public BlogController(BlogService blogService, CommentService commentService) {
        this.blogService = blogService;
        this.commentService = commentService;
    }

    @PostMapping(value="/articles/{id}")
    public Object details(@PathVariable("id") Integer id){
        Map<String,Object> result = new HashMap<>(16);
        Blog blog = blogService.findById(id);
        String keyWords = blog.getKeyWord();
        if(StringUtil.isNotEmpty(keyWords)){
            String[] arr = keyWords.split(" ");
            result.put("keyWords",StringUtil.filterWhite(Arrays.asList(arr)));
        }else{
            result.put("keyWords",null);
        }
        blog.setClickHit(blog.getClickHit()+1);
        blogService.update(blog);

        Map<String,Object> map = new HashMap<>(16);
        map.put("blogId", blog.getId());
        map.put("state", 1);


        Blog lastBlog = blogService.getlastBlog(id);
        Blog nextBlog = blogService.getNextBlog(id);
        result.put("blog", blog);
        if(lastBlog == null || lastBlog.getId() == null){
            result.put("lastBlog", null);
        }else{
            result.put("lastBlog", lastBlog);
        }

        if(nextBlog == null || nextBlog.getId() == null){
            result.put("nextBlog", null);
        }else{
            result.put("nextBlog", nextBlog);
        }

        result.put("commentList", commentService.list(map));
        return Result.success(result);
    }


}
