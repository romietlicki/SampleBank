package com.wipro.ta.samplebank.domain;

import static org.hamcrest.Matchers.is;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CreditManagerTest {
	
	CreditManager creditManager = new CreditManager();

	@Mock
	AccountRepository accountRepositoryMock;
	
	@Before
	public void setup() {
		creditManager.setAccountRepository(accountRepositoryMock);
	}
	
	@Test
	public void givenMakeLoanSucess() {

		Account acc = new Account("123456789");
		acc.deposit(new BigDecimal("10000"));
		
		BigDecimal cash = new BigDecimal(3000);
		Mockito.when(accountRepositoryMock.findAccount(Mockito.anyString())).thenReturn(acc);

		creditManager.makeLoan(acc.getOwnerCpf(), cash);
		
		Assert.assertEquals(new BigDecimal("13000.00"), acc.getBalance());
		
//		Account account = new Account("089");
//		account.deposit(new BigDecimal("20000.00"));
//		Mockito.when(accountRepositoryMock.findAccount("089")).thenReturn(account);
//		creditManager.makeLoan("089", new BigDecimal("100"));
//		Assert.assertEquals(new BigDecimal("20100.00"), account.getBalance());
		
	}


}
