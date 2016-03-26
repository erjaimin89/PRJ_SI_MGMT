package gui;

import hibernate.PurchaseOrderDetailEntity;
import hibernate.PurchaseOrderEntity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JButton;
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
import javax.swing.border.TitledBorder;
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
public class POFormPanel extends JPanel {

	private static final long serialVersionUID = 1437871935384471789L;
	
	private static final int PARTY_COL = 0;
	private static final int ITEM_COL = 1;
	private static final int TOTAL_QTY_COL = 2;
	
	private JTextField quantityField;
	private JTextField orderNo;
//	private JComboBox shippingStatusCombo;
	
	private JDatePickerImpl arrivalDate;
	
	private ManagePurchaseOrders managePurchaseOrders = new ManagePurchaseOrders();
	
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
	
	private DefaultTableModel tableModel;
	
	private TreeSet<PurchaseOrderDetailEntity> itemDetails;

	public POFormPanel() {
		setBorder(new TitledBorder(null, "New Purchase Order",
				TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma",
						Font.BOLD, 14), Color.BLACK));
		setBackground(new Color(255, 204, 153));
		
		JDatePanelImpl datePanel = new JDatePanelImpl(new UtilDateModel());
		DateLabelFormatter formatter = new DateLabelFormatter();
		arrivalDate = new JDatePickerImpl(datePanel, formatter);
		
		itemDetails = new TreeSet<PurchaseOrderDetailEntity>();
		
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 90, 420, 100 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		setLayout(layout);
		
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 1326513146623476842L;

