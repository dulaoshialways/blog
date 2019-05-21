package club.dulaoshi.blog.controller;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.Blogger;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BloggerService;
import club.dulaoshi.blog.utils.CryptographyUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author djg
 * @date 2019/5/17 14:17
 * @des 博主controller层
 */
@RestController
@RequestMapping("/blogger")
public class BloggerController {
    private final BloggerService bloggerService;

    public BloggerController(BloggerService bloggerService) {
        this.bloggerService = bloggerService;
    }


    /**
     * 用户登录
     * @param blogger
     * @return
     */
    @PostMapping(value = "/login")
    @SysLog("用户登录")
    public Object login(@RequestBody Blogger blogger){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(blogger.getUserName(), CryptographyUtil.md5(blogger.getPassword(), "java1234"));
        try {
            //登陆认证，调用realm中的验证方法
            subject.login(token);
            if (subject.isAuthenticated()) {
                JSON json = new JSONObject();
                ((JSONObject) json).put("token", subject.getSession().getId());

                return Result.success(json);
            }else {
                return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode());
            }
        }catch (UnknownAccountException e) {
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(),e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(),"账号或密码不正确");
        }catch (LockedAccountException e) {
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(),"账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(),"账户验证失败");
        }
    }
}
