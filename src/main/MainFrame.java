package main;

import gui.BackUpOrderFormPanel;
import gui.BackUpOrderListPanel;
import gui.ClockPane;
import gui.CustomerFormPanel;
import gui.CustomerListPanel;
import gui.POFormPanel;
import gui.POListPanel;
import gui.ProductFormPanel;
import gui.ProductListPanel;
import gui.ReportsPanel;
import gui.SOFormPanel;
import gui.SOListFormPanel;
import gui.SettingsFormPanel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -8239177586293357910L;
	private CustomerFormPanel customerPanel;
	private CustomerListPanel customerListPanel;

	private ProductFormPanel productFormPanel;
	private ProductListPanel productListPanel;
	
	private SOFormPanel salesOrderFormPanel;
	private SOListFormPanel salesOrderListFormPanel;

	private POFormPanel purchaseOrderFormPanel;
	private POListPanel purchaseOrderListPanel;
	
	private BackUpOrderFormPanel backUpOrderFormPanel;
	private BackUpOrderListPanel backUpOrderListPanel;
	
	private SettingsFormPanel settingsFormPanel;
	
	private ReportsPanel reportsPanel;
	
	private JPanel mainPanel;
	private JPanel leftPanel;

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				MainFrame mainFrame = new MainFrame();
				mainFrame.setVisible(true);
			}
		});
	}

	public MainFrame() {
		customerPanel = new CustomerFormPanel(Action.ADD, null);
		customerListPanel = new CustomerListPanel();

		productFormPanel = new ProductFormPanel();
		productListPanel = new ProductListPanel();
		
		salesOrderFormPanel = new SOFormPanel();
		salesOrderListFormPanel = new SOListFormPanel();
		
		purchaseOrderFormPanel = new POFormPanel();
		purchaseOrderListPanel = new POListPanel();
		
		backUpOrderFormPanel = new BackUpOrderFormPanel();
		backUpOrderListPanel = new BackUpOrderListPanel();
		
		reportsPanel = new ReportsPanel();
		
		settingsFormPanel = new SettingsFormPanel();
		
		prepareGUI();
	}

	private void prepareGUI() {
		setTitle("Sales Inventory Management Tool");
		setBounds(100, 100, 1024, 740);

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {150, 850};
		gridBagLayout.rowHeights = new int[] {740};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0};
		getContentPane().setLayout(gridBagLayout);
		
		leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(null, "Menu", TitledBorder.CENTER, TitledBorder.TOP, new Font("Tahoma", Font.BOLD, 14), Color.BLACK));
		leftPanel.setBackground(new Color(230, 230, 250));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 0, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(leftPanel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {150};
		gbl_panel.rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0, 0, 30};
		gbl_panel.columnWeights = new double[]{0.0};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		leftPanel.setLayout(gbl_panel);
		
