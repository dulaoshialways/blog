package club.dulaoshi.blog.controller.admin;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.lucene.BlogIndex;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.StringUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/14 14:53
 * @des 博客后端管理
 */
@RestController
@RequestMapping("/admin/blog")
public class BlogAdminController {

    private final BlogService blogService;

    private final BlogIndex blogIndex;

    public BlogAdminController(BlogService blogService, BlogIndex blogIndex) {
        this.blogService = blogService;
        this.blogIndex = blogIndex;
    }

    /**
     * 添加或者修改博客
     * @param blog
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @SysLog("添加或者修改博客")
    public Object save(@RequestBody Blog blog) throws Exception{
        int resultTotal;
        if(blog.getId()==null){
            resultTotal = blogService.add(blog);
            blogIndex.addIndex(blog);
        }else{
            resultTotal = blogService.update(blog);
            blogIndex.updateIndex(blog);
        }
        if(resultTotal>0){
            return Result.success();
        }else{
            return Result.fail(ResultCode.DB_ADD_FAIL.getCode());
        }
    }

    /**
     * 分页查询博客信息
     * @param currentPage
     * @param pageSize
     * @param searchStr
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    @SysLog("分页查询博客信息")
    public Object list(@RequestParam(value="page",defaultValue = "1")Integer currentPage,
                       @RequestParam(value="pageSize",defaultValue = "10")Integer pageSize,
                       @RequestParam(value="searchStr")String searchStr){
        Map<String,Object> map = new HashMap<>(16);
        map.put("title", StringUtil.formatLike(searchStr));
        map.put("start", currentPage-1);
        map.put("size", pageSize);

        List<Blog> blogList = blogService.list(map);
        Long total = blogService.getTotal(map);
        for (Blog blog : blogList) {
            blog.setReleaseDateStr(DateUtil.formatDate(blog.getReleaseDate(),"yyyy-MM-dd"));
        }
        Page page = new Page();
        page.setSearchStr(searchStr);
        long totalPage = total%pageSize==0?total/pageSize:total/pageSize+1;
        page.setPageTotal(totalPage);
        page.setPage(currentPage);
        page.setPageSize(pageSize);
        page.setList(blogList);
        page.setStart(currentPage);
        return Result.success(page);
    }

    /**
     * 博客信息删除
     * @param ids
     * @return
     * @throws Exception
     */
    @GetMapping("/delete")
    @SysLog("博客信息删除")
    public Object delete(@RequestParam(value="ids",required =false)String ids)throws Exception{
        String[] idsStr = ids.split(",");
        for(int i= 0;i<idsStr.length;i++){
            blogService.delete(Integer.parseInt(idsStr[i]));
            blogIndex.deleteIndex(idsStr[i]);
        }
        return Result.success();
    }

    /**
     * 通过id查找博客信息
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/findById")
    @SysLog("通过id查找博客信息")
    public Object findById(@RequestParam(value="id")String id){
        Blog blog = blogService.findById(Integer.parseInt(id));
        return Result.success(blog);
    }
}
