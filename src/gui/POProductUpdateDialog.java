package gui;

import hibernate.PurchaseOrderDetailEntity;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.SortedSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import main.ComboItem;
import main.Constants;
import main.ContainerStatus;
import main.IntegerEditor;

public class POProductUpdateDialog extends JDialog {
	private static final long serialVersionUID = 6904745643789849122L;
	
	private SortedSet<PurchaseOrderDetailEntity> entities;
	private long itemNo;
	private String itemName;
	private String partyName;
	private Integer quantity;
	private Integer shippingStatus;
	
	private DefaultTableModel tableModel;
	private JTextField partyNameField;
	private JTextField itemNameField;
	private JTextField quantityField;
	private static final int columnNos = 9;
	private static final int DETAIL_COL = 0;
	private static final int SUB_TOT_COL = 9;
	
	public POProductUpdateDialog(Frame parent, SortedSet<PurchaseOrderDetailEntity> entities, String partyName,long itemNo, String itemName, Integer quantity, Integer shippingStatus) {
		super(parent);
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.partyName = partyName;
		this.quantity = quantity;
		this.entities = entities;
		this.shippingStatus = shippingStatus;
	}
	
	public SortedSet<PurchaseOrderDetailEntity> showDialog() {
		createLayout();

		setTitle("View/Update Item Detail/quantity");
		setSize(600, 550);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
		return entities;
	}

	private void createLayout() {
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -3576962607922541896L;

			@Override
			public boolean isCellEditable(int row, int column) {
				 if(column == DETAIL_COL || column == SUB_TOT_COL || shippingStatus == ContainerStatus.ARRIVED.ordinal())
					 return false;
				 return true;
			}
		};

		for (int i = 0; i < Constants.productDialogColumnNames.length; i++) {
			tableModel.addColumn(Constants.productDialogColumnNames[i]);
		}
		
