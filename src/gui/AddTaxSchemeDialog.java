package gui;

import hibernate.TaxEntity;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import main.Constants;
import contoller.ManageTaxes;

public class AddTaxSchemeDialog extends JDialog {

	private static final long serialVersionUID = -3122620006507170324L;
	private static ManageTaxes manageTaxes = new ManageTaxes();
	
	private static final int TAX_SCHEME_COL = 0;
	private static final int PRI_TAX_NAME_COL = 1;
	private static final int PRI_TAX_RATE_COL = 2;
	private static final int SEC_TAX_NAME_COL = 3;
	private static final int SEC_TAX_RATE_COL = 4;
	private static final int SEC_COMP_COL = 5;
	private static int noOfExistingSchemes = 0;

	private DefaultTableModel tableModel;
	private List<TaxEntity> listTaxingSchemes;
	
	public AddTaxSchemeDialog(Frame parent) {
		super(parent);
		
		createLayout();

		setTitle("Add Taxing Scheme");
		setSize(800, 400);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
	}

	private void createLayout() {
		listTaxingSchemes = manageTaxes.listTaxingSchemes();
		noOfExistingSchemes = listTaxingSchemes.size();
		tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = 4447313472748838385L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if(row < noOfExistingSchemes && column == TAX_SCHEME_COL)
						return false;
				return true;
			}
		};

		for (int i = 0; i < Constants.taxColumnNames.length; i++) {
			tableModel.addColumn(Constants.taxColumnNames[i]);
		}
		
		Container panel = getContentPane();
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] { 0, 0 };
		gbl_panel_4.rowHeights = new int[] { 0, 0 };
		gbl_panel_4.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel_4);
		
		final JTable table = new JTable(tableModel){
				private static final long serialVersionUID = -5803441351466671297L;

				@SuppressWarnings({ "unchecked", "rawtypes" })
				public Class getColumnClass(int column) {
		                switch (column) {
		                    case 0:
		                        return String.class;
		                    case 1:
		                        return String.class;
		                    case 2:
		                        return Double.class;
		                    case 3:
		                        return String.class;
		                    case 4:
		                        return Double.class;
		                    default:
		                        return Boolean.class;
		                }
		            }
				
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
				private static final long serialVersionUID = 1920148703045758161L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table.setRowHeight(20);
			columnModel.getColumn(TAX_SCHEME_COL).setPreferredWidth(120);
			columnModel.getColumn(PRI_TAX_RATE_COL).setPreferredWidth(25);
			columnModel.getColumn(PRI_TAX_RATE_COL).setCellRenderer(rightRenderer);
			columnModel.getColumn(SEC_TAX_RATE_COL).setPreferredWidth(25);
			columnModel.getColumn(SEC_TAX_RATE_COL).setCellRenderer(rightRenderer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loadTaxingSchemes();
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(800, 400));
		GridBagConstraints gbc_table = new GridBagConstraints();
		gbc_table.fill = GridBagConstraints.BOTH;
		gbc_table.gridx = 0;
		gbc_table.gridy = 0;
		panel.add(scroll, gbc_table);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.anchor = GridBagConstraints.WEST;
		gbc_panel_6.insets = new Insets(0, 0, 0, 5);
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 1;
		panel.add(panel_6, gbc_panel_6);

		JButton btnNewButton = new JButton("Add");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel_6.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewButton_1.setVerticalAlignment(SwingConstants.TOP);
		panel_6.add(btnNewButton_1);
		
		panel_6.add(Box.createRigidArea(new Dimension(500, 5)));
		
		JButton btnNewButton_2 = new JButton("Save & Close");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton_2.setVerticalAlignment(SwingConstants.TOP);
		btnNewButton_2.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(btnNewButton_2);
		
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableModel.getRowCount() == 0) {
					tableModel.addRow(new Object[] { "", "", new Double("0.00"), "", new Double("0.00"), false});
				} else {
					Object valueAt = tableModel.getValueAt(
							tableModel.getRowCount() - 1, TAX_SCHEME_COL);
					if (valueAt != null
							&& valueAt.toString().trim().length() != 0) {
						tableModel.addRow(new Object[] { "", "", new Double("0.00"), "", new Double("0.00"), false});
					} else {
						String message = "The Tax Scheme Name cannot be empty!";
						JOptionPane.showMessageDialog(new JFrame(), message,
								"Dialog", JOptionPane.ERROR_MESSAGE);
						table.changeSelection(tableModel.getRowCount() - 1,
								TAX_SCHEME_COL, false, false);
						table.requestFocus();
					}
				}
			}
		});

		btnNewButton_1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tableModel.getRowCount() > noOfExistingSchemes)
					tableModel.removeRow(tableModel.getRowCount() - 1);
			}
		});
		
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e) {
				Vector dataVector = tableModel.getDataVector();
				int rowCount = tableModel.getRowCount();
				long currentTimeMillis = System.currentTimeMillis();
				if(!checkforNullAndDuplicate(dataVector, rowCount)){
					for (int i = 0; i < rowCount; i++) {
						Vector row = (Vector) dataVector.elementAt(i);
						TaxEntity entity = new TaxEntity(row.elementAt(0).toString(), (row.elementAt(1).toString().trim().length() != 0) ? row.elementAt(1).toString():"Tax 1", 
								Double.parseDouble(row.elementAt(2).toString()),(row.elementAt(3).toString().trim().length() != 0) ? row.elementAt(3).toString():"Tax 2", 
								Double.parseDouble(row.elementAt(4).toString()), Boolean.parseBoolean(row.elementAt(5).toString()), 
								currentTimeMillis, currentTimeMillis);
						if(i >= noOfExistingSchemes){
							manageTaxes.addTaxingScheme(entity);
						}else{
							manageTaxes.updateTaxingScheme(entity);
						}
					}
					hideDialog();
				}
			}

			@SuppressWarnings("rawtypes")
			private boolean checkforNullAndDuplicate(Vector dataVector, int rowCount) {
				
				boolean flag = false;
				List<Object> names = new ArrayList<Object>();
				for (int i = 0; i < rowCount; i++) {
					Vector row = (Vector) dataVector.elementAt(i);
					String elementAt = row.elementAt(0).toString();
					if (elementAt.trim().length() == 0) {
						JOptionPane
								.showMessageDialog(
										new JFrame(),
										"The tax scheme name cannot be empty.Remove the row or fill in the field!",
										"Dialog", JOptionPane.ERROR_MESSAGE);
						table.changeSelection(i,TAX_SCHEME_COL, false, false);
						table.requestFocus();
						flag = true;
						break;
					} else {
						if (names.contains(elementAt.toLowerCase())) {
							JOptionPane.showMessageDialog(new JFrame(),
									"Duplicate tax scheme name exists !",
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

	private void loadTaxingSchemes() {
		tableModel.setNumRows(0);
		if(noOfExistingSchemes > 0){
			for(int i=0; i<noOfExistingSchemes; i++){
				TaxEntity entity = listTaxingSchemes.get(i);
				tableModel.addRow(new Object[] {});
				tableModel.setValueAt(entity.getTaxSchemeName(), i, TAX_SCHEME_COL);
				tableModel.setValueAt(entity.getPrimaryTaxName(), i, PRI_TAX_NAME_COL);
				tableModel.setValueAt(entity.getPrimaryTaxRate(), i, PRI_TAX_RATE_COL);
				tableModel.setValueAt(entity.getSecondaryTaxName(), i, SEC_TAX_NAME_COL);
				tableModel.setValueAt(entity.getSecondaryTaxRate(), i, SEC_TAX_RATE_COL);
				tableModel.setValueAt(entity.getIsSecondaryCompound(), i, SEC_COMP_COL);
			}
		}
	}
	
	private void hideDialog(){
		setVisible(false);
		dispose();
	}

}
