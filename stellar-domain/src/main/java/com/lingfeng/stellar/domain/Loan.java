package com.lingfeng.stellar.domain;

import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.value.RepaymentSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@Slf4j
@Getter
@Builder
@AllArgsConstructor
public class Loan {
    // 聚合根ID
    private Integer id;

    // 值对象：平台名称
    @NonNull
    private final String platform;

    // 值对象：借款日期
    @NonNull
    private final LocalDate borrowDate;

    // 值对象：借款金额
    @NonNull
    private final BigDecimal borrowAmount;

    // 值对象：还款计划
    @NonNull
    private final RepaymentSchedule repaymentSchedule;

    // 审计字段
    @NonNull
    private final LocalDateTime createTime;

    // 审计字段
    @NonNull
    private final LocalDateTime updateTime;

    public static Loan create(@NonNull LoanPO loanPO) {
        // 参数校验
        validateLoanPO(loanPO);

        // 计算首次还款日（借款日下个月的同日，并适配还款日规则）
        LocalDate firstRepaymentDate = calculateFirstRepaymentDate(
                loanPO.getBorrowDate(),
                loanPO.getRepaymentDay()
        );

        return Loan.builder()
                .id(loanPO.getId())
                .platform(loanPO.getPlatform())
                .borrowDate(loanPO.getBorrowDate())
                .borrowAmount(loanPO.getBorrowAmount())
                .repaymentSchedule(RepaymentSchedule.builder()
                        .repaymentDay(loanPO.getRepaymentDay())
                        .installmentCount(loanPO.getInstallmentCount())
                        .installmentAmount(loanPO.getInstallmentAmount())
                        .firstRepaymentDate(firstRepaymentDate)
                        .build())
                .createTime(loanPO.getCreateTime())
                .updateTime(loanPO.getUpdateTime())
                .build();
    }

    private static void validateLoanPO(LoanPO loanPO) {
        Objects.requireNonNull(loanPO.getBorrowDate(), "借款日期不能为空");
        if (loanPO.getRepaymentDay() < 1 || loanPO.getRepaymentDay() > 31) {
            throw new IllegalArgumentException("还款日必须在1-31之间");
        }
        if (loanPO.getInstallmentCount() <= 0) {
            throw new IllegalArgumentException("分期次数必须大于0");
        }
        if (loanPO.getBorrowAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("借款金额必须大于0");
        }
    }

    private static LocalDate calculateFirstRepaymentDate(LocalDate borrowDate, int repaymentDay) {
        LocalDate baseDate = borrowDate.plusMonths(1);
        return baseDate.with(TemporalAdjusters.lastDayOfMonth())
                .withDayOfMonth(Math.min(repaymentDay, baseDate.lengthOfMonth()));
    }

    public static BigDecimal calculateTotalOutstandingBalance(List<Loan> loans) {
        if (CollectionUtils.isEmpty(loans)) {
            return BigDecimal.ZERO;
        }
        return loans.stream()
                .map(loan -> loan.getRepaymentSchedule().calculateRemainingBalance())
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}