package thesob3;
//Theo sobczak

import java.util.ArrayList;
import java.util.HashMap;

public class Transaction {
	/*
	 * Transaction fungerar som namnet antyder som en databas. Den plockar in och sparar transaktionerna i en Arraylist av HashMaps liknande
	 * som konto och kundhantering funkar.
	 */


	private HashMap<String, String> transactionDataStructure = new HashMap<String, String>();  // Grundstrukturen för transaktion-objektet
	
	
	
	{	
		transactionDataStructure.put("Time", "");
		transactionDataStructure.put("personNo", "");
		transactionDataStructure.put("Account", "");
		transactionDataStructure.put("Type", "");
		transactionDataStructure.put("Amount", "");
		transactionDataStructure.put("Total", "");
	}

	
	
	void transaction(String pNo, String type, int accountNo, float amount, double total, String date )
	{	
		/*
		 * transaction skapar ett nytt hashmap objekt och populerar vår arraylist med informationen.
		 */
		String accountNoS = Integer.toString(accountNo);
		String amountS = Float.toString(amount);
		String totalS = Double.toString(total);
		
		if(type.equals("withdraw"))	//Om det har vart en withdraw så lägg till ett minustecken
		{
			amountS = "-" + amountS;
		}
			
			transactionDataStructure.put("personNo", pNo);
			transactionDataStructure.put("Time", date); 																	
			transactionDataStructure.put("Account", accountNoS);
			transactionDataStructure.put("Type", type);
			transactionDataStructure.put("Amount", amountS);
			transactionDataStructure.put("Total", totalS);
		
		
	}
	
	String getPno()
	{
		return transactionDataStructure.get("personNo");
	}
	
	String getAccount ()
	{
		return transactionDataStructure.get("Account");
	}
	
		
	
	String getTransactions()
	/*
	 * Metod för att returnera transaktion som gjorts
	 */
	{
		String transactionInfo = "";

				transactionInfo = transactionDataStructure.get("Time") + " "+ transactionDataStructure.get("Amount")+ " kr "+ "Saldo: "+ transactionDataStructure.get("Total") + " kr" ; // Lägg information till en sträng
				return transactionInfo;

	}
	

}


