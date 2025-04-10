package com.lingfeng.stellar.loan;

import com.lingfeng.stellar.domain.Loan;
import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    public final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> queryAll(){
        List<LoanPO> loans = loanRepository.findAll();
        return loans.stream().map(Loan::create).collect(Collectors.toList());
    }
}
