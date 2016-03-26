package gui;

import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.UserEntity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import main.ComboItem;
import main.Constants;
import main.UserType;
import main.Utils;
import contoller.ManageProducts;
import contoller.ManagePurchaseOrders;
import contoller.ManageUsers;

@SuppressWarnings("rawtypes")
public class ProductListPanel extends JPanel {

	private static final long serialVersionUID = 4050027445361758126L;
	
	private JTextField itemNameField;
	private JTextField styleField;
	private JButton searchItem;
	private JComboBox partyCombo; 

	private DefaultTableModel model;
	private ManageProducts manageProducts;
	private ManagePurchaseOrders managePurchaseOrders;
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
	private static final int PARTY_COL = 0;
	private static final int ITEM_COL = 1;
	private static final int QTY_COL_1 = 3;
	private static final int QTY_COL_2 = 4;
	private static final int QTY_COL_3 = 5;
	private static final int EDIT_COLUMN = 6;
	private static final int DET_COLUMN = 7;
	
	private List<ProductEntity> list;
	
	public ProductListPanel() {
		setBorder(new TitledBorder(null, "Inventory", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.BLACK));
		setBackground(new Color(176, 224, 230));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0 };
		layout.rowHeights = new int[] { 0 };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0 };
		setLayout(layout);
		
		manageProducts = new ManageProducts();
		managePurchaseOrders = new ManagePurchaseOrders(); 
		model = new DefaultTableModel() {
			private static final long serialVersionUID = -565826275784501542L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case EDIT_COLUMN:
					return ImageIcon.class;
				case QTY_COL_1:
					return Integer.class;	
				case QTY_COL_2:
					return BigDecimal.class;	
				case QTY_COL_3:
					return BigDecimal.class;	
				case DET_COLUMN:
					return ImageIcon.class;
				default:
					return String.class;
				}
			}
		};

		for (int i = 0; i < Constants.productListColumnNames.length; i++) {
			model.addColumn(Constants.productListColumnNames[i]);
		}
		setLayoutComponents();
	}

	@SuppressWarnings({ "unchecked" })
	private void setLayoutComponents() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel.setBackground(new Color(176, 224, 230));
		
		JLabel lblParty = new JLabel("Party :");
		lblParty.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblParty.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblParty);
		
		partyCombo = new JComboBox();
		partyCombo.setPrototypeDisplayValue("012345678901234");
		partyCombo.removeAllItems();
		partyCombo.addItem(new ComboItem(0, ""));
		List<UserEntity> list = new ManageUsers().listUsers(UserType.PARTY.ordinal());
		for (UserEntity user : list) {
			partyCombo.addItem(new ComboItem(user.getUserId(), user.getUserName()));
		}
		panel.add(partyCombo);
		
		JLabel lblItemName = new JLabel("Item Name/Code :");
		lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblItemName.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblItemName);

		itemNameField = new JTextField();
		itemNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(itemNameField);
		itemNameField.setColumns(15);
		
		JLabel lblStyleName = new JLabel("Style :");
		lblStyleName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblStyleName.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblStyleName);

		styleField = new JTextField();
		styleField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(styleField);
		styleField.setColumns(15);
		
		searchItem = new JButton("Search");
		searchItem.setFont(new Font("Tahoma", Font.BOLD, 12));
		searchItem.setIcon(Utils.getImageIcon("search.png"));
		panel.add(searchItem);
		
		JTable table = new JTable(model);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		TableColumnModel columnModel = table.getColumnModel();
		try {
			table.setAutoCreateRowSorter(false);
			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = -389821879364414470L;

				@Override public Dimension getPreferredSize() {
				    Dimension d = super.getPreferredSize();
				    d.height = 32;
				    return d;
				  }
				});
			table.setRowHeight(20);
			columnModel.getColumn(QTY_COL_1).setCellRenderer(rightRenderer);
			columnModel.getColumn(QTY_COL_2).setCellRenderer(rightRenderer);
			columnModel.getColumn(QTY_COL_3).setCellRenderer(rightRenderer);
			columnModel.getColumn(QTY_COL_1).setMaxWidth(90);
			columnModel.getColumn(QTY_COL_1).setMaxWidth(90);
			columnModel.getColumn(QTY_COL_1).setPreferredWidth(90);
			columnModel.getColumn(QTY_COL_2).setMaxWidth(90);
			columnModel.getColumn(QTY_COL_2).setMaxWidth(90);
			columnModel.getColumn(QTY_COL_2).setPreferredWidth(90);
			columnModel.getColumn(QTY_COL_3).setMaxWidth(100);
			columnModel.getColumn(QTY_COL_3).setMaxWidth(100);
			columnModel.getColumn(QTY_COL_3).setPreferredWidth(100);
			columnModel.getColumn(EDIT_COLUMN).setMaxWidth(50);
			columnModel.getColumn(DET_COLUMN).setMaxWidth(50);
			columnModel.getColumn(EDIT_COLUMN).setMinWidth(50);
			columnModel.getColumn(DET_COLUMN).setMinWidth(50);
			columnModel.getColumn(EDIT_COLUMN).setPreferredWidth(50);
			columnModel.getColumn(DET_COLUMN).setPreferredWidth(50);
		} catch (Exception continuewithNoSort) {
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
					ComboItem party = (ComboItem) row.get(PARTY_COL);
					ComboItem item = (ComboItem) row.get(ITEM_COL);
					switch (columnNo) {
					case EDIT_COLUMN:
						int action = JOptionPane.showConfirmDialog(
								ProductListPanel.this,
								"Do you really want to update " + party.getValue()+"-"+item.getValue()
								+ "?", "Update Item Inventory",
								JOptionPane.OK_CANCEL_OPTION);
						if (action == JOptionPane.OK_OPTION) {
							    ProductUpdateDialog productUpdateDialog = new ProductUpdateDialog(parent, findItemFromList(item.getKey()));
							    boolean showDialog = productUpdateDialog.showDialog();
							    if(showDialog)
							    	refreshModel(null);
						}
						break;
					case DET_COLUMN:
						List<Object[]> inTransitDetails = managePurchaseOrders.getInTransitDetails(item.getKey());
						new ViewItemInTransitDetails(parent, findItemFromList(item.getKey()), inTransitDetails);
						break;
					}
				}
			}
		});
		
		
            searchItem.addActionListener(new ActionListener() {
			
				public void actionPerformed(ActionEvent arg0) {
					String itemName = itemNameField.getText().trim();
					String style = styleField.getText().trim();
					int selectedIndex = partyCombo.getSelectedIndex();
					if(selectedIndex > 0 || !"".equals(itemName) || !"".equals(style)){
						refreshModel(manageProducts.listProductsBySearch(itemName, style, ((ComboItem)partyCombo.getSelectedItem()).getKey()));
					}else{
						refreshModel(null);
					}	
				}
		});
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel,
				tableScroll);

		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.anchor = GridBagConstraints.NORTH;
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		add(splitPane, gbc_splitPane);
	}
	
	@SuppressWarnings("unchecked")
	public void clearForm(){
		partyCombo.removeAllItems();
		partyCombo.addItem(new ComboItem(0, ""));
		List<UserEntity> list = new ManageUsers().listUsers(UserType.PARTY.ordinal());
		for (UserEntity user : list) {
			partyCombo.addItem(new ComboItem(user.getUserId(), user.getUserName()));
		}
		refreshModel(null);
	}

	public void refreshModel(List<ProductEntity> listProductsByName) {
		model.setRowCount(0);
		list = new ArrayList<ProductEntity>();
		
		if(listProductsByName!= null)
			list = listProductsByName;
		else
			list = manageProducts.listAllProducts();
		
		for (Iterator<ProductEntity> iterator = list.iterator(); iterator.hasNext();) {
			ProductEntity product = iterator.next();
			model.addRow(new Object[] { new ComboItem(product.getPartyId(), product.getPartyName()), new ComboItem(product.getItemId(), product.getItemName()),
					product.getItemStyle(), product.getQuantity(), product.getTransitQuantity(), getProductionQuantity(product.getDetails()), Utils.getImageIcon("edit.png")
					, Utils.getImageIcon("DET.png"), product.getItemId()});
		}
		
	}

	private Integer getProductionQuantity(Set<ProductDetailEntity> details) {
		Integer qtyProd = 0;
		if(details!= null && !details.isEmpty()){
			for(ProductDetailEntity detail : details){
				int xQty = 0,sQty = 0, mQty = 0, lQty = 0, xlQty = 0, xxlQty = 0, xxxlQty = 0, mixQty = 0, totalQty = 0;
				if((detail.getProd_xsmall() - detail.getTrans_xsmall()) > 0)
					xQty = detail.getProd_xsmall() - detail.getTrans_xsmall();
				if((detail.getProd_small() - detail.getTrans_small()) > 0)
					sQty = detail.getProd_small() - detail.getTrans_small();
				if((detail.getProd_medium() - detail.getTrans_medium()) > 0)
					mQty = detail.getProd_medium() - detail.getTrans_medium();
				if((detail.getProd_large() - detail.getTrans_large()) > 0)
					lQty = detail.getProd_large() - detail.getTrans_large();
				if((detail.getProd_xlarge() - detail.getTrans_xlarge()) > 0)
					xlQty = detail.getProd_xlarge() - detail.getTrans_xlarge();
				if((detail.getProd_xxlarge() - detail.getTrans_xxlarge()) > 0)
					xxlQty = detail.getProd_xxlarge() - detail.getTrans_xxlarge();
				if((detail.getProd_xxxlarge() - detail.getTrans_xxxlarge()) > 0)
					xxxlQty = detail.getProd_xxxlarge() - detail.getTrans_xxxlarge();
				if((detail.getProd_mix() - detail.getTrans_mix()) > 0)
					mixQty = detail.getProd_mix() - detail.getTrans_mix();
				
				totalQty = xQty + sQty + mQty + lQty + xlQty + xxlQty + xxxlQty +
						+ mixQty;
				
				qtyProd += totalQty;
			}
		}
		return qtyProd;
	}

	public void focusTextField() {
		itemNameField.requestFocusInWindow();
	}

	private ProductEntity findItemFromList(long itemId) {
		for(ProductEntity entity: list){
			if(entity.getItemId() == itemId){
				return entity;
			}
		}
		return null;
	}

}
