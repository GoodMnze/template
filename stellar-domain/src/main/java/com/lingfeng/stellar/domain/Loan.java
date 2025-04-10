package com.lingfeng.stellar.domain;

import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.value.RepaymentSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
public class Loan {
    private Integer id; // 聚合根ID
    private final String platform; // 值对象：平台名称
    private final Date borrowDate; // 值对象：借款日期
    private final BigDecimal borrowAmount; // 值对象：借款金额
    private final RepaymentSchedule repaymentSchedule; // 值对象：还款计划
    private final LocalDateTime createTime; // 审计字段
    private final LocalDateTime updateTime; // 审计字段

    public static Loan create(LoanPO loanPO) {
        return Loan.builder()
                .id(loanPO.getId())
                .platform(loanPO.getPlatform())
                .borrowAmount(loanPO.getBorrowAmount())
                .repaymentSchedule(RepaymentSchedule.builder()
                        .repaymentDay(loanPO.getRepaymentDay())
                        .installmentCount(loanPO.getInstallmentCount())
                        .installmentCount(loanPO.getInstallmentCount())
                        .build())
                .createTime(loanPO.getCreateTime())
                .updateTime(loanPO.getUpdateTime())
                .build();
    }
}
