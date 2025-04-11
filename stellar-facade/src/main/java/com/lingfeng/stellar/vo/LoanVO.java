package com.lingfeng.stellar.vo;

import com.lingfeng.stellar.domain.Loan;
import com.lingfeng.stellar.value.RepaymentSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanVO {
    private Integer id;
    private String platform;
    private Date borrowDate;
    private BigDecimal borrowAmount;
    private RepaymentSchedule repaymentSchedule;

    public static LoanVO of(Loan loan){
        return LoanVO.builder()
                .id(loan.getId())
                .platform(loan.getPlatform())
                .borrowAmount(loan.getBorrowAmount())
                .repaymentSchedule(loan.getRepaymentSchedule()).build();
    }
}
