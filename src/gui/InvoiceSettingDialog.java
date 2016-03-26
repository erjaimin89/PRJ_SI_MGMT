package gui;

import hibernate.InvoiceDetailsEntity;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import contoller.ManageTaxes;

public class InvoiceSettingDialog extends JDialog {

	private static final long serialVersionUID = 7090686909015060633L;
	
	private DefaultTableModel tableModel1;
	private DefaultTableModel tableModel2;
	private ManageTaxes manageTaxes;
	private List<InvoiceDetailsEntity> detailEntities;
	
	public InvoiceSettingDialog(Frame parent) {
		super(parent);
		manageTaxes = new ManageTaxes();
		createLayout();

		setTitle("Invoice Setting");
		setSize(600, 600);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(getParent());
		setResizable(false);
		setVisible(true);
	}

	private void createLayout() {
		tableModel1 = new DefaultTableModel() {
			private static final long serialVersionUID = 2648010937514675282L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0)
					return false;
				return true;
			}
		};
		tableModel2 = new DefaultTableModel() {
			private static final long serialVersionUID = -6103410523666869554L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return true;
			}
		};
		
		tableModel1.addColumn("ID");
		tableModel1.addColumn("HEADER");
		tableModel2.addColumn("ID");
		tableModel2.addColumn("FOOTER");
		
		loadTableModel1();
//		loadTableModel2();
		
		Container panel = getContentPane();
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[] {0, 0};
		gbl_panel_4.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_4.columnWeights = new double[] {1.0, Double.MIN_VALUE };
		gbl_panel_4.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel_4);
		
		JTable table1 = new JTable(tableModel1);
		JTable table2 = new JTable(tableModel2);
		
		table1.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table2.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		
		try {
			TableColumnModel columnModel1 = table1.getColumnModel();
			TableColumnModel columnModel2 = table2.getColumnModel();
			table1.setTableHeader(new JTableHeader(columnModel1) {
				private static final long serialVersionUID = -8082631462718440818L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			table2.setTableHeader(new JTableHeader(columnModel2) {
				private static final long serialVersionUID = 3805098053122379252L;

				@Override
				public Dimension getPreferredSize() {
					Dimension d = super.getPreferredSize();
					d.height = 32;
					return d;
				}
			});
			columnModel1.getColumn(0).setWidth(20);
			columnModel1.getColumn(0).setMinWidth(20);
			columnModel1.getColumn(0).setMaxWidth(20);
			columnModel1.getColumn(0).setPreferredWidth(20);
			columnModel2.getColumn(0).setWidth(20);
			columnModel2.getColumn(0).setMinWidth(20);
			columnModel2.getColumn(0).setMaxWidth(20);
			columnModel2.getColumn(0).setPreferredWidth(20);
			table1.setRowHeight(20);
			table2.setRowHeight(20);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JScrollPane scroll1 = new JScrollPane(table1);
		scroll1.setPreferredSize(new Dimension(400, 200));
		
		JScrollPane scroll2 = new JScrollPane(table2);
		scroll2.setPreferredSize(new Dimension(400, 200));
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, scroll1,
				scroll2){
			private static final long serialVersionUID = 1077854588677922643L;

			private final int location = 200;
			    {
			        setDividerLocation( location );
			    }
			    @Override
			    public int getDividerLocation() {
			        return location ;
			    }
			    @Override
			    public int getLastDividerLocation() {
			        return location ;
			    }
		};

		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.anchor = GridBagConstraints.NORTH;
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		panel.add(splitPane, gbc_splitPane);
		
		JButton btnNewButton_2 = new JButton("Save & Close");
		btnNewButton_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.anchor = GridBagConstraints.NORTH;
		gbc_btnNewButton_2.insets = new Insets(5, 0, 5, 0);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 1;
		panel.add(btnNewButton_2, gbc_btnNewButton_2);
		
		
		btnNewButton_2.addActionListener(new ActionListener() {
			
			@SuppressWarnings("rawtypes")
			public void actionPerformed(ActionEvent e) {
				Vector dataVector = tableModel1.getDataVector();
				for (int i = 0; i < tableModel1.getRowCount(); i++) {
					Vector row = (Vector) dataVector.elementAt(i);
					Long id = Long.parseLong(row.get(0).toString());
					for(InvoiceDetailsEntity entity:detailEntities){
						if(entity.getDetailId() == id){
							entity.setText(row.get(1).toString());
							break;
						}
					}
				}	
				
				Vector dataVector2 = tableModel2.getDataVector();
				for (int i = 0; i < tableModel2.getRowCount(); i++) {
					Vector row = (Vector) dataVector2.elementAt(i);
					Long id = Long.parseLong(row.get(0).toString());
					for(InvoiceDetailsEntity entity:detailEntities){
						if(entity.getDetailId() == id){
							entity.setText(row.get(1).toString());
							break;
						}
					}
				}	
				manageTaxes.updateInvoiceDetails(detailEntities);
				hideDialog();
			}
		});
	}

	private void loadTableModel1() {
		detailEntities = manageTaxes.getInvoiceDetailEntities();
		if(detailEntities.size() == 0){
			manageTaxes.createEmptyDetails();
			detailEntities =  manageTaxes.getInvoiceDetailEntities();
		}
		for(InvoiceDetailsEntity entity : detailEntities){
			if(entity.getDetailId()<5 && entity.getType() == 0)
				tableModel1.addRow(new Object[]{entity.getDetailId(), entity.getText()});
			else
				tableModel2.addRow(new Object[]{entity.getDetailId(), entity.getText()});
		}	
	}
	
	private void hideDialog(){
		setVisible(false);
		dispose();
	}

}
