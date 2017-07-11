package com.wipro.ta.samplebank.domain;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManager {
	
	private AccountRepository accountRepository;

	public boolean createAccount(String ownerCpf) {
		if (accountRepository.findAccount(ownerCpf) == null) {
			Account c = new Account(ownerCpf);
			accountRepository.addAccount(c);

			return true;
		}
		return false;
	}

	public List<Account> getAllAccounts() {
		return accountRepository.allAccounts();
	}

	public Account getAccount(String ownerCpf) {
		return accountRepository.findAccount(ownerCpf);
	}

	public void makeDeposit(String ownerCpf, String value) {
		Account c = accountRepository.findAccount(ownerCpf);
		if (c != null) {
			c.deposit(new BigDecimal(value));
		}
	}

	public void makeWithdraw(String ownerCpf, String value) {
		Account c = accountRepository.findAccount(ownerCpf);
		if (c != null) {
			BigDecimal amount = new BigDecimal(value);
			if (c.getBalance().compareTo(amount) >= 0) {
				c.withdraw(new BigDecimal(value));
			}
		}
	}

	public void makeTransfer(String originOwnerCpf, String targetOwnerCpf, String value) {
		Account origin = accountRepository.findAccount(originOwnerCpf);
		Account target = accountRepository.findAccount(targetOwnerCpf);

		if (origin != null && target != null) {
			BigDecimal amount = new BigDecimal(value);
			if (origin.getBalance().compareTo(amount) >= 0) {
				origin.withdraw(amount);
				target.deposit(amount);
			}
		}
	}

	public BigDecimal getAccountBalance(String ownerCpf) {
		Account c = accountRepository.findAccount(ownerCpf);
		if (c != null) {
			return c.getBalance();
		}
		return null;
	}

	public boolean deleteAccount(String ownerCpf) {
		Account c = accountRepository.findAccount(ownerCpf);
		if (c != null) {
			accountRepository.deleteAccount(c);
			return true;
		}
		return false;
	}
	
	public boolean deleteAllAccounts() {
		AccountRepository.clearAccounts();
		return true;
	}
	
	@Autowired
	public void setAccountRepository(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
}