		JPanel panel = (JPanel) getContentPane();
		panel.setBackground(new Color(176, 224, 230));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 50, 400, 50 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		panel.setLayout(layout);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(176, 224, 230));
		panel_3.setLayout(new GridBagLayout());
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		panel.add(panel_3, gbc_panel_2);
		
		JLabel lblParty = new JLabel("Party Name :");
		lblParty.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblParty = new GridBagConstraints();
		gbc_lblParty.insets = new Insets(5, 5, 0, 0);
		gbc_lblParty.anchor = GridBagConstraints.EAST;
		gbc_lblParty.gridx = 0;
		gbc_lblParty.gridy = 0;
		panel_3.add(lblParty, gbc_lblParty);
		
		partyNameField = new JTextField();
		partyNameField.setText(partyName);
		partyNameField.setEditable(false);
		partyNameField.setColumns(10);
		GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
		gbc_orderByCombo.insets = new Insets(5, 5, 5, 5);
		gbc_orderByCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderByCombo.gridx = 1;
		gbc_orderByCombo.gridy = 0;
		panel_3.add(partyNameField, gbc_orderByCombo);
		
		JLabel lblItemName = new JLabel("Item Name/Code :");
		lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblCustomerName.insets = new Insets(5, 5, 0, 0);
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 1;
		panel_3.add(lblItemName, gbc_lblCustomerName);

		itemNameField = new JTextField();
		itemNameField.setEditable(false);
		GridBagConstraints gbc_itemcombo = new GridBagConstraints();
		gbc_itemcombo.insets = new Insets(5, 5, 0, 0);
		gbc_itemcombo.anchor = GridBagConstraints.NORTHWEST;
		gbc_itemcombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemcombo.gridx = 1;
		gbc_itemcombo.gridy = 1;
		itemNameField.setText(itemName);
		itemNameField.setColumns(10);
		panel_3.add(itemNameField, gbc_itemcombo);

		JLabel lblQuantity = new JLabel("Total Quantity :");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblQuantity.insets = new Insets(5, 5, 0, 0);
		gbc_lblQuantity.gridx = 2;
		gbc_lblQuantity.gridy = 0;
		panel_3.add(lblQuantity, gbc_lblQuantity);

		quantityField = new JTextField();
		quantityField.setEditable(false);
		quantityField.setText(quantity.toString());
		quantityField.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityField.setHorizontalAlignment(JTextField.RIGHT);
		quantityField.setColumns(10);
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.insets = new Insets(5, 5, 0, 0);
		gbc_quantityField.anchor = GridBagConstraints.NORTHWEST;
		gbc_quantityField.fill = GridBagConstraints.HORIZONTAL;
		gbc_quantityField.gridx = 3;
		gbc_quantityField.gridy = 0;
		panel_3.add(quantityField, gbc_quantityField);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(176, 224, 230));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		final JTable table = new JTable(tableModel){
			private static final long serialVersionUID = 7922598932152161904L;

			@Override
			// Always selectAll()
			public boolean editCellAt(int row, int column, EventObject e) {
				boolean result = super.editCellAt(row, column, e);
				final Component editor = getEditorComponent();
				if (editor == null || !(editor instanceof JTextComponent)) {
					return result;
				}
				if (e instanceof MouseEvent) {
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							((JTextComponent) editor).selectAll();
						}
					});
				} else {
					((JTextComponent) editor).selectAll();
				}
				return result;
			}
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public Class getColumnClass(int column) {
	                switch (column) {
	                    case DETAIL_COL:
	                        return String.class;
	                    default:
	                        return Integer.class;
	                }
	            }
		};
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

		table.setCellSelectionEnabled(true);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer font = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 8636358833928943958L;
			Font font = new Font("Tahoma", Font.BOLD, 12);

		    @Override
		    public Component getTableCellRendererComponent(JTable table,
		            Object value, boolean isSelected, boolean hasFocus,
		            int row, int column) {
		        super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
		                row, column);
		        setFont(font);
		        return this;
		    }

		};
		font.setHorizontalAlignment(JLabel.RIGHT);
		try {
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = 3751001455383701578L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table.setRowHeight(20);
			columnModel.getColumn(DETAIL_COL).setPreferredWidth(100);
			for (int i = 1; i < columnNos; i++) {
				columnModel.getColumn(i).setCellEditor(new IntegerEditor(0, Integer.MAX_VALUE));
				columnModel.getColumn(i).setCellRenderer(rightRenderer);
				columnModel.getColumn(i).setPreferredWidth(25);
			}
			columnModel.getColumn(SUB_TOT_COL).setPreferredWidth(50);
			columnModel.getColumn(SUB_TOT_COL).setCellRenderer(font);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loadItemDetails();

		
		tableModel.addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				int index = e.getColumn();

				switch (e.getType()) {
				case TableModelEvent.UPDATE:
					if (index >= 1 && index <= 8) {
						Integer total = 0;
						for (int i = 0; i < tableModel.getRowCount(); i++) {
							Integer rowCount = 0;
							for (int j = 1; j < columnNos; j++) {
								rowCount += (Integer) tableModel.getValueAt(i, j);
							}
							tableModel.setValueAt(rowCount, i, SUB_TOT_COL);
							total += rowCount;
						}
						quantityField.setText(total.toString());
					} else {
						// do nothing
					}
					break;
				default:
					break;
				}
			}
		});

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(500, 350));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel_4.add(scroll, gbc_table);
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.NONE;
		gbc_panel_7.anchor = GridBagConstraints.NORTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 2;
		panel.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 0 };
		gbl_panel_7.rowHeights = new int[] { 0 };
		gbl_panel_7.columnWeights = new double[] { 1.0 };
		gbl_panel_7.rowWeights = new double[] { 1.0 };
		panel_7.setLayout(gbl_panel_7);

		JButton btnUpdateProduct = new JButton("Enter");
		btnUpdateProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveProduct = new GridBagConstraints();
		gbc_btnSaveProduct.anchor = GridBagConstraints.CENTER;
		gbc_btnSaveProduct.gridx = 0;
		gbc_btnSaveProduct.gridy = 0;
		if(shippingStatus == ContainerStatus.IN_TRANSIT.ordinal())
			panel_7.add(btnUpdateProduct, gbc_btnSaveProduct);
		
		btnUpdateProduct.addActionListener(new ActionListener() {
			
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e) {
					Vector dataVector = tableModel.getDataVector();
					for(int i=0; i< tableModel.getRowCount(); i++){
						Vector row = (Vector) dataVector.elementAt(i);
						ComboItem detail = (ComboItem)row.get(DETAIL_COL);
						for(PurchaseOrderDetailEntity entity : entities){
							if((entity.getItemId() == itemNo) && (entity.getItemDetailId() == detail.getKey())){
								entity.setXsmall(Integer.parseInt(row.elementAt(1).toString()));
								entity.setSmall(Integer.parseInt(row.elementAt(2).toString()));
								entity.setMedium(Integer.parseInt(row.elementAt(3).toString()));
								entity.setLarge(Integer.parseInt(row.elementAt(4).toString()));
								entity.setXlarge(Integer.parseInt(row.elementAt(5).toString()));
								entity.setXxlarge(Integer.parseInt(row.elementAt(6).toString()));
								entity.setXxxlarge(Integer.parseInt(row.elementAt(7).toString()));
								entity.setMix(Integer.parseInt(row.elementAt(8).toString()));
								entity.setDetailTotalQuantity(Integer.parseInt(row.elementAt(SUB_TOT_COL).toString()));
								entity.setItemTotalQuantity(Integer.parseInt(quantityField.getText()));
								break;
							}
						}
					}
					hideDialog();
			}
		});
	}
	
	private void loadItemDetails() {
			tableModel.setNumRows(0);
			for(PurchaseOrderDetailEntity detail : entities){
				if(detail.getItemId() == itemNo){
					tableModel.addRow(new Object[] {new ComboItem(detail.getItemDetailId(), detail.getDetailName()), detail.getXsmall(), detail.getSmall(),
							detail.getMedium(), detail.getLarge(), detail.getXlarge(), detail.getXxlarge(), detail.getXxxlarge(),
							detail.getMix(), detail.getDetailTotalQuantity()});
				}
			}
	}

	public void hideDialog(){
		this.setVisible(false);
		this.dispose();
	}

}
