package club.dulaoshi.blog.entity;

import lombok.Data;

import java.util.List;

/**
 * @author djg
 * @date 2019/5/9 16:09
 * @des 博客分类
 */
@Data
public class BlogClassified {
    private Blogger blogger;
    private List<Link> listLink;
    private List<BlogType> listBlogType;
    private List<Blog> listBlog;
}
