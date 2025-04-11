package com.lingfeng.stellar.value;

import com.google.common.collect.Lists;
import com.lingfeng.stellar.domain.Loan;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Builder
@Value
public class RepaymentNode {
    @NonNull
    LocalDate nodeDate;                          // 节点日期
    ClearedLoan clearedLoan;                     // 结清贷款
    BigDecimal remainingMonthAmount;             //  剩余每月待还
    BigDecimal remainingTotalAmount;             // 剩余待还总额

    public static List<RepaymentNode> of(List<Loan> loans){
        List<@Nullable RepaymentNode> repaymentNodes = Lists.newArrayList();
        loans.stream()
                .sorted(Comparator.comparing(loan -> loan.getRepaymentSchedule().getFinalRepaymentDate()))
                .forEach(loan -> {
                    RepaymentNode repaymentNode = RepaymentNode.builder()
                            .nodeDate(loan.getRepaymentSchedule().getFinalRepaymentDate())
                            .clearedLoan(ClearedLoan.of(loan))
                            .remainingMonthAmount(Loan.remainingMonthlyPayment(loans, loan.getRepaymentSchedule().getFinalRepaymentDate()))
                            .remainingTotalAmount(Loan.calculateTotalOutstandingBalance(loans, loan.getRepaymentSchedule().getFinalRepaymentDate()))
                            .build();
                    repaymentNodes.add(repaymentNode);
                });
        return repaymentNodes;
    }
}