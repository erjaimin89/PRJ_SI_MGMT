package gui;

import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import main.Constants;
import main.MultiLineCellRenderer;
import main.Utils;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ViewItemInTransitDetails extends JDialog {
	private static final long serialVersionUID = 4950048244407397514L;
	
	private DefaultTableModel tableModel;
	private DefaultTableModel tableModel1;
	private DefaultTableModel tableModel2;
	private JTable table;
	private JTable table1;
	private JTable table2;
	private JTextField partyNameField;
	private JTextField itemNameField;
	private JTextField quantityField;
	private JTextField quantityHand;
	private JTextField quantityFieldProd;
	private JTextField styleField;
	private String itemName;
	private String style;
	private String partyName;
	private String qty;
	private String qtyHand;
	private Integer qtyProd = 0;
	private List<Object[]> inTransitDetails;
	private Set<ProductDetailEntity> details;
	
	public ViewItemInTransitDetails(Frame parent, ProductEntity productEntity, List<Object[]> inTransitDetails) {
		super(parent);
		this.itemName = productEntity.getItemName();
		this.style = productEntity.getItemStyle();
		this.partyName = productEntity.getPartyName();
		this.qtyHand = productEntity.getQuantity().toString();
		this.qty = productEntity.getTransitQuantity().toString();
		this.inTransitDetails = inTransitDetails;
		this.details = productEntity.getDetails();
		createLayout();

		setTitle("View Quantity in transit details");
		setSize(800, 600);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
	}

	private void createLayout() {
		tableModel = new DefaultTableModel(){
			private static final long serialVersionUID = -2556058227181241875L;
			@Override
			public boolean isCellEditable(int row, int column) {
						return false;
			}
			@Override	
			public Class getColumnClass(int columnIndex) {
			        return String.class;
			 }
		};
		
		for (int i = 0; i < Constants.productColumnNames.length; i++) {
			tableModel.addColumn(Constants.productColumnNames[i]);
		}
		
		tableModel1 = new DefaultTableModel() {
			private static final long serialVersionUID = -3478869768308103039L;

			@Override
			public boolean isCellEditable(int row, int column) {
						return false;
			}
			@Override	
			public Class getColumnClass(int columnIndex) {
			        return String.class;
			 }
		};

		for (int i = 0; i < Constants.productInTransitColumnNames.length; i++) {
			tableModel1.addColumn(Constants.productInTransitColumnNames[i]);
		}
		
		tableModel2 = new DefaultTableModel() {
			private static final long serialVersionUID = -6214987240227921484L;
			@Override
			public boolean isCellEditable(int row, int column) {
						return false;
			}
			@Override	
			public Class getColumnClass(int columnIndex) {
			        return String.class;
			 }
		};

		for (int i = 0; i < Constants.productInProductionColumnNames.length; i++) {
			tableModel2.addColumn(Constants.productInProductionColumnNames[i]);
		}
		
		JPanel panel = (JPanel) getContentPane();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0};
		gridBagLayout.rowHeights = new int[] {0};
		gridBagLayout.columnWeights = new double[]{1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		panel.setLayout(gridBagLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		panel.add(tabbedPane, gbc_tabbedPane);
		
		JButton printButton = new JButton("");
		printButton.setBorderPainted(false);
		printButton.setIcon(Utils.getImageIcon("print.png"));
		printButton.setVerticalAlignment(SwingConstants.TOP);
		printButton.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_printButton = new GridBagConstraints();
		gbc_printButton.fill = GridBagConstraints.BOTH;
		gbc_printButton.gridx = 0;
		gbc_printButton.gridy = 1;
		panel.add(printButton, gbc_printButton);
		printButton.addActionListener(new PrintUIWindow(this));
		
		JPanel panel_1 = new JPanel();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 60, 450 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0 };
		panel_1.setLayout(layout);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(layout);
		
		JPanel hpanel = new JPanel();
		hpanel.setLayout(layout);
		
		tabbedPane.addTab("In Hand", null, hpanel, null);
		tabbedPane.addTab("In Transit", null, panel_1, null);
		tabbedPane.addTab("In Production", null, panel_2, null);
		 //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        
        JPanel panel_9 = new JPanel();
        panel_9.setBackground(new Color(176, 224, 230));
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.insets = new Insets(0, 0, 0, 0);
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 0;
		hpanel.add(panel_9, gbc_panel_9);
		GridBagLayout gbl_panel_9 = new GridBagLayout();
		gbl_panel_9.columnWidths = new int[] { 0, 0, 0, 0, 0};
		gbl_panel_9.rowHeights = new int[] { 0, 0, 0};
		gbl_panel_9.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_9.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_9.setLayout(gbl_panel_9);
		
		JLabel lblParty = new JLabel("Party Name :");
		lblParty.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblParty = new GridBagConstraints();
		gbc_lblParty.insets = new Insets(5, 5, 0, 0);
		gbc_lblParty.anchor = GridBagConstraints.EAST;
		gbc_lblParty.gridx = 0;
		gbc_lblParty.gridy = 0;
		panel_9.add(lblParty, gbc_lblParty);
		
		JTextField partyNameField3 = new JTextField();
		partyNameField3.setColumns(20);
		partyNameField3.setText(partyName);
		partyNameField3.setEditable(false);
		GridBagConstraints gbc_orderByCombo3 = new GridBagConstraints();
		gbc_orderByCombo3.insets = new Insets(5, 5, 5, 5);
		gbc_orderByCombo3.fill = GridBagConstraints.BOTH;
		gbc_orderByCombo3.gridx = 1;
		gbc_orderByCombo3.gridy = 0;
		panel_9.add(partyNameField3, gbc_orderByCombo3);
		
		JLabel lblItemNamecode3 = new JLabel("Item Name/Code :");
		lblItemNamecode3.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemNamecode3 = new GridBagConstraints();
		gbc_lblItemNamecode3.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemNamecode3.anchor = GridBagConstraints.EAST;
		gbc_lblItemNamecode3.gridx = 0;
		gbc_lblItemNamecode3.gridy = 1;
		panel_9.add(lblItemNamecode3, gbc_lblItemNamecode3);

		JTextField itemNameField3 = new JTextField();
		itemNameField3.setColumns(20);
		itemNameField3.setText(itemName);
		itemNameField3.setEditable(false);
		GridBagConstraints gbc_itemNameField3 = new GridBagConstraints();
		gbc_itemNameField3.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField3.anchor = GridBagConstraints.WEST;
		gbc_itemNameField3.gridx = 1;
		gbc_itemNameField3.gridy = 1;
		panel_9.add(itemNameField3, gbc_itemNameField3);
		
		JLabel lblStyle = new JLabel("Style :");
		lblStyle.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblStyle = new GridBagConstraints();
		gbc_lblStyle.insets = new Insets(5, 5, 0, 0);
		gbc_lblStyle.anchor = GridBagConstraints.EAST;
		gbc_lblStyle.gridx = 2;
		gbc_lblStyle.gridy = 1;
		panel_9.add(lblStyle, gbc_lblStyle);

		styleField = new JTextField();
		styleField.setColumns(20);
		styleField.setText(style);
		styleField.setEditable(false);
		GridBagConstraints gbc_styleField = new GridBagConstraints();
		gbc_styleField.insets = new Insets(5, 5, 0, 0);
		gbc_styleField.anchor = GridBagConstraints.WEST;
		gbc_styleField.gridx = 3;
		gbc_styleField.gridy = 1;
		panel_9.add(styleField, gbc_styleField);

		JLabel lblQuantity3 = new JLabel("Quantity In Hand :");
		lblQuantity3.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity3 = new GridBagConstraints();
		gbc_lblQuantity3.insets = new Insets(5, 20, 0, 0);
		gbc_lblQuantity3.anchor = GridBagConstraints.EAST;
		gbc_lblQuantity3.gridx = 2;
		gbc_lblQuantity3.gridy = 0;
		panel_9.add(lblQuantity3, gbc_lblQuantity3);

		quantityHand = new JTextField();
		quantityHand.setEditable(false);
		quantityHand.setText(qtyHand);
		quantityHand.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityHand.setColumns(20);
		quantityHand.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_quantityHand = new GridBagConstraints();
		gbc_quantityHand.insets = new Insets(5, 5, 0, 0);
		gbc_quantityHand.anchor = GridBagConstraints.WEST;
		gbc_quantityHand.gridx = 3;
		gbc_quantityHand.gridy = 0;
		panel_9.add(quantityHand, gbc_quantityHand);
		
		JPanel panel_11 = new JPanel();
		GridBagConstraints gbc_panel_11 = new GridBagConstraints();
		gbc_panel_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_11.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_11.gridx = 0;
		gbc_panel_11.gridy = 1;
		hpanel.add(panel_11, gbc_panel_11);
		GridBagLayout gbl_panel_11 = new GridBagLayout();
		gbl_panel_11.columnWidths = new int[] { 0, 0 };
		gbl_panel_11.rowHeights = new int[] { 0, 0 };
		gbl_panel_11.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_11.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_11.setLayout(gbl_panel_11);
		
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		 table = new JTable(tableModel);
			TableColumnModel columnModel3 = table.getColumnModel();
			table.setTableHeader(new JTableHeader(columnModel3) {
				private static final long serialVersionUID = 5439523961292839698L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table.setRowHeight(20);
			for (int i = 1; i <= 9; i++) {
				columnModel3.getColumn(i).setPreferredWidth(25);
				columnModel3.getColumn(i).setCellRenderer(rightRenderer);
			}
			columnModel3.getColumn(10).setPreferredWidth(0);
			columnModel3.getColumn(10).setMinWidth(0);
			columnModel3.getColumn(10).setMaxWidth(0);
			columnModel3.getColumn(10).setWidth(10);
		    JScrollPane scroll3 = new JScrollPane(table);
			scroll3.setPreferredSize(new Dimension(600, 400));
			GridBagConstraints gbc_table23 = new GridBagConstraints();
			gbc_table23.fill = GridBagConstraints.BOTH;
			gbc_table23.gridx = 0;
			gbc_table23.gridy = 0;
			panel_11.add(scroll3, gbc_table23);
			
			loadItemInHandDetails();	
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(176, 224, 230));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		panel_1.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0};
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblParty3 = new JLabel("Party Name :");
		lblParty3.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblParty3 = new GridBagConstraints();
		gbc_lblParty3.insets = new Insets(5, 5, 0, 0);
		gbc_lblParty3.anchor = GridBagConstraints.EAST;
		gbc_lblParty3.gridx = 0;
		gbc_lblParty3.gridy = 0;
		panel_3.add(lblParty3, gbc_lblParty3);
		
		partyNameField = new JTextField();
		partyNameField.setColumns(20);
		partyNameField.setText(partyName);
		partyNameField.setEditable(false);
		GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
		gbc_orderByCombo.insets = new Insets(5, 5, 5, 5);
		gbc_orderByCombo.fill = GridBagConstraints.BOTH;
		gbc_orderByCombo.gridx = 1;
		gbc_orderByCombo.gridy = 0;
		panel_3.add(partyNameField, gbc_orderByCombo);
		
		JLabel lblItemNamecode = new JLabel("Item Name/Code :");
		lblItemNamecode.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemNamecode = new GridBagConstraints();
		gbc_lblItemNamecode.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemNamecode.anchor = GridBagConstraints.EAST;
		gbc_lblItemNamecode.gridx = 0;
		gbc_lblItemNamecode.gridy = 1;
		panel_3.add(lblItemNamecode, gbc_lblItemNamecode);

		itemNameField = new JTextField();
		itemNameField.setColumns(20);
		itemNameField.setText(itemName);
		itemNameField.setEditable(false);
		GridBagConstraints gbc_itemNameField = new GridBagConstraints();
		gbc_itemNameField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemNameField.gridx = 1;
		gbc_itemNameField.gridy = 1;
		panel_3.add(itemNameField, gbc_itemNameField);

		JLabel lblStyle1 = lblStyle;
		JTextField styleField1 = styleField;
		panel_3.add(lblStyle1, gbc_lblStyle);
		panel_3.add(styleField1, gbc_styleField);
		
		JLabel lblQuantity = new JLabel("Quantity In Transit :");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.insets = new Insets(5, 20, 0, 0);
		gbc_lblQuantity.anchor = GridBagConstraints.EAST;
		gbc_lblQuantity.gridx = 2;
		gbc_lblQuantity.gridy = 0;
		panel_3.add(lblQuantity, gbc_lblQuantity);

		quantityField = new JTextField();
		quantityField.setEditable(false);
		quantityField.setText(qty);
		quantityField.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityField.setColumns(20);
		quantityField.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.insets = new Insets(5, 5, 0, 0);
		gbc_quantityField.anchor = GridBagConstraints.WEST;
		gbc_quantityField.gridx = 3;
		gbc_quantityField.gridy = 0;
		panel_3.add(quantityField, gbc_quantityField);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_4.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 1;
		panel_1.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

	    table1 = new JTable(tableModel1);
		TableColumnModel columnModel = table1.getColumnModel();
		table1.setTableHeader(new JTableHeader(columnModel) {
			private static final long serialVersionUID = -1101522060638723455L;

			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 32;
				return d;
			}
		});
		table1.setRowHeight(20);
		for (int i = 3; i <= 11; i++) {
			columnModel.getColumn(i).setPreferredWidth(25);
		}
		columnModel.getColumn(2).setPreferredWidth(50);
	    table1.setDefaultRenderer(String.class, new MultiLineCellRenderer());
	    JScrollPane scroll = new JScrollPane(table1);
		scroll.setPreferredSize(new Dimension(600, 400));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel_4.add(scroll, gbc_table);
		
		loadItemInTransitDetails();
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(new Color(176, 224, 230));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(0, 0, 0, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 0;
		panel_2.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[] { 0, 0, 0, 0, 0};
		gbl_panel_5.rowHeights = new int[] { 0, 0, 0};
		gbl_panel_5.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_5.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_5.setLayout(gbl_panel_5);
		
		JLabel lblParty1 = new JLabel("Party Name :");
		lblParty1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblParty1 = new GridBagConstraints();
		gbc_lblParty1.insets = new Insets(5, 5, 0, 0);
		gbc_lblParty1.anchor = GridBagConstraints.EAST;
		gbc_lblParty1.gridx = 0;
		gbc_lblParty1.gridy = 0;
		panel_5.add(lblParty1, gbc_lblParty1);
		
		JTextField partyNameField1 = new JTextField();
		partyNameField1.setColumns(20);
		partyNameField1.setText(partyName);
		partyNameField1.setEditable(false);
		GridBagConstraints gbc_orderByCombo1= new GridBagConstraints();
		gbc_orderByCombo1.insets = new Insets(5, 5, 5, 5);
		gbc_orderByCombo1.fill = GridBagConstraints.BOTH;
		gbc_orderByCombo1.gridx = 1;
		gbc_orderByCombo1.gridy = 0;
		panel_5.add(partyNameField1, gbc_orderByCombo1);
		
		JLabel lblItemNamecode1 = new JLabel("Item Name/Code :");
		lblItemNamecode1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemNamecode1 = new GridBagConstraints();
		gbc_lblItemNamecode1.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemNamecode1.anchor = GridBagConstraints.EAST;
		gbc_lblItemNamecode1.gridx = 0;
		gbc_lblItemNamecode1.gridy = 1;
		panel_5.add(lblItemNamecode1, gbc_lblItemNamecode1);
		
		JTextField itemNameField1 = new JTextField();
		itemNameField1.setColumns(20);
		itemNameField1.setText(itemName);
		itemNameField1.setEditable(false);
		GridBagConstraints gbc_itemNameField1 = new GridBagConstraints();
		gbc_itemNameField1.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField1.anchor = GridBagConstraints.WEST;
		gbc_itemNameField1.gridx = 1;
		gbc_itemNameField1.gridy = 1;
		panel_5.add(itemNameField1, gbc_itemNameField1);
		
		JLabel lblStyle2 = lblStyle;
		JTextField styleField2 = styleField;
		panel_5.add(lblStyle2, gbc_lblStyle);
		panel_5.add(styleField2, gbc_styleField);

		JLabel lblQuantityProd = new JLabel("Quantity In Production :");
		lblQuantityProd.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantityProd = new GridBagConstraints();
		gbc_lblQuantityProd.insets = new Insets(5, 20, 0, 0);
		gbc_lblQuantityProd.anchor = GridBagConstraints.EAST;
		gbc_lblQuantityProd.gridx = 2;
		gbc_lblQuantityProd.gridy = 0;
		panel_5.add(lblQuantityProd, gbc_lblQuantityProd);

		quantityFieldProd = new JTextField();
		quantityFieldProd.setEditable(false);
		quantityFieldProd.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityFieldProd.setColumns(20);
		quantityFieldProd.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_quantityFieldProd = new GridBagConstraints();
		gbc_quantityFieldProd.insets = new Insets(5, 5, 0, 0);
		gbc_quantityFieldProd.anchor = GridBagConstraints.WEST;
		gbc_quantityFieldProd.gridx = 3;
		gbc_quantityFieldProd.gridy = 0;
		panel_5.add(quantityFieldProd, gbc_quantityFieldProd);
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_7.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 1;
		panel_2.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 0, 0 };
		gbl_panel_7.rowHeights = new int[] { 0, 0 };
		gbl_panel_7.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_7.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_7.setLayout(gbl_panel_7);

	    table2 = new JTable(tableModel2);
		TableColumnModel columnModel2 = table2.getColumnModel();
		table2.setTableHeader(new JTableHeader(columnModel2) {
			private static final long serialVersionUID = 2746809679363493767L;

			@Override
			public Dimension getPreferredSize() {
				Dimension d = super.getPreferredSize();
				d.height = 32;
				return d;
			}
		});
		table2.setRowHeight(20);
		for (int i = 1; i <= 9; i++) {
			columnModel2.getColumn(i).setPreferredWidth(25);
			columnModel2.getColumn(i).setCellRenderer(rightRenderer);
		}
	    JScrollPane scroll2 = new JScrollPane(table2);
		scroll2.setPreferredSize(new Dimension(600, 400));
		GridBagConstraints gbc_table2 = new GridBagConstraints();
		gbc_table2.fill = GridBagConstraints.BOTH;
		gbc_table2.gridx = 0;
		gbc_table2.gridy = 0;
		panel_7.add(scroll2, gbc_table2);
		
		loadItemInProductionDetails();
		quantityFieldProd.setText(qtyProd.toString());
		
	}

	private void loadItemInHandDetails() {
		if(details!= null && !details.isEmpty()){
			for(ProductDetailEntity detail : details){
					tableModel.addRow(new Object[]{detail.getName(), detail.getXsmall(), detail.getSmall(),
							detail.getMedium(), detail.getLarge(), detail.getXlarge(), detail.getXxlarge(), detail.getXxxlarge(),
							detail.getMix(), detail.getSubTotal(), detail.getItemDetailId()});
				}
		}
	}

	private void loadItemInProductionDetails() {
		if(details!= null && !details.isEmpty()){
			for(ProductDetailEntity detail : details){
				if(detail.getTotal_production() > 0){
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
					
//					if((detail.getTotal_production() - detail.getTotal_transit()) > 0)
//						totalQty = detail.getTotal_production() - detail.getTotal_transit();
					totalQty = xQty + sQty + mQty + lQty + xlQty + xxlQty + xxxlQty +
							+ mixQty;
					
					qtyProd += totalQty;
					tableModel2.addRow(new Object[]{detail.getName(), xQty, sQty, mQty,
							lQty, xlQty, xxlQty, xxxlQty,
							mixQty, totalQty});
				}
				
			}
		}
	}

	private void loadItemInTransitDetails() {
		if(!inTransitDetails.isEmpty()){
			Map<Integer, List<Object[]>> detailIds = new HashMap<Integer, List<Object[]>>();
			for(Object[] next: inTransitDetails){
				Integer detailId = (Integer) next[1];
				if(detailIds.containsKey(detailId)){
					List<Object[]> list = detailIds.get(detailId);
					list.add(next);
					detailIds.put(detailId, list);
				}else{
					ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
					arrayList.add(next);
					detailIds.put(detailId, arrayList);
				}
			}
			
			if(!detailIds.isEmpty()){
				Set<Integer> keySet = detailIds.keySet();
				int i = 0;
				for(Integer key : keySet){
					List<Object[]> list = detailIds.get(key);
					StringBuilder lotNos = new StringBuilder();
					StringBuilder arrivalDates = new StringBuilder();
					StringBuilder size1 = new StringBuilder();
					StringBuilder size2 = new StringBuilder();
					StringBuilder size3 = new StringBuilder();
					StringBuilder size4 = new StringBuilder();
					StringBuilder size5 = new StringBuilder();
					StringBuilder size6 = new StringBuilder();
					StringBuilder size7 = new StringBuilder();
					StringBuilder size8 = new StringBuilder();
					StringBuilder total = new StringBuilder();
					int totalDetail = 0; 
					for(Object[] next : list){
						if(Integer.parseInt(next[11].toString()) > 0){
							total.append(next[11].toString()).append("\n");
							lotNos.append(next[0].toString()).append("\n");
							size1.append(next[3].toString()).append("\n");
							size2.append(next[4].toString()).append("\n");
							size3.append(next[5].toString()).append("\n");
							size4.append(next[6].toString()).append("\n");
							size5.append(next[7].toString()).append("\n");
							size6.append(next[8].toString()).append("\n");
							size7.append(next[9].toString()).append("\n");
							size8.append(next[10].toString()).append("\n");
							totalDetail += Integer.parseInt(next[11].toString());
							long parseLong = Long.parseLong(next[12].toString());
							if(parseLong>0){
								arrivalDates.append(Utils.formatOrderDate(parseLong)).append("\n");
							}else{
								arrivalDates.append("-").append("\n");
							}	
						}	
					}
					String detailName = list.get(0)[2].toString() +"\n"+totalDetail;
					if(totalDetail > 0){
						tableModel1.addRow(new Object[]{detailName, lotNos.toString(), arrivalDates.toString(), size1.toString(), size2.toString(), size3.toString(),
								size4.toString(), size5.toString(), size6.toString(), size7.toString(), size8.toString(), total.toString()});
						table1.setRowHeight(i, table1.getRowHeight() * list.size());
						i++;
					}
				}
			}
		}
	}
}
