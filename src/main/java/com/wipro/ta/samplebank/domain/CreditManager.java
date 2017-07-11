package com.wipro.ta.samplebank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditManager {

	private BigDecimal MINIMUM_BALANCE = new BigDecimal("2000.00");
	private BigDecimal LOAN_ALLOWED_PERCENT = new BigDecimal("0.3");

	private AccountRepository accountRepository;
	
	public void makeLoan(String ownerCpf, BigDecimal loanAmount) {
		Account c = accountRepository.findAccount(ownerCpf);
		if (c != null) {
			if (c.getBalance().compareTo(getMinimumBalance()) >= 0) {
				if (loanAmount.compareTo(getAllowedAmount(c)) <= 0) {
					c.deposit(loanAmount);
					c.setHasPendingLoan(true);
				}
			}
		}
	}

	public BigDecimal getAllowedAmount(Account c) {
		return c.getBalance().multiply(LOAN_ALLOWED_PERCENT).setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getMinimumBalance() {
		return MINIMUM_BALANCE;
	}
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}