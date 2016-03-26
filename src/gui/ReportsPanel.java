package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import reportgui.InventorySummaryDialog;
import reportgui.ItemPurchaseDetailDialog;
import reportgui.ItemSalesDetailDialog;
import reportgui.SalesOrderDetailsDialog;
import reportgui.SalesOrderSummaryDialog;

public class ReportsPanel extends JPanel {
	
	private static final long serialVersionUID = -3780615921348484593L;
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);

	public ReportsPanel() {
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		layout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0,  0, 0 };
		layout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,  1.0, 1.0 };
		setLayout(layout);
		setBackground(new Color(153, 204, 153));
		
		JButton btnNewButton = new JButton("1. Sales Order Details");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setPreferredSize(new Dimension(250, 50));
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton.insets = new Insets(20, 5, 5, 5);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 0;
		add(btnNewButton, gbc_btnNewButton);
		
		JButton btnPartySetting = new JButton("2. Sales Order Summary");
		btnPartySetting.setPreferredSize(new Dimension(250, 50));
		btnPartySetting.setHorizontalAlignment(SwingConstants.LEFT);
		btnPartySetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnPartySetting = new GridBagConstraints();
		gbc_btnPartySetting.anchor = GridBagConstraints.NORTH;
		gbc_btnPartySetting.insets = new Insets(20, 5, 5, 5);
		gbc_btnPartySetting.gridx = 1;
		gbc_btnPartySetting.gridy = 0;
		add(btnPartySetting, gbc_btnPartySetting);
		
		JButton btnNewButton_1 = new JButton("3. Customer Balance Report");
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setPreferredSize(new Dimension(250, 50));
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_1.insets = new Insets(5, 5, 5, 5);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 1;
		add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnInvSetting = new JButton("4. Invoice Summary");
		btnInvSetting.setHorizontalAlignment(SwingConstants.LEFT);
		btnInvSetting.setPreferredSize(new Dimension(250, 50));
		btnInvSetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnInvSetting = new GridBagConstraints();
		gbc_btnInvSetting.anchor = GridBagConstraints.NORTH;
		gbc_btnInvSetting.insets = new Insets(5, 5, 5, 5);
		gbc_btnInvSetting.gridx = 1;
		gbc_btnInvSetting.gridy = 1;
		add(btnInvSetting, gbc_btnInvSetting);

		JButton btnBUSetting = new JButton("5. Item Sales Report");
		btnBUSetting.setHorizontalAlignment(SwingConstants.LEFT);
		btnBUSetting.setPreferredSize(new Dimension(250, 50));
		btnBUSetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnBUSetting = new GridBagConstraints();
		gbc_btnBUSetting.anchor = GridBagConstraints.NORTH;
		gbc_btnBUSetting.insets = new Insets(5, 5, 5, 5);
		gbc_btnBUSetting.gridx = 0;
		gbc_btnBUSetting.gridy = 2;
		add(btnBUSetting, gbc_btnBUSetting);
		
		JButton btnMailSetting = new JButton("6. Item Purchase Report");
		btnMailSetting.setHorizontalAlignment(SwingConstants.LEFT);
		btnMailSetting.setPreferredSize(new Dimension(250, 50));
		btnMailSetting.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnMailSetting = new GridBagConstraints();
		gbc_btnMailSetting.anchor = GridBagConstraints.NORTH;
		gbc_btnMailSetting.insets = new Insets(5, 5, 5, 5);
		gbc_btnMailSetting.gridx = 1;
		gbc_btnMailSetting.gridy = 2;
		add(btnMailSetting, gbc_btnMailSetting);
		
		
		JButton btnInvSummary = new JButton("7. Inventory Summary");
		btnInvSummary.setHorizontalAlignment(SwingConstants.LEFT);
		btnInvSummary.setPreferredSize(new Dimension(250, 50));
		btnInvSummary.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_btnInvSummary = new GridBagConstraints();
		gbc_btnInvSummary.anchor = GridBagConstraints.NORTH;
		gbc_btnInvSummary.insets = new Insets(5, 5, 5, 5);
		gbc_btnInvSummary.gridx = 0;
		gbc_btnInvSummary.gridy = 3;
		add(btnInvSummary, gbc_btnInvSummary);
		
		btnNewButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				new SalesOrderDetailsDialog(parent);
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new CustomerBalanceDialog(parent);
//				JasperPrint jpPrint = null;
//				try {
//					jpPrint = JasperFillManager.fillReport(Utils.getReportJasperName("customer.jasper"), null, hibernate.Database.getConnection());
//					JasperViewer jr = new JasperViewer(jpPrint, false);
//					jr.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
//					jr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//					jr.setVisible(true);
//				} catch (JRException ex) {
//					ex.printStackTrace();
//				}
			}
		});
		

		btnInvSetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new InvoiceSummaryDialog(parent);
			}
		});
		
		btnBUSetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new ItemSalesDetailDialog(parent);
			}
		});
		
		btnPartySetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				new SalesOrderSummaryDialog(parent);	
			}
		});
		
		btnMailSetting.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new ItemPurchaseDetailDialog(parent);
			}
		});
		
		btnInvSummary.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new InventorySummaryDialog(parent);
			}
		});
	}

}
