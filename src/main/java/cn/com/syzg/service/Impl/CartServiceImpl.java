package cn.com.syzg.service.Impl;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Goods;
import cn.com.syzg.repository.CartMapper;
import cn.com.syzg.service.CartService;
import cn.com.syzg.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {
    @Resource
    private CartMapper cartMapper;
    @Resource
    private GoodsService goodsService;

    @Transactional
    @Override
    public boolean addCart(Cart cart) {
        return cartMapper.insert(cart)>0?true:false;
    }

    @Override
    public Cart fingByEmplyNo(String emplyNo) {
        return cartMapper.selectByEmplyNo(emplyNo);
    }

    @Transactional
    @Override
    public boolean editCart(Cart cart) {
        return cartMapper.updateGoodsList(cart);
    }

    public Map<String,Integer> GoodsByCartForEmplyNo(String [] strings){
            List<String> list = Arrays.asList(strings);
            Map<String,Integer> map=new HashMap<>();
            for (int j = 0; j <list.size(); j++) {
                map.put(list.get(j),map.get(list.get(j))==null?1:map.get(list.get(j))+1);
            }
            return map;
    }
}
