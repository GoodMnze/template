package com.lingfeng.stellar.value;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;

@Slf4j
@Value
@Builder
public class RepaymentSchedule {
    // 每月还款日（1-31）
    int repaymentDay;

    // 分期次数
    int installmentCount;

    // 首次还款日期
    @NonNull
    LocalDate firstRepaymentDate;

    // 每期金额
    @NonNull
    BigDecimal installmentAmount;

    @Builder
    public RepaymentSchedule(int repaymentDay, int installmentCount,
                             @NonNull LocalDate firstRepaymentDate,
                             @NonNull BigDecimal installmentAmount) {
        validateParameters(repaymentDay, installmentCount, installmentAmount);

        this.repaymentDay = repaymentDay;
        this.installmentCount = installmentCount;
        this.firstRepaymentDate = firstRepaymentDate;
        this.installmentAmount = installmentAmount;
    }

    private void validateParameters(int repaymentDay, int installmentCount, BigDecimal installmentAmount) {
        if (repaymentDay < 1 || repaymentDay > 31) {
            throw new IllegalArgumentException("还款日必须在1-31之间");
        }
        if (installmentCount <= 0) {
            throw new IllegalArgumentException("分期次数必须大于0");
        }
        if (installmentAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("每期金额必须大于0");
        }
    }

    public BigDecimal calculateRemainingBalance(LocalDate date) {
        LocalDate finalRepaymentDate = getFinalRepaymentDate();

        // 计算下一个有效还款日
        LocalDate nextRepayment = calculateNextRepaymentDate(date);

        // 如果已经超过最终还款日则无剩余
        if (nextRepayment.isAfter(finalRepaymentDate)) {
            return BigDecimal.ZERO;
        }

        // 计算剩余期数（含头尾）
        long monthsBetween = ChronoUnit.MONTHS.between(nextRepayment, finalRepaymentDate);
        int remainingPeriods = (int) monthsBetween + 1;

        return installmentAmount.multiply(BigDecimal.valueOf(remainingPeriods));
    }

    private LocalDate calculateNextRepaymentDate(LocalDate currentDate) {
        // 首次还款日之前的日期直接返回首次还款日
        if (currentDate.isBefore(firstRepaymentDate)) {
            return firstRepaymentDate;
        }

        // 计算基准日期（当前周期的起始月）
        LocalDate baseDate = currentDate.getDayOfMonth() > repaymentDay
                ? currentDate.plusMonths(1)
                : currentDate;

        // 适配当月的有效还款日
        return adjustToValidDay(baseDate, repaymentDay);
    }

    public LocalDate getFinalRepaymentDate() {
        LocalDate finalDate = firstRepaymentDate.plusMonths(installmentCount - 1);
        return adjustToValidDay(finalDate, repaymentDay);
    }

    public BigDecimal getTotalRepayment(){
        return installmentAmount.multiply(BigDecimal.valueOf(installmentCount));
    }

    public BigDecimal get(BigDecimal principal){
        return getTotalRepayment().subtract(principal);
    }

    private LocalDate adjustToValidDay(LocalDate date, int targetDay) {
        // 使用智能日期调整策略
        return date.with(createDayAdjuster(targetDay));
    }

    private TemporalAdjuster createDayAdjuster(int targetDay) {
        return temporal -> {
            LocalDate date = LocalDate.from(temporal);
            int validDay = Math.min(targetDay, date.lengthOfMonth());
            return date.withDayOfMonth(validDay);
        };
    }
}

