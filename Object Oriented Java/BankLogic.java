package thesob3;
//Theo sobczak


import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class BankLogic {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SavingsAccount savingsAccounts = new SavingsAccount();
	private CreditAccount creditAccounts = new CreditAccount();
	private AccountHandler accountHandler = new AccountHandler();
	private Customer customerHandler = new Customer();
	static ArrayList<Transaction > transactions = new ArrayList<Transaction>();
	
	
	/*
	 * Banklogic hanterar alla inputs från Testbank och skapar egna objekt för alla
	 * calls. Båda klasserna Account (och dess extensions) och Customer håller koll på sin egna
	 * information. Dom är länkade genom banklogic och kontona är länkade genom
	 * personnummer.
	 * All kod kommer finnas i respektive klasser. Banklogic håller bara på felchecks och på konstruktorna.
	 * 
	 */
	

	public ArrayList<String> getAllCustomers() { //Metod för att hämta kunder.

		return customerHandler.getAllCustomers();

	}

	public boolean createCustomer(String name, String surname, String pNo) {

		ArrayList<String> stringsForCheck = new ArrayList<String>();
		stringsForCheck.add(name);
		stringsForCheck.add(surname);
		stringsForCheck.add(pNo);
		
		cleanStr(stringsForCheck); /*
									 * Enkel metod bara för att "städa" strängarna som skickas in för att passa
									 * format så metoderna inte behöver ha onödig kod i sig.
									 */
		return customerHandler.createCustomer(name, surname, pNo);

	}

	public ArrayList<String> getCustomer(String pNo) {

		ArrayList<String> stringsForCheck = new ArrayList<String>();
		stringsForCheck.add(pNo);
		ArrayList<String> returnCustomer = new ArrayList<String>();
		cleanStr(stringsForCheck);
		if (customerHandler.getCustomer(pNo) != null) { 	// Check för att undvika att köra addAll med null strängar.
			returnCustomer.addAll(customerHandler.getCustomer(pNo));
			if (accountHandler.getAccount(pNo, 0) != null) { // Kaskad if-satser, om kunden lyckades och OM det finns konton, lägg till dom med.// konton, lägg till dom med.
				returnCustomer.addAll(accountHandler.getAccount(pNo, 0));
			}

			return returnCustomer;
		} else {
			return null;
		}

	}

	public boolean changeCustomerName(String name, String surname, String pNo) {

		if (customerHandler.getCustomer(pNo) != null && !(name.equals("") && surname.equals(""))) {		//Om kunden finns och vi har information nog att byta namn										
			return customerHandler.changeCustomerName(name, surname, pNo);
		} else {
			return false;
		}
	}

	public boolean deposit(String pNo, int accountId, float amount) {

		if (amount < 0) {		// Om vi inte har minus amount...
			return false;
		}

		ArrayList<String> stringsForCheck = new ArrayList<String>();
		stringsForCheck.add(pNo);
		cleanStr(stringsForCheck);
		if (accountHandler.getAccount(pNo, accountId) != null) { // Check för att kolla så pNo accountId tillhör ett konto
			return accountHandler.deposit(pNo, accountId, amount);
		} else {
			return false;
		}

	}

	public boolean withdraw(String pNo, int accountId, float f) {
		if (f < 0) {		//  Om vi inte har minus amount...
			return false;
		}
		
		ArrayList<String> stringsForCheck = new ArrayList<String>();
		stringsForCheck.add(pNo);
		cleanStr(stringsForCheck);
		if (accountHandler.getAccount(pNo, accountId) != null) { // Check för att kolla så pNo accountId tillhör ett konto
			return accountHandler.withdraw(pNo, accountId, f);

				
		}
		else {
			return false;
		}
	}

	public String getAccount(String pNo, int accountId) {

		ArrayList<String> stringsForCheck = new ArrayList<String>();
		stringsForCheck.add(pNo);
		cleanStr(stringsForCheck);
		if (accountHandler.getAccount(pNo,	// Check för att kolla så att det finns information att returnera
				accountId) != null) { 
			return accountHandler.getAccount(pNo, accountId).toString();
		} else {
			return null;
		}

	}

	public String closeAccount(String pNo, int accountId) {

		ArrayList<String> stringsForCheck = new ArrayList<String>();
		ArrayList<String> closingAccountsInfo = new ArrayList<String>();
		stringsForCheck.add(pNo);
		cleanStr(stringsForCheck);

		if (accountHandler.getAccount(pNo, accountId) != null) { // Kolla om kontot vi vill stänga existerar
			closingAccountsInfo.addAll(accountHandler.closeAccounts(pNo, accountId)); //Lägg till alla kontont som finns med.
			return closingAccountsInfo.toString();
		} else {
			return null;
		}

	}

	public ArrayList<String> deleteCustomer(String pNo) {

		ArrayList<String> stringsForCheck = new ArrayList<String>();
		ArrayList<String> customerInfo = new ArrayList<String>();
		stringsForCheck.add(pNo);
		cleanStr(stringsForCheck);

		if (customerHandler.getCustomer(pNo) != null) { // Kolla om kunden vi vill ta bort existerar.
			customerInfo.addAll(customerHandler.deleteCustomer(pNo));

			if (accountHandler.getAccount(pNo, 0) != null) { // Kolla om kunden vi vill ta bort har konton.
				customerInfo.addAll(accountHandler.closeAccounts(pNo, 0)); // 0'an förklaras i metod.
				return customerInfo;
			} else {
				return customerInfo;
			}

		} else {
			return null;
		}

	}

	ArrayList<String> cleanStr(ArrayList<String> strList) { // Enkel metod för att ta bort blankspace

		for (String str : strList) {
			if (str != null) {
				str.replace(" ", "");
			}
		}
		return strList;
	}

	public int createSavingsAccount(String pNo,Integer accountNo) {
		if (customerHandler.getCustomer(pNo) != null) {	// Om kunden existerar så kan vi lägga till ett konto.
			return savingsAccounts.createAccount(pNo, "Sparkonto",accountNo);
		} else {
			return -1;
		}
	}
	
	public void resetAccNo()
	{
		accountHandler.resetAccNo();
	}
	public int createCreditAccount(String pNo,Integer accountNo) {
		if (customerHandler.getCustomer(pNo) != null) { // Om kunden existerar så kan vi lägga till ett konto.
			return creditAccounts.createAccount(pNo, "Kreditkonto",accountNo);
		} else {
			return -1;
		}
	}

	public ArrayList<String> getTransactions(String pNo, int accountId) {	
		
		ArrayList<String> transactionlist = new ArrayList<String>();
		for(Transaction transaction : transactions)
		{
			if(transaction.getPno().equals(pNo) && Integer.valueOf(transaction.getAccount()).equals(accountId))
			{
				 transactionlist.add(transaction.getTransactions());
			}
			else {
			}
			
		}
		if(!transactionlist.isEmpty())
		{
			return transactionlist;
		}
		else{
			return null;
		}
		
	}


	public static void addTransaction(Transaction transactionC) {
		transactions.add(transactionC);
	}
	
	public static boolean transactionExist(int accountId)
	{	
	
			for(Transaction transaction : transactions)
			{
				if(Integer.valueOf(transaction.getAccount()).equals(accountId))
				{
					return true;
				}
			}
	
		return false;
	}

}
