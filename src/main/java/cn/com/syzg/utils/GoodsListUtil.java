package cn.com.syzg.utils;

import cn.com.syzg.model.Goods;
import cn.com.syzg.service.GoodsService;

import javax.annotation.Resource;
import java.util.*;

public class GoodsListUtil {
    @Resource
    public GoodsService goodsService;

//    /**
//     * 添加商品到购物车
//     * @param oldGoodsList
//     * @param goodsId
//     * @return
//     */
//    public static String GoodsAppend(String oldGoodsList,Integer goodsId){
//     StringBuffer newGoodsList=new StringBuffer(oldGoodsList);
//     newGoodsList.append(","+goodsId.toString());
//     return newGoodsList.toString();
//    }
//
//    public static String GoodsRemoveGoodsId(String oldGoodsList,Integer goodsId){
//
//        StringBuffer newGoodsList=new StringBuffer(oldGoodsList);
//        int i = newGoodsList.lastIndexOf(goodsId.toString());
//        System.out.println(i+".....");
//        return null;
//    }

    public static String[] splitGoodsList(String GoodsList){
        return  GoodsList.split(",");
    }

}
