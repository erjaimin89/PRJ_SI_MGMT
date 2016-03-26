package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import main.Utils;

public class SettingsFormPanel extends JPanel {
	
	private static final long serialVersionUID = -3780615921348484593L;
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);

	public SettingsFormPanel() {
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		layout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0,  0, 0 };
		layout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,  1.0, 1.0 };
		setLayout(layout);
		setBackground(new Color(153, 204, 153));
		
		JButton btnNewButton = new JButton("Add New User");
		btnNewButton.setIcon(Utils.getImageIcon("NU.png"));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.insets = new Insets(20, 5, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		add(btnNewButton, gbc_btnNewButton);
		
		JButton btnPartySetting = new JButton("Add New party");
		btnPartySetting.setIcon(Utils.getImageIcon("party.png"));
		btnPartySetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnPartySetting = new GridBagConstraints();
		gbc_btnPartySetting.anchor = GridBagConstraints.NORTH;
		gbc_btnPartySetting.insets = new Insets(20, 5, 5, 5);
		gbc_btnPartySetting.gridx = 1;
		gbc_btnPartySetting.gridy = 0;
		add(btnPartySetting, gbc_btnPartySetting);
		
		JButton btnNewButton_1 = new JButton("Taxing Schemes");
		btnNewButton_1.setIcon(Utils.getImageIcon("TAX.png"));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_1.insets = new Insets(5, 5, 5, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 1;
		add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnInvSetting = new JButton("Invoice Settings");
		btnInvSetting.setIcon(Utils.getImageIcon("invoice.jpg"));
		btnInvSetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnInvSetting = new GridBagConstraints();
		gbc_btnInvSetting.anchor = GridBagConstraints.NORTH;
		gbc_btnInvSetting.insets = new Insets(5, 5, 5, 5);
		gbc_btnInvSetting.gridx = 1;
		gbc_btnInvSetting.gridy = 1;
		add(btnInvSetting, gbc_btnInvSetting);

		JButton btnBUSetting = new JButton("Backup order");
		btnBUSetting.setIcon(Utils.getImageIcon("backup-icon.png"));
		btnBUSetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnBUSetting = new GridBagConstraints();
		gbc_btnBUSetting.anchor = GridBagConstraints.NORTH;
		gbc_btnBUSetting.insets = new Insets(5, 5, 5, 5);
		gbc_btnBUSetting.gridx = 0;
		gbc_btnBUSetting.gridy = 2;
		add(btnBUSetting, gbc_btnBUSetting);
		
		JButton btnMailSetting = new JButton("EMAIL Settings");
		btnMailSetting.setIcon(Utils.getImageIcon("email.png"));
		btnMailSetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnMailSetting = new GridBagConstraints();
		gbc_btnMailSetting.anchor = GridBagConstraints.NORTH;
		gbc_btnMailSetting.insets = new Insets(5, 5, 5, 5);
		gbc_btnMailSetting.gridx = 1;
		gbc_btnMailSetting.gridy = 2;
		add(btnMailSetting, gbc_btnMailSetting);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				new AddUserDialog(parent);
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new AddTaxSchemeDialog(parent);
			}
		});
		

		btnInvSetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new InvoiceSettingDialog(parent);
			}
		});
		
		btnBUSetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new ThresholdSettingDialog(parent);
			}
		});
		
		btnPartySetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				new AddPartyDialog(parent);	
			}
		});
		
		btnMailSetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new EmailSettingsDialog(parent);
			}
		});
	}

}
