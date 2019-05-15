package club.dulaoshi.blog.controller.admin;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.BlogType;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.service.BlogTypeService;
import club.dulaoshi.blog.utils.RedisUtil;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/15 14:38
 * @des 管理员博客类别
 */
@RestController
@RequestMapping("/admin/blogType")
public class BlogTypeAdminController {

    private final BlogTypeService blogTypeService;

    private final BlogService blogService;

    private final RedisUtil redisUtil;

    public BlogTypeAdminController(BlogTypeService blogTypeService, BlogService blogService, RedisUtil redisUtil) {
        this.blogTypeService = blogTypeService;
        this.blogService = blogService;
        this.redisUtil = redisUtil;
    }

    /**
     * 分页查询博客类别信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @SysLog("分页查询博客类别信息")
    public Object list(@RequestParam(value="page",required=false,defaultValue = "1")Integer currentPage,
                       @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize){
        Map<String,Object> map = new HashMap<>(16);
        map.put("start", currentPage - 1);
        map.put("size", pageSize);

        List<BlogType> blogTypeList = blogTypeService.list(map);
        Long total = blogTypeService.getTotal(map);
        Page page = new Page();
        long totalPage = total%pageSize==0?total/pageSize:total/pageSize+1;
        page.setPageTotal(totalPage);
        page.setPageSize(pageSize);
        page.setPage(currentPage);
        page.setList(blogTypeList);
        page.setStart(currentPage);
        return Result.success(page);
    }

    /**
     * 添加或者修改博客类别
     * @param blogType
     * @return
     */
    @PostMapping("/save")
    @SysLog("添加或者修改博客类别")
    public Object save(@RequestBody BlogType blogType){
        int resultTotal;
        if(blogType.getId()==null){
            resultTotal = blogTypeService.add(blogType);
        }else{
            resultTotal = blogTypeService.update(blogType);
        }
//        redisUtil.lSet("blogTypeCountList",blogTypeService.countList());
        if(resultTotal>0){
            return Result.success();
        }else{
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(), "新增或修改类别失败");
        }
    }

    /**
     * 博客类别删除
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    @SysLog("博客类别删除")
    public Object delete(@RequestParam(value="ids",required =false)String ids){
        String[] idsStr = ids.split(",");
        for(int i= 0;i<idsStr.length;i++){
            if(blogService.getBlogByTypeId(Integer.parseInt(idsStr[i])) > 0){
                return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(),"博客类别下有博客，不能删除");
            }else{
                blogTypeService.delete(Integer.parseInt(idsStr[i]));
            }
        }
        return Result.success();

    }
}
