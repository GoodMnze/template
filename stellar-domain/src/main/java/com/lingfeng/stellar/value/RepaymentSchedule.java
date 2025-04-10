package com.lingfeng.stellar.value;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class RepaymentSchedule {
    int repaymentDay; // 每月还款日（1-31）
    int installmentCount; // 分期次数
    BigDecimal installmentAmount; // 每期金额
}
