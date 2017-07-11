package com.wipro.ta.samplebank.controller.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wipro.ta.samplebank.controller.api.AccountController;
import com.wipro.ta.samplebank.controller.api.CreditController;
import com.wipro.ta.samplebank.domain.Account;
import com.wipro.ta.samplebank.response.AccountDTO;
import com.wipro.ta.samplebank.response.OperationDTO;
import com.wipro.ta.samplebank.response.ResponseDTO;

@Controller
public class AdminController {

	@Autowired
	private AccountController accountController;

	@Autowired
	private CreditController creditController;
	
	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.GET)
	public String accountInfo(Model model) {
		model.addAttribute("account", new AccountDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		return "admin/accountInfo";
	}
	
	@RequestMapping(value = { "/accountInfo" }, method = RequestMethod.POST)
	public String getAccountInfo(HttpServletResponse httpResponse, Model model, AccountDTO account) {
		ResponseDTO<AccountDTO> responseDTO = accountController.getAccount(account.getOwnerCpf(), httpResponse);
		model.addAttribute("account", account);
		model.addAttribute("accounts", accountController.getAllAccounts());
		model.addAttribute("responseDTO", responseDTO);
		return "admin/accountInfo";
	}

	@RequestMapping(value = { "/addAccount" }, method = RequestMethod.GET)
	public String addAccount(Model model) {
		model.addAttribute("account", new AccountDTO());
		return "admin/addAccount";
	}

	@RequestMapping(value = { "/addAccount" }, method = RequestMethod.POST)
	public String saveAccount(Account account, HttpServletResponse httpResponse, Model model) {
		String numericCpf = account.getOwnerCpf().replaceAll("[\\.-]", "");
		ResponseDTO<AccountDTO> responseDTO = accountController.createAccount(numericCpf, httpResponse);
		model.addAttribute("responseDTO", responseDTO);
		return addAccount(model);
	}

	@RequestMapping(value = { "/withdraw" }, method = RequestMethod.GET)
	public String withdraw(Model model) {
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		return "admin/withdraw";
	}

	@RequestMapping(value = { "/withdraw" }, method = RequestMethod.POST)
	public String makeWithdraw(HttpServletResponse httpResponse, Model model, OperationDTO operation) {
		ResponseDTO<AccountDTO> responseDTO = accountController.makeWithdraw(operation.getTargetAccount(),
				operation.getAmount(), httpResponse);
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		model.addAttribute("responseDTO", responseDTO);
		return "admin/withdraw";
	}

	@RequestMapping(value = { "/deposit" }, method = RequestMethod.GET)
	public String deposit(Model model) {
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		return "admin/deposit";
	}

	@RequestMapping(value = { "/deposit" }, method = RequestMethod.POST)
	public String makeDeposit(HttpServletResponse httpResponse, Model model, OperationDTO operation) {
		ResponseDTO<AccountDTO> responseDTO = accountController.makeDeposit(operation.getTargetAccount(),
				operation.getAmount(), httpResponse);
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		model.addAttribute("responseDTO", responseDTO);
		return "admin/deposit";
	}

	@RequestMapping(value = { "/transfer" }, method = RequestMethod.GET)
	public String transfer(Model model) {
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		return "admin/transfer";
	}

	@RequestMapping(value = { "/transfer" }, method = RequestMethod.POST)
	public String makeTransfer(HttpServletResponse httpResponse, Model model, OperationDTO operation) {
		ResponseDTO<AccountDTO> responseDTO = accountController.makeTransfer(operation.getSourceAccount(),
				operation.getTargetAccount(), operation.getAmount(), httpResponse);
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		model.addAttribute("responseDTO", responseDTO);
		return "admin/transfer";
	}

	@RequestMapping(value = { "/loan" }, method = RequestMethod.GET)
	public String loan(Model model) {
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		return "admin/loan";
	}

	@RequestMapping(value = { "/loan" }, method = RequestMethod.POST)
	public String getLoan(HttpServletResponse httpResponse, Model model, OperationDTO operation) {
		ResponseDTO<AccountDTO> responseDTO = creditController.makeLoan(operation.getTargetAccount(),
				operation.getAmount(), httpResponse);
		model.addAttribute("operation", new OperationDTO());
		model.addAttribute("accounts", accountController.getAllAccounts());
		model.addAttribute("responseDTO", responseDTO);
		return "admin/loan";
	}
}