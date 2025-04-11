package com.lingfeng.stellar.restfull;

import com.lingfeng.stellar.domain.Loan;
import com.lingfeng.stellar.loan.LoanService;
import com.lingfeng.stellar.response.HttpResponse;
import com.lingfeng.stellar.vo.LoanSummaryVO;
import com.lingfeng.stellar.vo.LoanVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/loan")
public class LoanQueryController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/summary")
    public Map<String, Object> getLoanSummary(){
        List<Loan> allLoan = loanService.getAllLoan();
        LoanSummaryVO loanInfos = LoanSummaryVO.builder()
                .outstandingBalance(loanService.getTotalOutstandingAmount(allLoan))
                .allLoans(allLoan.stream().map(LoanVO::of).toList())
                .build();
        return HttpResponse.success(loanInfos);
    }
}
