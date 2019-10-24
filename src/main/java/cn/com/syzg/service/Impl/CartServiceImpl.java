package cn.com.syzg.service.Impl;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Emply;
import cn.com.syzg.model.Goods;
import cn.com.syzg.repository.CartMapper;
import cn.com.syzg.service.CartService;
import cn.com.syzg.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
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


    @Transactional
    @Override
    public boolean editCart(Cart cart) {
        return cartMapper.updateGoodsList(cart);
    }

//    public Map<String,Integer> GoodsByCartForEmplyNo(String [] strings){
//            List<String> list = Arrays.asList(strings);
//            Map<String,Integer> map=new HashMap<>();
//            for (int j = 0; j <list.size(); j++) {
//                map.put(list.get(j),map.get(list.get(j))==null?1:map.get(list.get(j))+1);
//            }
//            return map;
//    }

    @Override
    public List<Cart> findByEmplyNo(String emplyNo) {
        return cartMapper.selectByEmplyNo(emplyNo);
    }

    @Override
    public Cart findByEmplyNoAndGoodsId(String emplyNo, Integer goodsId) {
        return cartMapper.selectCartbyEmplyNoAndGoodsId(emplyNo,goodsId);
    }

    @Transactional
    @Override
    public boolean removeCartByIdlist(List<String> list) {
        return cartMapper.deletebatchbyCart(list);
    }

    @Override
    public List<Cart> findBatchById(List<String> list) {
        List<Cart> cartList=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            Cart cart = cartMapper.selectByPrimaryKey(Integer.parseInt(list.get(i)));
            cartList.add(cart);
        }
        return cartList;
    }

    @Override
    public Map<String, Emply> findByBatchByIdOnMoney(List<String> list) {
        Map<String,Emply> map=new HashMap<>();
        Cart cart=null;
        double amountOfMoney=0.0;
        double bcount=0.0;
        double money=0.0;
        double sumMoeny=0.0;
        for(int i=0;i<list.size();i++){
              cart = cartMapper.selectByPrimaryKey(Integer.parseInt(list.get(i)));
              amountOfMoney=cart.getGoods().getAmountOfMoney().doubleValue();
              bcount=cart.getCount();
              money=amountOfMoney*bcount;
              sumMoeny+=money;
        }
         map.put("order",new Emply(cart.getEmplyno(),new BigDecimal(sumMoeny)));
        return map;
    }
}
