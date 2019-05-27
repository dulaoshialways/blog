package club.dulaoshi.blog.controller.admin;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.Blogger;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BloggerService;
import club.dulaoshi.blog.utils.CryptographyUtil;
import club.dulaoshi.blog.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * @author djg
 * @date 2019/5/15 9:48
 * @des 博客信息controller
 */
@RestController
@RequestMapping("/admin/blogger")
@Api("博客信息controller")
public class BloggerAdminController {
    private final BloggerService bloggerService;

    public BloggerAdminController(BloggerService bloggerService) {
        this.bloggerService = bloggerService;
    }

    /**
     * 查询博主信息
     * @return
     */
    @GetMapping("/find")
    @SysLog("查询博主信息")
    @ApiOperation("查询博主信息")
    public Object find(){
        Blogger blogger = bloggerService.find();
        return Result.success(blogger);
    }

    /**
     * 修改博主信息
     * @param request
     * @param imageFile
     * @param blogger
     * @return
     * @throws Exception
     */
    @PostMapping("/save")
    @SysLog("修改博主信息")
    @ApiOperation("修改博主信息")
    public Object save(HttpServletRequest request,
                       @RequestParam(value="imageFile") MultipartFile imageFile,
                       @RequestBody Blogger blogger)throws Exception{
        if(!imageFile.isEmpty()){
            String filePath = request.getServletContext().getRealPath("/");
            String imageName = DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
            imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
            blogger.setImageName(imageName);
        }
        int resultTotal = bloggerService.update(blogger);
        if(resultTotal > 0){
            return Result.success();
        }else{
            return Result.fail(ResultCode.DB_UPDATE_FAIL.getCode());
        }

    }

    /**
     * 修改密码
     * @param newPassword
     * @return
     * @throws Exception
     */
    @PostMapping("/modifyPassword")
    @SysLog("修改密码")
    @ApiOperation("修改密码")
    public Object modifyPassword(String newPassword)throws Exception{
        Blogger blogger = new Blogger();
        blogger.setPassword(CryptographyUtil.md5(newPassword, "java1234"));
        int resultTotal = bloggerService.update(blogger);
        if(resultTotal > 0){
            return Result.success();
        }else{
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(),"密码修改失败");
        }
    }

    /**
     * 退出登陆
     * @return
     */
    @PostMapping("/logout")
    @SysLog("退出登陆")
    @ApiOperation("退出登陆")
    public Object logout(){
        SecurityUtils.getSubject().logout();
        return Result.success();
    }
}
