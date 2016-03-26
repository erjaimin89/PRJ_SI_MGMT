package gui;

import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.PurchaseOrderDetailEntity;
import hibernate.UserEntity;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
import main.IntegerEditor;
import main.UserType;
import main.Utils;
import contoller.ManageProducts;
import contoller.ManageUsers;

@SuppressWarnings("rawtypes")
public class POAddProductDialog extends JDialog {

	private static final long serialVersionUID = -2041915144342985155L;
	
	private static ManageProducts manageProducts = new ManageProducts();
	
	private List<ProductEntity> list;
	private Set<ProductDetailEntity> details;
	
	private Set<PurchaseOrderDetailEntity> detailEntitites;
	
	protected static boolean loadItems;
	
	private DefaultTableModel tableModel;

	private JComboBox partyCombo;
	private JTextComponent itemEditor;
	private JTextField quantityField;
	private static final int columnNos = 9;
	private static final int DETAIL_COL = 0;
	private static final int SUB_TOT_COL = 9;

	public POAddProductDialog(Frame parent) {
		super(parent);
		
	}
	
	public Set<PurchaseOrderDetailEntity> showDialog(){
		createLayout();
		
		detailEntitites = new HashSet<PurchaseOrderDetailEntity>();

		setTitle("Item/Detail quantity");
		setSize(700, 600);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
		return detailEntitites;
	}

	@SuppressWarnings({ "unchecked" })
	private void createLayout() {
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -3287067057100477784L;

			@Override
			public boolean isCellEditable(int row, int column) {
				 if(column == DETAIL_COL || column == SUB_TOT_COL)
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
		
		JLabel lblParty = new JLabel("Party Name:");
		lblParty.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblParty = new GridBagConstraints();
		gbc_lblParty.insets = new Insets(5, 5, 0, 0);
		gbc_lblParty.anchor = GridBagConstraints.EAST;
		gbc_lblParty.gridx = 0;
		gbc_lblParty.gridy = 0;
		panel_3.add(lblParty, gbc_lblParty);
		
		partyCombo = new JComboBox();
		GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
		gbc_orderByCombo.insets = new Insets(5, 5, 0, 0);
		gbc_orderByCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderByCombo.gridx = 1;
		gbc_orderByCombo.gridy = 0;
		panel_3.add(partyCombo, gbc_orderByCombo);
		
		JLabel lblItemName = new JLabel("Item Name/Code :");
		lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblCustomerName.insets = new Insets(5, 5, 0, 0);
		gbc_lblCustomerName.gridx = 0;
		gbc_lblCustomerName.gridy = 1;
		panel_3.add(lblItemName, gbc_lblCustomerName);

		final JComboBox itemcombo = new JComboBox();
		itemcombo.setEditable(true);
		itemcombo.setPrototypeDisplayValue("012345678901234");
		GridBagConstraints gbc_itemcombo = new GridBagConstraints();
		gbc_itemcombo.insets = new Insets(5, 5, 0, 0);
		gbc_itemcombo.anchor = GridBagConstraints.NORTHWEST;
		gbc_itemcombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemcombo.gridx = 1;
		gbc_itemcombo.gridy = 1;
		panel_3.add(itemcombo, gbc_itemcombo);
		
		itemEditor = (JTextComponent) itemcombo.getEditor().getEditorComponent();
		
		JButton button = new JButton("");
		button.setIcon(Utils.getImageIcon("search.png"));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(5, 5, 0, 0);
		gbc_label.anchor = GridBagConstraints.NORTHWEST;
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		panel_3.add(button, gbc_label);
		
		loadPartyCombo();

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selectedIndex = partyCombo.getSelectedIndex();
				if(selectedIndex <= 0){
					JOptionPane.showMessageDialog(new JFrame(),
							"Please select Party First!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}else{
					ComboItem party = (ComboItem)partyCombo.getSelectedItem();
					if (itemEditor.getText() != null && !"".equals(itemEditor.getText())) {
						loadItems(itemcombo, itemEditor.getText(), party.getKey());
					} else {
						loadItems(itemcombo, null, party.getKey());
					}
				}
			}
		});
			
		JLabel lblQuantity = new JLabel("Total Quantity :");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblQuantity.insets = new Insets(5, 5, 0, 0);
		gbc_lblQuantity.gridx = 3;
		gbc_lblQuantity.gridy = 0;
		panel_3.add(lblQuantity, gbc_lblQuantity);

		quantityField = new JTextField();
		quantityField.setEditable(false);
		quantityField.setText("0");
		quantityField.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityField.setColumns(10);
		quantityField.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.insets = new Insets(5, 5, 0, 0);
		gbc_quantityField.anchor = GridBagConstraints.NORTHWEST;
		gbc_quantityField.fill = GridBagConstraints.HORIZONTAL;
		gbc_quantityField.gridx = 4;
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
			private static final long serialVersionUID = -8463669397155766823L;

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
			columnModel.getColumn(DETAIL_COL).setPreferredWidth(150);
			for (int i = 1; i < columnNos; i++) {
				columnModel.getColumn(i).setCellEditor(new IntegerEditor(0, Integer.MAX_VALUE));
				columnModel.getColumn(i).setCellRenderer(rightRenderer);
				columnModel.getColumn(i).setPreferredWidth(30);
			}
			columnModel.getColumn(SUB_TOT_COL).setPreferredWidth(100);
			columnModel.getColumn(SUB_TOT_COL).setCellRenderer(font);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
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
		
