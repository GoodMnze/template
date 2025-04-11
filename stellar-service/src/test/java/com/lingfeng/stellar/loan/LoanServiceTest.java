package com.lingfeng.stellar.loan;

import com.alibaba.fastjson.JSON;
import com.lingfeng.stellar.ServiceTestConfig;
import com.lingfeng.stellar.domain.Loan;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class LoanServiceTest extends ServiceTestConfig {

    @Autowired
    private LoanService loanService;

    @Test
    public void crudTest(){
        List<Loan> loans = loanService.getAllLoan();
        log.info("loans: {}", JSON.toJSONString(loans));
    }

}
