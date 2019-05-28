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
    @GetMapping("/find/user")
    @SysLog("查询博主信息")
    @ApiOperation("查询博主信息")
    public Object find(@RequestParam Integer userId){
        Blogger blogger = bloggerService.getById(userId);
        return Result.success(blogger);
    }

    /**
     * 修改博主信息
     * @param imageFile
     * @param blogger
     * @return
     * @throws Exception
     */
    @PostMapping("/update/user")
    @SysLog("修改博主信息")
    @ApiOperation("修改博主信息")
    public Object save(@RequestParam(value="imageFile",required = false) MultipartFile imageFile,
                       @RequestBody Blogger blogger)throws Exception{
//        if(!imageFile.isEmpty()){
//            String filePath = request.getServletContext().getRealPath("/");
//            String imageName = DateUtil.getCurrentDateStr()+"."+imageFile.getOriginalFilename().split("\\.")[1];
//            imageFile.transferTo(new File(filePath+"static/userImages/"+imageName));
//            blogger.setImageName(imageName);
//        }
        int resultTotal = bloggerService.update(blogger);
        if(resultTotal > 0){
            return Result.success();
        }else{
            return Result.fail(ResultCode.DB_UPDATE_FAIL.getCode());
        }

    }

    /**
     * 修改密码
     * @param blogger
     * @return
     * @throws Exception
     */
    @PostMapping("/modifyPassword")
    @SysLog("修改密码")
    @ApiOperation("修改密码")
    public Object modifyPassword(@RequestBody Blogger blogger){
        //获取当前用户的密码
        String password = bloggerService.getById(blogger.getId()).getPassword();

        //验证密码一致性
        if (!password.equals(CryptographyUtil.md5(blogger.getPassword(), "java1234"))) {
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(), "密码验证失败");
        }

        blogger.setPassword(CryptographyUtil.md5(blogger.getNewPassword(), "java1234"));
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
