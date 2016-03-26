package gui;

import hibernate.CustomerEntity;
import hibernate.SalesOrderEntity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import mail.ConfigUtility;
import mail.EmailUtility;
import main.ComboItem;
import main.Constants;
import main.DateLabelFormatter;
import main.InvoiceStatus;
import main.PaymentStatus;
import main.ShippingStatus;
import main.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import contoller.ManageSalesOrders;

public class SOListFormPanel extends JPanel {

	private static final long serialVersionUID = 6471175494362767017L;
	
	private JTextField customerNameField;
	private JTextField orderNoField;
	private JDatePickerImpl datePickerFrom;
	private JDatePickerImpl datePickerTo;
	private JButton serachOrder;
	private static final int TOTAL_COL = 4;
	private static final int PAID_COL = 5;
	private static final int BALANCE_COL = 6;
	private static final int EDIT_COLUMN = 7;
	private static final int INV_COLUMN = 8;
	private static final int DEL_COLUMN = 9;

	private DefaultTableModel model;
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
	private ManageSalesOrders manageSalesOrders;
	private List<SalesOrderEntity> list;
	
	public SOListFormPanel() {
		manageSalesOrders = new ManageSalesOrders();
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(new UtilDateModel());
		JDatePanelImpl datePanelTo = new JDatePanelImpl(new UtilDateModel());
	    DateLabelFormatter formatter = new DateLabelFormatter();
		datePickerFrom = new JDatePickerImpl(datePanelFrom, formatter);
		datePickerTo = new JDatePickerImpl(datePanelTo, formatter);
		
		setBackground(new Color(102, 205, 170));
		setBorder(new TitledBorder(null, "Sales Order List", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.BLACK));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0 };
		layout.rowHeights = new int[] { 0 };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0 };
		setLayout(layout);
		
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 4447313472748838385L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case EDIT_COLUMN:
					return ImageIcon.class;
				case INV_COLUMN:
					return ImageIcon.class;
				case DEL_COLUMN:
					return ImageIcon.class;	
				default:
					return Object.class;
				}
			}
		};

		for (int i = 0; i < Constants.salesOrderListColumnNames.length; i++) {
			model.addColumn(Constants.salesOrderListColumnNames[i]);
		}
		setLayoutComponents();

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setLayoutComponents() {
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.setBackground(new Color(102, 205, 170));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel_1);
		
		JLabel lblCustomerName = new JLabel("Customer Name :");
		lblCustomerName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerName.insets = new Insets(5, 5, 5, 5);
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 0;
		panel.add(lblCustomerName, gbc_lblCustomerName);
		
		customerNameField = new JTextField();
		customerNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(5, 5, 0, 0);
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridwidth =2;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		customerNameField.setColumns(15);
		panel.add(customerNameField, gbc_textField_1);
		
		JLabel lblOrder = new JLabel("Order # :");
		lblOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrder = new GridBagConstraints();
		gbc_lblOrder.anchor = GridBagConstraints.EAST;
		gbc_lblOrder.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrder.gridx = 3;
		gbc_lblOrder.gridy = 0;
		panel.add(lblOrder, gbc_lblOrder);
		
		orderNoField = new JTextField();
		orderNoField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(5, 5, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridwidth=2;
		gbc_textField_2.gridx = 4;
		gbc_textField_2.gridy = 0;
		panel.add(orderNoField, gbc_textField_2);
		orderNoField.setColumns(10);
		
		JLabel lblInventoryStatus = new JLabel("Shipping Status :");
		lblInventoryStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblInventoryStatus = new GridBagConstraints();
		gbc_lblInventoryStatus.anchor = GridBagConstraints.EAST;
		gbc_lblInventoryStatus.insets = new Insets(5, 5, 0, 0);
		gbc_lblInventoryStatus.gridx = 0;
		gbc_lblInventoryStatus.gridy = 1;
		panel.add(lblInventoryStatus, gbc_lblInventoryStatus);
		
		final JComboBox inventoryStatusCombo = new JComboBox();
		inventoryStatusCombo.addItem(new ComboItem(0, ""));
		inventoryStatusCombo.addItem(new ComboItem(1, "Not Shipped"));
		inventoryStatusCombo.addItem(new ComboItem(2, "Shipped"));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(5, 5, 0, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel.add(inventoryStatusCombo, gbc_comboBox);
		
		JLabel lblOrderDate = new JLabel("Order Date :");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
		gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
		gbc_lblOrderDate.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrderDate.gridx = 2;
		gbc_lblOrderDate.gridy = 1;
		panel.add(lblOrderDate, gbc_lblOrderDate);
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(5, 5, 0, 0);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 1;
		panel.add(datePickerFrom, gbc_textField_3);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.anchor = GridBagConstraints.CENTER;
		gbc_lblTo.insets = new Insets(5, 5, 0, 0);
		gbc_lblTo.gridx = 4;
		gbc_lblTo.gridy = 1;
		panel.add(lblTo, gbc_lblTo);
		
		JLabel lblInvoiceStatus = new JLabel("Invoice Status :");
		lblInvoiceStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblInvoiceStatus = new GridBagConstraints();
		gbc_lblInvoiceStatus.anchor = GridBagConstraints.EAST;
		gbc_lblInvoiceStatus.insets = new Insets(5, 5, 0, 0);
		gbc_lblInvoiceStatus.gridx = 0;
		gbc_lblInvoiceStatus.gridy = 2;
		panel.add(lblInvoiceStatus, gbc_lblInvoiceStatus);
		
		final JComboBox invoiceStatusCombo = new JComboBox();
		invoiceStatusCombo.addItem(new ComboItem(0, ""));
		invoiceStatusCombo.addItem(new ComboItem(1, "Uninvoiced"));
		invoiceStatusCombo.addItem(new ComboItem(2, "Invoiced"));
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(5, 5, 0, 0);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 1;
		gbc_comboBox_1.gridy = 2;
		panel.add(invoiceStatusCombo, gbc_comboBox_1);
		
		JLabel lblPaymentStatus = new JLabel("Payment Status :");
		lblPaymentStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblPaymentStatus = new GridBagConstraints();
		gbc_lblPaymentStatus.anchor = GridBagConstraints.EAST;
		gbc_lblPaymentStatus.insets = new Insets(5, 5, 0, 0);
		gbc_lblPaymentStatus.gridx = 2;
		gbc_lblPaymentStatus.gridy = 2;
		panel.add(lblPaymentStatus, gbc_lblPaymentStatus);
		
		final JComboBox paymentStatusCombo = new JComboBox();
		paymentStatusCombo.addItem(new ComboItem(0, ""));
		paymentStatusCombo.addItem(new ComboItem(1, "Unpaid"));
		paymentStatusCombo.addItem(new ComboItem(2, "Paid"));
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.insets = new Insets(5, 5, 0, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 3;
		gbc_comboBox_2.gridy = 2;
		panel.add(paymentStatusCombo, gbc_comboBox_2);
		
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(5, 5, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 5;
		gbc_textField_4.gridy = 1;
		panel.add(datePickerTo, gbc_textField_4);
		
		serachOrder = new JButton("Search");
		serachOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
//		serachOrder.setIcon(Utils.getImageIcon("search.png"));
		serachOrder.setMnemonic(KeyEvent.VK_ENTER);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(7, 0, 5, 0);
		gbc_btnSearch.gridx = 2;
		gbc_btnSearch.gridy = 4;
		panel.add(serachOrder, gbc_btnSearch);
		
		JButton resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.insets = new Insets(7, 0, 5, 0);
		gbc_btnReset.gridx = 3;
		gbc_btnReset.gridy = 4;
		panel.add(resetButton, gbc_btnReset);
		
		JTable table = new JTable(model);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		try {
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(0).setCellRenderer(centerRenderer);
			columnModel.getColumn(0).setPreferredWidth(60);
			columnModel.getColumn(1).setCellRenderer(centerRenderer);
			columnModel.getColumn(1).setPreferredWidth(60);
			columnModel.getColumn(2).setPreferredWidth(100);
			columnModel.getColumn(TOTAL_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(PAID_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(BALANCE_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(EDIT_COLUMN).setPreferredWidth(10);
			columnModel.getColumn(INV_COLUMN).setPreferredWidth(10);
			columnModel.getColumn(DEL_COLUMN).setPreferredWidth(10);

			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = -7800178156661010679L;

				@Override public Dimension getPreferredSize() {
				    Dimension d = super.getPreferredSize();
				    d.height = 32;
				    return d;
				  }
				});
			table.setRowHeight(20);
		} catch (Exception continuewithNoSort) {
			//do nothing
		}
		JScrollPane tableScroll = new JScrollPane(table);
		tableScroll.setPreferredSize(new Dimension(600, 400));

		final Vector data = model.getDataVector();
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int rowNo = target.getSelectedRow();
					int columnNo = target.getSelectedColumn();
					Vector row = (Vector) data.elementAt(rowNo);
					String orderNo = (String) row.get(0);
					switch (columnNo) {
					case EDIT_COLUMN:
						int action = JOptionPane.showConfirmDialog(
								SOListFormPanel.this,
								"Do you really want to update Order #" + orderNo
								+ "?", "Update Sales Order",
								JOptionPane.OK_CANCEL_OPTION);
						if (action == JOptionPane.OK_OPTION) {
							    SOUpdateDialog soUpdateDialog = new SOUpdateDialog(parent, findOrderFromList(orderNo));
							    boolean showDialog = soUpdateDialog.showDialog();
							    if(showDialog)
							    	refreshModel(null);
						}
						break;
					case INV_COLUMN:
						ImageIcon icon = (ImageIcon) row.get(INV_COLUMN);
						if(icon != null){
							Object[] options = {"View",
				                    "Send Email"};
							int action1 = JOptionPane.showOptionDialog(
									SOListFormPanel.this,
									"What you want to do with invoice ?", "Invoice",
									JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							if (action1 == JOptionPane.OK_OPTION) {
								ViewInvoice viewInvoice = new ViewInvoice(parent, Utils.normalOrderId(orderNo), true);
								viewInvoice.showInvoice();
							}else{
								SalesOrderEntity findOrderFromList = findOrderFromList(orderNo);
								CustomerEntity customer = findOrderFromList.getCustomer();
								String email = customer.getEmail();
								if(email != null &&!"".equals(email)){
									String subject = "Invoice";
							        String message = "Hi "+customer.getCustomerName()+",\nPlease find attached invoice.\nThanks";
							        ViewInvoice viewInvoice = new ViewInvoice(parent, Utils.normalOrderId(orderNo), false);
									JasperPrint invoice = viewInvoice.showInvoice();
									OutputStream output;
									try {
										File file = new File(Utils.getFilesDirectory("Invoice.pdf"));
										output = new FileOutputStream(file);
										JasperExportManager.exportReportToPdfStream(invoice, output); 
								    
										Properties smtpProperties = new ConfigUtility().loadProperties();
								        
										EmailUtility.sendEmail(smtpProperties, email, subject, message, file);
								        
								        JOptionPane.showMessageDialog(new JFrame(), "The e-mail has been sent successfully!",
													"Message", JOptionPane.INFORMATION_MESSAGE);
								             
								        } catch (FileNotFoundException e1) {
											e1.printStackTrace();
										} catch (JRException e1) {
											e1.printStackTrace();
										} catch (Exception ex) {
								        	 JOptionPane.showMessageDialog(new JFrame(), "Error while sending the e-mail: " + ex.getMessage(),
														"Error", JOptionPane.ERROR_MESSAGE);
								        }
									
								}else{
									String message = "Please add email address for customer " + customer.getCustomerName();
									JOptionPane.showMessageDialog(new JFrame(), message,
											"Dialog", JOptionPane.ERROR_MESSAGE);
								}
								
							}
						}	
					    break;	
					case DEL_COLUMN:
						ImageIcon icon1 = (ImageIcon) row.get(DEL_COLUMN);
						if(icon1 != null){
							int action2 = JOptionPane.showConfirmDialog(
									SOListFormPanel.this,
									"Do you really want to remove/cancel Order #" + orderNo
									+ "? Note: This action will affect the inventory.", "Remove/Cancel Order",
									JOptionPane.OK_CANCEL_OPTION);
							if (action2 == JOptionPane.OK_OPTION) {
								boolean cancelOrder = manageSalesOrders.cancelOrder(Utils.normalOrderId(orderNo));
								if(cancelOrder){
									String message = "Order cancelled successfully!";
									JOptionPane.showMessageDialog(new JFrame(), message,
											"Dialog", JOptionPane.INFORMATION_MESSAGE);
									refreshModel(null);
								}else{
									String message = "Error while cancelling Order!";
									JOptionPane.showMessageDialog(new JFrame(), message,
											"Dialog", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
					    break;	    
					}
				}
			}
		});
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, tableScroll);

		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.anchor = GridBagConstraints.NORTH;
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		add(splitPane, gbc_splitPane);
		
		resetButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				customerNameField.setText("");
				orderNoField.setText("");
				inventoryStatusCombo.setSelectedIndex(0);
				paymentStatusCombo.setSelectedIndex(0);
				invoiceStatusCombo.setSelectedIndex(0);
				datePickerFrom.getModel().setValue(null);
				datePickerTo.getModel().setValue(null);
			}
		});
		
		serachOrder.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Date fromDate = (Date) datePickerFrom.getModel().getValue();
				Date toDate = (Date) datePickerTo.getModel().getValue();
				if(fromDate!= null && toDate!=null && fromDate.after(toDate)){
					JOptionPane.showMessageDialog(new JFrame(),	"From date cannot be greater than to date!", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					refreshModel(manageSalesOrders.listOrdersBySearch(customerNameField.getText(), orderNoField.getText(), 
							inventoryStatusCombo.getSelectedIndex() - 1, invoiceStatusCombo.getSelectedIndex() - 1, 
							paymentStatusCombo.getSelectedIndex() - 1, fromDate, toDate));
				}
				
			}
		});

	}
	
	public void refreshModel(List<SalesOrderEntity> modified) {
		model.setRowCount(0);
		list = new ArrayList<SalesOrderEntity>();
		
		if(modified!= null)
			list = modified;
		else
			list = manageSalesOrders.listOrders();
		
		for (Iterator<SalesOrderEntity> iterator = list.iterator(); iterator.hasNext();) {
			SalesOrderEntity order = iterator.next();
			InvoiceStatus invoiceStatus = InvoiceStatus.values()[order.getInvoiceStatus()];
			ShippingStatus shippingStatus = ShippingStatus.values()[order.getShippingStatus()];
			PaymentStatus paymentStatus = PaymentStatus.values()[order.getPaymentStatus()];
			ImageIcon icon = Utils.getImageIcon("pdf-icon.gif");
			ImageIcon cancelIcon = Utils.getImageIcon("cancel.png");
			if(invoiceStatus.equals(InvoiceStatus.UNINVOICED))
				icon = null;
			if(shippingStatus.equals(ShippingStatus.SHIPPED) || paymentStatus.equals(PaymentStatus.PAID))
				cancelIcon = null;
			
			model.addRow(new Object[] { Utils.formatOrderId(order.getSalesOrderId()),
					Utils.formatOrderDate(order.getOrderDate()), shippingStatus +", "+ paymentStatus, 
					order.getCustomer().getCustomerName(), Utils.currencyFormat(order.getTotal()), Utils.currencyFormat(order.getPaidAmount()), 
					Utils.currencyFormat(order.getRemainingBalance()), Utils.getImageIcon("edit.png"), 
					icon, cancelIcon});
		}
	}

	public void focusTextField() {
		customerNameField.requestFocusInWindow();
	}
	
	private SalesOrderEntity findOrderFromList(String orderNo) {
		long ordNo = Utils.normalOrderId(orderNo);
		for(SalesOrderEntity entity: list){
			if(entity.getSalesOrderId()== ordNo){
				return entity;
			}
		}
		return null;
	}

}
