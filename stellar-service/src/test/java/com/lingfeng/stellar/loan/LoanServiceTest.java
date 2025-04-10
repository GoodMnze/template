package com.lingfeng.stellar.loan;

import com.lingfeng.stellar.RootServiceTest;
import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.repository.LoanRepository;
import jakarta.inject.Inject;
import jakarta.persistence.criteria.Root;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LoanServiceTest extends RootServiceTest {

    @Autowired
    private LoanService loanService;

    @Test
    public void crudTest(){
        assertEquals(1, loanService.loanRepository.findAll().get(0).getId());
    }
}