		partyCombo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					ComboItem party = (ComboItem) e.getItem();
					if (party.getKey() != 0) {
						boolean loadItems = loadItems(itemcombo, null,
								party.getKey());
						if (!loadItems) {
							JOptionPane.showMessageDialog(new JFrame(),
									"No Items found for this party!", "Dialog",
									JOptionPane.ERROR_MESSAGE);
						}
					} else {
						itemcombo.removeAllItems();
					}
				}
			}
		});
		
		itemcombo.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				if (e.getStateChange() == ItemEvent.SELECTED) {
					ComboItem item = (ComboItem) e.getItem();
					if (item.getKey() != 0) {
						boolean loadItemDetails = loadItemDetails(item);
						if (!loadItemDetails) {
							JOptionPane
									.showMessageDialog(
											new JFrame(),
											"Empty Inventory! Update item inventory or choose another Item Name/Code!",
											"Dialog", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						clearForm();
					}
				}
			}
		});

		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(600, 400));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel_4.add(scroll, gbc_table);
		
		JPanel panel_7 = new JPanel();
//		panel_7.setBackground(new Color(176, 224, 230));
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
		panel_7.add(btnUpdateProduct, gbc_btnSaveProduct);
		
		btnUpdateProduct.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (itemcombo.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Item Name/Code cannot be empty!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				} else if(itemcombo.getSelectedIndex() > 0 && tableModel.getRowCount() == 0){
					JOptionPane.showMessageDialog(new JFrame(),
							"Item details cannot be empty! Add detail for this item.",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}else{
					ComboItem selectedParty = (ComboItem) partyCombo.getSelectedItem();
					ComboItem selectedItem = (ComboItem) itemcombo.getSelectedItem();
					Vector dataVector = tableModel.getDataVector();

					for(int i=0; i< tableModel.getRowCount(); i++){
						Vector row = (Vector) dataVector.elementAt(i);
						ComboItem detail = (ComboItem) row.get(DETAIL_COL);
						detailEntitites.add(new PurchaseOrderDetailEntity(selectedParty.getKey(), selectedParty.getValue(), selectedItem.getKey(), selectedItem.getValue(),
								detail.getKey(), 
								detail.getValue(), Integer.parseInt(row.elementAt(1).toString()), Integer.parseInt(row.elementAt(2).toString()), 
								Integer.parseInt(row.elementAt(3).toString()), Integer.parseInt(row.elementAt(4).toString()), 
								Integer.parseInt(row.elementAt(5).toString()), Integer.parseInt(row.elementAt(6).toString()), 
								Integer.parseInt(row.elementAt(7).toString()), Integer.parseInt(row.elementAt(8).toString()), 
								Integer.parseInt(row.elementAt(SUB_TOT_COL).toString()), Integer.parseInt(quantityField.getText())));
					}
					hideDialog();
				}	
			}
		});
	}
	
	private void hideDialog(){
		setVisible(false);
		dispose();
	}
	
	public void clearForm() {
		quantityField.setText("0");
		clearTable();
	}

	private void clearTable() {
		tableModel.setNumRows(0);
	}
	
	@SuppressWarnings({ "unchecked" })
	private boolean loadItems(JComboBox comboBox, String item, long partyId) {
		if(item == null)
			list = manageProducts.listProductsByParty(partyId);
		else
			list = manageProducts.listProductsByNameAndParty(item, partyId);
		comboBox.removeAllItems();
		if(list.size() > 0){
			comboBox.addItem(new ComboItem(0, ""));
			for (Iterator<ProductEntity> iterator = list.iterator(); iterator
					.hasNext();) {
				ProductEntity product = iterator.next();
				comboBox.addItem(new ComboItem(product.getItemId(),
						product.getItemName()));
			}
			return true;
		}
		return false;
	}

	@SuppressWarnings({ })
	protected boolean loadItemDetails(ComboItem item) {
		clearForm();
		ProductEntity entity = findItemFromList(item);
		details = entity.getDetails();
		if(!details.isEmpty()){
			for (ProductDetailEntity detail : details) {
				tableModel.addRow(new Object[]{new ComboItem(detail.getItemDetailId(), detail.getName()), new Integer(0), new Integer(0), new Integer(0),
						new Integer(0), new Integer(0), new Integer(0), new Integer(0), 
						new Integer(0),  new Integer(0)});
			}
			return true;
		}
		return false;
	}
	
	private ProductEntity findItemFromList(ComboItem item) {
		for(ProductEntity entity: list){
			if(entity.getItemId() == item.getKey()){
				return entity;
			}
		}
		return null;
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
	
}
