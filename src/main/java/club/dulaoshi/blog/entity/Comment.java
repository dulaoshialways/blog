package club.dulaoshi.blog.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author djg
 * @date 2019/5/6 16:04
 * @des 评论实体
 */
@Data
public class Comment {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 被评论的博客
     */
    private Blog blog;

    /**
     * 评论日期
     */
    private Date commentDate;

    /**
     * 评论日期字符串
     */
    private String commentDateStr;

    /**
     * 审核状态 0待审核 1 审核通过 2 审核 未通过
     */
    private Integer state;

    /**
     * 楼层
     */
    private Integer floor;
}
