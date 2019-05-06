package club.dulaoshi.blog.service.impl;

import club.dulaoshi.blog.dao.LinkDao;
import club.dulaoshi.blog.entity.Link;
import club.dulaoshi.blog.service.LinkService;
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

    public LinkServiceImpl(LinkDao linkDao) {
        this.linkDao = linkDao;
    }

    @Override
    public List<Link> list(Map<String, Object> map) {
        return linkDao.list(map);
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
