package club.dulaoshi.blog.entity;

import lombok.Data;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author djg
 * @date 2019/5/6 16:04
 * @des 博客实体
 */
@Data
public class Blog {
    /**
     * id
     */
    private Integer id;

    /**
     * 博客标题
     */
    private String title;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 发布日期
     */
    private Date releaseDate;

    /**
     * 阅读次数
     */
    private Integer clickHit;

    /**
     * 回复次数
     */
    private Integer replyHit;

    /**
     * 博客内容
     */
    private String content;

    /**
     * 博客内容无网页标签，lucene索引用到
     */
    private String contentNoTag;

    /**
     * 博客类型
     */
    private BlogType blogType;

    /**
     * 关键字 空格隔开
     */
    private String keyWord;

    /**
     * 博客数量，非博客实际属性，主要根据发布日期归档查询数量用到
     */
    private Integer blogCount;

    /**
     * 发布日期的字符串 只取年和月
     */
    private String releaseDateStr;

    /**
     * 博客里存在的图片，主要用于列表缩略图的展示
     */
    private List<String> imageList = new LinkedList<>();
}
