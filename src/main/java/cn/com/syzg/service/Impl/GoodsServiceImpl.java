package cn.com.syzg.service.Impl;

import cn.com.syzg.model.Goods;
import cn.com.syzg.repository.GoodsMapper;
import cn.com.syzg.service.GoodsService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@CacheConfig(cacheNames = "goods",cacheManager = "cacheManager") //抽取公共的缓存管理器
@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectAll();
    }

    @Override
    public Goods fingbyGoodsId(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }
}
