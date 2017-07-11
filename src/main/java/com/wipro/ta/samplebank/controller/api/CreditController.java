package com.wipro.ta.samplebank.controller.api;

import java.math.BigDecimal;

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
import com.wipro.ta.samplebank.domain.CreditManager;
import com.wipro.ta.samplebank.response.AccountDTO;
import com.wipro.ta.samplebank.response.ResponseDTO;
import com.wipro.ta.samplebank.response.ResponseMessage;
import com.wipro.ta.samplebank.validator.BusinessValidator;

@Controller
@RequestMapping("api/accounts/credit")
public class CreditController {

	@Autowired
	private AccountManager accountManager;

	@Autowired
	private CreditManager creditManager;

	@RequestMapping(value = "/{ownerCpf}/loan", method = RequestMethod.PUT, produces = { "application/json" })
	@ResponseBody
	public ResponseDTO<AccountDTO> makeLoan(@PathVariable String ownerCpf, @RequestParam String value,
			HttpServletResponse response) {

		ResponseDTO<AccountDTO> responseDTO = new ResponseDTO<>();

		if (BusinessValidator.isCpfValid(ownerCpf)) {

			Account account = accountManager.getAccount(ownerCpf);

			if (account != null) {
				if (BusinessValidator.isAmmountfValid(value)) {

					/*
					 * Credit operation ...
					 */
					if (!account.hasPendingLoan()) {
						if (account.getBalance().compareTo(creditManager.getMinimumBalance()) >= 0) {

							BigDecimal loanAmmount = new BigDecimal(value);

							if (loanAmmount.compareTo(creditManager.getAllowedAmount(account)) <= 0) {
								creditManager.makeLoan(ownerCpf, loanAmmount);
								responseDTO.setData(new AccountDTO(account));
								responseDTO.setMessage(ResponseMessage.SUCCESS);

							} else {
								responseDTO.setMessage(ResponseMessage.LOAN_CREDIT_EXCEEDED);
							}

						} else {
							responseDTO.setMessage(ResponseMessage.LOAN_NOT_ENOUGH_BALANCE);
						}

					} else {
						responseDTO.setMessage(ResponseMessage.LOAN_PENDING_FOR_ACCOUNT);
					}

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
}