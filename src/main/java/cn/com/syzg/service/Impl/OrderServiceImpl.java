package cn.com.syzg.service.Impl;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Orders;
import cn.com.syzg.repository.OrderMapper;
import cn.com.syzg.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;
    @Override
    public boolean addBatchOrder(List<Orders> list) {
        return orderMapper.insertBatchOrder(list)>0?true:false;
    }

    @Transactional
    @Override
    public boolean addMapping(List<Cart> list) {
        List<Orders> ordersList=new ArrayList<>();
        Orders orders=null;
        for(Cart c:list){
             orders=new Orders(c.getEmplyno(),c.getCount(),c.getGoodsId());
             ordersList.add(orders);
        }
        return addBatchOrder(ordersList);
    }
}
