package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.BloggerDao;
import club.dulaoshi.blog.entity.Blogger;
import club.dulaoshi.blog.service.BloggerService;
import club.dulaoshi.blog.utils.RedisUtil;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:30
 * @des 博主service实现类
 */
@Service("bloggerService")
public class BloggerServiceImpl  implements BloggerService {
    private final BloggerDao bloggerDao;

    private final RedisUtil redisUtil;

    public BloggerServiceImpl(BloggerDao bloggerDao, RedisUtil redisUtil) {
        this.bloggerDao = bloggerDao;
        this.redisUtil = redisUtil;
    }

    @Override
    public Blogger getByUserName(String userName) {
        return bloggerDao.getByUserName(userName);
    }

    @Override
    public Blogger find() {
//        Map<String, Object> redisBlogger = (Map<String, Object>) redisUtil.get("blogger");
//        if(redisBlogger == null){
//            Gson gson = new Gson();
//            String jsonStr = gson.toJson(redisBlogger);
//            Blogger user = gson.fromJson(jsonStr, LabUser.class);
//            Blogger blogger = bloggerDao.find();
//            redisUtil.set("blogger",blogger);
//            return blogger;
//        }
        return bloggerDao.find();
    }

    @Override
    public Integer update(Blogger blogger) {
        return bloggerDao.update(blogger);
    }

    @Override
    public Blogger getById(Integer userId) {
        return bloggerDao.getById(userId);
    }
}
