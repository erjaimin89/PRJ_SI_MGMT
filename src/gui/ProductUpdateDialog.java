package gui;

import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import java.util.EventObject;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JButton;
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
import javax.swing.text.JTextComponent;

import main.Constants;
import main.IntegerEditor;
import main.Utils;
import contoller.ManageProducts;

public class ProductUpdateDialog extends JDialog {

	private static final long serialVersionUID = -1025288202869665025L;
	
	private static ManageProducts manageProducts = new ManageProducts();
	private static final int columnNos = 9;
	private static final int DETAIL_COL = 0;
	private static final int SUB_TOT_COL = 9;
	private static final int DETAIL_ID = 10;
	private static int noOfExistingProducts = 0;
	
	private DefaultTableModel tableModel;
	private JTextField partyNameField;
	private JTextField itemNameField;
	private JTextField quantityField;
	private JTextField itemLabelField;
	private JTextField itemStyleField;
	private JTextField itemContentsField;
	private JTextField itemWeightField;
	private JTextField itemPolyBagField;
	private JTextField itemBoxField;
	private JTextField itemColorsField;
	private ProductEntity productEntity;
	
	private boolean flag = false;
	
	public ProductUpdateDialog(Frame parent, ProductEntity productEntity) {
		super(parent);
		this.productEntity = productEntity;
	}
	
	public boolean showDialog(){
		createLayout();

		setTitle("View/Update Product Inventory");
		setSize(800, 700);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
		return flag;
	}


	private void createLayout() {
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -61056991641205122L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == SUB_TOT_COL || column == DETAIL_ID)
						return false;
				return true;
			}
			
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case DETAIL_ID:
					return Long.class;
				default:
					return String.class;
				}
			}
		};

		for (int i = 0; i < Constants.productColumnNames.length; i++) {
			tableModel.addColumn(Constants.productColumnNames[i]);
		}
		
		JPanel panel = (JPanel) getContentPane();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 150, 450, 50 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		panel.setLayout(layout);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(176, 224, 230));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 0, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 0;
		add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] { 0, 0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel_3.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,0.0, Double.MIN_VALUE };
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblParty = new JLabel("Party Name :");
		lblParty.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblParty = new GridBagConstraints();
		gbc_lblParty.insets = new Insets(5, 5, 0, 0);
		gbc_lblParty.anchor = GridBagConstraints.EAST;
		gbc_lblParty.gridx = 0;
		gbc_lblParty.gridy = 0;
		panel_3.add(lblParty, gbc_lblParty);
		
		partyNameField = new JTextField();
		partyNameField.setColumns(20);
		partyNameField.setText(productEntity.getPartyName());
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
		itemNameField.setText(productEntity.getItemName());
