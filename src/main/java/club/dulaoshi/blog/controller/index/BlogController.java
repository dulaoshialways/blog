package club.dulaoshi.blog.controller.index;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.entity.Comment;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.lucene.BlogIndex;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.service.CommentService;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@Api("博客详情controller")
public class BlogController {
    private final BlogService blogService;
    private final CommentService commentService;
    private final BlogIndex blogIndex;

    public BlogController(BlogService blogService, CommentService commentService, BlogIndex blogIndex) {
        this.blogService = blogService;
        this.commentService = commentService;
        this.blogIndex = blogIndex;
    }

    /**
     * 获取博客详情
     * @param id
     * @return
     */
    @GetMapping(value="/articles")
    @SysLog("获取博客详情")
    @ApiOperation("获取博客详情")
    public Object details(@RequestParam("id") Integer id) throws Exception{
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


        Blog lastBlog = blogService.getLastBlog(id);
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
    @GetMapping(value="/comment/list")
    @SysLog("获取博客评论接口")
    @ApiOperation("获取博客评论接口")
    public Object getCommentList(@RequestParam(value = "page",defaultValue = "1") Integer currentPage,
                                 @RequestParam(value = "pageSize",defaultValue = "20") Integer pageSize,
                                 @RequestParam("id") Integer id){
        Map<String, Object> map = new HashMap<>(16);
        map.put("blogId", id);
        map.put("state", 1);
        List<Comment> list = commentService.list(map);
        Long total = commentService.getTotal(map);

        for(int i= 1;i<=list.size();i++){
            list.get(i-1).setCommentDateStr(DateUtil.formatDate(list.get(i-1).getCommentDate(),"yyyy-MM-dd HH:mm:ss"));
            list.get(i-1).setFloor(list.size()-i+1);
        }
        int toIndex = list.size() >= currentPage*pageSize?currentPage*pageSize:list.size();

        Page page = new Page();
        page.setList(list.subList((currentPage-1)*pageSize, toIndex));
        page.setPage(currentPage);
        page.setTotal(total);
        page.setStart(1);
        page.setPageSize(pageSize);
        long totalPage = total%pageSize==0?total/pageSize:total/pageSize+1;
        page.setPageTotal(totalPage);
        return Result.success(page);
    }


    /**
     * 博客搜索
     * @param searchStr
     * @param currentPage
     * @param pageSize
     * @return
     * @throws Exception
     */
    @GetMapping(value="/search")
    @SysLog("博客搜索")
    @ApiOperation("博客搜索")
    public Object search(@RequestParam(value="searchStr") String searchStr,
                         @RequestParam(value="page",required=false) Integer currentPage,
                         @RequestParam(value="pageSize",required=false) Integer pageSize) throws Exception {
        Page page = new Page();
        if(null == currentPage){
            currentPage = 1;
        }

        if (pageSize != null) {
            page.setPageSize(pageSize);
        }
        List<Blog> list = blogIndex.blogSearch(searchStr);

        int toIndex = list.size() >= currentPage*page.getPageSize()?currentPage*page.getPageSize():list.size();
        page.setList(list.subList((currentPage-1)*page.getPageSize(), toIndex));
        page.setPage(currentPage);
        page.setPageSize(page.getPageSize());
        long total = (long) list.size();
        long totalPage = total%page.getPageSize()==0?total/page.getPageSize():total/page.getPageSize()+1;
        page.setTotal(total);
        page.setPageTotal(totalPage);
        page.setSearchStr(searchStr);
        return Result.success(page);
    }

}
