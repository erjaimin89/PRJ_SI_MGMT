package gui;
import hibernate.ProductDetailEntity;
import hibernate.ProductEntity;
import hibernate.UserEntity;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
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
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.border.TitledBorder;
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
public class ProductFormPanel extends JPanel {

	private static final long serialVersionUID = 8436650672180775672L;
	private static final int columnNos = 9;
	private static final int DETAIL_COL = 0;
	private static final int SUB_TOTAL_COL = 9;
	private static final int DETAIL_ID = 10;
	private JComboBox partyCombo;
	private JTextField itemNameField;
	private JTextField quantityField;
	private JTextField itemLabelField;
	private JTextField itemStyleField;
	private JTextField itemContentsField;
	private JTextField itemWeightField;
	private JTextField itemPolyBagField;
	private JTextField itemBoxField;
	private JTextField itemColorsField;
	private DefaultTableModel tableModel;

	private ManageProducts manageProducts;

	public ProductFormPanel() {
		manageProducts = new ManageProducts();
		setBorder(new TitledBorder(null, "New Product", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), new Color(
						34, 139, 34)));
		setBackground(new Color(176, 224, 230));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0, 0 };
		layout.rowHeights = new int[] { 150, 450, 50 };
		layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
		setLayout(layout);

		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 4447313472748838385L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == SUB_TOTAL_COL)
					return false;
				return true;
			}
		};

		for (int i = 0; i < Constants.productColumnNames.length; i++) {
			tableModel.addColumn(Constants.productColumnNames[i]);
		}
		setLayoutComponents();
	}

	private void setLayoutComponents() {
		
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
		
		partyCombo = new JComboBox();
		GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
		gbc_orderByCombo.insets = new Insets(5, 5, 5, 5);
		gbc_orderByCombo.fill = GridBagConstraints.BOTH;
		gbc_orderByCombo.gridx = 1;
		gbc_orderByCombo.gridy = 0;
		panel_3.add(partyCombo, gbc_orderByCombo);
		
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
		quantityField.setText("0");
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
		add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel_4.setLayout(gbl_panel_4);

		// createEmptyRows();
		final JTable table = new JTable(tableModel){
			private static final long serialVersionUID = 8047541362047189204L;

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
			columnModel.getColumn(DETAIL_COL).setPreferredWidth(200);
			for (int i = 1; i < columnNos; i++) {
				columnModel.getColumn(i).setCellEditor(new IntegerEditor(0, Integer.MAX_VALUE));
				columnModel.getColumn(i).setCellRenderer(rightRenderer);
				columnModel.getColumn(i).setPreferredWidth(25);
			}
			columnModel.getColumn(SUB_TOTAL_COL).setPreferredWidth(100);
			columnModel.getColumn(SUB_TOTAL_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(DETAIL_ID).setPreferredWidth(0);
			columnModel.getColumn(DETAIL_ID).setWidth(0);
			columnModel.getColumn(DETAIL_ID).setMinWidth(0);
			columnModel.getColumn(DETAIL_ID).setMaxWidth(0);
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
							tableModel.setValueAt(rowCount, i, SUB_TOTAL_COL);
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
						tableModel.setValueAt(rowCount, i, SUB_TOTAL_COL);
						total += rowCount;
					}
					quantityField.setText(total.toString());
				default:
					break;
				}
			}
		});

		JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Remove");
        popupMenu.add(deleteItem);
        table.setComponentPopupMenu(popupMenu);

        deleteItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
			    tableModel.removeRow(table.getSelectedRow());
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
		printButton.addActionListener(new PrintUIWindow(this));

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
						String message = "The Item Detail cannot be empty!";
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
				if (tableModel.getRowCount() > 0)
					tableModel.removeRow(tableModel.getRowCount() - 1);
			}
		});

		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.NONE;
		gbc_panel_7.anchor = GridBagConstraints.NORTH;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 2;
		add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[] { 0 };
		gbl_panel_7.rowHeights = new int[] { 0 };
		gbl_panel_7.columnWeights = new double[] { 1.0 };
		gbl_panel_7.rowWeights = new double[] { 1.0 };
		panel_7.setLayout(gbl_panel_7);

		JButton btnSaveProduct = new JButton("Save Product");
		btnSaveProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSaveProduct = new GridBagConstraints();
		gbc_btnSaveProduct.anchor = GridBagConstraints.CENTER;
		gbc_btnSaveProduct.gridx = 0;
		gbc_btnSaveProduct.gridy = 0;
		panel_7.add(btnSaveProduct, gbc_btnSaveProduct);
		
		btnSaveProduct.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				String itemName = itemNameField.getText();
				if(partyCombo.getSelectedIndex() <= 0){
					JOptionPane.showMessageDialog(new JFrame(),	"The Party must be selected !", "Error", JOptionPane.ERROR_MESSAGE);
				}else if (itemName.isEmpty()) {
					String message = "Item Name Field must be filled in !";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);
					itemNameField.grabFocus();
				}else if (itemStyleField.getText().isEmpty()) {
					String message = "Style Field must be filled in !";
					JOptionPane.showMessageDialog(new JFrame(), message,
							"Dialog", JOptionPane.ERROR_MESSAGE);
					itemStyleField.grabFocus();
				} else if(tableModel.getRowCount() == 0){
					JOptionPane.showMessageDialog(new JFrame(),	"Add at least one detail to the table !",	"Error", JOptionPane.ERROR_MESSAGE);
				}else {
					long currentTimeMillis = System.currentTimeMillis();
					ComboItem selectedParty = (ComboItem)partyCombo.getSelectedItem();
					ProductEntity entity = new ProductEntity(itemName, selectedParty.getKey(), selectedParty.getValue(), Integer
							.parseInt(quantityField.getText()), 0, 0, itemLabelField.getText(), itemStyleField.getText(),
							itemContentsField.getText(), itemWeightField.getText(), itemPolyBagField.getText(),
							itemBoxField.getText(), itemColorsField.getText(),
							currentTimeMillis, currentTimeMillis);
					TreeSet<ProductDetailEntity> entities = new TreeSet<ProductDetailEntity>();
					Vector dataVector = tableModel.getDataVector();
					int rowCount = tableModel.getRowCount();

					if (!checkforNullAndDuplicate(dataVector, rowCount)) {

						for (int i = 0; i < rowCount; i++) {
							Vector row = (Vector) dataVector.elementAt(i);
							entities.add(new ProductDetailEntity(row.elementAt(0).toString(), Integer.parseInt(row.elementAt(1).toString()), 
									Integer.parseInt(row.elementAt(2).toString()),Integer.parseInt(row.elementAt(3).toString()), 
									Integer.parseInt(row.elementAt(4).toString()), Integer.parseInt(row.elementAt(5).toString()), 
									Integer.parseInt(row.elementAt(6).toString()), Integer.parseInt(row.elementAt(7).toString()), 
									Integer.parseInt(row.elementAt(8).toString()), Integer.parseInt(row.elementAt(9).toString()), 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0));
						}
						if (!manageProducts.isProductExists(itemName, selectedParty.getKey())) {
							boolean addProduct = false;
							entity.setDetails(entities);
							addProduct = manageProducts.addProduct(entity);
							if (addProduct) {
								String message = "New Product added successfully !";
								JOptionPane.showMessageDialog(new JFrame(),
										message, "Dialog",
										JOptionPane.INFORMATION_MESSAGE);
								clearForm();
							} else {
								String message = "Error while adding a new product !";
								JOptionPane.showMessageDialog(new JFrame(),
										message, "Dialog",
										JOptionPane.ERROR_MESSAGE);
							}
						} else {
							String message = "Product with same name/code already exists under this party !";
							JOptionPane.showMessageDialog(new JFrame(),
									message, "Dialog",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			private boolean checkforNullAndDuplicate(Vector dataVector,
					int rowCount) {
				boolean flag = false;
				List<Object> names = new ArrayList<Object>();
				for (int i = 0; i < rowCount; i++) {
					Vector row = (Vector) dataVector.elementAt(i);
					String elementAt = row.elementAt(0).toString();
					if (elementAt.trim().length() == 0) {
						JOptionPane.showMessageDialog(
										new JFrame(),
										"The item detail cannot be empty.Remove the row or fill in the field!",
										"Dialog", JOptionPane.ERROR_MESSAGE);
						table.changeSelection(i,DETAIL_COL, false, false);
						table.requestFocus();
						flag = true;
						break;
					} else {
						if (names.contains(elementAt.toLowerCase())) {
							JOptionPane.showMessageDialog(new JFrame(),
									"Item detail name must be unique!",
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

	public void clearForm() {
		itemNameField.setText("");
		quantityField.setText("0");
		itemLabelField.setText("");
		itemStyleField.setText("");
		itemContentsField.setText("");
		itemWeightField.setText("");
		itemPolyBagField.setText("");
		itemBoxField.setText("");
		itemColorsField.setText("");
		loadPartyCombo();
		partyCombo.setSelectedIndex(0);
		clearTable();
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

	private void clearTable() {
		tableModel.setNumRows(0);
	}

	public void focusTextField() {
		itemNameField.requestFocusInWindow();
	}

}
