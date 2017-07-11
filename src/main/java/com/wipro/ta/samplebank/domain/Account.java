package com.wipro.ta.samplebank.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {

	private String ownerCpf;
	private BigDecimal balance;
	private boolean hasPendingLoan;

	public Account() {
		
	}
	
	public Account(String ownerCpf) {
		this.ownerCpf = ownerCpf;
		this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public String getOwnerCpf() {
		return ownerCpf;
	}

	public void setOwnerCpf(String ownerCpf) {
		this.ownerCpf = ownerCpf;
	}

	public boolean hasPendingLoan() {
		return hasPendingLoan;
	}

	public void setHasPendingLoan(boolean hasPendingLoan) {
		this.hasPendingLoan = hasPendingLoan;
	}

	public void deposit(BigDecimal value) {
		this.balance = this.balance.add(value).setScale(2, RoundingMode.HALF_EVEN);
	}

	public void withdraw(BigDecimal value) {
		this.balance = this.balance.subtract(value).setScale(2, RoundingMode.HALF_EVEN);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ownerCpf == null) ? 0 : ownerCpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (ownerCpf == null) {
			if (other.ownerCpf != null)
				return false;
		} else if (!ownerCpf.equals(other.ownerCpf))
			return false;
		return true;
	}
}