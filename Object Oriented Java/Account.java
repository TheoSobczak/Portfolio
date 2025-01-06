package thesob3;
//Theo sobczak

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public abstract class Account {
	/*	Account är basen till SavingsAccount, CreditAccount, och Accounthandler.
	 *  Den definerar grundstrukturen varpå resterande utökar / hanterar olika delar av kontohantering
	 */

	static ArrayList<HashMap<String, String>> accountList = new ArrayList<>();
	HashMap<String, String> accountDataStructure = new HashMap<>(); // Grunden till datastrukturen																				
	static int accountnumber = 1001; // Basen till accountnumbers.
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	public void resetAccNo()
	{
		accountnumber = 1001;
	}
	{ // Definera vilka fält varje hashmap objekt ska innehålla
		accountDataStructure.put("personNo", "");
		accountDataStructure.put("AccountId", "1000X");
		accountDataStructure.put("Account Amount", "0");
		accountDataStructure.put("Account Rate", "0");
		accountDataStructure.put("Account Type", "Type");
	}

	public int createAccount(String pNo, String AccountType,Integer impAccountNo) {
		/*
		 * Jag valde att behålla createaccount i Account för skillnaderna är så små
		 * Det enda som skiljer kontotyperna är räntan. Finns säkerligen ett snyggt sätt att lösa det på.
		 */
		
		String accountIdS;
		accountIdS = Integer.toString(accountnumber);
		boolean go = false;

		for (HashMap<String, String> accounts : accountList) {

			if (accounts.get("AccountId").equals(accountIdS)) {
				go = true;
			}

		}
		while (go) {
			go = false;
			for (HashMap<String, String> accounts : accountList) {

				if (accounts.get("AccountId").equals(Integer.toString(accountnumber))) {
					accountnumber++;
					go = true;
				}
			}
		}

		if (impAccountNo != null) {
			accountIdS = Integer.toString(impAccountNo);
		} else {
			accountIdS = Integer.toString(accountnumber);
		}
		
		Float rate =  (float) 0.0;
	
		
		switch(AccountType) {		// Lägga till rätt ränta baserat på kontotypen,finns bättre sätt att lösa det på??
		case "Sparkonto":
			rate = SavingsAccount.getRate();
			break;
		case "Kreditkonto":
			rate = CreditAccount.getRate();
			break;
		}

		HashMap<String, String> newAccount = (HashMap<String, String>) accountDataStructure.clone(); // Klona hashmap
		{
			newAccount.put("personNo", pNo);
			newAccount.put("AccountId", accountIdS);
			newAccount.put("Account Rate", Float.toString(rate));
			newAccount.put("Account Type", AccountType);

		}
		accountList.add(newAccount);
		if(impAccountNo == null)
		{
			accountnumber++; // Plussa på accountnumber vilket håller kolla på hur många konton det finns
		}
		

		for (HashMap<String, String> accounts : accountList) { // Enkel extrakoll att vi har nu ett konto med rätt
																// kontonummer i "databasen"
			if (accounts.get("AccountId").equals(accountIdS)) {
				return Integer.valueOf(accountIdS);

			}
		}
		return -1;
	}

	boolean deposit(String pNo, int accountId, float amount) {
		/*
		 * deposit skiljer sig inte på någon av kontotyperna så det finns kvar i Account.
		 */

		String accountIdS = Integer.toString(accountId);
		for (HashMap<String, String> accounts : accountList) { // Iterera igenom kontolistan
			if (accounts.get("AccountId").equals(accountIdS) && (accounts.get("personNo").equals(pNo))) { // Om vi hittar rätt konto
				float tot = Float.valueOf(accounts.get("Account Amount")) + amount;
				accounts.put("Account Amount", Float.toString(tot)); // Uppdatera värdet med nya
				Date date = new Date();
				Transaction transactionC = new Transaction();
				transactionC.transaction(pNo, "deposit", accountId, amount, Float.valueOf(accounts.get("Account Amount")),  sdf.format(date).toString()); // Skicka ett inlägg till "transaktionsDatabasen".
				BankLogic.addTransaction(transactionC);
				return true;

			}
		}
		return false; //Om vi inte hittat något vid det här laget så returnera falskt.
	}
	
	abstract boolean withdraw (String pNo, int accountId, float amount); // Abstrakt metod då withdraw skiljer sig åt baserat på kontotyp.
	

}
