package com.wipro.ta.samplebank.validator;

import java.math.BigDecimal;

public class BusinessValidator {

	private static final int CPF_LENGTH = 11;

	public static boolean isCpfValid(String cpf) {
		if (cpf != null && cpf.length() == CPF_LENGTH) {
			return true;
		}
		return false;
	}

	public static boolean isAmmountfValid(String ammount) {
		try {
			if (ammount != null
					&& new BigDecimal(ammount).compareTo(BigDecimal.ZERO) == 1) {
				return true;
			}

			return false;

		} catch (NumberFormatException e) {
			return false;
		}
	}
}