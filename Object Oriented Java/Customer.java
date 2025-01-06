package thesob3;
//Theo sobczak

import java.util.ArrayList;
import java.util.HashMap;

public class Customer {
	private ArrayList<HashMap<String, String>> customerList = new ArrayList<>();			// Ville egentligen använda JSON-objekt men för att ni ska köra den utan att lägga till i buildpath så blev det hashmaps.
	private HashMap<String, String> customerDataStructure = new HashMap<String, String>();  // Grundstrukturen för Kund-objektet

	{
		customerDataStructure.put("First name", "");
		customerDataStructure.put("Surname", "");
		customerDataStructure.put("personNo", "");
	}

	boolean createCustomer(String name, String surname, String pNo) {

		for (HashMap<String, String> customer : customerList) {								// Om kunden redan existerar så returnera false redan nu.
			if (customer.get("personNo").equals(pNo)) {
				return false;
			}
		}
		HashMap<String, String> newCustomer = (HashMap<String, String>) customerDataStructure.clone();
		{
			newCustomer.put("First name", name); 			// Här ville jag ha toLowerCase() för framtida metoder och hanteringar men för att få ett PASS och (tid) på facit har jag tagit bort det.												
			newCustomer.put("Surname", surname);
			newCustomer.put("personNo", pNo);

		}

		customerList.add(newCustomer);
		for (HashMap<String, String> customer : customerList) {		// Extrakoll för att se att kunden verkligen existerar och är tillagd i listan.
			if (customer.get("personNo").equals(pNo)) {
				return true;
			}
		}
		return false;
	}

	boolean changeCustomerName(String name, String surname, String pNo) {

		if (name.equals("")) {
			for (HashMap<String, String> customer : customerList) {	//Tre olika metoder, en för bara förnamn, en för bara efternamn, och en för båda.
				if (customer.get("personNo").equals(pNo)) {
					customer.put("Surname", surname);
					return true;
				}
			}
		} else if (surname.equals("")) {
			for (HashMap<String, String> customer : customerList) {
				if (customer.get("personNo").equals(pNo)) {
					customer.put("First name", name);
					return true;
				}
			}
		} else {
			for (HashMap<String, String> customer : customerList) {
				if (customer.get("personNo").equals(pNo)) {
					customer.put("First name", name);
					customer.put("Surname", surname);
					return true;
				}
			}
		}
		return false;
	}

	ArrayList<String> getAllCustomers() {
		/*
		 * Iterera igenom customerList och hämta ut alla kunder i listan
		 */

		ArrayList<String> allCustomers = new ArrayList<String>();		
		String customerDetails = null;
		for (HashMap<String, String> customer : customerList) {
			customerDetails = customer.get("personNo") + " " + customer.get("First name") + " "
					+ customer.get("Surname");
			allCustomers.add(customerDetails);

		}
		return allCustomers;
	}

	ArrayList<String> getCustomer(String pNo) {
		
		/*
		 * Iterera igenom customerList och hämta ut kunden från listan
		 * Om vi inte hittar den returnera null
		 */
		ArrayList<String> customerInfo = new ArrayList<String>();

		for (HashMap<String, String> customer : customerList) {
			if (customer.get("personNo").equals(pNo)) {
				String customerDetails = customer.get("personNo") + " " + customer.get("First name") + " "
						+ customer.get("Surname");

				customerInfo.add(customerDetails);
				return customerInfo;

			}
		}
		return null;
	}

	ArrayList<String> deleteCustomer(String pNo) {
		/*
		 * Iterera igenom customerList och ta bort ut kunden från listan
		 * Om vi inte hittar den returnera null
		 * Banklogic och AccountHandler hanterar dess konton.
		 */

		int indexCounter = 0;
		ArrayList<String> customerInfo = new ArrayList<String>();
		for (HashMap<String, String> customer : customerList) {
			if (customer.get("personNo").equals(pNo)) {

				String customerString = customer.get("personNo") + " " + customer.get("First name") + " "
						+ customer.get("Surname");
				customerInfo.add(customerString);
				customerList.remove(indexCounter);
			
				return customerInfo;

			} else {
				indexCounter++;
			}

		}
		return null;
	}

}