			@Override
			public boolean isCellEditable(int row, int column) {
					return false;
			}
		};

		for (int i = 0; i < Constants.purchaseOrderColumnNamesCust.length; i++) {
			tableModel.addColumn(Constants.purchaseOrderColumnNamesCust[i]);
		}

		setLayoutComponents();
	}

	@SuppressWarnings({ "unchecked" })
	private void setLayoutComponents() {
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(255, 204, 153));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(0, 0, 0, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 0;
		add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		JLabel lblOrderNo = new JLabel("Order/Lot # :");
		lblOrderNo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderNo = new GridBagConstraints();
		gbc_lblOrderNo.insets = new Insets(5, 5, 5, 0);
		gbc_lblOrderNo.anchor = GridBagConstraints.EAST;
		gbc_lblOrderNo.gridx = 0;
		gbc_lblOrderNo.gridy = 0;
		panel_4.add(lblOrderNo, gbc_lblOrderNo);

		orderNo = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.anchor = GridBagConstraints.WEST;
		gbc_textField_1.insets = new Insets(5, 5, 5, 5);
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 0;
		orderNo.setColumns(15);
		panel_4.add(orderNo, gbc_textField_1);

		JLabel lblDate = new JLabel("Order Date:");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(5, 200, 5, 0);
		gbc_lblDate.anchor = GridBagConstraints.EAST;
		gbc_lblDate.gridx = 3;
		gbc_lblDate.gridy = 0;
		panel_4.add(lblDate, gbc_lblDate);

		JTextField orderDate = new JTextField();
		orderDate.setEditable(false);
		orderDate.setHorizontalAlignment(JTextField.CENTER);
		GridBagConstraints gbc_orderDate = new GridBagConstraints();
		gbc_orderDate.anchor = GridBagConstraints.WEST;
		gbc_orderDate.insets = new Insets(5, 5, 5, 5);
		gbc_orderDate.gridx = 4;
		gbc_orderDate.gridy = 0;
		orderDate.setColumns(15);
		orderDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		panel_4.add(orderDate, gbc_orderDate);

		JLabel lblOrderDate = new JLabel("Arrival Date :");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
		gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
		gbc_lblOrderDate.insets = new Insets(5, 5, 0, 0);
		gbc_lblOrderDate.gridx = 0;
		gbc_lblOrderDate.gridy = 1;
		panel_4.add(lblOrderDate, gbc_lblOrderDate);
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(5, 5, 0, 0);
		gbc_textField_3.anchor = GridBagConstraints.WEST;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 1;
		panel_4.add(arrivalDate, gbc_textField_3);
		
		JLabel lblQuantity = new JLabel("Total Quantity :");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.EAST;
		gbc_lblQuantity.insets = new Insets(5, 5, 0, 0);
		gbc_lblQuantity.gridx = 3;
		gbc_lblQuantity.gridy = 1;
		panel_4.add(lblQuantity, gbc_lblQuantity);

		quantityField = new JTextField();
		quantityField.setEditable(false);
		quantityField.setText("0");
		quantityField.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityField.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.insets = new Insets(5, 5, 0, 0);
		gbc_quantityField.anchor = GridBagConstraints.WEST;
		gbc_quantityField.gridx = 4;
		gbc_quantityField.gridy = 1;
		quantityField.setColumns(15);
		panel_4.add(quantityField, gbc_quantityField);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 1;
		add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0 };
		gbl_panel_5.rowHeights = new int[] { 0, 0 };
		gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);

		final JTable table = new JTable(tableModel) {
			private static final long serialVersionUID = -3446566835307259308L;

			@SuppressWarnings({ })
			public Class getColumnClass(int column) {
	                switch (column) {
	                	case PARTY_COL:
	                		return ComboItem.class;
	                    case ITEM_COL:
	                        return ComboItem.class;
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
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = -8218499240746675110L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table.setRowHeight(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Remove");
        popupMenu.add(deleteItem);
        table.setComponentPopupMenu(popupMenu);

        deleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				Vector dataVector = tableModel.getDataVector();
				Vector row = (Vector) dataVector.elementAt(selectedRow);
				ComboItem item = (ComboItem) row.elementAt(ITEM_COL);
				removeItemDetailsForItem(item.getKey());
				Integer quantity = Integer.parseInt(quantityField.getText().toString()) - Integer.parseInt(row.get(TOTAL_QTY_COL).toString());
				quantityField.setText(quantity.toString());
				tableModel.removeRow(selectedRow);
			}

			private void removeItemDetailsForItem(long itemId) {
				Iterator<PurchaseOrderDetailEntity> iterator = itemDetails.iterator();
				while(iterator.hasNext()){
					PurchaseOrderDetailEntity entity = iterator.next();
					if(entity.getItemId() == itemId){
						iterator.remove();
					}
				}
			}
		});
        
        table.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent event){
        		Point point = event.getPoint();
                int currentRow = table.rowAtPoint(point);
                table.setRowSelectionInterval(currentRow, currentRow);
        	}
		});

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
					PurchaseOrderDetailEntity next = showDialog.iterator().next();
					tableModel.addRow(new Object[]{new ComboItem(next.getPartyId(), next.getPartyName()), new ComboItem(next.getItemId(), next.getItemName()), next.getItemTotalQuantity() });
					Integer quantity = Integer.parseInt(quantityField.getText().toString()) + next.getItemTotalQuantity();
					quantityField.setText(quantity.toString());
					itemDetails.addAll(showDialog);
				}
			}
		});
		

		final JPanel panel_7 = new JPanel();
		panel_7.setBackground(new Color(255, 204, 153));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.anchor = GridBagConstraints.NORTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 2;
		add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] {0};
		gbl_panel_7.rowHeights = new int[] { 0};
		gbl_panel_7.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		JButton btnSaveOrder = new JButton("Save Order");
		btnSaveOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
		gbc_btnSaveOrder.anchor = GridBagConstraints.CENTER;
		gbc_btnSaveOrder.fill = GridBagConstraints.NONE;
		gbc_btnSaveOrder.insets = new Insets(5, 10, 0, 0);
		gbc_btnSaveOrder.gridx = 0;
		gbc_btnSaveOrder.gridy = 0;
		panel_7.add(btnSaveOrder, gbc_btnSaveOrder);
		
		btnSaveOrder.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(orderNo.getText().trim().length() == 0){
					String message = "Lot number cannot be empty !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else if(managePurchaseOrders.isLotNumberExists(orderNo.getText())){
					String message = "Lot number already exists !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else if(arrivalDate.getModel().getValue() == null){
					String message = "Enter Arrival Date !";
					JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
				}else{
					if(!itemDetails.isEmpty()){
						if (!checkforDuplicate()) {
//							boolean shipped = false;
							long currentTimeMillis = System.currentTimeMillis();
							long orderDate = Utils.getDateInMillies(new Date());
							Date date = (Date) arrivalDate.getModel().getValue();
							Long arrvDate = Utils.getDateInMillies(date);

//							int selectedShippingStatus = shippingStatusCombo.getSelectedIndex();
							
//							if(selectedShippingStatus == ContainerStatus.ARRIVED.ordinal())
//								shipped = true;
							
							boolean addPurchaseOrder = managePurchaseOrders.savePurchaseOrder(new PurchaseOrderEntity(orderNo.getText(), ContainerStatus.IN_TRANSIT.ordinal(), 
									Integer.parseInt(quantityField.getText()), arrvDate, orderDate, currentTimeMillis, itemDetails));
							if (addPurchaseOrder) {
								String message = "Purchase Order saved successfully !";
								JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.INFORMATION_MESSAGE);
								refreshForm();
							} else {
								String message = "Error while saving purchase order !";
								JOptionPane.showMessageDialog(new JFrame(),	message, "Dialog", JOptionPane.ERROR_MESSAGE);
							}
						}else{
							JOptionPane.showMessageDialog(new JFrame(),
									"Duplicate item name/code !",
									"Dialog", JOptionPane.ERROR_MESSAGE);
						}	
					}else{
						JOptionPane.showMessageDialog(new JFrame(),
								"Add at least an item to create purchase order!",
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}
				}	
			}
		});
		
	}
	
	public void refreshForm() {
		quantityField.setText("0");
		orderNo.setText("");
		arrivalDate.getModel().setValue(null);
		itemDetails.clear();
//		shippingStatusCombo.setSelectedIndex(0);
		clearTable();
	}

	private void clearTable() {
		tableModel.setNumRows(0);
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
	
	public void focusTextField() {
		orderNo.requestFocusInWindow();
	}
	
	
}
