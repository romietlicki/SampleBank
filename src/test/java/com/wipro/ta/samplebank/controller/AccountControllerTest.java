package com.wipro.ta.samplebank.controller;

import static org.hamcrest.Matchers.*;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.wipro.ta.samplebank.controller.api.AccountController;
import com.wipro.ta.samplebank.domain.Account;
import com.wipro.ta.samplebank.domain.AccountManager;
import com.wipro.ta.samplebank.domain.CreditManager;
import com.wipro.ta.samplebank.response.AccountDTO;
import com.wipro.ta.samplebank.response.ResponseDTO;
import com.wipro.ta.samplebank.response.ResponseMessage;

@RunWith(MockitoJUnitRunner.class)
public class AccountControllerTest {

	AccountController accountController =  new AccountController();
	
	@Mock
	AccountManager accountManagerMock;
	
	@Mock
	HttpServletResponse httpServletResponseMock;
	
	@Before
	public void setup() {
		accountController.setAccountManager(accountManagerMock);
	}
	
	@Test
	public void createAccountAllowed(){
		Mockito.when(accountManagerMock.createAccount(Mockito.anyString())).thenReturn(true);
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null, new Account());
		ResponseDTO<AccountDTO> response = accountController.createAccount("12345678910", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
	}
	
	@Test
	public void createAccountNotAllowed(){
		Mockito.when(accountManagerMock.createAccount(Mockito.anyString())).thenReturn(false);
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null);
		ResponseDTO<AccountDTO> response = accountController.createAccount("12345678910", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.OPERATION_ERROR.getCode(), response.getCode());
	}
	
	@Test
	public void createAccountWithExistAccount(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		ResponseDTO<AccountDTO> response = accountController.createAccount("12345678910", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.ACCOUNT_ALREADY_EXISTS.getCode(), response.getCode());
	}
	
	@Test
	public void createAccountWithWrongCPF(){
		ResponseDTO<AccountDTO> response = accountController.createAccount("1234567890", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.INVALID_CPF.getCode(), response.getCode());
	}
	
	@Test
	public void getAccountFound(){
		Mockito.when(accountManagerMock.createAccount(Mockito.anyString())).thenReturn(true);
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		ResponseDTO<AccountDTO> response = accountController.getAccount("12345678910", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
	}
	
	@Test
	public void getAccountNotFound(){
		Mockito.when(accountManagerMock.createAccount(Mockito.anyString())).thenReturn(true);
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null);
		ResponseDTO<AccountDTO> response = accountController.getAccount("12345678910", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.ACCOUNT_NOT_FOUND.getCode(), response.getCode());
	}
	
	@Test
	public void getAccountWithWrongCPF(){
		ResponseDTO<AccountDTO> response = accountController.getAccount("1234567891", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.INVALID_CPF.getCode(), response.getCode());
	}
	
	@Test
	public void makeDepositAllowed(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		ResponseDTO<AccountDTO> response = accountController.makeDeposit("12345678910", "5", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
	}
	
	@Test
	public void makeDepositWrongAmount(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		ResponseDTO<AccountDTO> response = accountController.makeDeposit("12345678910", "0", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.INVALID_AMMOUNT.getCode(), response.getCode());
	}
	
	@Test
	public void makeDepositWrongAccount(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null);
		ResponseDTO<AccountDTO> response = accountController.makeDeposit("12345678910", "0", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.ACCOUNT_NOT_FOUND.getCode(), response.getCode());
	}
	
	@Test
	public void makeDepositWrongCPF(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null);
		ResponseDTO<AccountDTO> response = accountController.makeDeposit("1234567890", "0", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.INVALID_CPF.getCode(), response.getCode());
	}
	
	@Test
	public void makeWithdrawAllowed(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		accountController.makeDeposit("12345678910", "15", httpServletResponseMock);
		ResponseDTO<AccountDTO> response = accountController.makeWithdraw("12345678910", "5", httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
	}
	
	@Test
	public void makeWithdrawWrongAmount(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		ResponseDTO<AccountDTO> response = accountController.makeWithdraw("12345678910", "0", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.INVALID_AMMOUNT.getCode(), response.getCode());
	}
	
	@Test
	public void makeWithdrawWrongAccount(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null);
		ResponseDTO<AccountDTO> response = accountController.makeWithdraw("12345678910", "0", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.ACCOUNT_NOT_FOUND.getCode(), response.getCode());
	}
	
	@Test
	public void makeWithdrawWrongCPF(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null);
		ResponseDTO<AccountDTO> response = accountController.makeWithdraw("1234567890", "0", httpServletResponseMock);
		Assert.assertEquals(ResponseMessage.INVALID_CPF.getCode(), response.getCode());
	}
	
	@Test
	public void makeTransferAllowed(){
		Mockito.when(accountManagerMock.getAccount("12345678910")).thenReturn(new Account());
		Mockito.when(accountManagerMock.getAccount("10987654321")).thenReturn(new Account());

		accountController.makeDeposit("12345678910", "500", httpServletResponseMock);
		
		ResponseDTO<AccountDTO> response = accountController.makeTransfer("12345678910", "10987654321","50", httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
	}
	
	@Test
	public void makeTrasnferWrongAmount(){
		Mockito.when(accountManagerMock.getAccount("12345678910")).thenReturn(new Account());
		Mockito.when(accountManagerMock.getAccount("10987654321")).thenReturn(new Account());

		accountController.makeDeposit("12345678910", "500", httpServletResponseMock);
		
		ResponseDTO<AccountDTO> response = accountController.makeTransfer("12345678910", "10987654321","0", httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.INVALID_AMMOUNT.getCode(), response.getCode());
	}
	
	@Test
	public void makeTrasnferWrongAccount(){
		Mockito.when(accountManagerMock.getAccount("12345678910")).thenReturn(null);
		Mockito.when(accountManagerMock.getAccount("10987654321")).thenReturn(new Account());

		accountController.makeDeposit("12345678910", "500", httpServletResponseMock);
		
		ResponseDTO<AccountDTO> response = accountController.makeTransfer("12345678910", "10987654321","500", httpServletResponseMock);

		Assert.assertEquals(ResponseMessage.ACCOUNT_NOT_FOUND.getCode(), response.getCode());
	}
	
	@Test
	public void makeTrasnferWrongCPF(){
		Mockito.when(accountManagerMock.getAccount("123456710")).thenReturn(new Account());
		Mockito.when(accountManagerMock.getAccount("109876541")).thenReturn(new Account());

		accountController.makeDeposit("123456710", "500", httpServletResponseMock);
		
		ResponseDTO<AccountDTO> response = accountController.makeTransfer("12345678910", "109876541","500", httpServletResponseMock);

		Assert.assertEquals(ResponseMessage.INVALID_CPF.getCode(), response.getCode());
	}
	
	@Test
	public void deleteAccountAllowed(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		Mockito.when(accountManagerMock.deleteAccount(Mockito.anyString())).thenReturn(true);
		ResponseDTO<AccountDTO> response = accountController.deleteAccount("12345678910", httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
	}
	
	@Test
	public void deleteAccountNotAllowed(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(new Account());
		Mockito.when(accountManagerMock.deleteAccount(Mockito.anyString())).thenReturn(false);
		ResponseDTO<AccountDTO> response = accountController.deleteAccount("12345678910", httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.OPERATION_ERROR.getCode(), response.getCode());
	}
	
	@Test
	public void deleteAccountWrongUser(){
		Mockito.when(accountManagerMock.getAccount(Mockito.anyString())).thenReturn(null);
		ResponseDTO<AccountDTO> response = accountController.deleteAccount("12345678910", httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.ACCOUNT_NOT_FOUND.getCode(), response.getCode());
	}
	
	@Test
	public void deleteAccountWrongCPF(){
		ResponseDTO<AccountDTO> response = accountController.deleteAccount("1234567891", httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.INVALID_CPF.getCode(), response.getCode());
	}
	
	@Test
	public void deleteAllAccount(){
		ResponseDTO<AccountDTO> response = accountController.deleteAllAccounts(httpServletResponseMock);
		
		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
	}
	
//	@Test
//	public void getAllAccounts(){
//		List<AccountDTO> response = accountController.getAllAccounts();
//		
//		Assert.assertEquals(ResponseMessage.SUCCESS.getCode(), response.getCode());
//	}

	
}
