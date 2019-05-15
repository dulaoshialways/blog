package club.dulaoshi.blog.controller.admin;

import club.dulaoshi.blog.conf.annotation.SysLog;
import club.dulaoshi.blog.entity.Comment;
import club.dulaoshi.blog.entity.Page;
import club.dulaoshi.blog.entity.PageBean;
import club.dulaoshi.blog.result.Result;
import club.dulaoshi.blog.service.CommentService;
import club.dulaoshi.blog.utils.DateUtil;
import club.dulaoshi.blog.utils.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/15 15:33
 * @des 后台评论管理
 */
@RestController
@RequestMapping("/admin/comment")
public class CommentAdminController {
    private final CommentService commentService;

    public CommentAdminController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * 分页查询评论信息
     * @param currentPage
     * @param pageSize
     * @param state
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    @SysLog("分页查询评论信息")
    public Object list(@RequestParam(value="page",required=false,defaultValue = "1")Integer currentPage,
                       @RequestParam(value="pageSize",required=false,defaultValue = "10")Integer pageSize,
                       @RequestParam(value="state",required=false)String state){
        Map<String,Object> map = new HashMap<>(3);
        map.put("state",state);
        map.put("start", currentPage-1);
        map.put("size", pageSize);

        List<Comment> commentList = commentService.list(map);
        Long total = commentService.getTotal(map);
        for (Comment comment : commentList) {
            comment.setCommentDateStr(DateUtil.formatDate(comment.getCommentDate(),"yyyy-MM-dd"));
        }
        Page page = new Page();
        long totalPage = total%pageSize==0?total/pageSize:total/pageSize+1;
        page.setPageTotal(totalPage);
        page.setPageSize(pageSize);
        page.setPage(currentPage);
        page.setStart(currentPage);
        page.setList(commentList);
        return Result.success(page);
    }

    /**
     * 博客评论审核
     * @param ids
     * @param state
     * @return
     * @throws Exception
     */
    @GetMapping("/review")
    @SysLog("博客评论审核")
    public Object review(@RequestParam(value="ids",required =false)String ids,
                         @RequestParam(value="state",required =false)Integer state){
        String[] idsStr = ids.split(",");
        for(int i= 0;i<idsStr.length;i++){
            Comment comment = new Comment();
            comment.setId(Integer.parseInt(idsStr[i]));
            comment.setState(state);
            commentService.update(comment);
        }
        return Result.success();
    }

    /**
     * 博客评论删除
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    @SysLog("博客评论删除")
    public Object delete(@RequestParam(value="ids",required =false)String ids){
        String[] idsStr = ids.split(",");
        for(int i= 0;i<idsStr.length;i++){
            commentService.delete(Integer.parseInt(idsStr[i]));
        }
        return Result.success();

    }
}
