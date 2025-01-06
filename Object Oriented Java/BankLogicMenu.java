package thesob3;
// Theo Sobczak

import java.awt.Button;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class BankLogicMenu {

	public static void main(String[] args) {

		/*
		 * Följande kommer koden för menyn och hantering a banklogic, Den bygger på en
		 * bottom-up konstruktion och följer mvc modelling om dock en rörig sådan.
		 * Banklogic hanterar alla inputs och anropar alla resterande klasserna och
		 * banklogic menu känner bara till banklogic.
		 */

		BankLogic bankLogicHandler = new BankLogic(); // Konstruktor för banklogic

/*		bankLogicHandler.createCustomer("Theo", "Sobczak", "9306011637");
		bankLogicHandler.createSavingsAccount("9306011637");
		bankLogicHandler.deposit("9306011637", 1001, 500);
		bankLogicHandler.deposit("9306011637", 1001, 1001);

		bankLogicHandler.createCustomer("Mikaela", "Setter", "802624524");
		bankLogicHandler.createSavingsAccount("802624524");
		bankLogicHandler.deposit("802624524", 1002, 5000);
		bankLogicHandler.deposit("802624524", 1002, 10);

		bankLogicHandler.createCustomer("Peter", "Lang", "00548521");
		bankLogicHandler.createCreditAccount("00548521");
		bankLogicHandler.deposit("00548521", 1003, 6060);
		bankLogicHandler.deposit("00548521", 1003, 6060);

		bankLogicHandler.createCustomer("Sven", "Adamsson", "85485115");
		bankLogicHandler.createCreditAccount("85485115");
		bankLogicHandler.deposit("85485115", 1004, 100);
		bankLogicHandler.deposit("85485115", 1004, 2000);*/

		JFrame frame = new JFrame("Banklogic Menu"); // Huvudframe

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon(System.getProperty("user.dir") + "\\src\\thesob3\\" + "banklogiclogo.png");
		frame.setIconImage(img.getImage());

		frame.setVisible(true); // Exhibit the frame

		JMenuBar menuBar = new JMenuBar();
		UIManager.put("MenuBar.background", Color.ORANGE);

		JMenu fileMenu = new JMenu("File"); // Meny fält File
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		JMenu customerMenu = new JMenu("Customer"); // Meny fält Customer
		customerMenu.setMnemonic(KeyEvent.VK_C);
		menuBar.add(customerMenu);

		JMenu transactionMenu = new JMenu("Transaction"); // Meny fält Transaktioner
		transactionMenu.setMnemonic(KeyEvent.VK_T);
		transactionMenu.setEnabled(false);
		menuBar.add(transactionMenu);
		frame.getContentPane().setBackground(Color.WHITE);

		GridLayout experimentLayout = new GridLayout(0, 2);
		frame.setLayout(experimentLayout);

		JLabel label = new JLabel(); // JLabel Creation för ikon
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\src\\thesob3\\" + "banklogic.png"));

		frame.add(label);

		JTextArea consoleWindowT = new JTextArea();
		consoleWindowT.setBackground(Color.LIGHT_GRAY);
		JScrollPane consoleWindow = new JScrollPane(consoleWindowT); // Konsolfönster för återapportering
		FlowLayout flowLa = new FlowLayout();
		frame.add(consoleWindow, flowLa); // Lägg till i huvudfönster med layout FlowLayout

		JMenuItem a_transaction_deposit = new JMenuItem(new AbstractAction("Deposit") {

			/*
			 * Menyfält för deposit
			 */

			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

				JTextField pNoField = new JTextField(5); // personnummer

				JPanel a_deposit_panel = new JPanel();
				a_deposit_panel.setLayout(new BoxLayout(a_deposit_panel, BoxLayout.Y_AXIS));

				a_deposit_panel.add(new JLabel("Social security No"));
				a_deposit_panel.add(pNoField);

				int result = JOptionPane.showConfirmDialog(null, a_deposit_panel, "Customer info",
						JOptionPane.OK_CANCEL_OPTION); // Spara resultat till int då knapparna korellerar till
														// int-värden

				if (result == JOptionPane.OK_OPTION) {

					String socialSecNo = pNoField.getText();

					String allAcounts = bankLogicHandler.getAccount(socialSecNo, 0); // Hämta alla konton för pno

					String setTextString = "Available Accounts \n"; // Iterera igenom alla accounts och bygg en sträng
																	// av de för posting till konsolfönstret.
					for (String customer : allAcounts.split(",")) {
						setTextString += customer + "\n";
					}
					consoleWindowT.setText(setTextString); // Posta available accounts till konsolfönstret.

					JTextField accountNoField = new JTextField(5);
					JTextField amountField = new JTextField(5);

					JPanel a_deposit_panel_Two = new JPanel();
					a_deposit_panel_Two.setLayout(new BoxLayout(a_deposit_panel_Two, BoxLayout.Y_AXIS));

					a_deposit_panel_Two.add(new JLabel("Account Number"));
					a_deposit_panel_Two.add(accountNoField);
					a_deposit_panel_Two.add(new JLabel("Amount"));
					a_deposit_panel_Two.add(amountField);
					int resultTwo = JOptionPane.showConfirmDialog(null, a_deposit_panel_Two, "Account info and Deposit", //
							JOptionPane.OK_CANCEL_OPTION);
					if (resultTwo == JOptionPane.OK_OPTION) {
						String accountNo = accountNoField.getText(); // Hämta kontonummer från fält
						String amount = amountField.getText(); // Hämta summa från fält

						try {
							Long.parseLong(socialSecNo); // Använder long då Integer inte sträcker sig över 10 digits,
															// om det finns bokstäver med så throw catch

							if (bankLogicHandler.deposit(socialSecNo, Integer.parseInt(accountNo), // Om deposit
																									// returnerar true
																									// för en giltig
																									// transaktion
									Integer.parseInt(amount))) {
								consoleWindowT.setText("Deposited : " + amount + ", Balance : "
										+ bankLogicHandler.getAccount(socialSecNo, Integer.parseInt(accountNo))); // Posta
																													// information
																													// till
																													// konsolfönstret

							} else {
								consoleWindowT.setText("Fail in deposit function"); // Om fel meddela misslyckandet
								JOptionPane.showMessageDialog(frame,
										"Fail in deposit, check input or contact developer");
							}
						} catch (NumberFormatException err) {
							consoleWindowT.setText("Faulty input");
							JOptionPane.showMessageDialog(frame,
									"Fail in input, check input values, contact developer");
						}

					}
				}
			}

		});
		transactionMenu.add(a_transaction_deposit);

		JMenuItem a_transaction_withdraw = new JMenuItem(new AbstractAction("Withdraw") {
			/**
			 * Withdraw meny
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JTextField pNoField = new JTextField(5);

				JPanel a_withdraw_panel = new JPanel();
				a_withdraw_panel.setLayout(new BoxLayout(a_withdraw_panel, BoxLayout.Y_AXIS));

				a_withdraw_panel.add(new JLabel("Social security No"));
				a_withdraw_panel.add(pNoField); // Personnummer

				int result = JOptionPane.showConfirmDialog(null, a_withdraw_panel, "Customer info",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {

					String socialSecNo = pNoField.getText();

					String allAcounts = bankLogicHandler.getAccount(socialSecNo, 0); // Hämta alla konton

					String setTextString = "Available Accounts \n";
					for (String customer : allAcounts.split(",")) { // Bygg en sträng av det och posta till
																	// konsolfönster
						setTextString += customer + "\n";
					}
					consoleWindowT.setText(setTextString);

					JTextField accountNoField = new JTextField(5);
					JTextField amountField = new JTextField(5);

					JPanel a_withdraw_panel_Two = new JPanel();
					a_withdraw_panel_Two.setLayout(new BoxLayout(a_withdraw_panel_Two, BoxLayout.Y_AXIS));

					a_withdraw_panel_Two.add(new JLabel("Account Number"));
					a_withdraw_panel_Two.add(accountNoField);
					a_withdraw_panel_Two.add(new JLabel("Amount"));
					a_withdraw_panel_Two.add(amountField);
					int resultTwo = JOptionPane.showConfirmDialog(null, a_withdraw_panel_Two,
							"Account info and Withdraw", JOptionPane.OK_CANCEL_OPTION);
					if (resultTwo == JOptionPane.OK_OPTION) {
						String accountNo = accountNoField.getText(); // Hämta kontonummer från fält
						String amount = amountField.getText(); // Hämta summa från fält

						try {
							Long.parseLong(socialSecNo); // Använder long då Integer inte sträcker sig över 10 digits,
															// om det finns bokstäver med så throw catch

							if (bankLogicHandler.withdraw(socialSecNo, Integer.parseInt(accountNo), // Om withdraw
																									// returnerar true
																									// för en giltig
																									// transaktion
									Float.parseFloat(amount))) {
								consoleWindowT.setText("Withdrawn : " + amount + ", Balance : " // Posta information
																								// till konsolfönstret.
										+ bankLogicHandler.getAccount(socialSecNo, Integer.parseInt(accountNo)));

							} else {
								consoleWindowT.setText("Fail in Withdrawn function");
								JOptionPane.showMessageDialog(frame,
										"Fail in Withdrawn, check input or contact developer");
							}
						} catch (NumberFormatException err) {
							consoleWindowT.setText("Faulty input");
							JOptionPane.showMessageDialog(frame,
									"Fail in input, check input values, contact developer");
						}

					}
				}
			}
		});
		transactionMenu.add(a_transaction_withdraw);

		JMenuItem a_transaction_get = new JMenuItem(new AbstractAction("Get Transaction") {
			private static final long serialVersionUID = 1L;

			/*
			 * Transaktion fungerar i grunden identiskt med withdraw och deposit. Därför är
			 * den sparsamt kommenterad
			 */
			public void actionPerformed(ActionEvent e) {
				JTextField pNoField = new JTextField(5);

				JPanel a_transaction_panel = new JPanel();
				a_transaction_panel.setLayout(new BoxLayout(a_transaction_panel, BoxLayout.Y_AXIS));

				a_transaction_panel.add(new JLabel("Social security No"));
				a_transaction_panel.add(pNoField);

				int result = JOptionPane.showConfirmDialog(null, a_transaction_panel, "Customer info",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {

					String socialSecNo = pNoField.getText();

					String allAcounts = bankLogicHandler.getAccount(socialSecNo, 0); // Hämta alla konton för
																						// transaktioner

					String setTextString = "Available Accounts \n";
					for (String customer : allAcounts.split(",")) {
						setTextString += customer + "\n";
					}
					consoleWindowT.setText(setTextString);

					JTextField accountNoField = new JTextField(5);

					JPanel a_transaction_panel_two = new JPanel();
					a_transaction_panel_two.setLayout(new BoxLayout(a_transaction_panel_two, BoxLayout.Y_AXIS));

					a_transaction_panel_two.add(new JLabel("Account Number"));
					a_transaction_panel_two.add(accountNoField);

					int resultTwo = JOptionPane.showConfirmDialog(null, a_transaction_panel_two,
							"Account info for transaction", JOptionPane.OK_CANCEL_OPTION);
					if (resultTwo == JOptionPane.OK_OPTION) {
						String accountNoInput = accountNoField.getText();

						try {
							Long.parseLong(socialSecNo);

							ArrayList<String> transactionList = new ArrayList<String>();
							transactionList = bankLogicHandler.getTransactions(socialSecNo, // Hämta alla transaktioner
																							// för kontot
									Integer.valueOf(accountNoInput));
							String setTextStringTwo = "";
							if (transactionList != null) { // Om det finns transaktioner
								for (String transactions : bankLogicHandler.getTransactions(socialSecNo,
										Integer.valueOf(accountNoInput))) {
									setTextStringTwo += transactions + "\n"; // Skapa en sträng för fönstret och posta
																				// den
									consoleWindowT.setText(setTextStringTwo);
								}

							} else {
								consoleWindowT
										.setText("Transaction for account :" + accountNoInput + " does not exist");
							}
						}

						catch (NumberFormatException err) {
							consoleWindowT.setText("Faulty input");
							JOptionPane.showMessageDialog(frame,
									"Fail in input, check input values, contact developer");
						}
					}

				}
			}
		});

		transactionMenu.add(a_transaction_get);

		JMenu accountManageMenu = new JMenu("Account Management"); // Initiera menyn för account management
		accountManageMenu.setMnemonic(KeyEvent.VK_A);
		accountManageMenu.setEnabled(false);
		menuBar.add(accountManageMenu);

		JMenuItem a_get = new JMenuItem(new AbstractAction("Get Account(s)") { // För get accounts alternativ
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JTextField pNo = new JTextField(5);

				JPanel c_getAccounts_panel = new JPanel();
				c_getAccounts_panel.setLayout(new BoxLayout(c_getAccounts_panel, BoxLayout.Y_AXIS));

				c_getAccounts_panel.add(new JLabel("Social Security Number"));
				c_getAccounts_panel.add(pNo);

				int result = JOptionPane.showConfirmDialog(null, c_getAccounts_panel, "Customer info",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String personNoInput = pNo.getText();
					String setTextStringTwo = "";
					for (String accounts : (bankLogicHandler.getAccount(personNoInput, 0).split(","))) { // Bygg en
																											// sträng av
																											// accounts

						setTextStringTwo += accounts + "\n";
					}
					consoleWindowT.setText(setTextStringTwo);

				}

			}
		});
		accountManageMenu.add(a_get);
		a_get.setEnabled(false);

		JMenuItem a_close = new JMenuItem(new AbstractAction("Remove Account") { // Remove account alternativ
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JTextField pNoField = new JTextField(5);

				JPanel a_remove_panel = new JPanel();
				a_remove_panel.setLayout(new BoxLayout(a_remove_panel, BoxLayout.Y_AXIS));

				a_remove_panel.add(new JLabel("Social security No"));
				a_remove_panel.add(pNoField);

				int result = JOptionPane.showConfirmDialog(null, a_remove_panel, "Customer info",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {

					String socialSecNo = pNoField.getText();
					String allAcounts = bankLogicHandler.getAccount(socialSecNo, 0);

					String setTextString = "Available Accounts \n";
					for (String customer : allAcounts.split(",")) { // Hämta konton och posta till konsolfönstret
						setTextString += customer + "\n";
					}
					consoleWindowT.setText(setTextString);

					JTextField accountNoField = new JTextField(5);

					JPanel a_remove_panel_two = new JPanel();
					a_remove_panel_two.setLayout(new BoxLayout(a_remove_panel_two, BoxLayout.Y_AXIS));

					a_remove_panel_two.add(new JLabel("Account Number"));
					a_remove_panel_two.add(accountNoField);

					int resultTwo = JOptionPane.showConfirmDialog(null, a_remove_panel_two,
							"Account number for removal", // Välj konto
							JOptionPane.OK_CANCEL_OPTION);
					if (resultTwo == JOptionPane.OK_OPTION) {
						String accountNo = accountNoField.getText();

						try {
							Long.parseLong(socialSecNo);
							consoleWindowT.setText("Removed account : "
									+ bankLogicHandler.closeAccount(socialSecNo, Integer.parseInt(accountNo))); // Posta

						} catch (NumberFormatException err) {
							consoleWindowT.setText("Faulty input");
							JOptionPane.showMessageDialog(frame,
									"Fail in input, check input values, contact developer");
						}

					}
				}
			}
		});
		accountManageMenu.add(a_close);
		a_close.setEnabled(false);

		JMenu a_create = new JMenu("Create Account");
		accountManageMenu.add(a_create, 0);

		JMenuItem a_create_SavingAcc = new JMenuItem(new AbstractAction("Create Savings Account") { // Create savings
																									// saccount meny
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			// account meny
			public void actionPerformed(ActionEvent e) {
				String userInput1 = JOptionPane.showInputDialog(frame, "Social Security Number", "10 Digits");
				if (bankLogicHandler.createSavingsAccount(userInput1,null) != -1) { // Check om createsavingsaccount
																				// returnerar -1 (inte skapad på grund
																				// av fel)
					a_get.setEnabled(true);
					a_close.setEnabled(true);
					transactionMenu.setEnabled(true);
					JOptionPane.showMessageDialog(frame, "Success");
				} else {
					JOptionPane.showMessageDialog(frame, "Fail in creating, contact developer");
				}
			}
		});
		a_create.add(a_create_SavingAcc);

		JMenuItem a_create_CreditAcc = new JMenuItem(new AbstractAction("Create Credit Account") { // Create credit
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			// account meny
			public void actionPerformed(ActionEvent e) {
				String userInput1 = JOptionPane.showInputDialog(frame, "Social Security Number", "10 Digits");
				if (bankLogicHandler.createCreditAccount(userInput1,null) != -1) { // Check om createsavingsaccount
																				// returnerar -1 (inte skapad på grund
																				// av fel)
					a_get.setEnabled(true);
					a_close.setEnabled(true);
					transactionMenu.setEnabled(true);
					JOptionPane.showMessageDialog(frame, "Success");
				} else {
					JOptionPane.showMessageDialog(frame, "Fail in creating, contact developer");
				}
			}
		});
		a_create.add(a_create_CreditAcc);

		JMenuItem c_Get = new JMenuItem(new AbstractAction("Get Customer") { // Get Customer meny
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				try {
					String socialNo = JOptionPane.showInputDialog(frame, "Social Security Number");
					Long.parseLong(socialNo);
					ArrayList<String> customerInfo = bankLogicHandler.getCustomer(socialNo); // Hämta customer och spara
																								// till ArrayList
					if (customerInfo != null) { // Om vi har lyckats hämta info...
						String setTextString = "";
						for (String customer : customerInfo) {
							setTextString += customer + "\n";
						}
						consoleWindowT.setText(setTextString);
					} else {
						consoleWindowT.setText("Could not find customer");
						JOptionPane.showMessageDialog(frame, "Could not find customer, recheck input");
					}
				} catch (NumberFormatException err) {
					consoleWindowT.setText("Error in input, only social security number allowed");
					JOptionPane.showMessageDialog(frame, "Recheck input");
				}
			}
		});
		customerMenu.add(c_Get);
		c_Get.setEnabled(false);
		JMenuItem c_getAll = new JMenuItem(new AbstractAction("Get All Customer") { // Get all Customer meny
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				ArrayList<String> allCustomerInfo = new ArrayList<String>();
				String setTextString = "";
				allCustomerInfo = bankLogicHandler.getAllCustomers();
				for (String customer : allCustomerInfo) {
					setTextString += customer + "\n";
				}
				consoleWindowT.setText(setTextString.toString());
			}
		});
		customerMenu.add(c_getAll);
		c_getAll.setEnabled(false);

		JMenuItem c_changeName = new JMenuItem(new AbstractAction("Change Customer Name") { // Change customer name
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JTextField pNo = new JTextField(5);
				JTextField firstN = new JTextField(5);
				JTextField lastN = new JTextField(5);

				JPanel c_change_name = new JPanel();
				c_change_name.setLayout(new BoxLayout(c_change_name, BoxLayout.Y_AXIS));

				c_change_name.add(new JLabel("Social security No"));
				c_change_name.add(pNo);
				c_change_name.add(new JLabel("First Name Change"));
				c_change_name.add(firstN);
				c_change_name.add(new JLabel("Last name Change"));
				c_change_name.add(lastN);

				int result = JOptionPane.showConfirmDialog(null, c_change_name, "Customer info",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String firstNameInput = firstN.getText();
					String lastNameInput = lastN.getText();
					String socialSecNoInput = pNo.getText();
					if (bankLogicHandler.changeCustomerName(firstNameInput, lastNameInput, socialSecNoInput)) { // Om
																												// den
																												// returnerar
																												// true
																												// så
																												// success
						JOptionPane.showMessageDialog(frame, "Success");
					} else {
						JOptionPane.showMessageDialog(frame, "Failure in changing, try again");
					}

				}
			}
		});
		customerMenu.add(c_changeName);
		c_changeName.setEnabled(false);

		JMenuItem c_remove = new JMenuItem(new AbstractAction("Remove Customer") { // Remove customer meny
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

				try {
					String socialNo = JOptionPane.showInputDialog(frame, "Social Security Number");
					Long.parseLong(socialNo);
					JPanel c_remove_panel = new JPanel();
					c_remove_panel.add(new JLabel("Are you sure, this can't be undone")); // En till Panel för
																							// "confirmation"

					int result = JOptionPane.showConfirmDialog(null, c_remove_panel, "Confirm Choice",
							JOptionPane.OK_CANCEL_OPTION);
					if (result == JOptionPane.OK_OPTION) {
						ArrayList<String> customerInfo = bankLogicHandler.deleteCustomer(socialNo);
						if (customerInfo != null) { // Om vi har något att redovisa dvs om kunden hr tagits bort.
							String setTextString = "";
							for (String customer : customerInfo) {
								setTextString += customer + "\n";
							}
							consoleWindowT.setText(setTextString);
						} else {
							consoleWindowT.setText("Could not find customer");
							JOptionPane.showMessageDialog(frame, "Could not find customer, recheck input");
						}
					}
				} catch (NumberFormatException err) {
					consoleWindowT.setText("Error in input, only social security number allowed");
					JOptionPane.showMessageDialog(frame, "Recheck input");
				}
			}
		});
		customerMenu.add(c_remove);
		c_remove.setEnabled(false);

		JMenuItem c_Create = new JMenuItem(new AbstractAction("Create Customer") { // Create customer meny
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {

				JTextField firstN = new JTextField(5);
				JTextField lastN = new JTextField(5);
				JTextField pNo = new JTextField(5);

				JPanel c_create_panel = new JPanel();
				c_create_panel.setLayout(new BoxLayout(c_create_panel, BoxLayout.Y_AXIS));

				c_create_panel.add(new JLabel("First Name"));
				c_create_panel.add(firstN);
				c_create_panel.add(new JLabel("Last name"));
				c_create_panel.add(lastN);
				c_create_panel.add(new JLabel("Social security No"));
				c_create_panel.add(pNo);

				int result = JOptionPane.showConfirmDialog(null, c_create_panel, "Customer info",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					String firstName = firstN.getText();
					String lastName = lastN.getText();
					String socialSecNo = pNo.getText();
					try {
						Long.parseLong(socialSecNo);
						if (bankLogicHandler.createCustomer(firstName, lastName, socialSecNo)) { // Om vi har skapat en
																									// kund så lås upp
																									// dom andra
																									// flikarna
							c_Get.setEnabled(true);
							c_getAll.setEnabled(true);
							c_changeName.setEnabled(true);
							c_remove.setEnabled(true);
							accountManageMenu.setEnabled(true);
							JOptionPane.showMessageDialog(frame, "Created Customer");
						}
					} catch (NumberFormatException err) {
						JOptionPane.showMessageDialog(frame,
								"Fail in creating, check social security number or contact developer");
					}

				}

			}
		});

		JMenuItem exports = new JMenuItem(new AbstractAction("Export") {
			private static final long serialVersionUID = 1L;
			/*
			 * Export skapar en egen fil i directory som körs, ändra filepath för önskad
			 * direcotry
			 */

			public void actionPerformed(ActionEvent e) {
				Desktop desktop = Desktop.getDesktop();
				try {

					File exportFile = new File(
							System.getProperty("user.dir") + "\\src\\thesob3\\" + "BankLogicExport.txt"); // Skapa ett
																											// filobjekt
					exportFile.createNewFile(); // Skapa en faktiskt fil
					consoleWindowT.setText("File created: " + exportFile.getName() + "\n" + "At location: "
							+ System.getProperty("user.dir") + "\\src\\thesob3\\" + "BankLogicExport.txt");
					
					FileWriter writer = new FileWriter(exportFile);

					ArrayList<String> allCustomers = bankLogicHandler.getAllCustomers(); // Hämta alla kunder som ska
																							// skrivas över

					for (String customer : allCustomers) {
						writer.write("Customer: " + customer + ", ");
						String[] splitStr = customer.split(" ");
						String pNo = splitStr[0].replaceAll("\\s", "");
						String customerAccs = bankLogicHandler.getAccount(pNo, 0);
						if (customerAccs != null) {
							String[] customerAccount = customerAccs.split(",");

							for (String accInfo : customerAccount) {
								writer.write("Account: " + accInfo + " , ");
								String[] accInfosplit = accInfo.split(" ");

								ArrayList<String> allTransactions = bankLogicHandler.getTransactions(
										pNo.replaceAll("\\s", ""),
										Integer.valueOf(accInfosplit[0].replaceAll("\\[", "").replaceAll("\\]", "")));

								if (allTransactions != null) {
									for (String trans : allTransactions) {
										System.out.println(allTransactions);
										writer.write("Transaction:  " + trans + ", ");

									}
								}
							}

						}
						writer.write("\n");
					}

					writer.close();
					desktop.open(exportFile); // Efter avslutad export, öppna filen

				}

				catch (IOException e1) {
					consoleWindowT.setText("An error occured during Export. Check savefile exists in open directory");
					System.out.println("An error occurred.");
					e1.printStackTrace();
				}

			}
		});
		fileMenu.add(exports);

		JMenuItem imports = new JMenuItem(new AbstractAction("Import") { // Importfunktion
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				File file = new File(System.getProperty("user.dir") + "\\src\\thesob3\\" + "BankLogicImport.txt"); // Hämta
																													// filen
																													// banklogicImport.txt
																													// från
																													// src/package
																													// directory
				Desktop desktop = Desktop.getDesktop();
				ArrayList<String> allCustomers = bankLogicHandler.getAllCustomers(); // Rensa alla kunder och deras data
																						// från system.
				for (String customerinfo : allCustomers) {
					String[] custStr = customerinfo.split(" ");
					bankLogicHandler.deleteCustomer(custStr[0].replaceAll("\\s", ""));
					bankLogicHandler.resetAccNo();
				}
				try {

				

					try (Scanner sc = new Scanner(file)) {
						while (sc.hasNextLine()) {
							String[] textline = sc.nextLine().split(","); // Dela upp raden i "," så får vi ett "objekt"
																			// i varje plats i arrayn.
							if (textline.length > 0) { // Extrakoll om filen har tomma rader eller dylikt
								System.out.println("New Customer");
								String[] customerinfo = textline[0].split(" "); // Första objektet ska alltid vara kund
																				// pga "mitt system" exporterar så
								bankLogicHandler.createCustomer(customerinfo[2], customerinfo[3], customerinfo[1]); // Förnamn,
																													
								String accountNo = "";

								for (int i = 0; i < textline.length; i++) { // Börja iterera igenom raden, det är
																			// möjligt att ha massa accounts och inga
																			// transaktions
									if (textline[i].length() > 0) {
										String[] accountinfo = textline[i].split(" ");
										if (accountinfo.length > 0 && accountinfo[i].contains("Account:")) { // Om
																												
											accountNo = accountinfo[2].replaceAll("\\[", "").replaceAll("\\]", "")
													.replace(":", "");
											switch (accountinfo[5]) { // Baserat på vilken typ av konto det är.
											case "Sparkonto":
												bankLogicHandler.createSavingsAccount(customerinfo[1], Integer.valueOf(accountNo));
												System.out.println("Added account");
												break;
											case "Kreditkonto":
												bankLogicHandler.createCreditAccount(customerinfo[1],Integer.valueOf(accountNo));
												System.out.println("Added account");
												break;
											default:
												break;
											}
											if (accountinfo[i].contains("-")) // Om det finns minusvärden på konton
											{
												bankLogicHandler.withdraw(customerinfo[1], Integer.valueOf(accountNo), //
														Float.valueOf(accountinfo[3]));
												System.out.println("Withdrew account");
											} else {
												bankLogicHandler.deposit(customerinfo[1], Integer.valueOf(accountNo), //
														Float.valueOf(accountinfo[3]));
												System.out.println("Deposited account");
											}

											for (int c = 0; c < textline.length; c++) {
												if (textline[c].contains("Transaction:")) { // Till sist hitta all
																							// transaktioner
													String[] transactionInfo = textline[c].split(" ");

													String type = "";
													Transaction newTrans = new Transaction();
													if (transactionInfo[4].contains("-")) { // Märkt av ifall det är
																							// withdraw eller deposit
																							// för info
														type = "withdraw";
													} else {
														type = "deposit";
													}
													newTrans.transaction(customerinfo[1], type,
															Integer.valueOf(accountNo),
															Float.valueOf(transactionInfo[5]),
															Double.valueOf(transactionInfo[8]),
															transactionInfo[2] + " " + transactionInfo[3]);
													BankLogic.addTransaction(newTrans);
													System.out.println("Added Transaction");

												}
											}
										}
									}
								}

							}
						}
						/*
						 * Enable alla fält.
						 */
						c_Get.setEnabled(true);
						c_getAll.setEnabled(true);
						c_changeName.setEnabled(true);
						c_remove.setEnabled(true);
						accountManageMenu.setEnabled(true);
						a_get.setEnabled(true);
						a_close.setEnabled(true);
						transactionMenu.setEnabled(true);

					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

				catch (IOException e1) {
					consoleWindowT.setText(
							"Failure during Import, please check import file location and name: BankLogicImport.txt");
					e1.printStackTrace();
				}

				try {
					desktop.open(file);

				} catch (IOException e1) {
					consoleWindowT.setText("Failure during Opening, please check location and file integrity");
					e1.printStackTrace();
				}
			}
		});
		fileMenu.add(imports);

		c_Create.setMnemonic(KeyEvent.VK_C);
		customerMenu.add(c_Create, 0);

		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_R);
		menuBar.add(helpMenu);

		JMenuItem Documents = new JMenuItem(new AbstractAction("Documents") { // Rolig dokumentering som leder till
			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			// labrapporten OM DEN INTE FUNKAR SÅ
			// KOMMENTERA BORT.
			public void actionPerformed(ActionEvent e) {
				File file = new File(System.getProperty("user.dir") + "\\src\\thesob3\\" + "lab4_thesob3.docx");
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(file);
				} catch (IOException e1) {

					e1.printStackTrace();
				}
			}
		});
		helpMenu.add(Documents);

		JMenuItem about = new JMenuItem(new AbstractAction("About") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Created by TSAB @Uppsala (v.1.0)");
			}
		});
		helpMenu.add(about);

		JMenu contactMenu = new JMenu("Contact");
		contactMenu.setMnemonic(KeyEvent.VK_R);
		menuBar.add(contactMenu);
		JTextField searchField = new JTextField();
		menuBar.add(searchField);
		Button searchMenu = new Button("Search");
		menuBar.add(searchMenu);

		frame.setJMenuBar(menuBar);
		frame.setSize(700, 350);
		frame.setVisible(true);

		searchMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(frame, searchField.getText());
			}
		});

		contactMenu.addMenuListener(new MenuListener() { // Kontakt meny om den är selected

			public void menuSelected(MenuEvent e) {
				JOptionPane.showMessageDialog(frame,
						"+460707803988" + System.lineSeparator() + "TheoSobczak@outlook.com" + System.lineSeparator()
								+ "Uppsala" + System.lineSeparator() + "Liljegatan 4a" + System.lineSeparator()
								+ "75324");

			}

			@Override
			public void menuDeselected(MenuEvent e) {

			}

			@Override
			public void menuCanceled(MenuEvent e) {

			}

		});

	}

	boolean createFile() {

		try {
			File myObj = new File("filename.txt");
			if (myObj.createNewFile()) {
				System.out.println("File created: " + myObj.getName());
				return true;
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return false;

	}

}
