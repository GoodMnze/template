package com.lingfeng.stellar.repository;


import com.lingfeng.stellar.po.LoanPO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanPO, Long> {

}