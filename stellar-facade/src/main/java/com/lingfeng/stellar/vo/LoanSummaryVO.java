package com.lingfeng.stellar.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanSummaryVO {
    private List<LoanVO> monthlyOutstanding;

    private BigDecimal outstandingBalance;

    private List<LoanVO> repaymentScheduleCurve;

    private List<LoanVO> allLoans;
}
