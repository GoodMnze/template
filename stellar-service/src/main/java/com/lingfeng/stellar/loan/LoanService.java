package com.lingfeng.stellar.loan;

import com.lingfeng.stellar.domain.Loan;
import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.repository.LoanRepository;
import com.lingfeng.stellar.value.RepaymentNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class LoanService {

    public final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> getAllLoan(){
        List<LoanPO> loans = loanRepository.findAll();
        return loans.stream().map(Loan::of).toList();
    }

    public List<RepaymentNode> getRepaymentNodes(List<Loan> loans){
        return Loan.getRepaymentNodes(loans);
    }

    public BigDecimal getTotalOutstandingAmount(List<Loan> loans){
        return Loan.calculateTotalOutstandingBalance(loans, LocalDate.now());
    }

    public Map<Integer, BigDecimal> getMonthlyOutstanding(List<Loan> loans){
        return Loan.groupByMonth(loans, LocalDate.now());
    }

    public BigDecimal getTotalOutstanding(List<Loan> loans){
        return Loan.calculateTotalOutstanding(loans);
    }
}
