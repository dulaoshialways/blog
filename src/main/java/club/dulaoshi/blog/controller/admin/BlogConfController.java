package club.dulaoshi.blog.controller.admin;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.BlogConf;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BlogConfService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author djg
 * @date 2019/6/4 17:29
 * @des blog配置controller
 */
@RestController
@RequestMapping("/admin/blog/conf")
@Api("blog配置controller")
public class BlogConfController {

    private final BlogConfService blogConfService;

    public BlogConfController(BlogConfService blogConfService) {
        this.blogConfService = blogConfService;
    }


    /**
     * 新增或修改系统配置
     * @param blogConf
     * @return
     */
    @PostMapping("/insertOrUpdate")
    @SysLog("新增或修改系统配置")
    @ApiOperation("新增或修改系统配置")
    public Object insertOrUpdate(@RequestBody BlogConf blogConf){

        int flag;
        if (blogConf.getId() != null) {
            flag = blogConfService.update(blogConf);
        }else {
            flag = blogConfService.add(blogConf);
        }

        if (flag > 0){
            return Result.success();
        }

        return Result.fail(ResultCode.DB_ADD_FAIL.getCode());
    }


    /**
     * 根据名称查询系统配置项
     * @param name
     * @return
     */
    @GetMapping("/findByName")
    @SysLog("根据名称查询系统配置项")
    @ApiOperation("根据名称查询系统配置项")
    public Object findByName(@RequestParam String name){
        BlogConf blogConf = blogConfService.findByName(name);
        return Result.success(blogConf);
    }
}
