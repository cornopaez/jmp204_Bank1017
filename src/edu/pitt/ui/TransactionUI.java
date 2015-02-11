package edu.pitt.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.pitt.bank.Account;
import edu.pitt.bank.Transaction;
import edu.pitt.utilities.ErrorLogger;
import edu.pitt.utilities.MySqlUtilities;

public class TransactionUI {

	private JFrame frmBankTransaction;
	private JScrollPane transactionPane;
	private JTable tblTransactions;
	private JButton btnClose;
	private Account currentAccount = null;


	/**
	 * Create the application 
	 * 
	 * @param account - This is the account that the user has selected for more details.
	 */
	public TransactionUI(Account account) {
		currentAccount = account;
		initialize();
		frmBankTransaction.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankTransaction = new JFrame();
		frmBankTransaction.setBounds(100, 100, 450, 300);
		frmBankTransaction.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		transactionPane = new JScrollPane();
		frmBankTransaction.getContentPane().add(transactionPane);
		String[] cols = {"Transaction ID", "Account Number", "Amount", "Date", "Type", "Balance"};
		try{
			DefaultTableModel transactionList = new MySqlUtilities().getDataTable(currentAccount.getTransactionList(), cols);
			tblTransactions = new JTable(transactionList);
			tblTransactions.setFillsViewportHeight(true);
			transactionPane.getViewport().add(tblTransactions);
			tblTransactions.setShowGrid(true);
			tblTransactions.setGridColor(Color.black);
			
		} catch (Exception e){
			ErrorLogger.log("The initial account container is null");
			ErrorLogger.log(e.getMessage());
			e.printStackTrace();
		}
		
		/*
		btnClose = new JButton("Close");
		btnClose.setBounds(327, 243, 117, 29);
		frmBankTransaction.getContentPane().add(btnClose);
		btnClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frmBankTransaction.setVisible(false);
			}
		});
		*/
	}

}
