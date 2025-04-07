package com.lingfeng.stellar.loan;

import com.lingfeng.stellar.StellarServiceTest;
import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LoanServiceTest extends StellarServiceTest {

    @Autowired
    private LoanService loanService;

    @Test
    public void crudTest() {
        List<LoanPO> loanPOS = loanService.queryAll();
        log.info(loanPOS.toString());
    }
}
