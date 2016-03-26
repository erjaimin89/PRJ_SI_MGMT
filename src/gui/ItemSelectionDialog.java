package gui;

import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.UserEntity;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.ComboItem;
import main.UserType;
import contoller.ManageProducts;
import contoller.ManageUsers;

@SuppressWarnings("rawtypes")
public class ItemSelectionDialog extends JDialog{

	private static final long serialVersionUID = -8560817719115391243L;
	
	private static ManageProducts manageProducts = new ManageProducts();
//	private JTextComponent itemEditor;
	private ComboItems items;
	private JComboBox partyCombo;
	protected static boolean loadItems;
	private List<ProductEntity> list;
	private Set<ProductDetailEntity> details;
	private JList<ComboItem> jlist;
	private DefaultListModel<ComboItem> listModel;
	private List<ComboItem> selectedValuesList;
	private JCheckBox checkbox;

	@SuppressWarnings("unchecked")
	public ItemSelectionDialog(Frame parent) {
		super(parent);
		listModel = new DefaultListModel<ComboItem>();
		jlist = new JList<>(listModel);
		jlist.setCellRenderer(new CheckboxListCellRenderer());
	}

	public ComboItems showDialog() {
		items = new ComboItems();
		createLayout();
		
		setTitle("Choose Item Details...");
		setSize(400, 420);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
		return items;
	}

	@SuppressWarnings({ "unchecked" })
	private void createLayout(JComponent... arg) {
		Container panel = getContentPane();
       
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel_1);
        
        JLabel lblParty = new JLabel("Party:");
		lblParty.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblParty = new GridBagConstraints();
		gbc_lblParty.insets = new Insets(10, 15, 10, 0);
		gbc_lblParty.anchor = GridBagConstraints.EAST;
		gbc_lblParty.gridx = 1;
		gbc_lblParty.gridy = 0;
		panel.add(lblParty, gbc_lblParty);
		
		partyCombo = new JComboBox();
		GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
		gbc_orderByCombo.insets = new Insets(10, 5, 10, 0);
		gbc_orderByCombo.fill = GridBagConstraints.BOTH;
		gbc_orderByCombo.gridx = 2;
		gbc_orderByCombo.gridy = 0;
		panel.add(partyCombo, gbc_orderByCombo);
        
        
        JLabel lblItemName = new JLabel("Item:");
		lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
		gbc_lblCustomerName.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblCustomerName.insets = new Insets(5, 15, 10, 0);
		gbc_lblCustomerName.gridx = 1;
		gbc_lblCustomerName.gridy = 1;
		panel.add(lblItemName, gbc_lblCustomerName);

		final JComboBox itemcombo = new JComboBox();
//		itemcombo.setEditable(true);
		itemcombo.setPrototypeDisplayValue("0123456789012345678");
		GridBagConstraints gbc_itemcombo = new GridBagConstraints();
		gbc_itemcombo.insets = new Insets(5, 5, 10, 0);
		gbc_itemcombo.anchor = GridBagConstraints.NORTHWEST;
		gbc_itemcombo.fill = GridBagConstraints.HORIZONTAL;
		gbc_itemcombo.gridx = 2;
		gbc_itemcombo.gridy = 1;
		panel.add(itemcombo, gbc_itemcombo);
		
		loadPartyCombo();

		checkbox = new JCheckBox("Select All Details");
		GridBagConstraints gbc_checkbox = new GridBagConstraints();
		gbc_checkbox.insets = new Insets(5, 5, 10, 0);
		gbc_checkbox.anchor = GridBagConstraints.NORTHWEST;
		gbc_checkbox.fill = GridBagConstraints.HORIZONTAL;
		gbc_checkbox.gridx = 2;
		gbc_checkbox.gridy = 2;
		panel.add(checkbox, gbc_checkbox);
		
//		JButton searchButton = new JButton("");
//		searchButton.setIcon(Utils.getImageIcon("search.png"));
//		GridBagConstraints gbc_label = new GridBagConstraints();
//		gbc_label.insets = new Insets(5, 5, 10, 0);
//		gbc_label.anchor = GridBagConstraints.NORTHWEST;
//		gbc_label.gridx = 3;
//		gbc_label.gridy = 1;
//		panel.add(searchButton, gbc_label);
		
