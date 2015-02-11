package edu.pitt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JButton;

import edu.pitt.bank.Account;
import edu.pitt.bank.Bank;
import edu.pitt.bank.Customer;
import edu.pitt.utilities.ErrorLogger;

public class AccountDetailsUI {

	private JFrame frmAccountDetails;
	private JLabel lblAccounts;
	private JLabel lblAmount;
	private JTextArea txtAccountDetails;
	private JTextPane txtTransAmount;
	private JTextArea txtWelcome;
	private JComboBox cboAccounts;
	private JButton btnDeposit;
	private JButton btnWithdraw;
	private JButton btnShowTransactions;
	private JButton btnExit;
	private Customer currentCustomer = null;
	private Account currentAccount = null;
	private String currentGroups = " Customer, ";
	

	/**
	 * Create the application.
	 * 
	 * @param customer - This is the customer who logged in to the system
	 * @param userGroups - This is the list of permissions that the user has in the system
	 */
	public AccountDetailsUI(Customer customer, ArrayList<String> userGroups) {
		currentCustomer = customer;
		
		//This will populate the user permissions in the system
		for (String group : userGroups){
			if (!group.equalsIgnoreCase("Customer")){
				currentGroups += " " + group;
			}
		}
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		Bank bank = new Bank();
		
		frmAccountDetails = new JFrame();
		frmAccountDetails.setTitle("Bank 1017 Account Details");
		frmAccountDetails.setBounds(100, 100, 450, 300);
		frmAccountDetails.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAccountDetails.getContentPane().setLayout(null);
		
		txtWelcome = new JTextArea();
		txtWelcome.setEditable(false);
		txtWelcome.setLineWrap(true);
		txtWelcome.setBackground(SystemColor.window);
		String tempText = currentCustomer.getFirstName() + " " + currentCustomer.getLastName() + ", welcome to the 1017 Bank.";
		tempText += "You have the following permissions in this system:" + currentGroups + ".";
		txtWelcome.setText(tempText);
		txtWelcome.setBounds(10, 10, 434, 50);
		frmAccountDetails.getContentPane().add(txtWelcome);
		
		lblAccounts = new JLabel("Your Accounts:");
		lblAccounts.setBounds(37, 69, 107, 16);
		frmAccountDetails.getContentPane().add(lblAccounts);
		
		
		cboAccounts = new JComboBox();
		cboAccounts.setBounds(156, 53, 261, 50);
		frmAccountDetails.getContentPane().add(cboAccounts);
		//This populates the comboBox with all the accounts the user has access to
		for (Account account : bank.getAccountList()){
			for (Customer customer : account.getAccountOwners()){
				if (customer.getCustomerID().equals(currentCustomer.getCustomerID())){
					cboAccounts.addItem(account);
				}
			}
		}
		cboAccounts.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent a){
				//This populates the text area with relevant information about the selected account
				currentAccount = (Account) cboAccounts.getSelectedItem();
				String details = "Account Type: " + currentAccount.getType() + "\n";
				details += "Balance: $" + currentAccount.getBalance() + "\n";
				details += "Interest Rate: " + (currentAccount.getInterestRate())*100 + "%\n";
				details += "Penalty: $" + currentAccount.getPenalty() + "\n";
				txtAccountDetails.setText(details);
				
			}
		});
		
		
		txtAccountDetails = new JTextArea();
		txtAccountDetails.setEditable(false);
		txtAccountDetails.setBackground(SystemColor.window);
		txtAccountDetails.setText("Account Details");
		txtAccountDetails.setBounds(37, 120, 154, 90);
		frmAccountDetails.getContentPane().add(txtAccountDetails);
		
		lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(247, 120, 61, 16);
		frmAccountDetails.getContentPane().add(lblAmount);
		
		txtTransAmount = new JTextPane();
		txtTransAmount.setBounds(320, 120, 97, 16);
		frmAccountDetails.getContentPane().add(txtTransAmount);
		
		btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(215, 160, 93, 29);
		frmAccountDetails.getContentPane().add(btnDeposit);
		btnDeposit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent b){
				//This tries and parse the information provided for a transaction.
				int amount = 0;
				try{
					amount = Integer.parseInt(txtTransAmount.getText());
				} catch (NumberFormatException nfeLoginUI) {
					ErrorLogger.log("Could not parse the amount. This field has incorrect characters");
					ErrorLogger.log(nfeLoginUI.getMessage());
				}
				currentAccount.setTransactionType("deposit");
				currentAccount.deposit(amount);
			}
		});
		
		btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(320, 160, 97, 29);
		frmAccountDetails.getContentPane().add(btnWithdraw);
		btnWithdraw.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent c){
				//This tries and parse the information provided for a transaction.
				int amount = 0;
				try{
					amount = Integer.parseInt(txtTransAmount.getText());
				} catch (NumberFormatException nfeLoginUI) {
					ErrorLogger.log("Could not parse the amount. This field has incorrect characters");
					ErrorLogger.log(nfeLoginUI.getMessage());
				}
				currentAccount.setTransactionType("withdraw");
				currentAccount.withdraw(amount);
			}
		});
		

		btnShowTransactions = new JButton("Show Transactions");
		btnShowTransactions.setBounds(146, 224, 162, 29);
		frmAccountDetails.getContentPane().add(btnShowTransactions);
		btnShowTransactions.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent d){
				//This launches the transaction window with the details of the selected account.
				TransactionUI t = new TransactionUI(currentAccount);
			}
		});
		
		//This button exits the program when clicked.
		btnExit = new JButton("Exit");
		btnExit.setBounds(320, 224, 117, 29);
		frmAccountDetails.getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		frmAccountDetails.setVisible(true);
		
	}
}
