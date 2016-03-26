package gui;

import hibernate.CustomerEntity;

import java.awt.Color;
import java.awt.Dialog;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
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

import main.Action;
import main.Constants;
import main.Utils;
import contoller.ManageCustomers;
import contoller.ManageSalesOrders;

public class CustomerListPanel extends JPanel {

	private static final long serialVersionUID = -6784939306904898200L;

	private JTextField customerNameField;
	private JTextField companyNameField;
	private JButton serachCustomer;

	private DefaultTableModel model;

	private ManageCustomers manageCustomers;
	private ManageSalesOrders manageSalesOrders;
	protected static JDialog mydialog;
	
	private static final int VIEW_COLUMN = 7;
	private static final int EDIT_COLUMN = 8;
	private static final int ACT_COLUMN = 9;
	private static final int DEL_COLUMN = 10;
	private static final int BALANCE_COLUMN = 2;
	
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);

	private List<CustomerEntity> list;
	
	public CustomerListPanel() {
		setBackground(new Color(240, 248, 255));
		setBorder(new TitledBorder(null, "Customer List", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.BLACK));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0 };
		layout.rowHeights = new int[] { 0 };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0 };
		setLayout(layout);

		manageCustomers = new ManageCustomers();
		manageSalesOrders = new ManageSalesOrders();
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 4447313472748838385L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case VIEW_COLUMN:
					return ImageIcon.class;
				case EDIT_COLUMN:
					return ImageIcon.class;
				case ACT_COLUMN:
					return ImageIcon.class;
