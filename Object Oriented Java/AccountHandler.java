package thesob3;
//Theo sobczak

import java.util.ArrayList;
import java.util.HashMap;

public class AccountHandler extends Account {
	/*
	 * AccountHandler hanterar "hanteringen" av konton. Allting som har med att hämta eller mannipulera konton.
	 * Mycket av koden är från uppgift1 och bara flyttad för att jag ville särskilja konton och hantering.
	 */
	
	private SavingsAccount savingsAccounts = new SavingsAccount();
	private CreditAccount creditAccounts = new CreditAccount();
	

	ArrayList<String> getAccount(String pNo, int accountId) {

		/*
		 * Här skapade jag två olika sätt att leta efter kontotn, antingen så går vi
		 * efter personummer och hämtar ut alla konton under det personummret. Eller så
		 * får vi ett specifikt kontonummer då returnerar vi bara det kontot. Om
		 * kontonummer inte skickades med så ska kontonummer vara 0 (från banklogic).
		 * Helst skulle jag ha delat upp det från banklogic till getAllAcounts och
		 * getAccount för att hålla metoderna så "rena" så möjligt med tanke på om något
		 * krashar så blir det ett helskotta att felsöka.
		 */
		
		String accountIdS = Integer.toString(accountId);
		ArrayList<String> customerAccounts = new ArrayList<>();
		String accountDetails;
		if (accountId != 0) {
			for (HashMap<String, String> accounts : accountList) {
				if (accounts.get("AccountId").equals(accountIdS) && accounts.get("personNo").equals(pNo)) {
					accountDetails = "Account id :"+ accounts.get("AccountId") + " "
							+ Float.valueOf(accounts.get("Account Amount")) + " kr "
							+ accounts.get("Account Type") + " " + accounts.get("Account Rate") + " %";

					customerAccounts.add(accountDetails); // Info för att returnera

				}
			}

		} else {

			for (HashMap<String, String> accounts : accountList) {
				if (accounts.get("personNo").equals(pNo)) {
					accountDetails = accounts.get("AccountId") + " "
							+ Float.valueOf(accounts.get("Account Amount")) + " kr "
							+ accounts.get("Account Type") + " " + accounts.get("Account Rate") + " %";

					customerAccounts.add(accountDetails); // Info för att returnera
				}

			}

		}
		if (customerAccounts.size() > 0) {
			return customerAccounts;
		}
		return null;

	}
	
	ArrayList<String> closeAccounts(String pNo, int accountId) {
		/*
		 * Samma här som i tidigare fast if-satserna är vända, om vi har ett inte har
		 * ett kontonummer att gå på stänger vi alla konton, annars så stänger vi bara
		 * det enda kontonummeret som skickades in.
		 */
		ArrayList<String> accountsForClosing = new ArrayList<>();
		String accountDetails;
		Float closingAmount;
		int indexCounter = 0;
		String accountIdS = Integer.toString(accountId);
		ArrayList<Integer> indexForClosing = new ArrayList<>();
		if (accountId == 0) {
			for (HashMap<String, String> accounts : accountList) {
				if (accounts.get("personNo").equals(pNo)) {
					closingAmount = Float.valueOf(accounts.get("Account Amount"))
							* (Float.valueOf(accounts.get("Account Rate")) / 100);

					accountDetails = accounts.get("AccountId") + " "
							+ Float.valueOf(accounts.get("Account Amount")) + " kr "
							+ accounts.get("Account Type") + " " + closingAmount + " kr";

					indexForClosing.add(indexCounter); // Lägger till index för kontot för att remove utanför loopen för att undvika concurrentmodifcationexception
														
					accountsForClosing.add(accountDetails); // Info för att returnera

				}

				else {
					indexCounter++;
				}
			}
		} else {
			for (HashMap<String, String> accounts : accountList) {
				if (accounts.get("AccountId").equals(accountIdS) && accounts.get("personNo").equals(pNo)) {
					closingAmount = Float.valueOf(accounts.get("Account Amount"))
							* (Float.valueOf(accounts.get("Account Rate")) / 100);

					accountDetails = accounts.get("AccountId") + " "
							+ Float.valueOf(accounts.get("Account Amount")) + " kr "
							+ accounts.get("Account Type") + " " + closingAmount + " kr";
					indexForClosing.add(indexCounter); // Lägger till index för kontot för att remove utanför loopen för
														// att undvika concurrentmodifcationexception
					accountsForClosing.add(accountDetails); // Info för att returnera
				} else {
					indexCounter++;
				}
			}
		}

		if (!indexForClosing.isEmpty()) // Om vi hittade konton att ta bort...
		{
			for (int index : indexForClosing) { // Iterera igenom integer arrayn och ta bort alla konton, subtraheras senare från antalet konton.
												
				accountList.remove(index);
				accountnumber = accountnumber - index; 
														
			}
			return accountsForClosing;
		}

		return null;

	}

	boolean withdraw(String pNo, int accountId, float float1) { 
		/*
		 * Metod för att hitta vilken typ av konto.
		 */
		if(getAccount(pNo,accountId).toString().contains("Sparkonto"))
		{
			return savingsAccounts.withdraw(pNo, accountId, float1);
		}
		else if (getAccount(pNo,accountId).toString().contains("Kreditkonto")) {
			return creditAccounts.withdraw(pNo, accountId, float1);
		}
		else {
			return false;
		}
	}
}
