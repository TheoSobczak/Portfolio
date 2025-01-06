package thesob3;
//Theo sobczak

import java.util.Date;
import java.util.HashMap;

public class SavingsAccount extends Account {
	/*
	 * SavingsAccount förlänger account och hanterar distinktionen mellan ett
	 * "vanligt" konto och ett sparkonto
	 */

	private static Float rate = (float) 1.2;

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
		 * Hanterar withdraw specifika element för savingsaccount
		 */

		String accountIdS = Integer.toString(accountId);
		Date date = new Date();
		float extraRate = (float) 0.0;
		if (BankLogic.transactionExist(accountId)) { // En check för att kolla om det är första gången vi tar ut något från detta sparkontot.
			extraRate = (float) 0.2;	// Om det inte är det så lägg på ränta.
		}
		for (HashMap<String, String> accounts : accountList) {
			if (accounts.get("AccountId").equals(accountIdS) && accounts.get("personNo").equals(pNo) //Om vi hittar rätt konto med rätt ägare...
					&& !((Float.valueOf(accounts.get("Account Amount")) - float1) - (extraRate * float1) < 0)) { //Om vi inte tar ut mer pengar än vad som finns.
				Float tot = (Float.valueOf(accounts.get("Account Amount")) - float1) - (extraRate * float1);
				accounts.put("Account Amount", Float.toString(tot));	//Uppdatera med nya värdet.
				
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
