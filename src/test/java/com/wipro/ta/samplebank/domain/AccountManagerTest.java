package com.wipro.ta.samplebank.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.ArrayList;

@RunWith(MockitoJUnitRunner.class)
public class AccountManagerTest {

	AccountManager accountManager = new AccountManager();

	@Mock
	AccountRepository accountRepositoryMock;

	@Mock
	Account acc;
	
	@Before
	public void setup() {
		accountManager.setAccountRepository(accountRepositoryMock);
		//acc.setOwnerCpf("123456789");
	}

	@Test
	public void givenAccountDoesNotExistThenCreateSuccessfully() {
		boolean result = accountManager.createAccount("123456789");
		Assert.assertThat(result, is(true));
		Mockito.verify(accountRepositoryMock, Mockito.times(1)).addAccount(Mockito.any(Account.class));

	}

	@Test
	public void givenAccountAlreadyExistsThenReturnFalse() {
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(new Account());
		boolean result = accountManager.createAccount("123456789");
		Assert.assertThat(result, is(false));
		Mockito.verify(accountRepositoryMock, Mockito.never()).addAccount(Mockito.any(Account.class));
	}

	@Test
	public void givenDepositDoneRight() {
 
		acc = new Account("123456789");
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(acc);

		BigDecimal v = acc.getBalance();

		// accountManager.createAccount("123456789");
		// Mockito.verify(accountRepositoryMock,Mockito.times(1)).addAccount(Mockito.any(Account.class));
		// Assert.assertEquals(v, BigDecimal.valueOf(0));

		accountManager.makeDeposit("123456789", "10");
		Assert.assertEquals(v.add(BigDecimal.valueOf(10)), acc.getBalance());

		// Assert.assertEquals(accountManager.getAccountBalance("123456789"),
		// 10);

	}

	@Test
	public void givenDepositDoneNull() {

		//Mock
		//Account acc = new Account("123456789");
		//acc = Mockito.mock(Account.class);

		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(null);
		accountManager.makeDeposit("123456799", "10");
		
		//Assert.assertEquals(null, acc.getBalance());
		//Mockito.verify(acc,Mockito.times(0)).deposit(acc.getBalance());

	}
	
	@Test
	public void getListOfAccounts() {

		//Account acc = new Account("123456789");
		//Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn((Account) Mockito.anyCollectionOf(Account.class));

		ArrayList accs = new ArrayList();
		Assert.assertEquals(accs, accountManager.getAllAccounts());
		
		Account acc1 = new Account("123456789");
		accs.add(acc1);

		Mockito.when(accountRepositoryMock.allAccounts()).thenReturn(accs);
		
		Assert.assertEquals(accs.size(), accountManager.getAllAccounts().size());

	}
	
	@Test
	public void getAccountTest() {

		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(acc);
		Assert.assertNotEquals(accountManager.getAccount(Mockito.anyString()),null);
	}
	
	@Test
	public void givenWithdrawDoneWithAccount() {

		acc = new Account("123456789");
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(acc);
		
		BigDecimal v = acc.getBalance();
		accountManager.makeDeposit("123456789", "10");

		accountManager.makeWithdraw("123456789", "10");
		Assert.assertEquals(v, acc.getBalance());
	}
	
	@Test
	public void givenWithdrawDoneWithNullAccount() {

		acc = Mockito.mock(Account.class);
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(null);
		
	}
	
	@Test
	public void givenWithdrawDoneWithAccountWithoutFunds() {

		acc = new Account("123456789");
		
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(acc);
		
		accountManager.makeDeposit("123456789", "5");
		BigDecimal v = acc.getBalance();

		accountManager.makeWithdraw("123456789", "10");
		Assert.assertEquals(v, acc.getBalance());
	}
	
	@Test
	public void givenTransferDoneWithAccountsSucess() {

		Account acc1 = new Account("123456789");
		Account acc2 = new Account("987654321");

		Mockito.when(accountRepositoryMock.findAccount("123456789")).thenReturn(acc1);
		Mockito.when(accountRepositoryMock.findAccount("987654321")).thenReturn(acc2);
		
		accountManager.makeDeposit("123456789", "50");
		accountManager.makeTransfer(acc1.getOwnerCpf(), acc2.getOwnerCpf(), "25");
		
		Assert.assertEquals(acc1.getBalance(), acc2.getBalance());
	}
	
	@Test
	public void givenAccountBalanceSucess() {

		Account acc1 = new Account("123456789");
		
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(acc1);
		
		acc1.deposit(BigDecimal.valueOf(50));
		accountManager.getAccountBalance(acc1.getOwnerCpf());
		
		Assert.assertNotEquals(null, acc1.getBalance());
		
	}
	
	@Test
	public void givenAccountBalanceNull() {

		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(null);
		
		accountManager.getAccountBalance(acc.getOwnerCpf());
		
		Assert.assertEquals(null, acc.getBalance());
		
	}
	
	@Test
	public void givenDeleteAccountSucess() {

		Account acc1 = new Account("123456789");
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(acc1);
		
		Boolean b = accountManager.deleteAccount("123456789");
		
		Assert.assertEquals(true, b);
		
	}
	
	@Test
	public void givenDeleteAccountFail() {

		//acc = Mockito.mock(Account.class);
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(null);
		
		Boolean b = accountManager.deleteAccount(acc.getOwnerCpf());
		
		Assert.assertEquals(false, b);
		
	}




}
