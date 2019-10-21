package cn.com.syzg.service.Impl;

import cn.com.syzg.model.Goods;
import cn.com.syzg.repository.GoodsMapper;
import cn.com.syzg.service.GoodsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectAll();
    }
}