//		itemNameField.setEditable(false);
		GridBagConstraints gbc_itemNameField = new GridBagConstraints();
		gbc_itemNameField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemNameField.gridx = 1;
		gbc_itemNameField.gridy = 1;
		panel_3.add(itemNameField, gbc_itemNameField);

		JLabel lblQuantity = new JLabel("Quantity on Hand :");
		lblQuantity.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblQuantity = new GridBagConstraints();
		gbc_lblQuantity.insets = new Insets(5, 20, 0, 0);
		gbc_lblQuantity.anchor = GridBagConstraints.EAST;
		gbc_lblQuantity.gridx = 2;
		gbc_lblQuantity.gridy = 0;
		panel_3.add(lblQuantity, gbc_lblQuantity);

		quantityField = new JTextField();
		quantityField.setEditable(false);
		quantityField.setText(productEntity.getQuantity().toString());
		quantityField.setFont(new Font("Tahoma", Font.BOLD, 12));
		quantityField.setColumns(20);
		quantityField.setHorizontalAlignment(JTextField.RIGHT);
		GridBagConstraints gbc_quantityField = new GridBagConstraints();
		gbc_quantityField.insets = new Insets(5, 5, 0, 0);
		gbc_quantityField.anchor = GridBagConstraints.WEST;
		gbc_quantityField.gridx = 3;
		gbc_quantityField.gridy = 0;
		panel_3.add(quantityField, gbc_quantityField);
		
		JLabel lblItemlabel = new JLabel("Label :");
		lblItemlabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemlabel = new GridBagConstraints();
		gbc_lblItemlabel.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemlabel.anchor = GridBagConstraints.EAST;
		gbc_lblItemlabel.gridx = 0;
		gbc_lblItemlabel.gridy = 3;
		panel_3.add(lblItemlabel, gbc_lblItemlabel);

		itemLabelField = new JTextField();
		itemLabelField.setColumns(20);
		itemLabelField.setText(productEntity.getItemLabel());
		GridBagConstraints gbc_itemLabelField = new GridBagConstraints();
		gbc_itemLabelField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemLabelField.gridx = 1;
		gbc_itemLabelField.gridy = 3;
		panel_3.add(itemLabelField, gbc_itemLabelField);
		
		JLabel lblItemStyle = new JLabel("Style :");
		lblItemStyle.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemStyle = new GridBagConstraints();
		gbc_lblItemStyle.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemStyle.anchor = GridBagConstraints.EAST;
		gbc_lblItemStyle.gridx = 0;
		gbc_lblItemStyle.gridy = 2;
		panel_3.add(lblItemStyle, gbc_lblItemStyle);

		itemStyleField = new JTextField();
		itemStyleField.setColumns(20);
		itemStyleField.setText(productEntity.getItemStyle());
		GridBagConstraints gbc_itemStyleField = new GridBagConstraints();
		gbc_itemStyleField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemStyleField.gridx = 1;
		gbc_itemStyleField.gridy = 2;
		panel_3.add(itemStyleField, gbc_itemStyleField);
		
		JLabel lblItemContent = new JLabel("Contents :");
		lblItemContent.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemContent = new GridBagConstraints();
		gbc_lblItemContent.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemContent.anchor = GridBagConstraints.EAST;
		gbc_lblItemContent.gridx = 2;
		gbc_lblItemContent.gridy = 1;
		panel_3.add(lblItemContent, gbc_lblItemContent);
		
		itemContentsField = new JTextField();
		itemContentsField.setColumns(20);
		itemContentsField.setText(productEntity.getItemContents());
		GridBagConstraints gbc_itemContentsField = new GridBagConstraints();
		gbc_itemContentsField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemContentsField.gridx = 3;
		gbc_itemContentsField.gridy = 1;
		panel_3.add(itemContentsField, gbc_itemContentsField);
		
		JLabel lblItemWeight = new JLabel("Weight :");
		lblItemWeight.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemWeight = new GridBagConstraints();
		gbc_lblItemWeight.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemWeight.anchor = GridBagConstraints.EAST;
		gbc_lblItemWeight.gridx = 0;
		gbc_lblItemWeight.gridy = 4;
		panel_3.add(lblItemWeight, gbc_lblItemWeight);

		itemWeightField = new JTextField();
		itemWeightField.setColumns(20);
		itemWeightField.setText(productEntity.getItemWeight());
		GridBagConstraints gbc_itemWeightField = new GridBagConstraints();
		gbc_itemWeightField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemWeightField.gridx = 1;
		gbc_itemWeightField.gridy = 4;
		panel_3.add(itemWeightField, gbc_itemWeightField);
		
		JLabel lblItemPolyBag = new JLabel("Packing/Poly bag :");
		lblItemPolyBag.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemPolyBag = new GridBagConstraints();
		gbc_lblItemPolyBag.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemPolyBag.anchor = GridBagConstraints.EAST;
		gbc_lblItemPolyBag.gridx = 2;
		gbc_lblItemPolyBag.gridy = 2;
		panel_3.add(lblItemPolyBag, gbc_lblItemPolyBag);
		
		itemPolyBagField = new JTextField();
		itemPolyBagField.setColumns(20);
		itemPolyBagField.setText(productEntity.getItemPolyBag());
		GridBagConstraints gbc_itemPolyBagField = new GridBagConstraints();
		gbc_itemPolyBagField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemPolyBagField.gridx = 3;
		gbc_itemPolyBagField.gridy = 2;
		panel_3.add(itemPolyBagField, gbc_itemPolyBagField);
		
		JLabel lblItemBox = new JLabel("Packing/Box :");
		lblItemBox.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemBox = new GridBagConstraints();
		gbc_lblItemBox.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemBox.anchor = GridBagConstraints.EAST;
		gbc_lblItemBox.gridx = 2;
		gbc_lblItemBox.gridy = 3;
		panel_3.add(lblItemBox, gbc_lblItemBox);

		itemBoxField = new JTextField();
		itemBoxField.setColumns(20);
		itemBoxField.setText(productEntity.getItemBox());
		GridBagConstraints gbc_itemBoxField = new GridBagConstraints();
		gbc_itemBoxField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemBoxField.gridx = 3;
		gbc_itemBoxField.gridy = 3;
		panel_3.add(itemBoxField, gbc_itemBoxField);
		
		JLabel lblItemColors = new JLabel("Packing/Colors :");
		lblItemColors.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblItemColors = new GridBagConstraints();
		gbc_lblItemColors.insets = new Insets(5, 5, 0, 0);
		gbc_lblItemColors.anchor = GridBagConstraints.EAST;
		gbc_lblItemColors.gridx = 2;
		gbc_lblItemColors.gridy = 4;
		panel_3.add(lblItemColors, gbc_lblItemColors);
		
		itemColorsField = new JTextField();
		itemColorsField.setColumns(20);
		itemColorsField.setText(productEntity.getItemColors());
		GridBagConstraints gbc_itemColorsField = new GridBagConstraints();
		gbc_itemColorsField.insets = new Insets(5, 5, 0, 0);
		gbc_itemNameField.anchor = GridBagConstraints.WEST;
		gbc_itemColorsField.gridx = 3;
		gbc_itemColorsField.gridy = 4;
		panel_3.add(itemColorsField, gbc_itemColorsField);

		JPanel panel_4 = new JPanel();
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
			private static final long serialVersionUID = -591890464258668472L;

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
			columnModel.getColumn(DETAIL_ID).setPreferredWidth(0);
			columnModel.getColumn(DETAIL_ID).setWidth(0);
			columnModel.getColumn(DETAIL_ID).setMinWidth(0);
			columnModel.getColumn(DETAIL_ID).setMaxWidth(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		loadProductDetails();
		
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
					
				case TableModelEvent.DELETE:
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
				default:
					break;
				}
			}
		});
		
		final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Remove");
        popupMenu.add(deleteItem);

        deleteItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			    tableModel.removeRow(table.getSelectedRow());
			}
		});
        
        table.addMouseListener(new MouseAdapter() {
        	public void mousePressed(MouseEvent event){
        		if(SwingUtilities.isRightMouseButton(event)){
	        		Point point = event.getPoint();
	                int currentRow = table.rowAtPoint(point);
	                if(currentRow >= noOfExistingProducts){
	                	table.setRowSelectionInterval(currentRow, currentRow);
	                	popupMenu.show(table.getTableHeader(), event.getX(), event.getY());
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

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.anchor = GridBagConstraints.WEST;
		gbc_panel_6.insets = new Insets(0, 0, 0, 5);
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		panel_4.add(panel_6, gbc_panel_6);

		JButton btnNewButton = new JButton("");
		btnNewButton.setBorderPainted(false);
		btnNewButton.setIcon(Utils.getImageIcon("add.png"));
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel_6.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setIcon(Utils.getImageIcon("remove.png"));
		btnNewButton_1.setVerticalAlignment(SwingConstants.TOP);
		panel_6.add(btnNewButton_1);
		
		JButton printButton = new JButton("");
		printButton.setBorderPainted(false);
		printButton.setIcon(Utils.getImageIcon("print.png"));
		printButton.setVerticalAlignment(SwingConstants.TOP);
		printButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(printButton);
		printButton.addActionListener(new PrintUIWindow(panel));
		
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableModel.getRowCount() == 0) {
					tableModel.addRow(new Object[] { "", 0, 0, 0, 0, 0, 0, 0, 0, 0 });
				} else {
					Object valueAt = tableModel.getValueAt(
							tableModel.getRowCount() - 1, DETAIL_COL);
					if (valueAt != null
							&& valueAt.toString().trim().length() != 0) {
						tableModel.addRow(new Object[] { "", 0, 0, 0, 0, 0, 0, 0, 0, 0 });
					} else {
						String message = "The Item detail cannot be empty!";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.ERROR_MESSAGE);
						table.changeSelection(tableModel.getRowCount() - 1,
								DETAIL_COL, false, false);
						table.requestFocus();
					}
				}
			}
		});

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableModel.getRowCount() > noOfExistingProducts)
					tableModel.removeRow(tableModel.getRowCount() - 1);
			}
		});
		

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
		
		JButton btnUpdateProduct = new JButton("Update Product");
		btnUpdateProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveProduct = new GridBagConstraints();
		gbc_btnSaveProduct.anchor = GridBagConstraints.CENTER;
		gbc_btnSaveProduct.gridx = 2;
		gbc_btnSaveProduct.gridy = 0;
		panel_7.add(btnUpdateProduct, gbc_btnSaveProduct);
		
		btnUpdateProduct.addActionListener(new ActionListener() {
			
			@SuppressWarnings({ "rawtypes" })
			public void actionPerformed(ActionEvent e) {
					long currentTimeMillis = System.currentTimeMillis();
					TreeSet<ProductDetailEntity> entities = new TreeSet<ProductDetailEntity>();
					Vector dataVector = tableModel.getDataVector();
					int rowCount = tableModel.getRowCount();
					Set<ProductDetailEntity> details = productEntity.getDetails();
					
					if (!checkforNullAndDuplicate(dataVector, rowCount)) {
						
						for(int i=0; i< noOfExistingProducts; i++){
							Vector row = (Vector) dataVector.elementAt(i);
							Long detailId = (Long) row.elementAt(DETAIL_ID);
							for(ProductDetailEntity entity : details){
								if(entity.getItemDetailId() == detailId){
									entity.setName(row.elementAt(DETAIL_COL).toString());
									entity.setXsmall(Integer.parseInt(row.elementAt(1).toString()));
									entity.setSmall(Integer.parseInt(row.elementAt(2).toString()));
									entity.setMedium(Integer.parseInt(row.elementAt(3).toString()));
									entity.setLarge(Integer.parseInt(row.elementAt(4).toString()));
									entity.setXlarge(Integer.parseInt(row.elementAt(5).toString()));
									entity.setXxlarge(Integer.parseInt(row.elementAt(6).toString()));
									entity.setXxxlarge(Integer.parseInt(row.elementAt(7).toString()));
									entity.setMix(Integer.parseInt(row.elementAt(8).toString()));
									entity.setSubTotal(Integer.parseInt(row.elementAt(9).toString()));
									entities.add(entity);
								}
							}
						}
						
						for (int i = noOfExistingProducts; i < rowCount; i++) {
							Vector row = (Vector) dataVector.elementAt(i);
							entities.add(new ProductDetailEntity(row.elementAt(0).toString(), Integer.parseInt(row.elementAt(1).toString()), 
									Integer.parseInt(row.elementAt(2).toString()), Integer.parseInt(row.elementAt(3).toString()), 
									Integer.parseInt(row.elementAt(4).toString()), Integer.parseInt(row.elementAt(5).toString()), 
									Integer.parseInt(row.elementAt(6).toString()), Integer.parseInt(row.elementAt(7).toString()), 
									Integer.parseInt(row.elementAt(8).toString()), Integer.parseInt(row.elementAt(9).toString()), 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0));
						}
							boolean updateProduct = false;
							
							productEntity.setQuantity(Integer.parseInt(quantityField.getText()));
							productEntity.setItemLabel(itemLabelField.getText());
							productEntity.setItemStyle(itemStyleField.getText());
							productEntity.setItemContents(itemContentsField.getText());
							productEntity.setItemWeight(itemWeightField.getText());
							productEntity.setItemPolyBag(itemPolyBagField.getText());
							productEntity.setItemBox(itemBoxField.getText());
							productEntity.setItemColors(itemColorsField.getText());
							
							productEntity.setDetails(entities);
							productEntity.setUpdatedOn(currentTimeMillis);
							
							if(!productEntity.getItemName().equalsIgnoreCase(itemNameField.getText())){
								boolean productExists = manageProducts.isProductExists(itemNameField.getText(), productEntity.getPartyId());
								if(productExists){
									String message = "Product with same name/code already exists under this party !";
									JOptionPane.showMessageDialog(new JFrame(),
											message, "Dialog",
											JOptionPane.ERROR_MESSAGE);
								}else{
									productEntity.setItemName(itemNameField.getText());
									updateProduct = manageProducts.updateProduct(productEntity);
									if (updateProduct) {
										String message = "Product updated successfully !";
										JOptionPane.showMessageDialog(new JFrame(),
												message, "Dialog",
												JOptionPane.INFORMATION_MESSAGE);
										hideDialog();
										flag = true;
									} else {
										String message = "Error while updating the product !";
										JOptionPane.showMessageDialog(new JFrame(),
												message, "Dialog",
												JOptionPane.ERROR_MESSAGE);
									}
								}
							}else{
								updateProduct = manageProducts.updateProduct(productEntity);
								if (updateProduct) {
									String message = "Product updated successfully !";
									JOptionPane.showMessageDialog(new JFrame(),
											message, "Dialog",
											JOptionPane.INFORMATION_MESSAGE);
									hideDialog();
									flag = true;
								} else {
									String message = "Error while updating the product !";
									JOptionPane.showMessageDialog(new JFrame(),
											message, "Dialog",
											JOptionPane.ERROR_MESSAGE);
								}
							} 
							
							
					}
			}

			@SuppressWarnings("rawtypes")
			private boolean checkforNullAndDuplicate(Vector dataVector,
					int rowCount) {
				boolean flag = false;
				List<Object> names = new ArrayList<Object>();
				for (int i = 0; i < rowCount; i++) {
					Vector row = (Vector) dataVector.elementAt(i);
					String elementAt = row.elementAt(0).toString();
					if (elementAt.trim().length() == 0) {
						JOptionPane.showMessageDialog(new JFrame(),
										"The item detail cannot be empty.Remove the row or fill in the field!",
										"Dialog", JOptionPane.ERROR_MESSAGE);
						table.changeSelection(i,DETAIL_COL, false, false);
						table.requestFocus();
						flag = true;
						break;
					} else {
						if (names.contains(elementAt.toLowerCase())) {
							JOptionPane.showMessageDialog(new JFrame(),
									"The field name already exists !",
									"Dialog", JOptionPane.ERROR_MESSAGE);
							flag = true;
							break;
						} else {
							names.add(elementAt.toLowerCase());
						}
					}
				}
				return flag;
			}
		});
		
	}
	
	private void loadProductDetails() {
		tableModel.setNumRows(0);
		Set<ProductDetailEntity> details = productEntity.getDetails();
		noOfExistingProducts = details.size();
			for(ProductDetailEntity detail : details){
				tableModel.addRow(new Object[] {detail.getName(), detail.getXsmall(), detail.getSmall(),
						detail.getMedium(), detail.getLarge(), detail.getXlarge(), detail.getXxlarge(), detail.getXxxlarge(),
						detail.getMix(), detail.getSubTotal(), detail.getItemDetailId()});
			}
	}

	private void hideDialog(){
		setVisible(false);
		dispose();
	}
}
