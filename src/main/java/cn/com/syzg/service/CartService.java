package cn.com.syzg.service;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Emply;
import cn.com.syzg.model.Goods;

import java.util.List;
import java.util.Map;

public interface CartService {
    boolean addCart(Cart cart);
    boolean editCart(Cart cart);
    List<Cart> findByEmplyNo(String emplyNo);
    Cart findByEmplyNoAndGoodsId(String emplyNo,Integer goodsId);
    boolean removeCartByIdlist(List<String> list);
    List<Cart> findBatchById(List<String> list);
    Map<String, Emply> findByBatchByIdOnMoney(List<String> list);
}
