package club.dulaoshi.blog.controller;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.dto.IndexDto;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/7 10:29
 * @des 首页controller
 */
@RestController
@RequestMapping("/")
public class IndexController {

    private final BlogService blogService;

    public IndexController(BlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 获取博客列表
     * @param currentPage
     * @param pageSize
     * @param typeId
     * @param releaseDateStr
     * @param request
     * @return
     */
    @GetMapping(value="/blog/list")
    @SysLog("获取博客列表")
    public Object blogList(@RequestParam("page") Integer currentPage,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("typeId") Integer typeId,
                           @RequestParam("releaseDateStr") String releaseDateStr,
                           HttpServletRequest request){
        String path = "http://" + request.getServerName();
        Page<Blog> page = new Page<>();
        if(currentPage == null){
            currentPage = 1;
        }
        if(pageSize != null){
            page.setPageSize(pageSize);
        }
        Map<String, Object> map = new HashMap<>(16);
        map.put("start", (currentPage-1)*page.getPageSize());
        map.put("size", page.getPageSize());
        map.put("releaseDateStr", releaseDateStr);
        map.put("typeId", typeId);

        List<Blog> blogList = blogService.list(map);

        for(Blog blog:blogList){
            List<String> imageList = blog.getImageList();
            String blogInfo = blog.getContent();
            Document doc = Jsoup.parse(blogInfo);
            Elements jpgs = doc.select("img[src$=.jpg]");
            for(int i = 0;i<jpgs.size();i++){
                Element jpg = jpgs.get(i);
                String[] imageArray = jpg.toString().split(" ");
                String image = path + imageArray[1].substring(5, imageArray[1].length()-1);

                imageList.add(image);
                if(i ==2){
                    break;
                }
            }
            blog.setReleaseDateStr(DateUtil.formatDate(blog.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
        }

        page.setList(blogList);
        page.setPage(currentPage);
        long total = blogService.getTotal(map);
        long totalPage = total%pageSize==0?total/pageSize:total/pageSize+1;
        page.setPageTotal(totalPage);
        page.setTotal(total);
        return Result.success(page);
    }

}
