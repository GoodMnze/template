package com.lingfeng.stellar.value;

import com.lingfeng.stellar.domain.Loan;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Value
public class ClearedLoan {
    @NonNull
    String platform;            // 借款平台
    @NonNull
    LocalDate borrowDate;       // 借款日期
    @NonNull
    BigDecimal principal;       // 本金
    @NonNull
    BigDecimal totalInterest;   // 总利息
    @NonNull
    BigDecimal totalRepayment;  // 总还款

   public static ClearedLoan of(Loan loan){
       return ClearedLoan.builder()
               .platform(loan.getPlatform())
               .borrowDate(loan.getBorrowDate())
               .principal(loan.getBorrowAmount())
               .totalInterest(loan.calculateTotalInterest())
               .totalRepayment(loan.getTotalRepayment())
               .build();
   }
}