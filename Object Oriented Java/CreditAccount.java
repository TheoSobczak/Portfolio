package thesob3;
//Theo sobczak

import java.util.Date;
import java.util.HashMap;


public class CreditAccount extends Account {
	/*
	 * CreditAccount förlänger account och hanterar distinktionen mellan ett
	 * "vanligt" konto och ett Kreditkonto
	 */
	private static Float rate = (float) 0.5;

	public static Float getRate() {
		/*
		 * Returnerar rate, såsom sagt tidigare så finns det nog bättre sätt att lösa
		 * det på för när vi kallar på den här metoden i koden är vi tekniskt sätt redan
		 * i det här kontot.
		 */
		return rate;
	}

	boolean withdraw(String pNo, int accountId, float float1) {
		/*
		 * Hanterar withdraw specifika element för Creditaccounts
		 */
		String accountIdS = Integer.toString(accountId);
		Date date = new Date();

		for (HashMap<String, String> accounts : accountList) {
			if (accounts.get("AccountId").equals(accountIdS) && accounts.get("personNo").equals(pNo) // Om vi hittar rätt konto med rätt ägare...
					&& !(Float.valueOf(accounts.get("Account Amount")) - float1 < -5000))  // Om vi inte tar ut mer än vad man får.
			{
				if (Float.valueOf(accounts.get("Account Amount")) - float1 < 0) {	// Om vi hamnar i skuld höjs räntan
					accounts.put("Account Rate", "7");
				}
				Float tot = Float.valueOf(accounts.get("Account Amount")) - float1;
				accounts.put("Account Amount", Float.toString(tot));	//Uppdatera med nya vsärdet.
				
				Transaction transactionC = new Transaction();				
				transactionC.transaction(pNo, "withdraw", accountId, float1,
						Float.valueOf(accounts.get("Account Amount")), sdf.format(date).toString()); //Registrera en transaktion
				BankLogic.addTransaction(transactionC);
				return true;

			}
		}
		return false; // Om vi inte lyckats vid det här laget returnera falskt.

	}



}
