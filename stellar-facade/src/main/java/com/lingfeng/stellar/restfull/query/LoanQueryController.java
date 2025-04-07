package com.lingfeng.stellar.restfull.query;

import com.lingfeng.stellar.loan.LoanService;
import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.restfull.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xiaolingfeng
 * @date 2023/4/12 12:49 PM
 */

@RestController
@RequestMapping("/api/loan")
public class LoanQueryController {

    @Autowired
    private LoanService loanService;

    @RequestMapping("/query")
    public List<LoanPO> queryUserById(){
        return loanService.queryAll();
    }
}
