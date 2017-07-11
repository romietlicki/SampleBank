package com.wipro.ta.samplebank.domain;

import java.nio.file.FileAlreadyExistsException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AccountRepositoryTest {
	
	AccountRepository accountRepository;
	
	@Before
	public void setup() {
		accountRepository = new AccountRepository();
		
		// Create Test Fixtures
		accountRepository.addAccount(createAccount("06444870976"));
		accountRepository.addAccount(createAccount("06444870977"));
	}
	
	@Test
	public void testAllAccountsReturnsSuccessful() {
		int size = accountRepository.allAccounts().size();
		Assert.assertEquals(2, size);
	}
	
	@Test
	public void testFindAccountReturnsExistingAccount() {
		Account account = accountRepository.findAccount("06444870976");
		Assert.assertNotNull(account);
	}
	
	@Test(expected = FileAlreadyExistsException.class)
	public void testFindAccountReturnsNullWhenAccountDoesNotExist() {
		accountRepository.findAccount("1111111111");
	}
	
	@Test
	public void testDeleteAccountSuccessful() {
		accountRepository.deleteAccount(createAccount("06444870976"));
		Assert.assertEquals(1, accountRepository.allAccounts().size());
		
		Account account = accountRepository.findAccount("06444870977");
		Assert.assertNotNull(account);
	}
	
	private Account createAccount(String cpf) {
		Account a = new Account();
		a.setOwnerCpf(cpf);
		return a;
	}
}