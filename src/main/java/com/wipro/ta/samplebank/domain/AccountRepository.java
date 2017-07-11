package com.wipro.ta.samplebank.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
class AccountRepository {

	private static final Map<String, Account> accounts = new ConcurrentHashMap<String, Account>();

	/**
	 * Returns a list of available accounts
	 */
	public List<Account> allAccounts() {
		return new ArrayList<Account>(accounts.values());
	}

	/**
	 * Find a specific account in the repository using cpf identifier
	 * @param ownerCpf
	 */
	public Account findAccount(String ownerCpf) {
		if (accounts.containsKey(ownerCpf)) {
			return accounts.get(ownerCpf);
		}

		return null;
	}

	/**
	 * Deletes a specific account
	 */
	public void deleteAccount(Account c) {
		if (accounts.containsValue(c)) {

			Iterator<Entry<String, Account>> it = accounts.entrySet().iterator();

			while (it.hasNext()) {

				Entry<String, Account> item = it.next();

				if (item.getKey().equals(c.getOwnerCpf())) {
					it.remove();
				}
			}
		}
	}

	/**
	 * Adds a new account to the repository
	 */
	public void addAccount(Account c) {
		accounts.put(String.valueOf(c.getOwnerCpf()), c);
	}

	static void clearAccounts() {
		accounts.clear();
	}
}