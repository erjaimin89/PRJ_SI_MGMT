package gui;

import hibernate.PurchaseOrderEntity;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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

import main.Constants;
import main.ContainerStatus;
import main.DateLabelFormatter;
import main.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import contoller.ManagePurchaseOrders;

public class POListPanel extends JPanel {

	private static final long serialVersionUID = 3361996908846325279L;
	private DefaultTableModel model;
	
	private static final int LOT_NO = 0;
	private static final int DATE_COL = 1;
	private static final int ARR_DATE_COL = 2;
	private static final int TOTAL_COL = 4;
	private static final int DET_COL = 5;
	private static final int CANCEL_COL = 6;
	private static final int ORDER_ID_COL = 7;
	
	private List<PurchaseOrderEntity> list;
	private JDatePickerImpl datePickerFrom;
	private JDatePickerImpl datePickerTo;
	
	private static ManagePurchaseOrders managePurchaseOrders = new ManagePurchaseOrders();
	
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
	
	public POListPanel() {
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(new UtilDateModel());
		JDatePanelImpl datePanelTo = new JDatePanelImpl(new UtilDateModel());
		DateLabelFormatter formatter = new DateLabelFormatter();
		datePickerFrom = new JDatePickerImpl(datePanelFrom, formatter);
		datePickerTo = new JDatePickerImpl(datePanelTo, formatter);

		setBackground(new Color(255, 204, 153));
		setBorder(new TitledBorder(null, "Purchase Order List", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.BLACK));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0 };
		layout.rowHeights = new int[] { 0 };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0 };
		setLayout(layout);
		
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 3380584284274833109L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case DET_COL:
					return ImageIcon.class;
				case CANCEL_COL:
					return ImageIcon.class;	
				case TOTAL_COL:
					return Integer.class;
				case ORDER_ID_COL:
					return Long.class;
				case DATE_COL:
					return Date.class;	
				case ARR_DATE_COL:
					return Date.class;	
				default:
					return String.class;
				}
			}
		};

		for (int i = 0; i < Constants.purchaseOrderListColumnNames.length; i++) {
			model.addColumn(Constants.purchaseOrderListColumnNames[i]);
		}
		
		setLayoutComponents();
	}

	@SuppressWarnings("rawtypes")
	private void setLayoutComponents() {
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.setBackground(new Color(255, 204, 153));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel_1);
		
		JLabel lblOrder = new JLabel("Lot # :");
		lblOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrder = new GridBagConstraints();
		gbc_lblOrder.anchor = GridBagConstraints.EAST;
		gbc_lblOrder.insets = new Insets(5, 5, 5, 5);
		gbc_lblOrder.gridx = 0;
		gbc_lblOrder.gridy = 0;
		panel.add(lblOrder, gbc_lblOrder);
		
		final JTextField orderNoField = new JTextField();
		orderNoField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(5, 5, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridwidth=2;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel.add(orderNoField, gbc_textField_2);
		orderNoField.setColumns(10);
		
		JLabel lblOrderDate = new JLabel("Order Date :");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
		gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
		gbc_lblOrderDate.insets = new Insets(5, 5, 5, 5);
		gbc_lblOrderDate.gridx = 0;
		gbc_lblOrderDate.gridy = 1;
		panel.add(lblOrderDate, gbc_lblOrderDate);
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(5, 5, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 1;
		panel.add(datePickerFrom, gbc_textField_3);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.anchor = GridBagConstraints.EAST;
		gbc_lblTo.insets = new Insets(5, 5, 5, 5);
		gbc_lblTo.gridx = 2;
		gbc_lblTo.gridy = 1;
		panel.add(lblTo, gbc_lblTo);
		
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(5, 5, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 1;
		panel.add(datePickerTo, gbc_textField_4);
		
		JButton serachOrder = new JButton("Search");
		serachOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
//		serachOrder.setIcon(Utils.getImageIcon("search.png"));
		serachOrder.setMnemonic(KeyEvent.VK_ENTER);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(7, 0, 5, 0);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 2;
		panel.add(serachOrder, gbc_btnSearch);
		
		JButton resetButton = new JButton("Reset");
		resetButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.insets = new Insets(7, 0, 5, 0);
		gbc_btnReset.gridx = 2;
		gbc_btnReset.gridy = 2;
		panel.add(resetButton, gbc_btnReset);
		
		JTable table = new JTable(model);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		try {
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(LOT_NO).setCellRenderer(centerRenderer);
			columnModel.getColumn(LOT_NO).setPreferredWidth(60);
			columnModel.getColumn(DATE_COL).setCellRenderer(centerRenderer);
			columnModel.getColumn(DATE_COL).setPreferredWidth(60);
			columnModel.getColumn(ARR_DATE_COL).setCellRenderer(centerRenderer);
			columnModel.getColumn(ARR_DATE_COL).setPreferredWidth(60);
			columnModel.getColumn(TOTAL_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(DET_COL).setPreferredWidth(25);
			columnModel.getColumn(CANCEL_COL).setPreferredWidth(25);
			columnModel.getColumn(ORDER_ID_COL).setPreferredWidth(0);
			columnModel.getColumn(ORDER_ID_COL).setWidth(0);
			columnModel.getColumn(ORDER_ID_COL).setMinWidth(0);
			columnModel.getColumn(ORDER_ID_COL).setMaxWidth(0);
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
					long orderNo = (Long) row.get(ORDER_ID_COL);
					switch (columnNo) {
					case DET_COL:
						int action = JOptionPane.showConfirmDialog(
								POListPanel.this,
								"Do you really want to update Order #" + orderNo
								+ "?", "Update Purchase Order",
								JOptionPane.OK_CANCEL_OPTION);
						if (action == JOptionPane.OK_OPTION) {
							    POUpdateDialog poUpdateDialog = new POUpdateDialog(parent, findOrderFromList(orderNo));
							    boolean showDialog = poUpdateDialog.showDialog();
							    if(showDialog)
							    	refreshModel(null);
						}
						break;
					case CANCEL_COL:
						ImageIcon icon = (ImageIcon) row.get(CANCEL_COL);
						if(icon != null){
							int action1 = JOptionPane.showConfirmDialog(
									POListPanel.this,
									"Do you really want to remove/cancel lot #" + orderNo
									+ "? Note: This action will affect the quantity in transit.", "Remove/Cancel Order",
									JOptionPane.OK_CANCEL_OPTION);
							if (action1 == JOptionPane.OK_OPTION) {
								boolean cancelOrder = managePurchaseOrders.cancelPurchaseOrder(orderNo);
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
						orderNoField.setText("");
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
					refreshModel(managePurchaseOrders.listOrdersBySearch(orderNoField.getText(), fromDate, toDate));
				}
				
			}
		});
	}

	public void refreshModel(List<PurchaseOrderEntity> modified) {
		model.setRowCount(0);
		list = new ArrayList<PurchaseOrderEntity>();
		
		if(modified!= null)
			list = modified;
		else
			list = managePurchaseOrders.listOrders();
		
		for (PurchaseOrderEntity entity : list) {
			ContainerStatus containerStatus = ContainerStatus.values()[entity.getShippingStatus()];
			ImageIcon icon = null;
			if(containerStatus.equals(ContainerStatus.IN_TRANSIT)){
				icon = Utils.getImageIcon("cancel.png");
			}
			model.addRow(new Object[] {entity.getLotNo(),
					Utils.formatOrderDate(entity.getOrderDate()), Utils.formatOrderDate(entity.getArrivalDate()), containerStatus, entity.getTotalItemQuantity(),
					Utils.getImageIcon("edit.png"),icon, entity.getPurchaseOrderId()});
		}
	}
	
	private PurchaseOrderEntity findOrderFromList(long orderNo) {
		for(PurchaseOrderEntity entity: list){
			if(entity.getPurchaseOrderId()== orderNo){
				return entity;
			}
		}
		return null;
	}

}
