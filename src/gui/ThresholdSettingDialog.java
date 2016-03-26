package gui;

import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.ThresholdDetailEntity;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
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
import contoller.ManageBackUpOrders;
import contoller.ManageProducts;
import contoller.ManageUsers;

@SuppressWarnings("rawtypes")
public class ThresholdSettingDialog extends JDialog {
	private static final long serialVersionUID = 1548621027043141384L;
	
	private static ManageProducts manageProducts = new ManageProducts();
	private static ManageBackUpOrders manageBackUpOrders = new ManageBackUpOrders();

	private DefaultTableModel tableModel1;
	private DefaultTableModel tableModel2;
	private JComboBox partyCombo;
	private JTextComponent itemEditor;
	private JComboBox itemcombo;
	
	private List<ProductEntity> list;
	private Set<ProductDetailEntity> details;
	
	private List<ThresholdDetailEntity> detailEntitites;
	
	private static final int columnNos = 9;
	private static final int DETAIL_COL = 0;
	
	private boolean isUpdate;
	
	public ThresholdSettingDialog(Frame parent) {
		super(parent);

		detailEntitites = new ArrayList<ThresholdDetailEntity>();
		
		createLayout();

		setTitle("Item Threshold Setting");
		setSize(750, 750);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
	}

	@SuppressWarnings({ "unchecked" })
	private void createLayout() {
		tableModel1 = new DefaultTableModel() {
			private static final long serialVersionUID = -8744729907499868806L;

			@Override
			public boolean isCellEditable(int row, int column) {
				 if(column == DETAIL_COL)
					 return false;
				 return true;
			}
		};
		tableModel2 = new DefaultTableModel() {
			private static final long serialVersionUID = -2233299556831971915L;

			@Override
			public boolean isCellEditable(int row, int column) {
				 if(column == DETAIL_COL)
					 return false;
				 return true;
			}
		};

		for (int i = 0; i < Constants.productDialogColumnNames1.length; i++) {
			tableModel1.addColumn(Constants.productDialogColumnNames1[i]);
			tableModel2.addColumn(Constants.productDialogColumnNames1[i]);
		}
		
		JPanel panel = (JPanel) getContentPane();
		panel.setBackground(new Color(176, 224, 230));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		layout.rowHeights = new int[] { 30, 600, 50 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0,  1.0, 1.0, 1.0 };
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
		
		partyCombo = new JComboBox();
		partyCombo.setPrototypeDisplayValue("012345678901234");
		GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
		gbc_orderByCombo.insets = new Insets(5, 5, 0, 0);
		gbc_orderByCombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_orderByCombo.gridx = 1;
		gbc_orderByCombo.gridy = 0;
		panel_3.add(partyCombo, gbc_orderByCombo);
		
		JLabel lblItemName = new JLabel("Item Name/Code :");
		lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.anchor = GridBagConstraints.EAST;
		gbc_lblCustomerName.insets = new Insets(5, 5, 0, 0);
		gbc_lblCustomerName.gridx = 2;
		gbc_lblCustomerName.gridy = 0;
		panel_3.add(lblItemName, gbc_lblCustomerName);

		itemcombo = new JComboBox();
		itemcombo.setEditable(true);
		itemcombo.setPrototypeDisplayValue("012345678901234");
		GridBagConstraints gbc_itemcombo = new GridBagConstraints();
		gbc_itemcombo.insets = new Insets(5, 5, 0, 0);
		gbc_itemcombo.anchor = GridBagConstraints.WEST;
		gbc_itemcombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemcombo.gridx = 3;
		gbc_itemcombo.gridy = 0;
		panel_3.add(itemcombo, gbc_itemcombo);
		
		itemEditor = (JTextComponent) itemcombo.getEditor().getEditorComponent();
		
		JButton button = new JButton("");
		button.setIcon(Utils.getImageIcon("search.png"));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(5, 5, 0, 0);
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.gridx = 4;
		gbc_label.gridy = 0;
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
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		final JTable table1 = new JTable(tableModel1){
			private static final long serialVersionUID = 1344104699517922121L;

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
		final JTable table2 = new JTable(tableModel2){
			private static final long serialVersionUID = -5401120983040794186L;

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
		table1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table1.setCellSelectionEnabled(true);
		table2.setCellSelectionEnabled(true);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		try {
			TableColumnModel columnModel1 = table1.getColumnModel();
			table1.setTableHeader(new JTableHeader(columnModel1) {
				private static final long serialVersionUID = 1612377026023301779L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table1.setRowHeight(20);
			columnModel1.getColumn(DETAIL_COL).setPreferredWidth(150);
			for (int i = 1; i < columnNos; i++) {
				columnModel1.getColumn(i).setCellEditor(new IntegerEditor(0, Integer.MAX_VALUE));
				columnModel1.getColumn(i).setCellRenderer(rightRenderer);
				columnModel1.getColumn(i).setPreferredWidth(30);
			}
			TableColumnModel columnModel2 = table2.getColumnModel();
			table2.setTableHeader(new JTableHeader(columnModel2) {
				private static final long serialVersionUID = -3204899788448103458L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table2.setRowHeight(20);
			columnModel2.getColumn(DETAIL_COL).setPreferredWidth(150);
			for (int i = 1; i < columnNos; i++) {
				columnModel2.getColumn(i).setCellEditor(new IntegerEditor(0, Integer.MAX_VALUE));
				columnModel2.getColumn(i).setCellRenderer(rightRenderer);
				columnModel2.getColumn(i).setPreferredWidth(30);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
						clearTable();
					}
				}
			}
		});
		
		JLabel lblThreshold = new JLabel("Threshold :");
		lblThreshold.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblThreshold = new GridBagConstraints();
		gbc_lblThreshold.anchor = GridBagConstraints.WEST;
		gbc_lblThreshold.insets = new Insets(5, 5, 0, 0);
		gbc_lblThreshold.gridx = 0;
		gbc_lblThreshold.gridy = 0;
		panel_4.add(lblThreshold, gbc_lblThreshold);
		
		JScrollPane scroll1 = new JScrollPane(table1);
		scroll1.setPreferredSize(new Dimension(600, 280));
		GridBagConstraints gbc_table1 = new GridBagConstraints();
		gbc_table1.fill = GridBagConstraints.HORIZONTAL;
		gbc_table1.gridx = 0;
		gbc_table1.gridy = 1;
		panel_4.add(scroll1, gbc_table1);
		
		JLabel lblTarget = new JLabel("Target :");
		lblTarget.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTarget = new GridBagConstraints();
		gbc_lblTarget.anchor = GridBagConstraints.WEST;
		gbc_lblTarget.insets = new Insets(5, 5, 0, 0);
		gbc_lblTarget.gridx = 0;
		gbc_lblTarget.gridy = 2;
		panel_4.add(lblTarget, gbc_lblTarget);
		
		
		JScrollPane scroll2 = new JScrollPane(table2);
		scroll2.setPreferredSize(new Dimension(600, 280));
		GridBagConstraints gbc_table2 = new GridBagConstraints();
		gbc_table2.fill = GridBagConstraints.HORIZONTAL;
		gbc_table2.insets = new Insets(0, 0, 5, 0);
		gbc_table2.gridx = 0;
		gbc_table2.gridy = 3;
		panel_4.add(scroll2, gbc_table2);
		
		
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

		JButton btnEnterProduct = new JButton("Save");
		btnEnterProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveProduct = new GridBagConstraints();
		gbc_btnSaveProduct.anchor = GridBagConstraints.CENTER;
		gbc_btnSaveProduct.gridx = 0;
		gbc_btnSaveProduct.gridy = 0;
		panel_7.add(btnEnterProduct, gbc_btnSaveProduct);
		
		partyCombo.addItemListener(new ItemListener() {
			
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					ComboItem party = (ComboItem) e.getItem();
					if (party.getKey() != 0) {
						boolean loadItems = loadItems(itemcombo,
								null, party.getKey());
						if (!loadItems) {
							JOptionPane.showMessageDialog(new JFrame(),
									"No Items found for this party!",
									"Dialog", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						itemcombo.removeAllItems();
					}
				}
			}
		});
		
		
		btnEnterProduct.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (partyCombo.getSelectedIndex() <= 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Select Party first!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				} else if (itemcombo.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Item Name/Code cannot be empty!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				} else if(itemcombo.getSelectedIndex() > 0 && tableModel1.getRowCount() == 0){
					JOptionPane.showMessageDialog(new JFrame(),
							"Item details cannot be empty! Add detail for this item.",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}else{
					ComboItem selectedItem = (ComboItem) itemcombo.getSelectedItem();
					Vector dataVector1 = tableModel1.getDataVector();
					Vector dataVector2 = tableModel2.getDataVector();

					for(int i=0; i< tableModel1.getRowCount(); i++){
						Vector row1 = (Vector) dataVector1.elementAt(i);
						Vector row2 = (Vector) dataVector2.elementAt(i);
						ComboItem selectedParty = (ComboItem) partyCombo.getSelectedItem();
						ComboItem detail = (ComboItem) row1.get(DETAIL_COL);
						if(!isUpdate){
							detailEntitites.add(new ThresholdDetailEntity(selectedParty.getKey(), selectedParty.getValue(), 
									selectedItem.getKey(), selectedItem.getValue(),
									detail.getKey(), detail.getValue(),
									Integer.parseInt(row1.elementAt(1).toString()), Integer.parseInt(row1.elementAt(2).toString()), 
									Integer.parseInt(row1.elementAt(3).toString()), Integer.parseInt(row1.elementAt(4).toString()), 
									Integer.parseInt(row1.elementAt(5).toString()), Integer.parseInt(row1.elementAt(6).toString()), 
									Integer.parseInt(row1.elementAt(7).toString()), Integer.parseInt(row1.elementAt(8).toString()), 
									Integer.parseInt(row2.elementAt(1).toString()), Integer.parseInt(row2.elementAt(2).toString()), 
									Integer.parseInt(row2.elementAt(3).toString()), Integer.parseInt(row2.elementAt(4).toString()), 
									Integer.parseInt(row2.elementAt(5).toString()), Integer.parseInt(row2.elementAt(6).toString()), 
									Integer.parseInt(row2.elementAt(7).toString()), Integer.parseInt(row2.elementAt(8).toString()), 
									Utils.getDateInMillies(new Date()), System.currentTimeMillis()));
						}else{
							boolean found = false;
							for(ThresholdDetailEntity entity : detailEntitites){
								if(entity.getItemDetailId() == detail.getKey()){
									entity.setXsmallThreshold(Integer.parseInt(row1.elementAt(1).toString()));
									entity.setSmallThreshold(Integer.parseInt(row1.elementAt(2).toString()));
									entity.setMediumThreshold(Integer.parseInt(row1.elementAt(3).toString()));
									entity.setLargeThreshold(Integer.parseInt(row1.elementAt(4).toString()));
									entity.setXlargeThreshold(Integer.parseInt(row1.elementAt(5).toString()));
									entity.setXxlargeThreshold(Integer.parseInt(row1.elementAt(6).toString()));
									entity.setXxxlargeThreshold(Integer.parseInt(row1.elementAt(7).toString()));
									entity.setMixThreshold(Integer.parseInt(row1.elementAt(8).toString()));
									entity.setXsmallTarget(Integer.parseInt(row2.elementAt(1).toString()));
									entity.setSmallTarget(Integer.parseInt(row2.elementAt(2).toString()));
									entity.setMediumTarget(Integer.parseInt(row2.elementAt(3).toString()));
									entity.setLargeTarget(Integer.parseInt(row2.elementAt(4).toString()));
									entity.setXlargeTarget(Integer.parseInt(row2.elementAt(5).toString()));
									entity.setXxlargeTarget(Integer.parseInt(row2.elementAt(6).toString()));
									entity.setXxxlargeTarget(Integer.parseInt(row2.elementAt(7).toString()));
									entity.setMixTarget(Integer.parseInt(row2.elementAt(8).toString()));
									found = true;
									break;
								}
							}
							if(!found){
								detailEntitites.add(new ThresholdDetailEntity(selectedParty.getKey(), selectedParty.getValue(), 
										selectedItem.getKey(), selectedItem.getValue(),
										detail.getKey(), detail.getValue(),
										Integer.parseInt(row1.elementAt(1).toString()), Integer.parseInt(row1.elementAt(2).toString()), 
										Integer.parseInt(row1.elementAt(3).toString()), Integer.parseInt(row1.elementAt(4).toString()), 
										Integer.parseInt(row1.elementAt(5).toString()), Integer.parseInt(row1.elementAt(6).toString()), 
										Integer.parseInt(row1.elementAt(7).toString()), Integer.parseInt(row1.elementAt(8).toString()), 
										Integer.parseInt(row2.elementAt(1).toString()), Integer.parseInt(row2.elementAt(2).toString()), 
										Integer.parseInt(row2.elementAt(3).toString()), Integer.parseInt(row2.elementAt(4).toString()), 
										Integer.parseInt(row2.elementAt(5).toString()), Integer.parseInt(row2.elementAt(6).toString()), 
										Integer.parseInt(row2.elementAt(7).toString()), Integer.parseInt(row2.elementAt(8).toString()), 
										Utils.getDateInMillies(new Date()), System.currentTimeMillis()));
							}
						}	
					}
					boolean addBackupOrderDetails = manageBackUpOrders.addThresholdDetails(detailEntitites);
					if(addBackupOrderDetails){
						JOptionPane.showMessageDialog(new JFrame(),
								"SUCCESS",
								"Dialog", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(new JFrame(),
								"FAILED!",
								"Dialog", JOptionPane.ERROR_MESSAGE);
					}
					clearForm();
				}	
			}
		});
		
	}
	
	@SuppressWarnings({ "unchecked" })
	private boolean loadItems(JComboBox comboBox, String itemName, long partyId) {
		if(itemName == null)
			list = manageProducts.listProductsByParty(partyId);
		else
			list = manageProducts.listProductsByNameAndParty(itemName, partyId);
		comboBox.removeAllItems();
		if(list.size() > 0){
			comboBox.addItem(new ComboItem(0, ""));
			for (ProductEntity product : list) {
				comboBox.addItem(new ComboItem(product.getItemId(),
						product.getItemName()));
			}
			return true;
		}
		return false;
	}
	
	protected boolean loadItemDetails(ComboItem item) {
		clearTable();
		
		detailEntitites = manageBackUpOrders.fetchThresholdDetails(item.getKey());
		
		if(detailEntitites != null && !detailEntitites.isEmpty()){
			isUpdate = true;
		}else{
			isUpdate = false;
		}
		ProductEntity entity = findItemFromList(item);
		details = entity.getDetails();
		if(!isUpdate){
			if(!details.isEmpty()){
				for (ProductDetailEntity detail : details) {
					tableModel1.addRow(new Object[]{new ComboItem(detail.getItemDetailId(), detail.getName()), new Integer(0), new Integer(0), new Integer(0),
							new Integer(0), new Integer(0), new Integer(0), new Integer(0), 
							new Integer(0)});
					tableModel2.addRow(new Object[]{new ComboItem(detail.getItemDetailId(), detail.getName()), new Integer(0), new Integer(0), new Integer(0),
							new Integer(0), new Integer(0), new Integer(0), new Integer(0), 
							new Integer(0)});
				}
				return true;
			}
		}else{
			for(ProductDetailEntity detail : details){
				boolean found = false;
				for(ThresholdDetailEntity detailEntity: detailEntitites){
					if(detail.getItemDetailId() == detailEntity.getItemDetailId()){
						tableModel1.addRow(new Object[]{new ComboItem(detailEntity.getItemDetailId(), detailEntity.getDetailName()), detailEntity.getXsmallThreshold(), detailEntity.getSmallThreshold(), detailEntity.getMediumThreshold(),
								detailEntity.getLargeThreshold(), detailEntity.getXlargeThreshold(), detailEntity.getXxlargeThreshold(), detailEntity.getXxxlargeThreshold(), 
								detailEntity.getMixThreshold(),  detailEntity.getItemDetailId()});
						tableModel2.addRow(new Object[]{new ComboItem(detailEntity.getItemDetailId(), detailEntity.getDetailName()), detailEntity.getXsmallTarget(), detailEntity.getSmallTarget(), detailEntity.getMediumTarget(),
								detailEntity.getLargeTarget(), detailEntity.getXlargeTarget(), detailEntity.getXxlargeTarget(), detailEntity.getXxxlargeTarget(), 
								detailEntity.getMixTarget()});
						found = true;
						break;
					}
				}
				if(!found){
					tableModel1.addRow(new Object[]{new ComboItem(detail.getItemDetailId(), detail.getName()), new Integer(0), new Integer(0), new Integer(0),
							new Integer(0), new Integer(0), new Integer(0), new Integer(0), 
							new Integer(0)});
					tableModel2.addRow(new Object[]{new ComboItem(detail.getItemDetailId(), detail.getName()), new Integer(0), new Integer(0), new Integer(0),
							new Integer(0), new Integer(0), new Integer(0), new Integer(0), 
							new Integer(0)});
				}
			}
			return true;
		}
		return false;
	}
	
	public void clearForm() {
		partyCombo.setSelectedIndex(0);
		detailEntitites.clear();
		clearTable();
	}

	private void clearTable() {
		tableModel1.setNumRows(0);
		tableModel2.setNumRows(0);
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
