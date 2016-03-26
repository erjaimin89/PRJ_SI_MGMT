package gui;

import hibernate.PaymentEntity;
import hibernate.SalesOrderEntity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import main.Constants;
import main.InvoiceStatus;
import main.OrderPayStatus;
import main.PaymentStatus;
import main.ShippingStatus;
import main.Utils;

public class ViewPaymentDetails extends JDialog {

	private static final long serialVersionUID = -3006536712323496156L;
	
	private DefaultTableModel tableModel;
	private SalesOrderEntity salesOrderEntity;

	public ViewPaymentDetails(Frame parent, SalesOrderEntity salesOrderEntity) {
		super(parent);
		this.salesOrderEntity = salesOrderEntity;
		createLayout();
		
		setTitle("Payment Details");
		setSize(600, 600);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
	}

	private void createLayout() {
	
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -5179752599564957406L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (int i = 0; i < Constants.paymentDetailsColumnNames.length; i++) {
			tableModel.addColumn(Constants.paymentDetailsColumnNames[i]);
		}
		
		JPanel panel = (JPanel) getContentPane();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 100, 400 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0 };
		panel.setLayout(layout);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(245, 222, 179));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 0, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0};
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0};
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lblCustomerName = new JLabel("Customer Name :");
		lblCustomerName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.insets = new Insets(5, 5, 0, 0);
		gbc_lblCustomerName.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblCustomerName.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 0;
		panel_4.add(lblCustomerName, gbc_lblCustomerName);

		JTextField customerName = new JTextField();
		customerName.setEditable(false);
		customerName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(5, 5, 0, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.anchor = GridBagConstraints.WEST;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		customerName.setColumns(15);
		customerName.setText(salesOrderEntity.getCustomer().getCustomerName());
		panel_4.add(customerName, gbc_comboBox);
		
		JLabel lblOrderDate = new JLabel("Order Date :");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
		gbc_lblOrderDate.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
		gbc_lblOrderDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblOrderDate.gridx = 2;
		gbc_lblOrderDate.gridy = 0;
		panel_4.add(lblOrderDate, gbc_lblOrderDate);
		
		JTextField orderDate = new JTextField();
		orderDate.setEditable(false);
		orderDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_orderDate = new GridBagConstraints();
		gbc_orderDate.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderDate.anchor = GridBagConstraints.WEST;
		gbc_orderDate.insets = new Insets(5, 5, 0, 0);
		gbc_orderDate.gridx = 3;
		gbc_orderDate.gridy = 0;
		orderDate.setColumns(15);
		orderDate.setText(Utils.formatOrderDate(salesOrderEntity.getOrderDate()));
		panel_4.add(orderDate, gbc_orderDate);

		JLabel lblOrder = new JLabel("Order # :");
		lblOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrder = new GridBagConstraints();
		gbc_lblOrder.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrder.anchor = GridBagConstraints.EAST;
		gbc_lblOrder.gridx = 2;
		gbc_lblOrder.gridy = 1;
		panel_4.add(lblOrder, gbc_lblOrder);

		JTextField orderNo = new JTextField();
		orderNo.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(5, 5, 0, 0);
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 1;
		orderNo.setColumns(15);
		orderNo.setText(Utils.formatOrderId(salesOrderEntity.getSalesOrderId()));
		panel_4.add(orderNo, gbc_textField_1);

		JLabel lblOrderStatus = new JLabel("Order Status :");
		lblOrderStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderStatus = new GridBagConstraints();
		gbc_lblOrderStatus.anchor = GridBagConstraints.EAST;
		gbc_lblOrderStatus.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrderStatus.gridx = 0;
		gbc_lblOrderStatus.gridy = 3;
		panel_4.add(lblOrderStatus, gbc_lblOrderStatus);

		JTextField orderStatus = new JTextField();
		orderStatus.setEditable(false);
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(5, 5, 0, 0);
		gbc_textField_2.anchor = GridBagConstraints.WEST;
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridwidth = 2;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 3;
		orderStatus.setColumns(15);
		orderStatus.setText(ShippingStatus.values()[salesOrderEntity.getShippingStatus()] + ", "
				+ InvoiceStatus.values()[salesOrderEntity.getInvoiceStatus()] + ", "
				+ PaymentStatus.values()[salesOrderEntity.getPaymentStatus()]);
		panel_4.add(orderStatus, gbc_textField_2);
		
		JLabel lblST = new JLabel("Order Total :");
		lblST.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblST = new GridBagConstraints();
		gbc_lblST.insets = new Insets(5, 5, 0, 0);
		gbc_lblST.anchor = GridBagConstraints.EAST;
		gbc_lblST.fill = GridBagConstraints.BOTH;
		gbc_lblST.gridx = 0;
		gbc_lblST.gridy = 1;
		panel_4.add(lblST, gbc_lblST);

		JTextField orderST = new JTextField();
		orderST.setEditable(false);
		GridBagConstraints gbc_orderST = new GridBagConstraints();
		gbc_orderST.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderST.anchor = GridBagConstraints.WEST;
		gbc_orderST.insets = new Insets(5, 5, 0, 0);
		gbc_orderST.gridx = 1;
		gbc_orderST.gridy = 1;
		orderST.setColumns(10);
		orderST.setText(Utils.currencyFormat(salesOrderEntity.getTotal()));
		panel_4.add(orderST, gbc_orderST);
		
		JLabel lblPaid = new JLabel("Paid :");
		lblPaid.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblPaid = new GridBagConstraints();
		gbc_lblPaid.insets = new Insets(5, 5, 0, 0);
		gbc_lblPaid.anchor = GridBagConstraints.EAST;
//		gbc_lblPaid.fill = GridBagConstraints.BOTH;
		gbc_lblPaid.gridx = 0;
		gbc_lblPaid.gridy = 2;
		panel_4.add(lblPaid, gbc_lblPaid);

		JTextField orderPaid = new JTextField();
		orderPaid.setEditable(false);
		GridBagConstraints gbc_orderPaid = new GridBagConstraints();
		gbc_orderPaid.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderPaid.anchor = GridBagConstraints.WEST;
		gbc_orderPaid.insets = new Insets(5, 5, 0, 0);
		gbc_orderPaid.gridx = 1;
		gbc_orderPaid.gridy = 2;
		orderPaid.setColumns(10);
		orderPaid.setText(Utils.currencyFormat(salesOrderEntity.getPaidAmount()));
		panel_4.add(orderPaid, gbc_orderPaid);
		
		JLabel lblbal = new JLabel("Balance :");
		lblbal.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblbal = new GridBagConstraints();
		gbc_lblbal.insets = new Insets(5, 5, 0, 0);
		gbc_lblbal.anchor = GridBagConstraints.EAST;
		gbc_lblbal.gridx = 2;
		gbc_lblbal.gridy = 2;
		panel_4.add(lblbal, gbc_lblbal);

		JTextField orderBal = new JTextField();
		orderBal.setEditable(false);
		GridBagConstraints gbc_orderBal = new GridBagConstraints();
		gbc_orderBal.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderBal.anchor = GridBagConstraints.WEST;
		gbc_orderBal.insets = new Insets(5, 5, 0, 0);
		gbc_orderBal.gridx = 3;
		gbc_orderBal.gridy = 2;
		orderBal.setColumns(10);
		orderBal.setText(Utils.currencyFormat(salesOrderEntity.getRemainingBalance()));
		panel_4.add(orderBal, gbc_orderBal);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(245, 222, 179));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 1;
		add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0};
		gbl_panel_5.rowHeights = new int[] { 0 };
		gbl_panel_5.columnWeights = new double[] {Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		JTable table = new JTable(tableModel);
		
		loadTransactions();
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		try {
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = -601851424217498017L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table.setRowHeight(20);
//			DefaultRowSorter sorter = ((DefaultRowSorter)table.getRowSorter()); 
//	        ArrayList list = new ArrayList();
//	        list.add( new RowSorter.SortKey(0, SortOrder.DESCENDING));
//	        sorter.setSortKeys(list);
//	        sorter.sort();
			columnModel.getColumn(1).setCellRenderer(rightRenderer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(400, 300));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.NONE;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel_5.add(scroll, gbc_table);
		
	}

	private void loadTransactions() {
		tableModel.setNumRows(0);
		Set<PaymentEntity> paymentDetails = salesOrderEntity.getPaymentDetails();
		for(PaymentEntity entity : paymentDetails){
			String currencyFormat = "";
			if(OrderPayStatus.values()[entity.getType()] == OrderPayStatus.REFUND) {
				currencyFormat = "( "+Utils.currencyFormat(entity.getAmount())+" )";
			}else{
				currencyFormat = Utils.currencyFormat(entity.getAmount());
			}
			tableModel.addRow(new Object[]{Utils.formatOrderDateTime(entity.getPaymentDate()),  currencyFormat});
		}
	}
	
}
