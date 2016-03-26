package gui;

import hibernate.CustomerEntity;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.Action;
import main.Utils;
import contoller.ManageCustomers;

public class CustomerFormPanel extends JPanel {

	private static final long serialVersionUID = -6784939306904898200L;

	private JTextField customerNameField;
	private JTextField companyNameField;
	private JTextField balanceField;
	private JTextArea addressField;
	private JTextField phoneField;
	private JTextField faxField;
	private JTextField emailField;
	private JTextArea remarks;
	private JButton btnSaveCustomer;
	private JButton btnUpdateCustomer;
	private Action action;
	private ManageCustomers manageCustomers;
	private CustomerEntity customer;

	public CustomerFormPanel(Action action, CustomerEntity entity) {
		this.action = action;
		this.customer = entity;
		manageCustomers = new ManageCustomers();
		String title = "";
		if (action.equals(Action.ADD)) {
			title = "New Customer";
		} else {
			title = "Update Customer";
		}
		setBorder(new TitledBorder(null, title, TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.BLACK));
		setBackground(new Color(240, 248, 255));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 30, 30, 30, 30, 100, 30, 30, 30, 100,
				210 };
		setLayout(layout);
		setLayoutComponents();

		btnSaveCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String customerName = customerNameField.getText().trim();
				if (customerName.isEmpty() || "".equals(customerName)) {
					String message = "The Customer Name Field must be filled in !";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);
					customerNameField.grabFocus();
				} else {
					long currentTimeMillis = System.currentTimeMillis();
					CustomerEntity entity = new CustomerEntity(customerName,
							companyNameField.getText(), Utils.normalFormat(
									balanceField.getText()), addressField
									.getText(), phoneField.getText(), faxField
									.getText(), emailField.getText(), true,
							currentTimeMillis, currentTimeMillis, remarks
									.getText());
					if (!manageCustomers.isCustomerExists(customerName)) {
						boolean addCustomer = manageCustomers
								.addCustomer(entity);
						if (addCustomer) {
							String message = "New Customer added successfully !";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.INFORMATION_MESSAGE);
							clearForm();
						} else {
							String message = "Error while adding a new customer !";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						String message = "Customer with same name already exists !";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnUpdateCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String customerName = customerNameField.getText().trim();
				if (customerName.isEmpty() || "".equals(customerName)) {
					String message = "The Customer Name Field must be filled in !";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);
					customerNameField.grabFocus();
				} else if (isNameChanged() && manageCustomers.isCustomerExists(customerName)) {
					String message = "Customer with same name already exists !";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}else{
					long currentTimeMillis = System.currentTimeMillis();
					customer.setCustomerName(customerName);
					customer.setCompanyName(companyNameField.getText());
					customer.setAddress(addressField.getText());
					customer.setPhone(phoneField.getText());
					customer.setFax(faxField.getText());
					customer.setEmail(emailField.getText());
					customer.setActive(true);
					customer.setRemarks(remarks.getText());
					customer.setUpdatedOn(currentTimeMillis);
					boolean status = manageCustomers.updateCustomer(customer);
					if (status) {
						JOptionPane.showMessageDialog(new JFrame(), 
								"Customer updated successfully !", "Dialog",
								JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(new JFrame(),
								"Error while updating!", "Dialog",
								JOptionPane.ERROR_MESSAGE);
					}
					CustomerListPanel.mydialog.dispose();
				}
			}

			private boolean isNameChanged() {
				return !(customerNameField.getText().equalsIgnoreCase(customer.getCustomerName()));
			}
		});
	}

	public void clearForm() {
		customerNameField.setText("");
		companyNameField.setText("");
		balanceField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
		addressField.setText("");
		phoneField.setText("");
		faxField.setText("");
		emailField.setText("");
		remarks.setText("");
	}

	private void setLayoutComponents() {

		JLabel lblBasic = new JLabel("Customer Info");
		lblBasic.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblBasic.setForeground(new Color(34, 139, 34));
		GridBagConstraints gbc_lblBasic = new GridBagConstraints();
		gbc_lblBasic.anchor = GridBagConstraints.EAST;
		gbc_lblBasic.insets = new Insets(0, 0, 5, 5);
		gbc_lblBasic.gridx = 0;
		gbc_lblBasic.gridy = 0;
		add(lblBasic, gbc_lblBasic);

		JLabel nameLabel = new JLabel("Customer Name :");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCustomerName.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 1;
		add(nameLabel, gbc_lblCustomerName);

		customerNameField = new JTextField();
		customerNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		customerNameField.setColumns(20);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		add(customerNameField, gbc_textField);

		JLabel lblCompanyName = new JLabel("Company Name :");
		lblCompanyName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCompanyName = new GridBagConstraints();
		gbc_lblCompanyName.anchor = GridBagConstraints.EAST;
		gbc_lblCompanyName.insets = new Insets(0, 0, 5, 5);
		gbc_lblCompanyName.gridx = 0;
		gbc_lblCompanyName.gridy = 2;
		add(lblCompanyName, gbc_lblCompanyName);

		companyNameField = new JTextField();
		companyNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		companyNameField.setColumns(20);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(0, 0, 5, 0);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		add(companyNameField, gbc_textField_1);

		JLabel lblBalance = new JLabel("Balance :  CAD");
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblBalance = new GridBagConstraints();
		gbc_lblBalance.anchor = GridBagConstraints.EAST;
		gbc_lblBalance.insets = new Insets(0, 0, 5, 5);
		gbc_lblBalance.gridx = 0;
		gbc_lblBalance.gridy = 3;
		add(lblBalance, gbc_lblBalance);

		balanceField = new JTextField();
		balanceField.setFont(new Font("Tahoma", Font.BOLD, 12));
		balanceField.setEditable(false);
		balanceField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
		balanceField.setColumns(20);
		balanceField.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.anchor = GridBagConstraints.WEST;
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 3;
		add(balanceField, gbc_textField_2);

		JLabel lblAddress = new JLabel("Address :");
		lblAddress.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblAddress_1 = new GridBagConstraints();
		gbc_lblAddress_1.anchor = GridBagConstraints.EAST;
		gbc_lblAddress_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblAddress_1.gridx = 0;
		gbc_lblAddress_1.gridy = 4;
		add(lblAddress, gbc_lblAddress_1);

		addressField = new JTextArea();
		addressField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		addressField.setBorder(new LineBorder(UIManager
				.getColor("TextArea.inactiveForeground")));
		addressField.setRows(5);
		addressField.setColumns(31);
		// addressField.set
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.anchor = GridBagConstraints.WEST;
		gbc_textArea.insets = new Insets(0, 0, 5, 0);
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 4;
		JScrollPane scrollpane = new JScrollPane(addressField);
		add(scrollpane, gbc_textArea);

		JLabel lblPhone = new JLabel("Phone :");
		lblPhone.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblPhone = new GridBagConstraints();
		gbc_lblPhone.anchor = GridBagConstraints.EAST;
		gbc_lblPhone.insets = new Insets(0, 0, 5, 5);
		gbc_lblPhone.gridx = 0;
		gbc_lblPhone.gridy = 5;
		add(lblPhone, gbc_lblPhone);

		phoneField = new JTextField();
		phoneField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		phoneField.setColumns(10);
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.anchor = GridBagConstraints.WEST;
		gbc_textField_3.insets = new Insets(0, 0, 5, 0);
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 5;
		add(phoneField, gbc_textField_3);

		JLabel lblFax = new JLabel("Fax :");
		lblFax.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblFax = new GridBagConstraints();
		gbc_lblFax.anchor = GridBagConstraints.EAST;
		gbc_lblFax.insets = new Insets(0, 0, 5, 5);
		gbc_lblFax.gridx = 0;
		gbc_lblFax.gridy = 6;
		add(lblFax, gbc_lblFax);

		faxField = new JTextField();
		faxField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		faxField.setColumns(20);
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.anchor = GridBagConstraints.WEST;
		gbc_textField_4.insets = new Insets(0, 0, 5, 0);
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 6;
		add(faxField, gbc_textField_4);

		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblEmail = new GridBagConstraints();
		gbc_lblEmail.anchor = GridBagConstraints.EAST;
		gbc_lblEmail.insets = new Insets(0, 0, 5, 5);
		gbc_lblEmail.gridx = 0;
		gbc_lblEmail.gridy = 7;
		add(lblEmail, gbc_lblEmail);

		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailField.setColumns(20);
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.anchor = GridBagConstraints.WEST;
		gbc_textField_5.insets = new Insets(0, 0, 5, 0);
		gbc_textField_5.gridx = 1;
		gbc_textField_5.gridy = 7;
		add(emailField, gbc_textField_5);

		JLabel lblRemarks = new JLabel("Remarks :");
		lblRemarks.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblRemarks = new GridBagConstraints();
		gbc_lblRemarks.anchor = GridBagConstraints.EAST;
		gbc_lblRemarks.insets = new Insets(0, 0, 5, 5);
		gbc_lblRemarks.gridx = 0;
		gbc_lblRemarks.gridy = 8;
		add(lblRemarks, gbc_lblRemarks);

		remarks = new JTextArea();
		remarks.setFont(new Font("Monospaced", Font.PLAIN, 12));
		remarks.setRows(3);
		remarks.setColumns(31);
		remarks.setBorder(new LineBorder(UIManager
				.getColor("TextArea.inactiveForeground")));
		GridBagConstraints gbc_textArea1 = new GridBagConstraints();
		gbc_textArea1.anchor = GridBagConstraints.WEST;
		gbc_textArea1.insets = new Insets(0, 0, 5, 0);
		gbc_textArea1.gridx = 1;
		gbc_textArea1.gridy = 8;
		JScrollPane scrollpane1 = new JScrollPane(remarks);
		add(scrollpane1, gbc_textArea1);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnReset.insets = new Insets(20, 0, 0, 5);
		gbc_btnReset.gridx = 0;
		gbc_btnReset.gridy = 9;
	
		btnReset.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		
		btnSaveCustomer = new JButton("Save Customer");
		btnSaveCustomer.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveCustomer = new GridBagConstraints();
		gbc_btnSaveCustomer.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSaveCustomer.insets = new Insets(20, 5, 0, 0);
		gbc_btnSaveCustomer.gridx = 1;
		gbc_btnSaveCustomer.gridy = 9;

		btnUpdateCustomer = new JButton("Update Customer");
		btnUpdateCustomer.setFont(new Font("Tahoma", Font.BOLD, 12));
		if (action.equals(Action.ADD)) {
			add(btnReset, gbc_btnReset);
			add(btnSaveCustomer, gbc_btnSaveCustomer);
			
		} else {
			if (customer != null) {
				customerNameField.setText(customer.getCustomerName());
//				customerNameField.setEditable(false);
				companyNameField.setText(customer.getCompanyName());
				balanceField.setText(Utils.currencyFormat(customer.getBalance()));
				emailField.setText(customer.getEmail());
				phoneField.setText(customer.getPhone());
				faxField.setText(customer.getFax());
				addressField.setText(customer.getAddress());
				remarks.setText(customer.getRemarks());
			}
			add(btnUpdateCustomer, gbc_btnSaveCustomer);
		}
	}
	


	public void focusTextField() {
		customerNameField.requestFocusInWindow();
	}
	
}
