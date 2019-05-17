package club.dulaoshi.blog.controller.admin;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.Link;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.entity.PageBean;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.result.ResultCode;
import club.dulaoshi.blog.service.LinkService;
import club.dulaoshi.blog.utils.RedisUtil;
import club.dulaoshi.blog.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/15 17:43
 * @des
 */
@RestController
@RequestMapping("/admin/link")
public class LinkAdminController {
    private final LinkService linkService;
    private final RedisUtil redisUtil;

    public LinkAdminController(LinkService linkService, RedisUtil redisUtil) {
        this.linkService = linkService;
        this.redisUtil = redisUtil;
    }

    /**
     * 分页查询友情链接信息
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    @SysLog("分页友情链接信息")
    public Object list(@RequestParam(value="page",required=false,defaultValue = "1")Integer currentPage,
                       @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize){
        Map<String,Object> map = new HashMap<>(16);
        map.put("start", currentPage-1);
        map.put("size", pageSize);

        List<Link> linkList = linkService.list(map);
        Long total = linkService.getTotal(map);
        Page page = new Page();
        long totalPage = total%pageSize==0?total/pageSize:total/pageSize+1;
        page.setPageSize(pageSize);
        page.setPageTotal(totalPage);
        page.setPage(currentPage);
        page.setStart(currentPage);
        page.setList(linkList);
        return Result.success(page);
    }

    /**
     * 添加或者修改友情链接
     * @param link
     * @return
     */
    @PostMapping("/save")
    @SysLog("添加或者修改友情链接")
    public Object save(@RequestBody Link link){
        int resultTotal;
        if(link.getId()==null){
            resultTotal = linkService.add(link);
        }else{
            resultTotal = linkService.update(link);
        }
//        redisUtil.lSet("linkList",linkService.list(null));
        if(resultTotal>0){
            return Result.success();
        }else{
            return Result.fail(ResultCode.UNKNOWN_EXCEPTION.getCode(), "添加或者修改友情链接失败");
        }
    }

    /**
     * 友情链接删除
     * @param ids
     * @return
     * @throws Exception
     */
    @PostMapping("/delete")
    @SysLog("友情链接删除")
    public Object delete(@RequestParam(value="ids",required =false)String ids){
        String[] idsStr = ids.split(",");
        for(int i= 0;i<idsStr.length;i++){
            linkService.delete(Integer.parseInt(idsStr[i]));
        }
//        redisUtil.lSet("linkList",linkService.list(null));
        return Result.success();
    }
}
