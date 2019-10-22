package cn.com.syzg.controller;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Emply;
import cn.com.syzg.model.Goods;
import cn.com.syzg.service.CartService;
import cn.com.syzg.service.EmplyService;
import cn.com.syzg.service.GoodsService;
import cn.com.syzg.utils.GoodsListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequestMapping("/emp")
@Controller
public class EmplyController {
    @Resource
    private EmplyService emplyService;
    @Resource
    private GoodsService goodsService;
    @Resource
    private CartService cartService;

    @RequestMapping("/userHtml")
    public String HTML(Model model,HttpSession session,
                       HttpServletRequest request,@RequestParam(value = "emplyNo",required = false)String emplyNo){
        //Integer id=Integer.parseInt(request.getParameter("emplyNo"));
        Emply byEmplyNo = emplyService.findByEmplyNo(emplyNo);
        session.setAttribute("emplyNo",byEmplyNo.getEmplyno());
        model.addAttribute("byEmplyNo",byEmplyNo);
        return "user";
    }

    @RequestMapping("/my_allowanceHtml")
    public String my_allowanceHtml(Model model,HttpSession session,
                                   @RequestParam(value = "emplyNo",required = false)String emplyNo){
        Cart cart = cartService.fingByEmplyNo(emplyNo);
        if(cart!=null){
            String[] strings = GoodsListUtil.splitGoodsList(cart.getGoodslist());
            Map<String, Integer> goodsMap = cartService.GoodsByCartForEmplyNo(strings);
            List<Goods> goodsList=new ArrayList<>();
            for(Map.Entry<String,Integer> entry:goodsMap.entrySet()){
                Goods goods = goodsService.fingbyGoodsId(Integer.parseInt(entry.getKey()));
                goodsList.add(goods);
            }

            model.addAttribute("goodsList",goodsList);
            model.addAttribute("goodsMap",goodsMap);
        }
           session.setAttribute("emplyNoThree",emplyNo);
        return "my_allowance";
    }

    @RequestMapping("/subsidy_listHtml")
    public String subsidy_listHtml(Model model,HttpSession session,
                                   @RequestParam(value = "emplyNo",required = false)String emplyNo){
        List<Goods> goodsAll = goodsService.findAll();
        session.setAttribute("emplyNoTwo",emplyNo);
        model.addAttribute("goodsAll",goodsAll);
        return "subsidy_list";
    }

    /***
     * 添加一条购物车记录 ，一个用户只能存一条数据，采用商品编号汇总到GoodsList字符串
     * @param goodsId
     * @param emplyNo
     * @return
     */
    @RequestMapping("/addshoppingByCart")
    public String addshoppingByCart(@RequestParam(value = "goodsId",required = false)Integer goodsId,
                                     @RequestParam(value = "emplyNo",required = false)String emplyNo){
        Cart byEmplyNo = cartService.fingByEmplyNo(emplyNo);
        if(byEmplyNo==null){  //没有查到这个用户添加商品到过购物车
           Cart cart=new Cart(emplyNo,goodsId.toString());
            boolean b = cartService.addCart(cart);
            if(b==true){
                return "cart001";
            }else{
                return "cart003";
            }
        }else{
            String GoodsList = GoodsListUtil.GoodsAppend(byEmplyNo.getGoodslist(),goodsId);
            Cart cart=new Cart(byEmplyNo.getId(),GoodsList);
            boolean b = cartService.editCart(cart);
            if(b==true){
                return "cart002";
            }else{
                return "cart004";
            }
        }
    }

}
