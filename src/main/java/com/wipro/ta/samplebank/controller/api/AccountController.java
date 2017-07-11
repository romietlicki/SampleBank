package com.wipro.ta.samplebank.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wipro.ta.samplebank.domain.Account;
import com.wipro.ta.samplebank.domain.AccountManager;
import com.wipro.ta.samplebank.response.AccountDTO;
import com.wipro.ta.samplebank.response.ResponseDTO;
import com.wipro.ta.samplebank.response.ResponseMessage;
import com.wipro.ta.samplebank.validator.BusinessValidator;

@Controller
@RequestMapping("api/accounts")
public class AccountController {

	private AccountManager accountManager;

	@RequestMapping(value = "/", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> createAccount(@RequestParam(required = false) String ownerCpf,
			HttpServletResponse response) {

		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		if (BusinessValidator.isCpfValid(ownerCpf)) {
			if (accountManager.getAccount(ownerCpf) == null) {
				if (accountManager.createAccount(ownerCpf)) {

					responseDTO.setData(new AccountDTO(accountManager.getAccount(ownerCpf)));
					responseDTO.setMessage(ResponseMessage.SUCCESS);

				} else {
					responseDTO.setMessage(ResponseMessage.OPERATION_ERROR);
				}

			} else {
				responseDTO.setMessage(ResponseMessage.ACCOUNT_ALREADY_EXISTS);
			}

		} else {
			responseDTO.setMessage(ResponseMessage.INVALID_CPF);
		}

		response.setStatus(HttpStatus.OK.value());

		return responseDTO;
	}

	@RequestMapping(value = "/{ownerCpf}", method = RequestMethod.GET, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> getAccount(@PathVariable String ownerCpf, HttpServletResponse response) {

		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		if (BusinessValidator.isCpfValid(ownerCpf)) {

			Account account = accountManager.getAccount(ownerCpf);

			if (account != null) {
				responseDTO.setData(new AccountDTO(account));
				responseDTO.setMessage(ResponseMessage.SUCCESS);
			} else {
				responseDTO.setMessage(ResponseMessage.ACCOUNT_NOT_FOUND);
			}

		} else {
			responseDTO.setMessage(ResponseMessage.INVALID_CPF);
		}

		response.setStatus(HttpStatus.OK.value());

		return responseDTO;
	}

	@RequestMapping(value = "/{ownerCpf}/deposit", method = RequestMethod.PUT, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> makeDeposit(@PathVariable String ownerCpf, @RequestParam String value,
			HttpServletResponse response) {

		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		if (BusinessValidator.isCpfValid(ownerCpf)) {
			if (accountManager.getAccount(ownerCpf) != null) {
				if (BusinessValidator.isAmmountfValid(value)) {
					accountManager.makeDeposit(ownerCpf, value);
					responseDTO.setData(new AccountDTO(accountManager.getAccount(ownerCpf)));
					responseDTO.setMessage(ResponseMessage.SUCCESS);

				} else {
					responseDTO.setMessage(ResponseMessage.INVALID_AMMOUNT);
				}

			} else {
				responseDTO.setMessage(ResponseMessage.ACCOUNT_NOT_FOUND);
			}

		} else {
			responseDTO.setMessage(ResponseMessage.INVALID_CPF);
		}

		response.setStatus(HttpStatus.OK.value());

		return responseDTO;
	}

	@RequestMapping(value = "/{ownerCpf}/withdraw", method = RequestMethod.PUT, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> makeWithdraw(@PathVariable String ownerCpf, @RequestParam String value,
			HttpServletResponse response) {

		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		if (BusinessValidator.isCpfValid(ownerCpf)) {
			if (accountManager.getAccount(ownerCpf) != null) {
				if (BusinessValidator.isAmmountfValid(value)) {
					accountManager.makeWithdraw(ownerCpf, value);
					responseDTO.setData(new AccountDTO(accountManager.getAccount(ownerCpf)));
					responseDTO.setMessage(ResponseMessage.SUCCESS);

				} else {
					responseDTO.setMessage(ResponseMessage.INVALID_AMMOUNT);
				}

			} else {
				responseDTO.setMessage(ResponseMessage.ACCOUNT_NOT_FOUND);
			}

		} else {
			responseDTO.setMessage(ResponseMessage.INVALID_CPF);
		}

		response.setStatus(HttpStatus.OK.value());

		return responseDTO;
	}

	@RequestMapping(value = "/{ownerCpf}/transfer", method = RequestMethod.PUT, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> makeTransfer(@PathVariable String ownerCpf, @RequestParam String targetCpf,
			@RequestParam String value, HttpServletResponse response) {

		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		if (BusinessValidator.isCpfValid(ownerCpf) && BusinessValidator.isCpfValid(targetCpf)) {
			if (accountManager.getAccount(ownerCpf) != null && accountManager.getAccount(targetCpf) != null) {

				if (BusinessValidator.isAmmountfValid(value)) {
					accountManager.makeTransfer(ownerCpf, targetCpf, value);
					responseDTO.setData(new AccountDTO(accountManager.getAccount(ownerCpf)));
					responseDTO.setMessage(ResponseMessage.SUCCESS);

				} else {
					responseDTO.setMessage(ResponseMessage.INVALID_AMMOUNT);
				}

			} else {
				responseDTO.setMessage(ResponseMessage.ACCOUNT_NOT_FOUND);
			}

		} else {
			responseDTO.setMessage(ResponseMessage.INVALID_CPF);
		}

		response.setStatus(HttpStatus.OK.value());

		return responseDTO;
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> deleteAccount(@RequestParam(required = false) String ownerCpf,
			HttpServletResponse response) {

		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		if (BusinessValidator.isCpfValid(ownerCpf)) {
			if (accountManager.getAccount(ownerCpf) != null) {
				if (accountManager.deleteAccount(ownerCpf)) {
					responseDTO.setMessage(ResponseMessage.SUCCESS);
				} else {
					responseDTO.setMessage(ResponseMessage.OPERATION_ERROR);
				}

			} else {
				responseDTO.setMessage(ResponseMessage.ACCOUNT_NOT_FOUND);
			}

		} else {
			responseDTO.setMessage(ResponseMessage.INVALID_CPF);
		}

		response.setStatus(HttpStatus.OK.value());

		return responseDTO;
	}

	@RequestMapping(value = "/all", method = RequestMethod.DELETE, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> deleteAllAccounts(HttpServletResponse response) {
		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		accountManager.deleteAllAccounts();

		responseDTO.setMessage(ResponseMessage.SUCCESS);
		response.setStatus(HttpStatus.OK.value());

		return responseDTO;
	}

	public List<AccountDTO> getAllAccounts() {
		List<AccountDTO> accounts = new ArrayList<>();
		for (Account account : accountManager.getAllAccounts()) {
			accounts.add(new AccountDTO(account));
		}
		return accounts;
	}
	
	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}