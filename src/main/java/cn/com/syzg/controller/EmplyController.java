package cn.com.syzg.controller;

import cn.com.syzg.model.Emply;
import cn.com.syzg.model.Goods;
import cn.com.syzg.service.EmplyService;
import cn.com.syzg.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/emp")
@Controller
public class EmplyController {
    @Resource
    private EmplyService emplyService;
    @Resource
    private GoodsService goodsService;

    @RequestMapping("userHtml")
    public String HTML(Model model,HttpSession session,
                       HttpServletRequest request,@RequestParam(value = "emplyNo",required = false)String emplyNo){
        //Integer id=Integer.parseInt(request.getParameter("emplyNo"));
        Emply byEmplyNo = emplyService.findByEmplyNo(emplyNo);
        session.setAttribute("emplyNo",byEmplyNo.getEmplyno());
        model.addAttribute("byEmplyNo",byEmplyNo);
        return "/user";
    }

    @RequestMapping("my_allowanceHtml")
    public String my_allowanceHtml(Model model,HttpSession session,
                                   @RequestParam(value = "emplyNo",required = false)String emplyNo){
        session.setAttribute("emplyNoThree",emplyNo);
        return "/my_allowance";
    }

    @RequestMapping("subsidy_listHtml")
    public String subsidy_listHtml(Model model,HttpSession session,
                                   @RequestParam(value = "emplyNo",required = false)String emplyNo){
        List<Goods> goodsAll = goodsService.findAll();
        System.out.println(emplyNo+"+++");
        session.setAttribute("emplyNoTwo",emplyNo);
        model.addAttribute("goodsAll",goodsAll);
        return "/subsidy_list";
    }

}
