package cn.com.syzg.service;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Goods;

import java.util.List;
import java.util.Map;

public interface CartService {
    boolean addCart(Cart cart);
    Cart fingByEmplyNo(String emplyNo);
    boolean editCart(Cart cart);
    Map<String,Integer> GoodsByCartForEmplyNo(String [] strings);
}
