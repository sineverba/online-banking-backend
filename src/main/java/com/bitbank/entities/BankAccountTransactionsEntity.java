package com.bitbank.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder=true)
@Entity
@Table(name="bank_account_transactions")
public class BankAccountTransactionsEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", columnDefinition="INT(11) UNSIGNED")
    private Long id;
	
	@Column(name="amount", precision=11, scale=2)
	private BigDecimal amount;
	
	@Column(name="purpose", columnDefinition="VARCHAR(256)")
    private String purpose;
	
	@CreationTimestamp
    private LocalDateTime transactionDate;
	
}
