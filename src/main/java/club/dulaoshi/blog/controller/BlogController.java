package club.dulaoshi.blog.controller;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.entity.Comment;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.service.CommentService;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.StringUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    /**
     * 获取博客详情
     * @param id
     * @return
     */
    @GetMapping(value="/articles")
    @SysLog("获取博客详情")
    public Object details(@RequestParam("id") Integer id){
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


    /**
     * 获取博客评论接口
     * @param currentPage
     * @param id
     * @return
     */
    @GetMapping(value="commentList")
    @SysLog("获取博客评论接口")
    public Object getCommentList(@RequestParam("page") String currentPage,@RequestParam("id") Integer id){
        if(StringUtil.isEmpty(currentPage)){
            currentPage = "1";
        }

        Map<String, Object> map = new HashMap<>(16);
        map.put("blogId", id);
        map.put("state", 1);
        List<Comment> list = commentService.list(map);
        Long total = commentService.getTotal(map);

        for(int i= 1;i<=list.size();i++){
            list.get(i-1).setCommentDateStr(DateUtil.formatDate(list.get(i-1).getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
            list.get(i-1).setFloor(list.size()-i+1);
        }
        int toIndex = list.size() >= Integer.parseInt(currentPage)*10?Integer.parseInt(currentPage)*10:list.size();

        Page page = new Page();
        page.setList(list.subList((Integer.parseInt(currentPage)-1)*10, toIndex));
        page.setPage(Integer.parseInt(currentPage));
        page.setTotal(total);
        page.setStart(1);
        page.setPageSize(10);
        long totalPage = total%10==0?total/10:total/10+1;
        page.setPageTotal(totalPage);
        return Result.success(page);
    }


}
