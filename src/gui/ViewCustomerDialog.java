package gui;

import hibernate.PaymentEntity;
import hibernate.SalesOrderEntity;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultRowSorter;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
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

public class ViewCustomerDialog extends JDialog {

	private static final long serialVersionUID = 2945190088946407880L;
	private List<SalesOrderEntity> list;
	private DefaultTableModel model;
	private DefaultTableModel model2;
	
	private static final int TOTAL_COL = 3;
	private static final int PAID_COL = 4;
	private static final int BALANCE_COL = 5;
	
	public ViewCustomerDialog(Frame parent, List<SalesOrderEntity> list) {
		super(parent);
		this.list = list;
		createLayout();
		
		setTitle("View Customer Details");
		setSize(850, 700);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createLayout() {
		model = new DefaultTableModel() {
			private static final long serialVersionUID = -2520815172411974618L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (int i = 0; i < Constants.salesOrderListColumnNamesCust.length; i++) {
			model.addColumn(Constants.salesOrderListColumnNamesCust[i]);
		}
		
		model2 = new DefaultTableModel() {
			private static final long serialVersionUID = -1920134513799013237L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		for (int i = 0; i < Constants.paymentDetailsColumnNamesCust.length; i++) {
			model2.addColumn(Constants.paymentDetailsColumnNamesCust[i]);
		}
		
		
		JPanel panel = (JPanel) getContentPane();
		panel.setLayout(new GridBagLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		tabbedPane.addTab("Order History", null, panel_5);//add(panel_5., gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);
		
		add(tabbedPane);
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		JTable table = new JTable(model);
		loadOrderSummary();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		try {
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(TOTAL_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(PAID_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(BALANCE_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(0).setCellRenderer(centerRenderer);
			columnModel.getColumn(0).setPreferredWidth(35);
			columnModel.getColumn(1).setCellRenderer(centerRenderer);
			columnModel.getColumn(1).setPreferredWidth(60);
			columnModel.getColumn(2).setPreferredWidth(175);

			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = -7800178156661010679L;

				@Override public Dimension getPreferredSize() {
				    Dimension d = super.getPreferredSize();
				    d.height = 32;
				    return d;
				  }
				});
			table.setRowHeight(20);
			 DefaultRowSorter sorter = ((DefaultRowSorter)table.getRowSorter()); 
		        ArrayList list = new ArrayList();
		        list.add( new RowSorter.SortKey(0, SortOrder.DESCENDING));
		        sorter.setSortKeys(list);
		        sorter.sort();
		} catch (Exception continuewithNoSort) {
			//do nothing
		}
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(700, 600));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel_5.add(scroll, gbc_table);
		
		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_6.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 0;
		tabbedPane.addTab("Payment History", null, panel_6);//add(panel_5., gbc_panel_5);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[] { 0, 0 };
		gbl_panel_6.rowHeights = new int[] { 0, 0 };
		gbl_panel_6.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_6.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_6.setLayout(gbl_panel_6);
		
		JTable table2 = new JTable(model2);
		loadTransactions();
		try {
			table2.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table2.getColumnModel();
//			columnModel.getColumn(TOTAL_COL).setCellRenderer(rightRenderer);
//			columnModel.getColumn(PAID_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(2).setCellRenderer(rightRenderer);
//			columnModel.getColumn(0).setCellRenderer(centerRenderer);
//			columnModel.getColumn(0).setPreferredWidth(35);
//			columnModel.getColumn(1).setCellRenderer(centerRenderer);
//			columnModel.getColumn(1).setPreferredWidth(60);

			table2.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = -7800178156661010679L;

				@Override public Dimension getPreferredSize() {
				    Dimension d = super.getPreferredSize();
				    d.height = 32;
				    return d;
				  }
				});
			table2.setRowHeight(20);
			// DefaultRowSorter has the sort() method
	        DefaultRowSorter sorter = ((DefaultRowSorter)table2.getRowSorter()); 
	        ArrayList list = new ArrayList();
	        list.add( new RowSorter.SortKey(0, SortOrder.DESCENDING) );
	        sorter.setSortKeys(list);
	        sorter.sort();
		} catch (Exception continuewithNoSort) {
			//do nothing
		}
		JScrollPane scroll2 = new JScrollPane(table2);
		scroll2.setPreferredSize(new Dimension(700, 600));
		GridBagConstraints gbc_table2 = new GridBagConstraints();
		gbc_table2.fill = GridBagConstraints.BOTH;
		gbc_table2.gridx = 0;
		gbc_table2.gridy = 0;
		panel_6.add(scroll2, gbc_table2);
		
		
	}
	
	public void loadOrderSummary() {
		model.setRowCount(0);
		for (SalesOrderEntity order : list) {
			model.addRow(new Object[] { Utils.formatOrderId(order.getSalesOrderId()),
					Utils.formatOrderDate(order.getOrderDate()), ShippingStatus.values()[order.getShippingStatus()].toString()+", "+
					InvoiceStatus.values()[order.getInvoiceStatus()].toString()+", "+ PaymentStatus.values()[order.getPaymentStatus()].toString(), 
					Utils.currencyFormat(order.getTotal()), Utils.currencyFormat(order.getPaidAmount()), 
					Utils.currencyFormat(order.getRemainingBalance())});
		}
	}
	
	private void loadTransactions() {
		model2.setNumRows(0);
		for (SalesOrderEntity order : list) {
			Set<PaymentEntity> paymentDetails = order.getPaymentDetails();
			for(PaymentEntity entity : paymentDetails){
				model2.addRow(new Object[]{
						Utils.formatOrderDateTime(entity.getPaymentDate()), OrderPayStatus.values()[entity.getType()].toString() +
						Utils.formatOrderId(order.getSalesOrderId()),Utils.currencyFormat(entity.getAmount()) });
			}
		}
	}
}
