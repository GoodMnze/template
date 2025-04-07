package com.lingfeng.stellar.po;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "loan", schema = "lighthouse")
public class LoanPO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "platform", nullable = false, length = 50)
    private String platform;

    @NotNull
    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate;

    @NotNull
    @Column(name = "borrow_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal borrowAmount;

    @NotNull
    @Column(name = "repayment_day", nullable = false)
    private Byte repaymentDay;

    @NotNull
    @Column(name = "installment_count", nullable = false)
    private Integer installmentCount;

    @NotNull
    @Column(name = "installment_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal installmentAmount;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

}