//				case DEL_COLUMN:
//					return ImageIcon.class;
				default:
					return Object.class;
				}
			}
		};

		for (int i = 0; i < Constants.custColumnNames.length; i++) {
			model.addColumn(Constants.custColumnNames[i]);
		}
		setLayoutComponents();
	}

	@SuppressWarnings("rawtypes")
	private void setLayoutComponents() {

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.setBackground(new Color(240, 248, 255));
		JLabel lblCustomerName = new JLabel("Customer Name :");
		lblCustomerName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCustomerName.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblCustomerName);

		customerNameField = new JTextField();
		customerNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(customerNameField);
		customerNameField.setColumns(20);
		
		JLabel lblCompanyName = new JLabel("Company Name :");
		lblCompanyName.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCompanyName.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblCompanyName);
		
		companyNameField = new JTextField();
		companyNameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		companyNameField.setColumns(20);
		panel.add(companyNameField);

		serachCustomer = new JButton("Search");
		serachCustomer.setFont(new Font("Tahoma", Font.BOLD, 12));
		serachCustomer.setIcon(Utils.getImageIcon("search.png"));
		panel.add(serachCustomer);
		
		JButton resetButton = new JButton("");
		resetButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		resetButton.setIcon(Utils.getImageIcon("RESET.png"));
		panel.add(resetButton);

		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);

		JTable table = new JTable(model);
		try {
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(BALANCE_COLUMN).setCellRenderer(rightRenderer);
			columnModel.getColumn(VIEW_COLUMN).setPreferredWidth(25);
			columnModel.getColumn(EDIT_COLUMN).setPreferredWidth(25);
			columnModel.getColumn(ACT_COLUMN).setPreferredWidth(25);
			columnModel.getColumn(DEL_COLUMN).setPreferredWidth(0);
			columnModel.getColumn(DEL_COLUMN).setWidth(0);
			columnModel.getColumn(DEL_COLUMN).setMinWidth(0);
			columnModel.getColumn(DEL_COLUMN).setMaxWidth(0);
			
			table.setTableHeader(new JTableHeader(table.getColumnModel()) {
				private static final long serialVersionUID = -7800178156661010679L;

				@Override public Dimension getPreferredSize() {
				    Dimension d = super.getPreferredSize();
				    d.height = 32;
				    return d;
				  }
				});
			table.setRowHeight(20);
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
					long custId = (Long) row.get(DEL_COLUMN);
					switch (columnNo) {
					case VIEW_COLUMN:
						new ViewCustomerDialog(parent, manageSalesOrders.getCustomerOrders(custId));
						break;
					case EDIT_COLUMN:
						int action = JOptionPane.showConfirmDialog(
								CustomerListPanel.this,
								"Do you really want to update customer ?", "Update Customer",
								JOptionPane.OK_CANCEL_OPTION);
						if (action == JOptionPane.OK_OPTION) {
							    mydialog = new JDialog();
							    CustomerFormPanel customerFormPanel = new CustomerFormPanel(Action.UPDATE, manageCustomers.getCustomer(custId));
							    mydialog.add(customerFormPanel);
				                mydialog.setSize(new Dimension(650,750));
				                mydialog.setTitle("Update Customer");
				                mydialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL); // prevent user from doing something else
				                mydialog.setLocationRelativeTo(null);
				                mydialog.setVisible(true);
				                refreshModel(null);
						}
						break;
					case ACT_COLUMN:
						int action1 = JOptionPane.showConfirmDialog(
								CustomerListPanel.this,
								"Do you really want to activate or deactivate customer ?", "Activate/Deactivate Customer",
								JOptionPane.OK_CANCEL_OPTION);
						if (action1 == JOptionPane.OK_OPTION) {
							int customer = manageCustomers
									.activateDeactivateCustomer(custId);
							if (customer == 1) {
								JOptionPane.showMessageDialog(new JFrame(),
										"Customer changed successfully !",
										"Dialog",
										JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(new JFrame(),
										"Error while updating!", "Dialog",
										JOptionPane.ERROR_MESSAGE);
							}
						}
						refreshModel(null);
						break;
//					case DEL_COLUMN:
//						int action2 = JOptionPane.showConfirmDialog(
//								CustomerListPanel.this,
//								"Do you really want to delete " + custName
//								+ "?", "Delete Customer",
//								JOptionPane.OK_CANCEL_OPTION);
//
//						if (action2 == JOptionPane.OK_OPTION) {
//							int customer = manageCustomers
//									.deleteCustomer(custName);
//							if (customer == 1) {
//								JOptionPane.showMessageDialog(new JFrame(),
//										custName + " deleted successfully !",
//										"Dialog",
//										JOptionPane.INFORMATION_MESSAGE);
//							} else {
//								JOptionPane.showMessageDialog(new JFrame(),
//										"Error while deleting!", "Dialog",
//										JOptionPane.ERROR_MESSAGE);
//							}
//						}
//						refreshModel(null);
//						break;
					}
				}
			}
		});
		
		resetButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				customerNameField.setText("");
				companyNameField.setText("");
			}
		});

		serachCustomer.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String custName = customerNameField.getText().trim();
				String compName = companyNameField.getText().trim();
				if(custName.isEmpty() && compName.isEmpty()){
					refreshModel(null);
				}else if(compName.isEmpty()){
					refreshModel(manageCustomers.listCustomerByName(custName));
				}else if(custName.isEmpty()){
					refreshModel(manageCustomers.listCustomerByCompany(compName));
				}else{
					refreshModel(manageCustomers.listCustomerByNameAndCompany(custName, compName));
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

	public void refreshModel(List<CustomerEntity> modified) {
		model.setRowCount(0);
		ImageIcon icon = null;
		list = new ArrayList<CustomerEntity>();
		
		if(modified!= null)
			list = modified;
		else
			list = manageCustomers.listCustomer();
		
		for (Iterator<CustomerEntity> iterator = list.iterator(); iterator.hasNext();) {
			CustomerEntity customer = iterator.next();
			if (customer.isActive()) {
				icon = Utils.getImageIcon("deactivate.png");
			} else {
				icon = Utils.getImageIcon("activate.png");
			}
			model.addRow(new Object[] { customer.getCustomerName(),
					customer.getCompanyName(), Utils.currencyFormat(manageSalesOrders.getCustomerBalance(customer.getCustomerId())),
					customer.getAddress(), customer.getPhone(),
					customer.getEmail(), customer.getFax(),
					Utils.getImageIcon("view-icon.png"), Utils.getImageIcon("edit.png"), icon,
//					new ImageIcon(Constants.IMAGE_DIR + "delete.png"), 
					customer.getCustomerId() });
		}
	}

	public void focusTextField() {
		customerNameField.requestFocusInWindow();
	}
	
	public void hideDialog(){
		mydialog.dispose();	
	}
	
}
