package cn.com.syzg.controller;

import cn.com.syzg.model.Emply;
import cn.com.syzg.service.EmplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequestMapping("/emp")
@Controller
public class EmplyController {
    @Autowired
    private EmplyService emplyService;

    @RequestMapping("findByEmplyNo")
    public String getEmply(HttpSession session, @RequestParam(value = "empNo",required = false)Integer id){
        Emply byEmpNo = emplyService.findByEmpNo(id);
        session.setAttribute("byEmp",byEmpNo);
        return "/user";
    }

    @RequestMapping("userHtml")
    public String HTML(){
        return "/user";
    }

}
