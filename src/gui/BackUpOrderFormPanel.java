package gui;

import hibernate.UserEntity;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import main.ComboItem;
import main.DateLabelFormatter;
import main.UserType;
import main.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import contoller.ManageBackUpOrders;
import contoller.ManageUsers;

@SuppressWarnings("rawtypes")
public class BackUpOrderFormPanel extends JPanel {
	private static final long serialVersionUID = 7520697490627137426L;
	
	private JTextArea noteField;
	private JDatePickerImpl shippingDate;
	private JComboBox partyCombo;
	private static ManageBackUpOrders manageBackUpOrders = new ManageBackUpOrders();

	public BackUpOrderFormPanel() {
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(new UtilDateModel());
	    DateLabelFormatter formatter = new DateLabelFormatter();
	    shippingDate = new JDatePickerImpl(datePanelFrom, formatter);
		
		setBorder(new TitledBorder(null, "BackUp Order", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), new Color(
						34, 139, 34)));
		setBackground(new Color(176, 224, 230));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 150, 450, 50 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		setLayout(layout);
		
		setLayoutComponents();
		
	}

	@SuppressWarnings("unchecked")
	private void setLayoutComponents() {
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(176, 224, 230));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0};
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0,0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblItemNamecode = new JLabel("Party:");
		lblItemNamecode.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemNamecode = new GridBagConstraints();
		gbc_lblItemNamecode.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemNamecode.anchor = GridBagConstraints.EAST;
		gbc_lblItemNamecode.gridx = 0;
		gbc_lblItemNamecode.gridy = 0;
		panel_3.add(lblItemNamecode, gbc_lblItemNamecode);
		
		partyCombo = new JComboBox();
		partyCombo.setPrototypeDisplayValue("012345678901234");
		GridBagConstraints gbc_itemNameField = new GridBagConstraints();
		gbc_itemNameField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemNameField.fill = GridBagConstraints.BOTH;
		gbc_itemNameField.gridx = 1;
		gbc_itemNameField.gridy = 0;
		panel_3.add(partyCombo, gbc_itemNameField);
		
//		loadPartyCombo();
		
		JLabel lblQuantity = new JLabel("Note :");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.insets = new Insets(5, 5, 0, 0);
		gbc_lblQuantity.anchor = GridBagConstraints.EAST;
		gbc_lblQuantity.gridx = 0;
		gbc_lblQuantity.gridy = 1;
		panel_3.add(lblQuantity, gbc_lblQuantity);
		
		noteField = new JTextArea();
		noteField.setFont(new Font("Monospaced", Font.PLAIN, 12));
		noteField.setBorder(new LineBorder(UIManager
				.getColor("TextArea.inactiveForeground")));
		noteField.setRows(3);
		noteField.setColumns(31);
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.anchor = GridBagConstraints.WEST;
		gbc_textArea.insets = new Insets(5, 5, 0, 0);
		gbc_textArea.gridx = 1;
		gbc_textArea.gridy = 1;
		JScrollPane scrollpane = new JScrollPane(noteField);
		panel_3.add(scrollpane, gbc_textArea);
		
		JLabel lblItemlabel = new JLabel("Shipping Date :");
		lblItemlabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemlabel = new GridBagConstraints();
		gbc_lblItemlabel.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemlabel.anchor = GridBagConstraints.EAST;
		gbc_lblItemlabel.gridx = 2;
		gbc_lblItemlabel.gridy = 0;
		panel_3.add(lblItemlabel, gbc_lblItemlabel);
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(5, 5, 0, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 0;
		panel_3.add(shippingDate, gbc_textField_3);
		
		JButton btnSaveProduct = new JButton("Generate Order");
		btnSaveProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveProduct = new GridBagConstraints();
		gbc_btnSaveProduct.anchor = GridBagConstraints.CENTER;
		gbc_btnSaveProduct.gridx = 2;
		gbc_btnSaveProduct.gridy = 2;
		panel_3.add(btnSaveProduct, gbc_btnSaveProduct);
		
		btnSaveProduct.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(partyCombo.getSelectedIndex() <= 0){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please select Party First!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}else if(shippingDate.getModel().getValue() == null){
					String message = "Enter Shipping Date !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else{
					Date date = (Date) shippingDate.getModel().getValue();
					Long arrvDate = Utils.getDateInMillies(date);
					boolean generateBackUpOrder = manageBackUpOrders
							.generateBackUpOrder(
									(ComboItem) partyCombo.getSelectedItem(),
									arrvDate, noteField.getText());
					if(generateBackUpOrder){
						String message = "BackUp Order generated successfully !";
						JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
					}else{
						String message = "No items found under threshold for the party! Error while generating backup order !";
						JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
					}
					refreshForm();
				}
			}

		});
		
	}
	
	private void refreshForm() {
		partyCombo.setSelectedIndex(0);
		shippingDate.getModel().setValue(null);
		noteField.setText("");
	}
	
	public void clearForm() {
		loadPartyCombo();
		shippingDate.getModel().setValue(null);
		noteField.setText("");
	}
	
	@SuppressWarnings("unchecked")
	private void loadPartyCombo() {
		partyCombo.removeAllItems();
		partyCombo.addItem(new ComboItem(0, ""));
		List<UserEntity> list = new ManageUsers().listUsers(UserType.PARTY.ordinal());
		for (UserEntity user : list) {
			partyCombo.addItem(new ComboItem(user.getUserId(), user.getUserName()));
		}
	}

	public void focusTextField() {
		partyCombo.requestFocus();
	}
	
}
