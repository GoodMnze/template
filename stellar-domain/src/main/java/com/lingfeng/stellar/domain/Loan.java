package com.lingfeng.stellar.domain;

import com.lingfeng.stellar.po.LoanPO;
import com.lingfeng.stellar.value.RepaymentNode;
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
import java.util.*;
import java.util.stream.Collectors;

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

    public static Loan of(@NonNull LoanPO loanPO) {
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

    public LocalDate getClearDate() {
        return repaymentSchedule.getFinalRepaymentDate();
    }


    // 领域能力：判断是否在指定日期已结清
    public boolean isClearedOn(LocalDate date) {
        return date.isAfter(repaymentSchedule.getFinalRepaymentDate());
    }

    // 领域能力：获取贷款总还款额（本金+利息）
    public BigDecimal getTotalRepayment() {
        return repaymentSchedule.getInstallmentAmount()
                .multiply(BigDecimal.valueOf(repaymentSchedule.getInstallmentCount()));
    }

    // 领域能力：计算已产生的总利息
    public BigDecimal calculateTotalInterest() {
        return getTotalRepayment().subtract(borrowAmount);
    }

    // 计算能力：每月剩余总待还
    public static BigDecimal remainingMonthlyPayment(List<Loan> loans, LocalDate date){
        return loans.stream().filter(loan -> !loan.isClearedOn(date))
                .map(loan -> loan.getRepaymentSchedule().getInstallmentAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private static LocalDate calculateFirstRepaymentDate(LocalDate borrowDate, int repaymentDay) {
        LocalDate baseDate = borrowDate.plusMonths(1);
        return baseDate.with(TemporalAdjusters.lastDayOfMonth())
                .withDayOfMonth(Math.min(repaymentDay, baseDate.lengthOfMonth()));
    }

    // 计算每月待还
    public static BigDecimal calculateTotalOutstandingBalance(List<Loan> loans, LocalDate date) {
        if (CollectionUtils.isEmpty(loans)) {
            return BigDecimal.ZERO;
        }
        return loans.stream()
                .map(loan -> loan.getRepaymentSchedule().calculateRemainingBalance(date))
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static Map<Integer, BigDecimal> groupByMonth(List<Loan> loans, LocalDate date) {
        return loans.stream()
                .filter(loan -> !loan.isClearedOn(date))
                .collect(Collectors.groupingBy(
                        loan -> loan.getRepaymentSchedule().getRepaymentDay(),
                        TreeMap::new,  // 使用TreeMap确保按键排序
                        Collectors.mapping(
                                loan -> loan.getRepaymentSchedule().getInstallmentAmount(),
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ));
    }

    public static BigDecimal calculateTotalOutstanding(List<Loan> loans){
        return loans.stream()
                .map(loan -> loan.getRepaymentSchedule().getInstallmentAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static List<RepaymentNode> getRepaymentNodes(List<Loan> loans){
        return RepaymentNode.of(loans);
    }


}