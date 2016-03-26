package gui;

import hibernate.PurchaseOrderDetailEntity;
import hibernate.PurchaseOrderEntity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import main.ComboItem;
import main.Constants;
import main.ContainerStatus;
import main.DateLabelFormatter;
import main.Utils;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import contoller.ManagePurchaseOrders;

@SuppressWarnings("rawtypes")
public class POUpdateDialog extends JDialog {
	private static final long serialVersionUID = 6593061153395951850L;
	private ManagePurchaseOrders managePurchaseOrders = new ManagePurchaseOrders();
	
	private PurchaseOrderEntity purchaseOrderEntity;
	private SortedSet<PurchaseOrderDetailEntity> entities;
	private JTextField quantityField;
	private JComboBox shippingStatusCombo;
	private boolean shippedFlag;
	
	private DefaultTableModel tableModel;
	private JDatePickerImpl arrivalDate;
	private UtilDateModel utilDateModel;
	private Frame parent;
	private boolean flag;
	private static int noOfExistingProducts = 0;
	
	private static final int PARTY_COL = 0;
	private static final int ITEM_COL = 1;
	private static final int QTY_COL = 2;
	private static final int DET_COL = 3;

	public POUpdateDialog(Frame parent, PurchaseOrderEntity purchaseOrderEntity) {
		super(parent);
		this.parent = parent;
		this.purchaseOrderEntity = purchaseOrderEntity;
		this.entities = purchaseOrderEntity.getOrderDetails();
		utilDateModel = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(utilDateModel);
		DateLabelFormatter formatter = new DateLabelFormatter();
		arrivalDate = new JDatePickerImpl(datePanel, formatter);
	}
	
	public boolean showDialog(){
		createLayout();

		setTitle("View/Update Purchase Order");
		setSize(700, 650);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
		return flag;
	}

	@SuppressWarnings({ "unchecked" })
	private void createLayout() {
		final Integer shippingStatus = purchaseOrderEntity.getShippingStatus();
		if(shippingStatus == ContainerStatus.ARRIVED.ordinal()){
			shippedFlag = true;
		}	
		
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 4059131234455130802L;

			@Override
			public boolean isCellEditable(int row, int column) {
					return false;
			}
		};

		for (int i = 0; i < Constants.purchaseOrderColumnNamesCust1.length; i++) {
			tableModel.addColumn(Constants.purchaseOrderColumnNamesCust1[i]);
		}
		
		JPanel panel = (JPanel) getContentPane();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 60, 420, 80 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		panel.setLayout(layout);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 204, 153));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 0, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		panel.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lblOrderNo = new JLabel("Lot # :");
		lblOrderNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderNo = new GridBagConstraints();
		gbc_lblOrderNo.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrderNo.anchor = GridBagConstraints.EAST;
		gbc_lblOrderNo.gridx = 0;
		gbc_lblOrderNo.gridy = 0;
		panel_4.add(lblOrderNo, gbc_lblOrderNo);

		final JTextField orderNo = new JTextField();
