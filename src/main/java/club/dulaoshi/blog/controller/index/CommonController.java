package club.dulaoshi.blog.controller.index;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.*;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.service.BlogTypeService;
import club.dulaoshi.blog.service.BloggerService;
import club.dulaoshi.blog.service.LinkService;
import club.dulaoshi.blog.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author djg
 * @date 2019/5/9 15:24
 * @des 博客公共信息
 */
@RestController
@RequestMapping("/common")
@Api("博客公共信息")
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    private final BloggerService bloggerService;
    private final LinkService linkService;
    private final BlogTypeService blogTypeService;
    private final BlogService blogService;
    private final RedisUtil redisUtil;

    public CommonController(BloggerService bloggerService, LinkService linkService, BlogTypeService blogTypeService, BlogService blogService, RedisUtil redisUtil) {
        this.bloggerService = bloggerService;
        this.linkService = linkService;
        this.blogTypeService = blogTypeService;
        this.blogService = blogService;
        this.redisUtil = redisUtil;
    }


    /**
     * 查询友情链接列表
     * @return
     */
    @GetMapping(value="/link/list")
    @SysLog("查询友情链接列表")
    @ApiOperation("查询友情链接列表")
    public Object getLinkList(){
        List<Link> list = (List<Link>) redisUtil.get("linkList");
        //双重校验锁
        if(null == list || list.size() == 0){
            // 查询所有的友情链接信息
            list = linkService.list(null);
            redisUtil.set("linkList", list);
        }
        return Result.success(list);
    }

    /**
     * 查询博客类别
     * @return
     */
    @GetMapping(value="/blog/type/list")
    @SysLog("查询博客类别")
    @ApiOperation("查询博客类别")
    public Object getBlogTypeCountList(){
        List<BlogType> blogTypes = blogTypeService.countList();
        return Result.success(blogTypes);
    }

    /**
     * 获取博客分类信息
     * @return
     */
    @GetMapping(value="/blog/classified")
    @SysLog("获取博客分类信息")
    @ApiOperation("获取博客分类信息")
    public Object refreshSystem(){
        BlogClassified blogClassified = new BlogClassified();
        // 获取博主信息
        Blogger blogger=bloggerService.find();
        blogClassified.setBlogger(blogger);

        // 查询所有的友情链接信息
        List<Link> linkList=linkService.list(null);
        blogClassified.setListLink(linkList);

        // 查询博客类别以及博客的数量
        List<BlogType> blogTypeCountList=blogTypeService.countList();
        blogClassified.setListBlogType(blogTypeCountList);

        // 根据日期分组查询博客
        List<Blog> blogCountList=blogService.countList();
        blogClassified.setListBlog(blogCountList);

        return Result.success(blogClassified);
    }


    @RequestMapping(value="/unauth")
    @SysLog("未授权登录")
    @ApiOperation("未授权登录")
    public Object unauth(){
        SecurityUtils.getSubject().logout();
        return Result.fail(ResultCode.UNAUTHO_ERROR.getCode());
    }
}