		JLabel lblDetails = new JLabel("Detail:");
		lblDetails.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblDetails = new GridBagConstraints();
		gbc_lblDetails.anchor = GridBagConstraints.NORTHEAST;
		gbc_lblDetails.insets = new Insets(5, 15, 10, 0);
		gbc_lblDetails.gridx = 1;
		gbc_lblDetails.gridy = 3;
		panel.add(lblDetails, gbc_lblDetails);
		
		GridBagConstraints gbc_detailCombo = new GridBagConstraints();
		gbc_detailCombo.insets = new Insets(5, 5, 10, 0);
		gbc_detailCombo.anchor = GridBagConstraints.NORTHWEST;
		gbc_detailCombo.gridx = 2;
		gbc_detailCombo.gridy = 3;
		JScrollPane tableScroll = new JScrollPane(jlist);
		tableScroll.setPreferredSize(new Dimension(200, 150));
		panel.add(tableScroll, gbc_detailCombo);
		
//		itemEditor = (JTextComponent) itemcombo.getEditor().getEditorComponent();
		
		final JLabel qtyDetails1 = new JLabel("");
		qtyDetails1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_qtyDetails1 = new GridBagConstraints();
		gbc_qtyDetails1.anchor = GridBagConstraints.NORTHWEST;
		gbc_qtyDetails1.insets = new Insets(5, 5, 0, 0);
		gbc_qtyDetails1.gridwidth = 2;
		gbc_qtyDetails1.gridx = 1;
		gbc_qtyDetails1.gridy = 4;
		panel.add(qtyDetails1, gbc_qtyDetails1);
		
		final JLabel qtyDetails2 = new JLabel("");
		qtyDetails2.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_qtyDetails2 = new GridBagConstraints();
		gbc_qtyDetails2.anchor = GridBagConstraints.NORTHWEST;
		gbc_qtyDetails2.insets = new Insets(0, 5, 5, 0);
		gbc_qtyDetails2.gridwidth = 2;
		gbc_qtyDetails2.gridx = 1;
		gbc_qtyDetails2.gridy = 5;
		panel.add(qtyDetails2, gbc_qtyDetails2);
		
