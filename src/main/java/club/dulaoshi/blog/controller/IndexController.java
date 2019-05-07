package club.dulaoshi.blog.controller;

import club.dulaoshi.blog.dto.IndexDto;
import club.dulaoshi.blog.entity.Blog;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.entity.PageBean;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.service.BlogService;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value="/index")
    public Object index(@RequestBody IndexDto indexDto, HttpServletRequest request){
        String path = "http://" + request.getServerName();
        if(StringUtil.isEmpty(indexDto.getPage())){
            indexDto.setPage("1");
        }
        PageBean pageBean = new PageBean(Integer.parseInt(indexDto.getPage()), 10);
        Map<String, Object> map = new HashMap<>(16);
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("releaseDateStr", indexDto.getReleaseDateStr());
        map.put("typeId", indexDto.getTypeId());

        List<Blog> blogList = blogService.list(map);

        for(Blog blog:blogList){
            List<String> imagelist = blog.getImageList();
            String blogInfo = blog.getContent();
            Document doc = Jsoup.parse(blogInfo);
            Elements jpgs = doc.select("img[src$=.jpg]");
            for(int i = 0;i<jpgs.size();i++){
                Element jpg = jpgs.get(i);
                String[] imageArray = jpg.toString().split(" ");
                String iamge = path + imageArray[1].substring(5, imageArray[1].length()-1);

                imagelist.add(iamge);
                if(i ==2){
                    break;
                }
            }
            blog.setReleaseDateStr(DateUtil.formatDate(blog.getReleaseDate(),"yyyy-MM-dd HH:mm:ss"));
        }
        Page<Blog> pageList = new Page<>();
        pageList.setList(blogList);
        pageList.setPage(Integer.parseInt(indexDto.getPage()));
        pageList.setPageSize(10);
        pageList.setStart(1);
        long total = blogService.getTotal(map);
        long totalPage = total%10==0?total/10:total/10+1;
        pageList.setPageTotal(totalPage);
        pageList.setTotal(total);

        return Result.success(pageList);
    }

}
