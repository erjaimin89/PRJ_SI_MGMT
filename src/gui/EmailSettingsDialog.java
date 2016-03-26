package gui;

import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import mail.ConfigUtility;

public class EmailSettingsDialog extends JDialog {
	private static final long serialVersionUID = -7349897874882752170L;
	private ConfigUtility configUtil = new ConfigUtility();
	
	private JTextField hostNameField;
	private JTextField portNoField;
	private JTextField emailField;
	private JTextField passwordField;
	private JTextField emailField2;
	
	public EmailSettingsDialog(Frame parent) {
		super(parent);	
		
		createLayout();
		
		setTitle("Email Setting");
		setSize(400, 300);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
	}

	private void createLayout() {
		Container panel = getContentPane();

		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0, 0 };
		layout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		layout.columnWeights = new double[] { 1.0, 1.0,Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		panel.setLayout(layout);

		JLabel lblUserName = new JLabel("Host Name :");
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblUserName = new GridBagConstraints();
		gbc_lblUserName.insets = new Insets(5, 5, 0, 0);
		gbc_lblUserName.anchor = GridBagConstraints.EAST;
		gbc_lblUserName.gridx = 0;
		gbc_lblUserName.gridy = 0;
		panel.add(lblUserName, gbc_lblUserName);

		hostNameField = new JTextField();
		hostNameField.setFont(new Font("Tahoma", Font.BOLD, 12));
		hostNameField.setColumns(15);
		GridBagConstraints gbc_userNameField = new GridBagConstraints();
		gbc_userNameField.insets = new Insets(5, 5, 0, 0);
		gbc_userNameField.anchor = GridBagConstraints.WEST;
		gbc_userNameField.gridx = 1;
		gbc_userNameField.gridy = 0;
		panel.add(hostNameField, gbc_userNameField);
		
		JLabel lblPortNo = new JLabel("Port No :");
		lblPortNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblPortNo = new GridBagConstraints();
		gbc_lblPortNo.insets = new Insets(5, 5, 0, 0);
		gbc_lblPortNo.anchor = GridBagConstraints.EAST;
		gbc_lblPortNo.gridx = 0;
		gbc_lblPortNo.gridy = 1;
		panel.add(lblPortNo, gbc_lblPortNo);

		portNoField = new JTextField();
		portNoField.setFont(new Font("Tahoma", Font.BOLD, 12));
		portNoField.setColumns(15);
		GridBagConstraints gbc_portNoField = new GridBagConstraints();
		gbc_portNoField.insets = new Insets(5, 5, 0, 0);
		gbc_portNoField.anchor = GridBagConstraints.WEST;
		gbc_portNoField.gridx = 1;
		gbc_portNoField.gridy = 1;
		panel.add(portNoField, gbc_portNoField);
		
		JLabel lblEmailId = new JLabel("Email ID :");
		lblEmailId.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblEmailId = new GridBagConstraints();
		gbc_lblEmailId.insets = new Insets(5, 5, 0, 0);
		gbc_lblEmailId.anchor = GridBagConstraints.EAST;
		gbc_lblEmailId.gridx = 0;
		gbc_lblEmailId.gridy = 2;
		panel.add(lblEmailId, gbc_lblEmailId);

		emailField = new JTextField();
		emailField.setFont(new Font("Tahoma", Font.BOLD, 12));
		emailField.setColumns(15);
		GridBagConstraints gbc_emailField = new GridBagConstraints();
		gbc_emailField.insets = new Insets(5, 5, 0, 0);
		gbc_emailField.anchor = GridBagConstraints.WEST;
		gbc_emailField.gridx = 1;
		gbc_emailField.gridy = 2;
		panel.add(emailField, gbc_emailField);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblPassword = new GridBagConstraints();
		gbc_lblPassword.insets = new Insets(5, 5, 0, 0);
		gbc_lblPassword.anchor = GridBagConstraints.EAST;
		gbc_lblPassword.gridx = 0;
		gbc_lblPassword.gridy = 3;
		panel.add(lblPassword, gbc_lblPassword);

		passwordField = new JTextField();
		passwordField.setFont(new Font("Tahoma", Font.BOLD, 12));
		passwordField.setColumns(15);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(5, 5, 0, 0);
		gbc_passwordField.anchor = GridBagConstraints.WEST;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		panel.add(passwordField, gbc_passwordField);
		
		JLabel lblVendorEmailId = new JLabel("Vendor Email :");
		lblVendorEmailId.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblVendorEmailId = new GridBagConstraints();
		gbc_lblVendorEmailId.insets = new Insets(5, 5, 0, 0);
		gbc_lblVendorEmailId.anchor = GridBagConstraints.EAST;
		gbc_lblVendorEmailId.gridx = 0;
		gbc_lblVendorEmailId.gridy = 4;
		panel.add(lblVendorEmailId, gbc_lblVendorEmailId);

		emailField2 = new JTextField();
		emailField2.setFont(new Font("Tahoma", Font.BOLD, 12));
		emailField2.setColumns(15);
		GridBagConstraints gbc_emailField2 = new GridBagConstraints();
		gbc_emailField2.insets = new Insets(5, 5, 0, 0);
		gbc_emailField2.anchor = GridBagConstraints.WEST;
		gbc_emailField2.gridx = 1;
		gbc_emailField2.gridy = 4;
		panel.add(emailField2, gbc_emailField2);
		
		JButton btnUpdateProduct = new JButton("Save");
		btnUpdateProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveProduct = new GridBagConstraints();
		gbc_btnSaveProduct.anchor = GridBagConstraints.WEST;
		gbc_btnSaveProduct.gridx = 1;
		gbc_btnSaveProduct.gridy = 5;
		panel.add(btnUpdateProduct, gbc_btnSaveProduct);
		
		loadSettings();
		
		btnUpdateProduct.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(hostNameField.getText().equals("")){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter Host Name!", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					hostNameField.requestFocus();
				} else if(portNoField.getText().equals("")){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter Port Number!", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					portNoField.requestFocus();
				} else if(emailField.getText().equals("")){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter Email ID!", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					emailField.requestFocus();
				}else if(passwordField.getText().equals("")){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter Password!", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					passwordField.requestFocus();
				}else if(emailField2.getText().equals("")){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please enter Vendor Email ID!", "Dialog",
							JOptionPane.ERROR_MESSAGE);
					emailField2.requestFocus();
				}else{
					try {
						configUtil.saveProperties(hostNameField.getText(), portNoField.getText(), 
								emailField.getText(), passwordField.getText(), emailField2.getText());
						 JOptionPane.showMessageDialog(EmailSettingsDialog.this,
				                    "Properties were saved successfully!");    
				         dispose();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(EmailSettingsDialog.this,
			                    "Error saving properties file: " + e1.getMessage(),
			                    "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
	}

	private void loadSettings() {
		Properties configProps = null;
        try {
            configProps = configUtil.loadProperties();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error reading settings: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
         
        hostNameField.setText(configProps.getProperty("mail.smtp.host"));
        portNoField.setText(configProps.getProperty("mail.smtp.port"));
        emailField.setText(configProps.getProperty("mail.user"));
        passwordField.setText(configProps.getProperty("mail.password"));
        emailField2.setText(configProps.getProperty("mail.vendor"));
		
	}

	
}
