package cn.com.syzg.controller;

import cn.com.syzg.model.Cart;
import cn.com.syzg.model.Emply;
import cn.com.syzg.model.Goods;
import cn.com.syzg.service.CartService;
import cn.com.syzg.service.EmplyService;
import cn.com.syzg.service.GoodsService;
import cn.com.syzg.service.OrderService;
import cn.com.syzg.utils.GoodsListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Resource
    private OrderService orderService;

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
        List<Cart> byEmplyNo = cartService.findByEmplyNo(emplyNo);
        model.addAttribute("byEmplyNo",byEmplyNo);
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
     *
     * @param goodsId
     * @param emplyNo
     * @return
     */
    @ResponseBody
    @PostMapping("/addshoppingByCart")
    public String addshoppingByCart(@RequestParam(value = "goodsId",required = false)Integer goodsId,
                                     @RequestParam(value = "emplyNo",required = false)String emplyNo){
        Cart cart = cartService.findByEmplyNoAndGoodsId(emplyNo,goodsId);
        if(cart==null){  //没有查到这个用户的这个商品添加到过购物车
            boolean b = cartService.addCart(new Cart(emplyNo,1,goodsId));
            if(b==true){
                return "cart001";
            }else{
                return "cart003";
            }
        }else{
            boolean b = cartService.editCart(new Cart(cart.getId(),cart.getCount()+1));
            if(b==true){
                return "cart002";
            }else{
                return "cart004";
            }
        }
    }

    @RequestMapping("/RemoveGoodslistById")
    public String RemoveGoodslistById(@RequestParam(value = "goodsid",required = false)Integer goodsId,
                                      @RequestParam(value = "emplyNo",required = false)String emplyNo,
                                      HttpSession session,Model model){
        Cart cart = cartService.findByEmplyNoAndGoodsId(emplyNo,goodsId);
        if(cart.getCount()>1){
            boolean b = cartService.editCart(new Cart(cart.getId(), (cart.getCount()-1)));
            if(b==true){
                List<Cart> byEmplyNo = cartService.findByEmplyNo(emplyNo);
                model.addAttribute("byEmplyNo",byEmplyNo);
                session.setAttribute("emplyNoThree",emplyNo);
                return "my_allowance::article_type";
            }else{
                return "my_allowance::article_type";
            }
        }
        return null;
    }

    @ResponseBody
    @PostMapping("/addGoodslistById")
    public String addGoodslistById(@RequestParam(value = "goodsid",required = false)Integer goodsId,
                                   @RequestParam(value = "emplyNo",required = false)String emplyNo,
                                   HttpSession session,Model model){ ;
        Cart cart = cartService.findByEmplyNoAndGoodsId(emplyNo,goodsId);
        boolean b = cartService.editCart(new Cart(cart.getId(),(cart.getCount() + 1)));
        if(b==true){
            return "addCart001";
        }else{
            return "addCart002";
        }
    }

    @RequestMapping("/removeCartById")
    public String removeCartById(@RequestParam(value = "strlist",required = false)String strlist,
                                 Model model,HttpSession session){
        String emplyNoThree =(String) session.getAttribute("emplyNoThree");
        String[] strings = GoodsListUtil.splitGoodsList(strlist);
        List<String> list = Arrays.asList(strings);
        boolean b = cartService.removeCartByIdlist(list);
        if(b==true){
            List<Cart> byEmplyNo = cartService.findByEmplyNo(emplyNoThree);
            model.addAttribute("byEmplyNo",byEmplyNo);
            session.setAttribute("emplyNoThree",emplyNoThree);
            return "my_allowance::article_type";
        }else{
            return "my_allowance::article_type";
        }
    }

    @Transactional
    @ResponseBody
    @RequestMapping("/addOrder")
    public String addOrder(@RequestParam(value = "strlist",required = false)String strlist){
        String[] strings = GoodsListUtil.splitGoodsList(strlist);
        List<String> list = Arrays.asList(strings);
        List<Cart> batchById = cartService.findBatchById(list);
        boolean b = orderService.addMapping(batchById);
        if(b==true){
            Map<String, Emply> byBatchByIdOnMoney = cartService.findByBatchByIdOnMoney(list);
                Emply emply =(Emply) byBatchByIdOnMoney.get("order");
                Emply byEmplyNo = emplyService.findByEmplyNo(emply.getEmplyno());
            try {
                if(byEmplyNo.getSubsidyMoney().compareTo(emply.getAusedMoney().add(byEmplyNo.getAusedMoney()))==-1){
                    throw new RuntimeException();
                }else{
                    boolean bool = emplyService.editUserMoeny(new Emply(byEmplyNo.getEmplyno(),emply.getAusedMoney().add(byEmplyNo.getAusedMoney())));
                    if(bool==true){
                        return "order001";
                    }else{
                        return "order002";
                    }
                }
            } catch (Exception e) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return "order004";
            }
        }else{
            return "order002";
        }
  }
}
