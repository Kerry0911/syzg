package cn.com.syzg.service;

import cn.com.syzg.model.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> findAll();
    Goods fingbyGoodsId(Integer goodsId);
}
