package gui;

import hibernate.BackUpOrderEntity;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.BackUpOrderStatus;
import main.ComboItem;
import main.DateLabelFormatter;
import main.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import contoller.ManageBackUpOrders;

public class BackUpUpdateDialog extends JDialog {
	private static final long serialVersionUID = -2597558935681651839L;
	
	private BackUpOrderEntity backUpOrderEntity;
	private boolean shippedFlag;
	@SuppressWarnings("rawtypes")
	private JComboBox shippingStatusCombo;
	private UtilDateModel utilDateModel;
	private JDatePickerImpl arrivalDate;
	private boolean flag;
	private static ManageBackUpOrders manageBackUpOrders = new ManageBackUpOrders();
	
	public BackUpUpdateDialog(JFrame parent, BackUpOrderEntity backUpOrderEntity) {
		super(parent);
		this.backUpOrderEntity = backUpOrderEntity;
		utilDateModel = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(utilDateModel);
		DateLabelFormatter formatter = new DateLabelFormatter();
		arrivalDate = new JDatePickerImpl(datePanel, formatter);
	}

	public boolean showDialog() {
		createLayout();
		
		setTitle("View/Update BackUp Order");
		setSize(600, 250);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
		return flag;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createLayout() {
		final Integer shippingStatus = backUpOrderEntity.getShippingStatus();
		if(shippingStatus == BackUpOrderStatus.SENT.ordinal()){
			shippedFlag = true;
		}	
		JPanel panel = (JPanel) getContentPane();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 0, 0 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		panel.setLayout(layout);
		
		JPanel panel_4 = new JPanel();
		panel.setBackground(new Color(255, 204, 153));
		panel_4.setBackground(new Color(255, 204, 153));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 0, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lblOrderNo = new JLabel("Order # :");
		lblOrderNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderNo = new GridBagConstraints();
		gbc_lblOrderNo.insets = new Insets(20, 5, 0, 0);
		gbc_lblOrderNo.anchor = GridBagConstraints.EAST;
		gbc_lblOrderNo.gridx = 0;
		gbc_lblOrderNo.gridy = 0;
		panel_4.add(lblOrderNo, gbc_lblOrderNo);

		JTextField orderNo = new JTextField();
		orderNo.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(20, 5, 0, 0);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		orderNo.setColumns(15);
		orderNo.setText(Utils.formatBackUpOrderId(backUpOrderEntity.getBackUpOrderId()));
		panel_4.add(orderNo, gbc_textField_1);
		
		JLabel lblDate = new JLabel("Order Date:");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(20, 5, 0, 0);
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.gridx = 2;
		gbc_lblDate.gridy = 0;
		panel_4.add(lblDate, gbc_lblDate);

		JTextField orderDate = new JTextField();
		orderDate.setEditable(false);
		GridBagConstraints gbc_orderDate = new GridBagConstraints();
		gbc_orderDate.anchor = GridBagConstraints.WEST;
		gbc_orderDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderDate.insets = new Insets(20, 5, 0, 5);
		gbc_orderDate.gridx = 3;
		gbc_orderDate.gridy = 0;
		orderDate.setText(Utils.formatOrderDate(backUpOrderEntity.getOrderDate()));
		panel_4.add(orderDate, gbc_orderDate);
		
		JLabel lblOrderStatus = new JLabel("Order Status :");
		lblOrderStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderStatus = new GridBagConstraints();
		gbc_lblOrderStatus.anchor = GridBagConstraints.WEST;
		gbc_lblOrderStatus.insets = new Insets(15, 5, 0, 0);
		gbc_lblOrderStatus.gridx = 0;
		gbc_lblOrderStatus.gridy = 1;
		panel_4.add(lblOrderStatus, gbc_lblOrderStatus);

		shippingStatusCombo = new JComboBox();
		shippingStatusCombo.setPrototypeDisplayValue("012345678901234");
		shippingStatusCombo.addItem(new ComboItem(0, BackUpOrderStatus.NOT_SENT.toString()));
		shippingStatusCombo.addItem(new ComboItem(1, BackUpOrderStatus.SENT.toString()));
		GridBagConstraints gbc_inventoryStatusCombo = new GridBagConstraints();
		gbc_inventoryStatusCombo.insets = new Insets(15, 5, 0, 0);
		gbc_inventoryStatusCombo.anchor = GridBagConstraints.WEST;
		gbc_inventoryStatusCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_inventoryStatusCombo.gridx = 1;
		gbc_inventoryStatusCombo.gridy = 1;
		
		if(shippedFlag)
			shippingStatusCombo.setEnabled(false);
		shippingStatusCombo.setSelectedIndex(shippingStatus);
		panel_4.add(shippingStatusCombo, gbc_inventoryStatusCombo);
		
		JLabel lblOrderDate = new JLabel("Shipping Date :");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
		gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
		gbc_lblOrderDate.insets = new Insets(15, 5, 0, 5);
		gbc_lblOrderDate.gridx = 2;
		gbc_lblOrderDate.gridy = 1;
		panel_4.add(lblOrderDate, gbc_lblOrderDate);
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(15, 5, 0, 5);
		gbc_textField_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 1;
		long date = backUpOrderEntity.getArrivalDate();
		if(date > 0){
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date);
			utilDateModel.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			utilDateModel.setSelected(true);
			
		}
		panel_4.add(arrivalDate, gbc_textField_3);
		
		JButton btnUpdateProduct = new JButton("Update");
		btnUpdateProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveProduct = new GridBagConstraints();
		gbc_btnSaveProduct.anchor = GridBagConstraints.WEST;
		gbc_btnSaveProduct.insets = new Insets(30, 0, 10, 0);
		gbc_btnSaveProduct.gridx = 2;
		gbc_btnSaveProduct.gridy = 2;
		if(shippingStatus == BackUpOrderStatus.NOT_SENT.ordinal())
			panel_4.add(btnUpdateProduct, gbc_btnSaveProduct);
		
		btnUpdateProduct.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(arrivalDate.getModel().getValue() == null){
					String message = "Enter Shipping Date !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else{
					Date date = (Date) arrivalDate.getModel().getValue();
					Long arrvDate = Utils.getDateInMillies(date);
					backUpOrderEntity.setArrivalDate(arrvDate);
					boolean shipped = false;
					int selectedShippingStatus = shippingStatusCombo.getSelectedIndex();
					if(selectedShippingStatus == BackUpOrderStatus.SENT.ordinal() && !shippedFlag){
						shipped = true;
						backUpOrderEntity.setShippingStatus(selectedShippingStatus);
					}
					
					boolean updateBackUpOrder = manageBackUpOrders
							.updateBackUpOrder(backUpOrderEntity, shipped);
					if(updateBackUpOrder){
						String message = "BackUp Order updated successfully !";
						JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
						flag = true;
						hideDialog();
					}else{
						String message = "Error while updating backup order !";
						JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
					}
					
				}
			}
		});
		
	}
	
	private void hideDialog(){
		setVisible(false);
		dispose();
	}

}