		JButton btn = new JButton("Select");
		GridBagConstraints gbc_btn = new GridBagConstraints();
		gbc_btn.insets = new Insets(5, 15, 10, 0);
		gbc_btn.anchor = GridBagConstraints.WEST;
		gbc_btn.gridx = 2;
		gbc_btn.gridy = 6;
		panel.add(btn, gbc_btn);
		
//		searchButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				int selectedIndex = partyCombo.getSelectedIndex();
//				if(selectedIndex <= 0){
//					JOptionPane.showMessageDialog(new JFrame(),
//							"Please select Party First!",
//							"Dialog", JOptionPane.ERROR_MESSAGE);
//				}else{
//					ComboItem party = (ComboItem)partyCombo.getSelectedItem();
//					if (itemEditor.getText() != null && !"".equals(itemEditor.getText())) {
//						loadItems(itemcombo, itemEditor.getText(), party.getKey());
//					} else {
//						loadItems(itemcombo, null, party.getKey());
//					}
//				}
//			}
//		});
		
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
							itemcombo.removeAllItems();
							listModel.removeAllElements();
							checkbox.setSelected(false);
							qtyDetails1.setText("");qtyDetails2.setText("");
						}
					} else {
						itemcombo.removeAllItems();
						listModel.removeAllElements();
						checkbox.setSelected(false);
						qtyDetails1.setText("");qtyDetails2.setText("");
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
					if(loadItemDetails){
						jlist.addListSelectionListener(new ListSelectionListener() {
							
							@Override
							public void valueChanged(ListSelectionEvent e) {
								if (!e.getValueIsAdjusting()) {
				                    selectedValuesList = jlist.getSelectedValuesList();
				                    if(selectedValuesList.size() == 1){
				                    	ComboItem selectedItem = (ComboItem) itemcombo.getSelectedItem();
				                    	ComboItem selectedDetail = selectedValuesList.get(0);
				                    	ProductDetailEntity entity = findProductDetailEntity(selectedItem.getKey(), selectedDetail.getKey());
				                    	qtyDetails1.setText("[XS: "+(entity.getXsmall() -entity.getRes_xsmall())+" | S: "+(entity.getSmall() - entity.getRes_small())+" | M: "+(entity.getMedium() - entity.getRes_medium())
				                    			+" | L: "+(entity.getLarge() - entity.getRes_large())+"]");
				                    	qtyDetails2.setText("[1X: "+(entity.getXlarge() - entity.getRes_xlarge())+" | 2X: "+(entity.getXxlarge() - entity.getRes_xxlarge())+" | 3X: "+(entity.getXxxlarge() - entity.getRes_xxxlarge())
				                    			+" | Mix: "+(entity.getMix() - entity.getRes_mix())+"]");
				                    }else{
				                    	qtyDetails1.setText("");qtyDetails2.setText("");
				                    }
				                }
							}
						});
					}else{ 
							JOptionPane.showMessageDialog(new JFrame(),
											"Empty Inventory! Update item inventory or choose another party/Item!",
											"Dialog", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						listModel.removeAllElements();
						qtyDetails1.setText("");qtyDetails2.setText("");
						checkbox.setSelected(false);
					}
				}
			}
		});
		
		checkbox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(checkbox.isSelected()){
					jlist.setEnabled(false);
				}else{
					jlist.setEnabled(true);
				}
			}
		});
		
		btn.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if (partyCombo.getSelectedIndex() <= 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Party cannot be empty!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}else if (itemcombo.getSelectedIndex() <= 0) {
					JOptionPane.showMessageDialog(new JFrame(),
							"Item cannot be empty!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				}else if((selectedValuesList==null || selectedValuesList.isEmpty()) && !checkbox.isSelected()){
					JOptionPane.showMessageDialog(new JFrame(),
							"Detail cannot be empty!",
							"Dialog", JOptionPane.ERROR_MESSAGE);
				} else{
					ComboItem selectedParty = (ComboItem) partyCombo.getSelectedItem();
					ComboItem selectedItem = (ComboItem) itemcombo.getSelectedItem();
					items.setParty(selectedParty);
					items.setItem(selectedItem);
					Map<ComboItem, ProductDetailEntity> detailMap = new HashMap<ComboItem, ProductDetailEntity>();
					if(checkbox.isSelected()){
						items.setSelectAll(true);
						Set<ProductDetailEntity> findProductEntity = findProductEntity(selectedItem.getKey());
						for(ProductDetailEntity pde : findProductEntity){
							detailMap.put(new ComboItem(pde.getItemDetailId(), pde.getName()), pde);
						}
					}else{
						items.setSelectAll(false);
						for(ComboItem item : selectedValuesList){
							detailMap.put(item, findProductDetailEntity(selectedItem.getKey(), item.getKey()));
						}
					}
					items.setDetailMap(detailMap);
					hideDialog();
				}	
			}
		});
        pack();
	}

	protected ProductDetailEntity findProductDetailEntity(long itemId, long detailId) {
		Set<ProductDetailEntity> detailentitys = null;
		for(ProductEntity entity : list){
			if(entity.getItemId() == itemId){
				detailentitys = entity.getDetails();
				break;
			}
		}
		if(detailentitys != null){
			for(ProductDetailEntity entity : detailentitys){
				if(entity.getItemDetailId() == detailId){
					return entity;
				}
			}
		}	
		return null;
	}
	
	protected Set<ProductDetailEntity> findProductEntity(long itemId) {
		for(ProductEntity entity : list){
			if(entity.getItemId() == itemId){
				return entity.getDetails();
			}
		}
		return null;
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
	
	protected boolean loadItemDetails(ComboItem item) {
		listModel.removeAllElements();
		ProductEntity entity = findItemFromList(item);
		details = entity.getDetails();
		if(!details.isEmpty()){
			for (ProductDetailEntity detail : details) {
				listModel.addElement(new ComboItem(detail.getItemDetailId(), detail.getName()));
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
	
//	private ProductDetailEntity findItemDetailFromList(ComboItem item) {
//		for(ProductDetailEntity entity: details){
//			if(entity.getItemDetailId() == item.getKey()){
//				return entity;
//			}
//		}
//		return null;
//	}

	
	@SuppressWarnings("unchecked")
	private void loadPartyCombo() {
		partyCombo.removeAllItems();
		partyCombo.addItem(new ComboItem(0, ""));
		List<UserEntity> list = new ManageUsers().listUsers(UserType.PARTY.ordinal());
		for (UserEntity user : list) {
			partyCombo.addItem(new ComboItem(user.getUserId(), user.getUserName()));
		}
	}
	
	public void hideDialog(){
		this.setVisible(false);
		this.dispose();
	}
	
}
