package gui;

import hibernate.BackUpOrderDetailEntity;
import hibernate.BackUpOrderEntity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

import mail.ConfigUtility;
import mail.EmailUtility;
import main.BackUpOrderStatus;
import main.ComboItem;
import main.Constants;
import main.DateLabelFormatter;
import main.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import contoller.ManageBackUpOrders;

public class BackUpOrderListPanel extends JPanel {
	private static final long serialVersionUID = -4741209307816005721L;

	private DefaultTableModel model;
	
	private static final int ORDER_NO = 0;
	private static final int PARTY_COL = 1;
	private static final int DATE_COL = 2;
	private static final int ARR_DATE_COL = 3;
	private static final int UPDATE_COL = 5;
	private static final int OF_COL = 6;
	private static final int CANCEL_COL = 7;
	
	private JFrame parent = (JFrame) SwingUtilities.getWindowAncestor(this);
	private List<BackUpOrderEntity> list;
	private JDatePickerImpl datePickerFrom;
	private JDatePickerImpl datePickerTo;
	
	private static ManageBackUpOrders manageBackUpOrders = new ManageBackUpOrders();
	
	public BackUpOrderListPanel() {
		JDatePanelImpl datePanelFrom = new JDatePanelImpl(new UtilDateModel());
		JDatePanelImpl datePanelTo = new JDatePanelImpl(new UtilDateModel());
		DateLabelFormatter formatter = new DateLabelFormatter();
		datePickerFrom = new JDatePickerImpl(datePanelFrom, formatter);
		datePickerTo = new JDatePickerImpl(datePanelTo, formatter);

		setBackground(new Color(255, 204, 153));
		setBorder(new TitledBorder(null, "BackUp Order List", TitledBorder.CENTER,
				TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.BLACK));
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] { 0 };
		layout.rowHeights = new int[] { 0 };
		layout.columnWeights = new double[] { 1.0 };
		layout.rowWeights = new double[] { 1.0 };
		setLayout(layout);
		
		model = new DefaultTableModel() {
			private static final long serialVersionUID = 8824352406450385228L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			
			@Override
			public Class<?> getColumnClass(int column) {
				switch (column) {
				case UPDATE_COL:
					return ImageIcon.class;
				case OF_COL:
					return ImageIcon.class;
				case CANCEL_COL:
					return ImageIcon.class;	
				case ORDER_NO:
					return Long.class;
				case DATE_COL:
					return Date.class;	
				case ARR_DATE_COL:
					return Date.class;
				case PARTY_COL:
					return ComboItem.class;
				default:
					return String.class;
				}
			}
		};

		for (int i = 0; i < Constants.backUpOrderListColumnNames.length; i++) {
			model.addColumn(Constants.backUpOrderListColumnNames[i]);
		}
		
