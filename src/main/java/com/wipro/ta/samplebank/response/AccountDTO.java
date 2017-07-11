package com.wipro.ta.samplebank.response;

import java.io.Serializable;

import com.wipro.ta.samplebank.domain.Account;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String ownerCpf;
	private String balance;
	private String hasPendingLoan;

	public AccountDTO() {

	}

	public AccountDTO(Account account) {
		this.ownerCpf = account.getOwnerCpf();
		this.balance = String.valueOf(account.getBalance());
		this.hasPendingLoan = String.valueOf(account.hasPendingLoan());
	}

	public String getOwnerCpf() {
		return ownerCpf;
	}

	public void setOwnerCpf(String ownerCpf) {
		this.ownerCpf = ownerCpf;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getHasPendingLoan() {
		return hasPendingLoan;
	}

	public void setHasPendingLoan(String hasPendingLoan) {
		this.hasPendingLoan = hasPendingLoan;
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
		AccountDTO other = (AccountDTO) obj;
		if (ownerCpf == null) {
			if (other.ownerCpf != null)
				return false;
		} else if (!ownerCpf.equals(other.ownerCpf))
			return false;
		return true;
	}
}