package cn.com.syzg.service;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Orders;

import java.util.List;

public interface OrderService {
    boolean addBatchOrder(List<Orders> list);
    boolean addMapping(List<Cart> list);
}