		setLayoutComponents();
	}

	@SuppressWarnings("rawtypes")
	private void setLayoutComponents() {
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.setBackground(new Color(255, 204, 153));
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel_1);
		
		JLabel lblOrder = new JLabel("Order # :");
		lblOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrder = new GridBagConstraints();
		gbc_lblOrder.anchor = GridBagConstraints.EAST;
		gbc_lblOrder.insets = new Insets(5, 5, 5, 5);
		gbc_lblOrder.gridx = 0;
		gbc_lblOrder.gridy = 0;
		panel.add(lblOrder, gbc_lblOrder);
		
		final JTextField orderNoField = new JTextField();
		orderNoField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(5, 5, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridwidth=2;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel.add(orderNoField, gbc_textField_2);
		orderNoField.setColumns(10);
		
		JLabel lblOrderDate = new JLabel("Order Date :");
		lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
		gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
		gbc_lblOrderDate.insets = new Insets(5, 5, 5, 5);
		gbc_lblOrderDate.gridx = 0;
		gbc_lblOrderDate.gridy = 1;
		panel.add(lblOrderDate, gbc_lblOrderDate);
		
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(5, 5, 5, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 1;
		gbc_textField_3.gridy = 1;
		panel.add(datePickerFrom, gbc_textField_3);
		
		JLabel lblTo = new JLabel("to");
		lblTo.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_lblTo = new GridBagConstraints();
		gbc_lblTo.anchor = GridBagConstraints.EAST;
		gbc_lblTo.insets = new Insets(5, 5, 5, 5);
		gbc_lblTo.gridx = 2;
		gbc_lblTo.gridy = 1;
		panel.add(lblTo, gbc_lblTo);
		
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(5, 5, 5, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 3;
		gbc_textField_4.gridy = 1;
		panel.add(datePickerTo, gbc_textField_4);
		
		JButton serachOrder = new JButton("Search");
		serachOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		serachOrder.setIcon(Utils.getImageIcon("search.png"));
		serachOrder.setMnemonic(KeyEvent.VK_ENTER);
		GridBagConstraints gbc_btnSearch = new GridBagConstraints();
		gbc_btnSearch.insets = new Insets(7, 0, 5, 0);
		gbc_btnSearch.gridx = 1;
		gbc_btnSearch.gridy = 2;
		panel.add(serachOrder, gbc_btnSearch);
		
		JTable table = new JTable(model);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		try {
			table.setAutoCreateRowSorter(false);
			TableColumnModel columnModel = table.getColumnModel();
			columnModel.getColumn(ORDER_NO).setCellRenderer(centerRenderer);
			columnModel.getColumn(ORDER_NO).setPreferredWidth(30);
			columnModel.getColumn(DATE_COL).setCellRenderer(centerRenderer);
			columnModel.getColumn(DATE_COL).setPreferredWidth(60);
			columnModel.getColumn(ARR_DATE_COL).setCellRenderer(centerRenderer);
			columnModel.getColumn(ARR_DATE_COL).setPreferredWidth(60);
			columnModel.getColumn(UPDATE_COL).setPreferredWidth(25);
			columnModel.getColumn(OF_COL).setPreferredWidth(25);
			columnModel.getColumn(CANCEL_COL).setPreferredWidth(25);
			table.setTableHeader(new JTableHeader(columnModel) {
				private static final long serialVersionUID = 5341323355448544567L;

				@Override public Dimension getPreferredSize() {
				    Dimension d = super.getPreferredSize();
				    d.height = 32;
				    return d;
				  }
				});
			table.setRowHeight(20);
		} catch (Exception continuewithNoSort) {
			//do nothing
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
					String orderNo = row.get(ORDER_NO).toString();
					switch (columnNo) {
					case OF_COL:
						Object[] options = { "View", "Send Email" };
						int action = JOptionPane.showOptionDialog(
								BackUpOrderListPanel.this,
								"What you want to do with order Form ?",
								"Invoice", JOptionPane.OK_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options,
								options[0]);
						if (action == JOptionPane.OK_OPTION) {
							Utils.viewJasperReport("backUpOrderForm.jasper", Utils.normalBackUpOrderId(orderNo), true);
						}else{
							JasperPrint orderForm = Utils.viewJasperReport("backUpOrderForm.jasper", Utils.normalBackUpOrderId(orderNo), false);
							OutputStream output;
							try {
								File file = new File(Utils.getFilesDirectory("BackUp_Order_Form.pdf"));
								output = new FileOutputStream(file);
								JasperExportManager.exportReportToPdfStream(orderForm, output); 
								String subject = "BackUp Order";
						        String message = "Hi,\nPlease find attached order form.\nThanks";
								Properties smtpProperties = new ConfigUtility().loadProperties();
						        
								EmailUtility.sendEmail(smtpProperties, smtpProperties.getProperty("mail.vendor"), subject, message, file);
						        
						        JOptionPane.showMessageDialog(new JFrame(), "The e-mail has been sent successfully!",
											"Message", JOptionPane.INFORMATION_MESSAGE);
						             
						        } catch (FileNotFoundException e1) {
									e1.printStackTrace();
								} catch (JRException e1) {
									e1.printStackTrace();
								} catch (Exception ex) {
						        	 JOptionPane.showMessageDialog(new JFrame(), "Error while sending the e-mail: " + ex.getMessage(),
												"Error", JOptionPane.ERROR_MESSAGE);
						        }
						}
						break;
					case UPDATE_COL:
						int action2 = JOptionPane.showConfirmDialog(
								BackUpOrderListPanel.this,
								"Do you really want to update BackUp Order #" + orderNo
								+ "?", "Update BackUp Order",
								JOptionPane.OK_CANCEL_OPTION);
						if (action2 == JOptionPane.OK_OPTION) {
							    BackUpUpdateDialog soUpdateDialog = new BackUpUpdateDialog(parent, findOrderFromList(Utils.normalBackUpOrderId(orderNo)));
							    boolean showDialog = soUpdateDialog.showDialog();
							    if(showDialog)
							    	refreshModel(null);
						}
						break;	
					case CANCEL_COL:
						ImageIcon icon = (ImageIcon) row.get(CANCEL_COL);
						if(icon != null){
							int action1 = JOptionPane.showConfirmDialog(
									BackUpOrderListPanel.this,
									"Do you really want to delete Order #" + orderNo
									+ "? Note: This action will affect the inventory.", "Remove/Cancel Order",
									JOptionPane.OK_CANCEL_OPTION);
							if (action1 == JOptionPane.OK_OPTION) {
								boolean cancelOrder = manageBackUpOrders.deleteOrder(Utils.normalBackUpOrderId(orderNo));
								if(cancelOrder){
									String message = "Order deleted successfully!";
									JOptionPane.showMessageDialog(new JFrame(), message,
											"Dialog", JOptionPane.INFORMATION_MESSAGE);
									refreshModel(null);
								}else{
									String message = "Error while deleting Order!";
									JOptionPane.showMessageDialog(new JFrame(), message,
											"Dialog", JOptionPane.ERROR_MESSAGE);
								}
							}
						}
						break;	
					}
				}
			}
		});
		
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panel, tableScroll);
		
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.anchor = GridBagConstraints.NORTH;
		gbc_splitPane.insets = new Insets(0, 0, 5, 0);
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		add(splitPane, gbc_splitPane);
		
		serachOrder.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Date fromDate = (Date) datePickerFrom.getModel().getValue();
				Date toDate = (Date) datePickerTo.getModel().getValue();
				if(fromDate!= null && toDate!=null && fromDate.after(toDate)){
					JOptionPane.showMessageDialog(new JFrame(),	"From date cannot be greater than to date!", "Error", JOptionPane.ERROR_MESSAGE);
				}else{
					refreshModel(manageBackUpOrders.listOrdersBySearch(orderNoField.getText(), fromDate, toDate));
				}
				
			}
		});
	}

	public void refreshModel(List<BackUpOrderEntity> modified) {
		model.setRowCount(0);
		list = new ArrayList<BackUpOrderEntity>();
		
		if(modified!= null)
			list = modified;
		else
			list = manageBackUpOrders.listOrders();
		
		for (BackUpOrderEntity entity : list) {
			Set<BackUpOrderDetailEntity> orderDetails = entity.getOrderDetails();
			ComboItem party = null;
			for(BackUpOrderDetailEntity detail : orderDetails){
				party = new ComboItem(detail.getPartyId(), detail.getPartyName());
				break;
			}
			BackUpOrderStatus backUpOrderStatus = BackUpOrderStatus.values()[entity.getShippingStatus()];
			ImageIcon icon = Utils.getImageIcon("cancel.png");
			if(backUpOrderStatus.equals(BackUpOrderStatus.SENT))
				icon = null;
			model.addRow(new Object[] {Utils.formatBackUpOrderId(entity.getBackUpOrderId()),
					Utils.formatOrderDate(entity.getOrderDate()), party,Utils.formatOrderDate(entity.getArrivalDate()),
					backUpOrderStatus,
					Utils.getImageIcon("edit.png"), Utils.getImageIcon("pdf-icon.gif"), icon});
		}
	}
	
	private BackUpOrderEntity findOrderFromList(long orderNo) {
		for(BackUpOrderEntity entity: list){
			if(entity.getBackUpOrderId()== orderNo){
				return entity;
			}
		}
		return null;
	}

}
