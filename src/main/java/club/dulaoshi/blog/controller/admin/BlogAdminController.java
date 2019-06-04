package club.dulaoshi.blog.controller.admin;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.dto.BlogDelete;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.lucene.BlogIndex;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api("博客后端管理")
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
    @ApiOperation("添加或者修改博客")
    public Object save(@RequestBody Blog blog) throws Exception{
        int resultTotal;
        if(blog.getId()==null){
            resultTotal = blogService.add(blog);
        }else{
            resultTotal = blogService.update(blog);
        }
        if(resultTotal>0){
            return Result.success(blog);
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
     */
    @GetMapping("/list")
    @SysLog("分页查询博客信息")
    @ApiOperation("分页查询博客信息")
    public Object list(@RequestParam(value="page",defaultValue = "1")Integer currentPage,
                       @RequestParam(value="pageSize",defaultValue = "10")Integer pageSize,
                       @RequestParam(value="searchStr")String searchStr,
                       @RequestParam(value="state")Integer state){
        if (state == -1) {
            state = null;
        }
        Map<String,Object> map = new HashMap<>(16);
        map.put("title", StringUtil.formatLike(searchStr));
        map.put("start", currentPage-1);
        map.put("size", pageSize);
        map.put("state", state);

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
        page.setTotal(total);
        return Result.success(page);
    }

    /**
     * 博客信息删除（包含删除和放入回收站）
     * @param blogDelete
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @SysLog("博客信息删除（包含删除和放入回收站）")
    @ApiOperation("博客信息删除（包含删除和放入回收站）")
    public Object delete(@RequestBody BlogDelete blogDelete)throws Exception{
        List<Integer> ids = blogDelete.getIds();
        Integer status = blogDelete.getStatus();
        if (status == 0) {
            for (Integer id : ids) {
                blogService.deleteById(id);
                blogIndex.deleteIndex(String.valueOf(id));
            }
        }else {
            for (Integer id : ids) {
                blogService.deleteByState(id);
                //TODO 这里需要逻辑删除lucene
            }
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
    @ApiOperation("通过id查找博客信息")
    public Object findById(@RequestParam(value="id")Integer id){
        Blog blog = blogService.findById(id);
        return Result.success(blog);
    }

}
