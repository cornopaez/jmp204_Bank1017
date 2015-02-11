package edu.pitt.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;

import edu.pitt.bank.Bank;
import edu.pitt.bank.Customer;
import edu.pitt.bank.Security;
import edu.pitt.utilities.ErrorLogger;

import java.awt.Color;

public class LoginUI {

	private JFrame frmBankLogin;
	private JTextField txtLoginName;
	private JTextField txtPassword;
	private JLabel lblLoginName;
	private JLabel lblPassword;
	private JLabel lblInvalidInput;
	private JButton btnLogin;
	private JButton btnExit;
	private boolean quit = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginUI window = new LoginUI();
					window.frmBankLogin.setVisible(true);
				} catch (Exception e) {
					ErrorLogger.log("The login window could not be launched.");
					ErrorLogger.log(e.getMessage());
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBankLogin = new JFrame();
		frmBankLogin.setTitle("Bank 1017 Login");
		frmBankLogin.setBounds(100, 100, 450, 200);
		frmBankLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBankLogin.getContentPane().setLayout(null);
		
		lblLoginName = new JLabel("Login Name:");
		lblLoginName.setBounds(42, 43, 90, 16);
		frmBankLogin.getContentPane().add(lblLoginName);
		
		txtLoginName = new JTextField();
		txtLoginName.setBounds(144, 37, 250, 28);
		frmBankLogin.getContentPane().add(txtLoginName);
		txtLoginName.setColumns(10);
		
		lblPassword = new JLabel("Password:");
		lblPassword.setBounds(42, 78, 90, 16);
		frmBankLogin.getContentPane().add(lblPassword);
		
		txtPassword = new JTextField();
		txtPassword.setBounds(144, 72, 250, 28);
		frmBankLogin.getContentPane().add(txtPassword);
		txtPassword.setColumns(10);
		
		lblInvalidInput = new JLabel("Invalid login!");
		lblInvalidInput.setForeground(Color.RED);
		lblInvalidInput.setBounds(42, 117, 154, 16);
		lblInvalidInput.setVisible(false);
		frmBankLogin.getContentPane().add(lblInvalidInput);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(208, 112, 117, 29);
		frmBankLogin.getContentPane().add(btnLogin);
		btnLogin.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				//We'll try and parse the pin provided
				int parsedPin = 0;
				try{
					parsedPin = Integer.parseInt(txtPassword.getText());
				} catch (NumberFormatException nfeLoginUI) {
					ErrorLogger.log("Could not parse the password. Incorrect input");
					ErrorLogger.log(nfeLoginUI.getMessage());
				}
				
				// If the pin was parsed, we'll try to validate the login. If login is invalid, the customer will know.
				Security s = new Security();
				if (s.validateLogin(txtLoginName.getText(), parsedPin) != null){
					frmBankLogin.setVisible(false);
					Customer c = new Customer(s.validateLogin(txtLoginName.getText(), parsedPin).getCustomerID());
					AccountDetailsUI ad = new AccountDetailsUI(c , s.listUserGroups(c));
					
				} else {
					lblInvalidInput.setVisible(true);
				}
			
			}
		});
		
		btnExit = new JButton("Exit");
		btnExit.setBounds(337, 112, 57, 29);
		frmBankLogin.getContentPane().add(btnExit);
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
	}
}
