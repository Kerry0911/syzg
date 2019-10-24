package cn.com.syzg.service.Impl;

import cn.com.syzg.model.Emply;
import cn.com.syzg.repository.EmplyMapper;
import cn.com.syzg.service.EmplyService;
import cn.com.syzg.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmplyServiceImpl  implements EmplyService {

    @Resource
    private EmplyMapper emplyMapper;
    @Resource
    private OrderService orderService;

    @Override
    public Emply findByEmplyNo(String emplyNo) {
        return emplyMapper.findByEmplyNo(emplyNo);
    }

    @Override
    public boolean editUserMoeny(Emply emply) {
        return emplyMapper.updateEmplyNo(emply)>0?true:false;
    }
}
