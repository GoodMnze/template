package com.lingfeng.stellar.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanSummaryVO {
    private Map<Integer, BigDecimal> monthlyOutstanding;

    private BigDecimal totalOutstanding;

    private BigDecimal outstandingBalance;

    private List<LoanVO> repaymentScheduleCurve;

    private List<LoanVO> allLoans;
}