//		JButton btnHome = new JButton("Homepage");
//		btnHome.setHorizontalAlignment(SwingConstants.LEFT);
//		btnHome.setIcon(Utils.getImageIcon("HOME.png"));
//		btnHome.setFont(new Font("Tahoma", Font.BOLD, 12));
//		GridBagConstraints gbc_btnHome = new GridBagConstraints();
//		gbc_btnHome.fill = GridBagConstraints.BOTH;
//		gbc_btnHome.insets = new Insets(5, 0, 5, 0);
//		gbc_btnHome.gridx = 0;
//		gbc_btnHome.gridy = 0;
//		leftPanel.add(btnHome, gbc_btnHome);
		
		JButton btnNewCustomer = new JButton("New Customer");
		btnNewCustomer.setBackground(UIManager.getColor("Button.background"));
		btnNewCustomer.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewCustomer.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewCustomer.setIcon(Utils.getImageIcon("NC.png"));
		GridBagConstraints gbc_btnNewCustomer = new GridBagConstraints();
		gbc_btnNewCustomer.insets = new Insets(10, 0, 5, 0);
		gbc_btnNewCustomer.fill = GridBagConstraints.BOTH;
		gbc_btnNewCustomer.gridx = 0;
		gbc_btnNewCustomer.gridy = 0;
		leftPanel.add(btnNewCustomer, gbc_btnNewCustomer);
		
		JButton btnCustomerList = new JButton("Customer List");
		btnCustomerList.setHorizontalAlignment(SwingConstants.LEFT);
		btnCustomerList.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCustomerList.setIcon(Utils.getImageIcon("CL.png"));
		GridBagConstraints gbc_btnCustomerList = new GridBagConstraints();
		gbc_btnCustomerList.insets = new Insets(0, 0, 5, 0);
		gbc_btnCustomerList.fill = GridBagConstraints.BOTH;
		gbc_btnCustomerList.gridx = 0;
		gbc_btnCustomerList.gridy = 1;
		leftPanel.add(btnCustomerList, gbc_btnCustomerList);

		JButton btnNewSalesOrder = new JButton("New Sales Order");
		btnNewSalesOrder.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewSalesOrder.setIcon(Utils.getImageIcon("SO.jpg"));
		btnNewSalesOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewSalesOrder = new GridBagConstraints();
		gbc_btnNewSalesOrder.fill = GridBagConstraints.BOTH;
		gbc_btnNewSalesOrder.insets = new Insets(10, 0, 5, 0);
		gbc_btnNewSalesOrder.gridx = 0;
		gbc_btnNewSalesOrder.gridy = 2;
		leftPanel.add(btnNewSalesOrder, gbc_btnNewSalesOrder);
		

		JButton btnSalesOrderList = new JButton("Sales Order List");
		btnSalesOrderList.setHorizontalAlignment(SwingConstants.LEFT);
		btnSalesOrderList.setIcon(Utils.getImageIcon("OL.png"));
		btnSalesOrderList.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSalesOrderList = new GridBagConstraints();
		gbc_btnSalesOrderList.fill = GridBagConstraints.BOTH;
		gbc_btnSalesOrderList.insets = new Insets(0, 0, 5, 0);
		gbc_btnSalesOrderList.gridx = 0;
		gbc_btnSalesOrderList.gridy = 3;
		leftPanel.add(btnSalesOrderList, gbc_btnSalesOrderList);
		
		JButton btnNewProduct = new JButton("New Product");
		btnNewProduct.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewProduct.setIcon(Utils.getImageIcon("NP.png"));
		btnNewProduct.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewProduct = new GridBagConstraints();
		gbc_btnNewProduct.fill = GridBagConstraints.BOTH;
		gbc_btnNewProduct.insets = new Insets(10, 0, 5, 0);
		gbc_btnNewProduct.gridx = 0;
		gbc_btnNewProduct.gridy = 4;
		leftPanel.add(btnNewProduct, gbc_btnNewProduct);
		
		JButton btnProductList = new JButton("Inventory");
		btnProductList.setHorizontalAlignment(SwingConstants.LEFT);
		btnProductList.setIcon(Utils.getImageIcon("INV.jpg"));
		btnProductList.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnProductList = new GridBagConstraints();
		gbc_btnProductList.fill = GridBagConstraints.BOTH;
		gbc_btnProductList.insets = new Insets(0, 0, 5, 0);
		gbc_btnProductList.gridx = 0;
		gbc_btnProductList.gridy = 5;
		leftPanel.add(btnProductList, gbc_btnProductList);
		
		JButton btnNewPurchaseOrder = new JButton("New Purchase Order");
		btnNewPurchaseOrder.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewPurchaseOrder.setIcon(Utils.getImageIcon("PO.png"));
		btnNewPurchaseOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewPurchaseOrder= new GridBagConstraints();
		gbc_btnNewPurchaseOrder.fill = GridBagConstraints.BOTH;
		gbc_btnNewPurchaseOrder.insets = new Insets(10, 0, 5, 0);
		gbc_btnNewPurchaseOrder.gridx = 0;
		gbc_btnNewPurchaseOrder.gridy = 6;
		leftPanel.add(btnNewPurchaseOrder, gbc_btnNewPurchaseOrder);
		

		JButton btnPurchaseOrderList = new JButton("Purchase Order List");
		btnPurchaseOrderList.setHorizontalAlignment(SwingConstants.LEFT);
		btnPurchaseOrderList.setIcon(Utils.getImageIcon("POL.png"));
		btnPurchaseOrderList.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnPurchaseOrderList = new GridBagConstraints();
		gbc_btnPurchaseOrderList.fill = GridBagConstraints.BOTH;
		gbc_btnPurchaseOrderList.insets = new Insets(0, 0, 5, 0);
		gbc_btnPurchaseOrderList.gridx = 0;
		gbc_btnPurchaseOrderList.gridy = 7;
		leftPanel.add(btnPurchaseOrderList, gbc_btnPurchaseOrderList);
		
		
		JButton btnNewBackUpOrder = new JButton("BackUp Order");
		btnNewBackUpOrder.setHorizontalAlignment(SwingConstants.LEFT);
		btnNewBackUpOrder.setIcon(Utils.getImageIcon("safe-backup-icon.png"));
		btnNewBackUpOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewbtnNewBackUpOrder= new GridBagConstraints();
		gbc_btnNewbtnNewBackUpOrder.fill = GridBagConstraints.BOTH;
		gbc_btnNewbtnNewBackUpOrder.insets = new Insets(10, 0, 5, 0);
		gbc_btnNewbtnNewBackUpOrder.gridx = 0;
		gbc_btnNewbtnNewBackUpOrder.gridy = 8;
		leftPanel.add(btnNewBackUpOrder, gbc_btnNewbtnNewBackUpOrder);
		

		JButton btnBackUpOrderList = new JButton("BackUp Order List");
		btnBackUpOrderList.setHorizontalAlignment(SwingConstants.LEFT);
		btnBackUpOrderList.setIcon(Utils.getImageIcon("bu_list.png"));
		btnBackUpOrderList.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnBackUpOrderList = new GridBagConstraints();
		gbc_btnBackUpOrderList.fill = GridBagConstraints.BOTH;
		gbc_btnBackUpOrderList.insets = new Insets(0, 0, 5, 0);
		gbc_btnBackUpOrderList.gridx = 0;
		gbc_btnBackUpOrderList.gridy = 9;
		leftPanel.add(btnBackUpOrderList, gbc_btnBackUpOrderList);
		
		JButton btnReports = new JButton("Reports");
		btnReports.setHorizontalAlignment(SwingConstants.LEFT);
		btnReports.setIcon(Utils.getImageIcon("RPTS.png"));
		btnReports.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnReports = new GridBagConstraints();
		gbc_btnReports.insets = new Insets(10, 0, 5, 0);
		gbc_btnReports.fill = GridBagConstraints.BOTH;
		gbc_btnReports.gridx = 0;
		gbc_btnReports.gridy = 10;
		leftPanel.add(btnReports, gbc_btnReports);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setHorizontalAlignment(SwingConstants.LEFT);
		btnSettings.setIcon(Utils.getImageIcon("SET.png"));
		btnSettings.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnSettings = new GridBagConstraints();
		gbc_btnSettings.insets = new Insets(10, 0, 5, 0);
		gbc_btnSettings.fill = GridBagConstraints.BOTH;
		gbc_btnSettings.gridx = 0;
		gbc_btnSettings.gridy = 11;
		leftPanel.add(btnSettings, gbc_btnSettings);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setHorizontalAlignment(SwingConstants.LEFT);
		btnExit.setIcon(Utils.getImageIcon("EX.png"));
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 12));
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.fill = GridBagConstraints.BOTH;
		gbc_btnExit.insets = new Insets(10, 0, 5, 0);
		gbc_btnExit.gridx = 0;
		gbc_btnExit.gridy = 12;
		leftPanel.add(btnExit, gbc_btnExit);
		
		JPanel rightpanel = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 0;
		getContentPane().add(rightpanel, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] {850};
		gbl_panel_1.rowHeights = new int[] {10, 670};
		gbl_panel_1.columnWeights = new double[]{1.0};
		gbl_panel_1.rowWeights = new double[]{1.0, 1.0};
		rightpanel.setLayout(gbl_panel_1);
		
		JPanel toppanel = new JPanel();
		toppanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(34, 139, 34), null));
		toppanel.setBackground(new Color(230, 230, 250));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 0;
		rightpanel.add(toppanel, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] {600, 0};
		gbl_panel_2.rowHeights = new int[] {10};
		gbl_panel_2.columnWeights = new double[]{0.0, 0.0};
		gbl_panel_2.rowWeights = new double[]{0.0};
		toppanel.setLayout(gbl_panel_2);
		JLabel lblwelcome = new JLabel("Welcome!");
		lblwelcome.setFont(new Font("Tahoma", Font.BOLD, 14));
		GridBagConstraints gbc_lblwelcome = new GridBagConstraints();
		gbc_lblwelcome.fill = GridBagConstraints.BOTH;
		gbc_lblwelcome.gridx = 0;
		gbc_lblwelcome.gridy = 0;
		toppanel.add(lblwelcome, gbc_lblwelcome);
		ClockPane clockPane = new ClockPane();
		GridBagConstraints gbc_clockPane = new GridBagConstraints();
		gbc_clockPane.anchor = GridBagConstraints.NORTHWEST;
		gbc_clockPane.gridx = 1;
		gbc_clockPane.gridy = 0;
		toppanel.add(clockPane, gbc_clockPane);
		GridBagLayout gbl_clockPane = new GridBagLayout();
		gbl_clockPane.columnWidths = new int[]{0};
		gbl_clockPane.rowHeights = new int[]{0};
		gbl_clockPane.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_clockPane.rowWeights = new double[]{Double.MIN_VALUE};
		clockPane.setLayout(gbl_clockPane);
		
		mainPanel = new JPanel();
		mainPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(34, 139, 34), null));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 1;
		rightpanel.add(mainPanel, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[] {850};
		gbl_panel_3.rowHeights = new int[] {670};
		gbl_panel_3.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{Double.MIN_VALUE};
		mainPanel.setLayout(gbl_panel_3);

		btnNewCustomer.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				customerPanel.clearForm();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(customerPanel, gbc_panel_2);
				customerPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();

			}
		});
		
		btnCustomerList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				customerListPanel.refreshModel(null);
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(customerListPanel, gbc_panel_2);
				customerListPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();

			}
		});
		
		btnNewSalesOrder.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				salesOrderFormPanel.refreshForm();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(salesOrderFormPanel, gbc_panel_2);
				salesOrderFormPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();

			}
		});
		
		btnNewPurchaseOrder.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				purchaseOrderFormPanel.refreshForm();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(purchaseOrderFormPanel, gbc_panel_2);
				purchaseOrderFormPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		btnNewProduct.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				productFormPanel.clearForm();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(productFormPanel, gbc_panel_2);
				productFormPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();

			}
		});
		
		btnNewBackUpOrder.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				backUpOrderFormPanel.clearForm();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(backUpOrderFormPanel, gbc_panel_2);
				backUpOrderFormPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		btnProductList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				productListPanel.clearForm();
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(productListPanel, gbc_panel_2);
				productListPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		btnSalesOrderList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				salesOrderListFormPanel.refreshModel(null);
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(salesOrderListFormPanel, gbc_panel_2);
				salesOrderListFormPanel.focusTextField();
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		btnPurchaseOrderList.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				purchaseOrderListPanel.refreshModel(null);
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(purchaseOrderListPanel, gbc_panel_2);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		btnBackUpOrderList.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				backUpOrderListPanel.refreshModel(null);
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(backUpOrderListPanel, gbc_panel_2);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});
		
		
		btnReports.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(reportsPanel, gbc_panel_2);
				mainPanel.revalidate();
				mainPanel.repaint();
				
			}
		});
		
		btnSettings.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				GridBagConstraints gbc_panel_2 = new GridBagConstraints();
				gbc_panel_2.anchor = GridBagConstraints.NORTHWEST;
				gbc_panel_2.fill = GridBagConstraints.BOTH;
				gbc_panel_2.gridx = 0;
				gbc_panel_2.gridy = 0;
				mainPanel.removeAll();
				mainPanel.add(settingsFormPanel, gbc_panel_2);
				mainPanel.revalidate();
				mainPanel.repaint();
			}
		});

		btnExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit?", "Exit Application",
						JOptionPane.OK_CANCEL_OPTION);

				if (action == JOptionPane.OK_OPTION)
					System.exit(0);
			}
		});
		
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}
}
