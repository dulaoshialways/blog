package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.LinkDao;
import club.dulaoshi.blog.entity.Link;
import club.dulaoshi.blog.service.LinkService;
import club.dulaoshi.blog.utils.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author djg
 * @date 2019/5/6 16:36
 * @des 友情链接service实现类
 */
@Service
public class LinkServiceImpl implements LinkService {

    private final LinkDao linkDao;

    private final RedisUtil redisUtil;

    public LinkServiceImpl(LinkDao linkDao, RedisUtil redisUtil) {
        this.linkDao = linkDao;
        this.redisUtil = redisUtil;
    }

    @Override
    public List<Link> list(Map<String, Object> map) {
        List<Link> redisList = (List<Link>) redisUtil.get("linkList");
        if(redisList == null){
            List<Link> list = linkDao.list(map);
            redisUtil.set("linkList", list);
            return list;
        }
        return redisList;
    }

    @Override
    public Long getTotal(Map<String, Object> map) {
        return linkDao.getTotal(map);
    }

    @Override
    public Integer add(Link link) {
        return linkDao.add(link);
    }

    @Override
    public Integer update(Link link) {
        return linkDao.update(link);
    }

    @Override
    public Integer delete(Integer id) {
        return linkDao.delete(id);
    }
}
