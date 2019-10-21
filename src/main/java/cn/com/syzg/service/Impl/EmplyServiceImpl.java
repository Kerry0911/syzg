package cn.com.syzg.service.Impl;

import cn.com.syzg.model.Emply;
import cn.com.syzg.repository.EmplyMapper;
import cn.com.syzg.service.EmplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class EmplyServiceImpl  implements EmplyService {

    @Autowired
    private EmplyMapper emplyMapper;

    @Override
    public Emply findByEmpNo(Integer emplyNo) {
        return emplyMapper.findByEmplyNo(emplyNo);
    }
}