//		orderNo.setEditable(false);
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(5, 5, 0, 0);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		orderNo.setColumns(15);
		orderNo.setText(purchaseOrderEntity.getLotNo());
		panel_4.add(orderNo, gbc_textField_1);

		JLabel lblDate = new JLabel("Order Date:");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(5, 100, 0, 0);
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.gridx = 3;
		gbc_lblDate.gridy = 0;
		panel_4.add(lblDate, gbc_lblDate);

		JTextField orderDate = new JTextField();
		orderDate.setEditable(false);
		orderDate.setHorizontalAlignment(JTextField.CENTER);
		GridBagConstraints gbc_orderDate = new GridBagConstraints();
		gbc_orderDate.anchor = GridBagConstraints.WEST;
		gbc_orderDate.insets = new Insets(5, 5, 0, 5);
		gbc_orderDate.gridx = 4;
		gbc_orderDate.gridy = 0;
		orderDate.setColumns(15);
		orderDate.setText(Utils.formatOrderDate(purchaseOrderEntity.getOrderDate()));
		panel_4.add(orderDate, gbc_orderDate);

		JLabel lblOrderStatus = new JLabel("Order Status :");
		lblOrderStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderStatus = new GridBagConstraints();
		gbc_lblOrderStatus.anchor = GridBagConstraints.WEST;
		gbc_lblOrderStatus.insets = new Insets(5, 5, 0, 5);
		gbc_lblOrderStatus.gridx = 0;
		gbc_lblOrderStatus.gridy = 2;
		panel_4.add(lblOrderStatus, gbc_lblOrderStatus);

		shippingStatusCombo = new JComboBox();
		shippingStatusCombo.setPrototypeDisplayValue("012345678901234");
		shippingStatusCombo.addItem(new ComboItem(0, ContainerStatus.IN_TRANSIT.toString()));
		shippingStatusCombo.addItem(new ComboItem(1, ContainerStatus.ARRIVED.toString()));
		GridBagConstraints gbc_inventoryStatusCombo = new GridBagConstraints();
		gbc_inventoryStatusCombo.insets = new Insets(5, 5, 0, 10);
		gbc_inventoryStatusCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_inventoryStatusCombo.gridx = 1;
		gbc_inventoryStatusCombo.gridy = 2;
		
		if(shippedFlag)
			shippingStatusCombo.setEnabled(false);
		shippingStatusCombo.setSelectedIndex(shippingStatus);
		panel_4.add(shippingStatusCombo, gbc_inventoryStatusCombo);
		
		JLabel lblOrderDate = new JLabel("Arrival Date :");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
		gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
		gbc_lblOrderDate.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrderDate.gridx = 3;
		gbc_lblOrderDate.gridy = 1;
		panel_4.add(lblOrderDate, gbc_lblOrderDate);
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(5, 5, 0, 0);
		gbc_textField_3.anchor = GridBagConstraints.NORTHWEST;
		gbc_textField_3.gridx = 4;
		gbc_textField_3.gridy = 1;
		long date = purchaseOrderEntity.getArrivalDate();
		if(date > 0){
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(date);
			utilDateModel.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			utilDateModel.setSelected(true);
		}
		panel_4.add(arrivalDate, gbc_textField_3);
		
		JLabel lblQuantity = new JLabel("Total Quantity :");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblQuantity.insets = new Insets(5, 5, 0, 0);
		gbc_lblQuantity.gridx = 3;
		gbc_lblQuantity.gridy = 2;
		panel_4.add(lblQuantity, gbc_lblQuantity);

		quantityField = new JTextField();
		quantityField.setEditable(false);
		quantityField.setText(purchaseOrderEntity.getTotalItemQuantity().toString());
		quantityField.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityField.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.insets = new Insets(5, 5, 0, 0);
		gbc_quantityField.anchor = GridBagConstraints.NORTHWEST;
		gbc_quantityField.gridx = 4;
		gbc_quantityField.gridy = 2;
		quantityField.setColumns(15);
		panel_4.add(quantityField, gbc_quantityField);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 1;
		panel.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		final JTable table = new JTable(tableModel) {
			private static final long serialVersionUID = 1685102613631027828L;

			public Class getColumnClass(int column) {
	                switch (column) {
	                	case PARTY_COL:
	                		return ComboItem.class;
	                    case ITEM_COL:
	                        return ComboItem.class;
	                    case DET_COL:
	                    	return ImageIcon.class;
	                    default:
	                        return Integer.class;
	                }
	            }
		};

		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setCellSelectionEnabled(true);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		try {
			TableColumnModel columnModel = table.getColumnModel();
			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = -3558764280123378094L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table.setRowHeight(20);
			columnModel.getColumn(DET_COL).setPreferredWidth(25);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		final Vector data = tableModel.getDataVector();
		
		tableModel.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				int index = e.getColumn();
				switch (e.getType()) {
				case TableModelEvent.UPDATE:
					if (index == QTY_COL) {
						reCalculate();
					} else {
						// do nothing
					}
					break;
				case TableModelEvent.INSERT:
					reCalculate();
					break;	
				case TableModelEvent.DELETE:
					reCalculate();
					break;		
				default:
					break;
				}
			}

			private void reCalculate() {
				Integer total = 0;
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					total += (Integer) tableModel.getValueAt(i, QTY_COL);
				}
				quantityField.setText(total.toString());
			}
		});
		
		loadOrderDetails();
		noOfExistingProducts = tableModel.getRowCount();
		
		final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Remove");
        popupMenu.add(deleteItem);

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel_5.add(scroll, gbc_table);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.anchor = GridBagConstraints.WEST;
		gbc_panel_6.insets = new Insets(0, 0, 0, 5);
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		panel_5.add(panel_6, gbc_panel_6);

		if(!shippedFlag){
			JButton btnNewButton = new JButton("");
			btnNewButton.setBorderPainted(false);
			btnNewButton.setIcon(Utils.getImageIcon("add.png"));
			btnNewButton.setVerticalAlignment(SwingConstants.TOP);
			btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
			panel_6.add(btnNewButton);
			
			btnNewButton.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					POAddProductDialog productFormDialog = new POAddProductDialog(parent);
					Set<PurchaseOrderDetailEntity> showDialog = productFormDialog.showDialog();
					if(!showDialog.isEmpty()){
						PurchaseOrderDetailEntity detail = showDialog.iterator().next();
						tableModel.addRow(new Object[]{new ComboItem(detail.getPartyId(), detail.getPartyName()), 
								new ComboItem(detail.getItemId(), detail.getItemName()), detail.getItemTotalQuantity(), Utils.getImageIcon("DET.png")});
						entities.addAll(showDialog);
					}
				}
			});
	
		}

		final JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(255, 204, 153));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.anchor = GridBagConstraints.NORTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 2;
		panel.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] {0};
		gbl_panel_7.rowHeights = new int[] { 0};
		gbl_panel_7.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		JButton btnUpdateOrder = new JButton("Update Order");
		btnUpdateOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
		gbc_btnSaveOrder.anchor = GridBagConstraints.CENTER;
		gbc_btnSaveOrder.fill = GridBagConstraints.NONE;
		gbc_btnSaveOrder.insets = new Insets(5, 10, 0, 0);
		gbc_btnSaveOrder.gridx = 0;
		gbc_btnSaveOrder.gridy = 0;
		if(!shippedFlag)
			panel_7.add(btnUpdateOrder, gbc_btnSaveOrder);
		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable) e.getSource();
					int rowNo = target.getSelectedRow();
					int columnNo = target.getSelectedColumn();
					switch (columnNo) {
					case DET_COL:
						Vector row = (Vector) data.elementAt(rowNo);
						ComboItem party = (ComboItem) row.get(PARTY_COL);
						ComboItem item = (ComboItem) row.get(ITEM_COL);
						Integer quantity = (Integer) row.get(QTY_COL);
						POProductUpdateDialog updateDialog = new POProductUpdateDialog(parent, entities, party.getValue(),
								item.getKey(), item.getValue(), quantity, shippingStatus);
						entities = updateDialog.showDialog();
						Integer newQuantity = 0;
						for(PurchaseOrderDetailEntity entity : entities){
							if(entity.getItemId() == item.getKey()){
								newQuantity = entity.getItemTotalQuantity();
								break;
							}
						}
						if(quantity != newQuantity)
							tableModel.setValueAt(newQuantity, rowNo, QTY_COL); 
						break;
					}
				}
			}

			public void mousePressed(MouseEvent event){
				Point point = event.getPoint();
				int currentRow = table.rowAtPoint(point);
				table.setRowSelectionInterval(currentRow, currentRow);
				if(SwingUtilities.isRightMouseButton(event)){
					if(currentRow >= noOfExistingProducts){
						popupMenu.show(table.getTableHeader(), event.getX(), event.getY());
					}
				} 
			}
		});
		
        deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				Vector dataVector = tableModel.getDataVector();
				Vector row = (Vector) dataVector.elementAt(selectedRow);
				ComboItem item = (ComboItem) row.elementAt(ITEM_COL);
				removeItemDetailsForItem(item.getKey());
				tableModel.removeRow(selectedRow);
			}

			private void removeItemDetailsForItem(long itemId) {
				Iterator<PurchaseOrderDetailEntity> iterator = entities.iterator();
				while(iterator.hasNext()){
					PurchaseOrderDetailEntity entity = iterator.next();
					if(entity.getItemId() == itemId){
						iterator.remove();
					}
				}
			}
		});
        
        btnUpdateOrder.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String lotname = orderNo.getText();
				if(lotname.trim().length() == 0){
					String message = "Lot number cannot be empty !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else if(isNameChanged() && managePurchaseOrders.isLotNumberExists(lotname)){
					String message = "Lot number already exists !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else if(arrivalDate.getModel().getValue() == null){
					String message = "Enter Arrival Date !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else if(!entities.isEmpty()){
					if (!checkforDuplicate()) {
						long currentTimeMillis = System.currentTimeMillis();
						Date date = (Date) arrivalDate.getModel().getValue();
						Long arrvDate = Utils.getDateInMillies(date);

						boolean shipped = false;
						int selectedShippingStatus = shippingStatusCombo.getSelectedIndex();
						if(selectedShippingStatus == ContainerStatus.ARRIVED.ordinal() && !shippedFlag){
							shipped = true;
							purchaseOrderEntity.setShippingStatus(selectedShippingStatus);
						}
						purchaseOrderEntity.setLotNo(lotname);
						purchaseOrderEntity.setOrderDetails(entities);
						purchaseOrderEntity.setArrivalDate(arrvDate);
						purchaseOrderEntity.setTotalItemQuantity(Integer.parseInt(quantityField.getText()));
						purchaseOrderEntity.setUpdatedOn(currentTimeMillis);
						
						boolean updatePurchaseOrder = managePurchaseOrders.updatePurchaseOrder(purchaseOrderEntity, shipped);
						if (updatePurchaseOrder) {
							String message = "Purchase Order updated successfully !";
							JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
							flag = true;
							hideDialog();
						} else {
							String message = "Error while updating purchase order !";
							JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(new JFrame(),
								"Duplicate item name/code !",
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}	
				}
			}

			private boolean isNameChanged() {
				return !(orderNo.getText().equalsIgnoreCase(purchaseOrderEntity.getLotNo()));
			}
		});
        
	}

	private void loadOrderDetails() {
		tableModel.setNumRows(0);
		List<Long> itemIds = new ArrayList<Long>();
		for(PurchaseOrderDetailEntity detail : entities){
			if(!itemIds.contains(detail.getItemId())){
				tableModel.addRow(new Object[] {new ComboItem(detail.getPartyId(), detail.getPartyName()), 
						new ComboItem(detail.getItemId(), detail.getItemName()), detail.getItemTotalQuantity(), Utils.getImageIcon("DET.png")});
				itemIds.add(detail.getItemId());
			}
		}
	}
	
	private boolean checkforDuplicate() {
		boolean flag = false;
		Vector dataVector = tableModel.getDataVector();
		int rowCount = tableModel.getRowCount();
		List<Object> names = new ArrayList<Object>();
		for (int i = 0; i < rowCount; i++) {
			Vector row = (Vector) dataVector.elementAt(i);
			ComboItem party = (ComboItem) row.elementAt(PARTY_COL);
			ComboItem item = (ComboItem) row.elementAt(ITEM_COL);
			if (names.contains(party.getValue()+item.getValue())) {
				flag = true;
				break;
			} else {
				names.add(party.getValue()+item.getValue());
			}
		}
		return flag;
	}
	
	public void hideDialog(){
		this.setVisible(false);
		this.dispose();
	}
}
