package gui;

import hibernate.CustomerEntity;
import hibernate.InvoiceEntity;
import hibernate.PaymentEntity;
import hibernate.ProductDetailEntity;
import hibernate.SalesOrderDetailEntity;
import hibernate.SalesOrderEntity;
import hibernate.TaxEntity;

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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

import mail.ConfigUtility;
import mail.EmailUtility;
import main.ComboItem;
import main.Constants;
import main.IntegerEditor;
import main.InvoiceStatus;
import main.OrderPayStatus;
import main.PaymentStatus;
import main.ShippingStatus;
import main.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import contoller.ManageProducts;
import contoller.ManageSalesOrders;
import contoller.ManageTaxes;

@SuppressWarnings("rawtypes")
public class SOUpdateDialog extends JDialog {

    private static final long serialVersionUID = 3529852432071053106L;

    private static final int columnNos = 11;
    private static final int PARTY_COL = 0;
    private static final int ITEM_COL = 1;
    private static final int DETAIL_COL = 2;
    private static final int SUB_TOTAL_QUANTITY = 11;
    private static final int PRICE_COL = 12;
    private static final int SUB_TOTAL_COL = 13;
    private static final String len = "012345678901234";
    private static final int size = 12;

    private static final BigDecimal hundred = new BigDecimal(100);
    private Frame parent;

    private DefaultTableModel tableModel;
    private JTextField orderNo;
    private JTextField orderDate;
    private JTextField orderStatus;
    private JTextField orderTotalField;
    private JTextField discountField;
    private JTextField subTotalField;
    private JLabel lblTax1;
    private JLabel lblTax2;
    private JTextField taxField1;
    private JTextField taxField2;
    private JTextField totalField;
    private JTextField paidField;
    private JTextField balanceField;
    private JTextArea remarks;
    private JTextArea conditions;
    private JTextField viaField;
    private JTextField refField;

    private boolean validated;

    private JTextField customerName;
    private JTextField orderByCombo;
    private JComboBox taxSchemeCombo;
    private JComboBox shippingStatusCombo;
    private JComboBox invoiceStatusCombo;
    private JComboBox paymentStatusCombo;
    private SalesOrderEntity salesOrderEntity;

    private ManageTaxes manageTaxes;
    private ManageSalesOrders manageSalesOrders;

    private ManageProducts manageProducts;

    private boolean shippedFlag;
    private boolean invoiceFlag;
    private boolean paidFlag;

    private boolean flagForRefresh;

    private List<TaxEntity> listTaxingSchemes;

    private Set<SalesOrderDetailEntity> entities;

    public SOUpdateDialog(Frame parent, SalesOrderEntity salesOrderEntity) {

        super(parent);
        this.parent = parent;
        this.salesOrderEntity = salesOrderEntity;
        entities = salesOrderEntity.getOrderDetails();
        manageTaxes = new ManageTaxes();
        manageSalesOrders = new ManageSalesOrders();
        manageProducts = new ManageProducts();
    }

    public boolean showDialog() {
        createLayout();

        setTitle("View/Update Sales Order");
        setSize(850, 700);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setVisible(true);
        return flagForRefresh;
    }

    @SuppressWarnings({ "unchecked" })
    private void createLayout() {

        Integer shippingStatus = salesOrderEntity.getShippingStatus();
        if (shippingStatus == ShippingStatus.SHIPPED.ordinal()) {
            shippedFlag = true;
        }

        Integer invoiceStatus = salesOrderEntity.getInvoiceStatus();
        if (invoiceStatus == InvoiceStatus.INVOICED.ordinal()) {
            invoiceFlag = true;
        }

        Integer paymentStatus = salesOrderEntity.getPaymentStatus();
        if (paymentStatus == PaymentStatus.PAID.ordinal()) {
            paidFlag = true;
        }

        tableModel = new DefaultTableModel() {

            private static final long serialVersionUID = -2747018091472120483L;

            @Override
            public boolean isCellEditable(int row, int column) {
                if (shippedFlag) {
                    if (column != PRICE_COL) {
                        return false;
                    }
                } else {
                    if ((column == PARTY_COL) || (column == ITEM_COL) || (column == DETAIL_COL)
                            || (column == SUB_TOTAL_COL) || (column == SUB_TOTAL_QUANTITY)) {
                        return false;
                    }
                }
                return true;
            }
        };

        for (String salesOrderColumnName : Constants.salesOrderColumnNames) {
            tableModel.addColumn(salesOrderColumnName);
        }

        JPanel panel = (JPanel) getContentPane();
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[] { 0, 0 };
        layout.rowHeights = new int[] { 60, 320, 230 };
        layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        layout.rowWeights = new double[] { 1.0, 1.0, 1.0 };
        panel.setLayout(layout);

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(new Color(245, 222, 179));
        GridBagConstraints gbc_panel_4 = new GridBagConstraints();
        gbc_panel_4.insets = new Insets(0, 0, 0, 0);
        gbc_panel_4.fill = GridBagConstraints.BOTH;
        gbc_panel_4.gridx = 0;
        gbc_panel_4.gridy = 0;
        add(panel_4, gbc_panel_4);
        GridBagLayout gbl_panel_4 = new GridBagLayout();
        gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel_4.rowHeights = new int[] { 0, 0, 0 };
        gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        panel_4.setLayout(gbl_panel_4);

        JLabel lblCustomerName = new JLabel("Name :");
        lblCustomerName.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
        gbc_lblCustomerName.insets = new Insets(5, 5, 0, 0);
        gbc_lblCustomerName.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblCustomerName.anchor = GridBagConstraints.EAST;
        gbc_lblCustomerName.gridx = 0;
        gbc_lblCustomerName.gridy = 1;
        panel_4.add(lblCustomerName, gbc_lblCustomerName);

        customerName = new JTextField();
        customerName.setEditable(false);
        customerName.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(5, 5, 0, 0);
        gbc_comboBox.gridwidth = 2;
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.anchor = GridBagConstraints.WEST;
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 1;
        customerName.setColumns(10);
        customerName.setText(salesOrderEntity.getCustomer().getCustomerName());
        panel_4.add(customerName, gbc_comboBox);

        JLabel lblOrderDate = new JLabel("Date :");
        lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
        gbc_lblOrderDate.insets = new Insets(5, 5, 0, 0);
        gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
        gbc_lblOrderDate.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblOrderDate.gridx = 2;
        gbc_lblOrderDate.gridy = 0;
        panel_4.add(lblOrderDate, gbc_lblOrderDate);

        orderDate = new JTextField();
        orderDate.setEditable(false);
        orderDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GridBagConstraints gbc_orderDate = new GridBagConstraints();
        gbc_orderDate.fill = GridBagConstraints.HORIZONTAL;
        gbc_orderDate.anchor = GridBagConstraints.WEST;
        gbc_orderDate.insets = new Insets(5, 5, 0, 0);
        gbc_orderDate.gridx = 3;
        gbc_orderDate.gridy = 0;
        orderDate.setColumns(10);
        orderDate.setText(Utils.formatOrderDate(salesOrderEntity.getOrderDate()));
        panel_4.add(orderDate, gbc_orderDate);

        JLabel lblOrder = new JLabel("Order # :");
        lblOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblOrder = new GridBagConstraints();
        gbc_lblOrder.insets = new Insets(5, 5, 0, 0);
        gbc_lblOrder.anchor = GridBagConstraints.EAST;
        gbc_lblOrder.fill = GridBagConstraints.BOTH;
        gbc_lblOrder.gridx = 0;
        gbc_lblOrder.gridy = 0;
        panel_4.add(lblOrder, gbc_lblOrder);

        orderNo = new JTextField();
        orderNo.setEditable(false);
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        //		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.anchor = GridBagConstraints.WEST;
        gbc_textField_1.insets = new Insets(5, 5, 0, 0);
        gbc_textField_1.gridx = 1;
        gbc_textField_1.gridy = 0;
        orderNo.setColumns(10);
        orderNo.setText(Utils.formatOrderId(salesOrderEntity.getSalesOrderId()));
        panel_4.add(orderNo, gbc_textField_1);

        JLabel lblOrderStatus = new JLabel("Order Status :");
        lblOrderStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblOrderStatus = new GridBagConstraints();
        gbc_lblOrderStatus.anchor = GridBagConstraints.EAST;
        gbc_lblOrderStatus.insets = new Insets(5, 5, 0, 0);
        gbc_lblOrderStatus.gridx = 3;
        gbc_lblOrderStatus.gridy = 1;
        panel_4.add(lblOrderStatus, gbc_lblOrderStatus);

        orderStatus = new JTextField();
        orderStatus.setEditable(false);
        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.insets = new Insets(5, 5, 0, 0);
        gbc_textField_2.anchor = GridBagConstraints.WEST;
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridwidth = 3;
        gbc_textField_2.gridx = 4;
        gbc_textField_2.gridy = 1;
        orderStatus.setColumns(20);
        orderStatus.setText(ShippingStatus.values()[salesOrderEntity.getShippingStatus()] + ", "
                + InvoiceStatus.values()[salesOrderEntity.getInvoiceStatus()] + ", "
                + PaymentStatus.values()[salesOrderEntity.getPaymentStatus()]);
        panel_4.add(orderStatus, gbc_textField_2);

        JLabel lblOrderBy = new JLabel("Order By :");
        lblOrderBy.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblOrderBy = new GridBagConstraints();
        gbc_lblOrderBy.anchor = GridBagConstraints.EAST;
        gbc_lblOrderBy.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblOrderBy.insets = new Insets(5, 5, 0, 0);
        gbc_lblOrderBy.gridx = 4;
        gbc_lblOrderBy.gridy = 0;
        panel_4.add(lblOrderBy, gbc_lblOrderBy);

        orderByCombo = new JTextField();
        orderByCombo.setEditable(false);
        orderByCombo.setColumns(10);
        GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
        gbc_orderByCombo.insets = new Insets(5, 5, 0, 0);
        gbc_orderByCombo.anchor = GridBagConstraints.WEST;
        gbc_orderByCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_orderByCombo.gridx = 5;
        gbc_orderByCombo.gridy = 0;
        orderByCombo.setText((salesOrderEntity.getUser() == null) ? "" : salesOrderEntity.getUser().getUserName());
        panel_4.add(orderByCombo, gbc_orderByCombo);

        JLabel lblvia = new JLabel("VIA :");
        lblvia.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblvia = new GridBagConstraints();
        gbc_lblvia.insets = new Insets(5, 5, 0, 0);
        gbc_lblvia.anchor = GridBagConstraints.EAST;
        gbc_lblvia.gridx = 7;
        gbc_lblvia.gridy = 0;
        panel_4.add(lblvia, gbc_lblvia);

        viaField = new JTextField();
        GridBagConstraints gbc_viaField = new GridBagConstraints();
        gbc_viaField.anchor = GridBagConstraints.WEST;
        gbc_viaField.insets = new Insets(5, 5, 0, 5);
        gbc_viaField.gridx = 8;
        gbc_viaField.gridy = 0;
        viaField.setColumns(10);
        viaField.setText(salesOrderEntity.getVia());
        panel_4.add(viaField, gbc_viaField);

        JLabel lblref = new JLabel("REF #:");
        lblref.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblref = new GridBagConstraints();
        gbc_lblref.insets = new Insets(5, 5, 0, 0);
        gbc_lblref.anchor = GridBagConstraints.EAST;
        gbc_lblref.gridx = 7;
        gbc_lblref.gridy = 1;
        panel_4.add(lblref, gbc_lblref);

        refField = new JTextField();
        GridBagConstraints gbc_refField = new GridBagConstraints();
        gbc_refField.anchor = GridBagConstraints.WEST;
        gbc_refField.insets = new Insets(5, 5, 0, 5);
        gbc_refField.gridx = 8;
        gbc_refField.gridy = 1;
        refField.setColumns(10);
        refField.setText(salesOrderEntity.getRefNo());
        panel_4.add(refField, gbc_refField);

        JPanel panel_5 = new JPanel();
        GridBagConstraints gbc_panel_5 = new GridBagConstraints();
        gbc_panel_5.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_5.anchor = GridBagConstraints.NORTHWEST;
        gbc_panel_5.gridx = 0;
        gbc_panel_5.gridy = 1;
        add(panel_5, gbc_panel_5);
        GridBagLayout gbl_panel_5 = new GridBagLayout();
        gbl_panel_5.columnWidths = new int[] { 0, 0 };
        gbl_panel_5.rowHeights = new int[] { 0, 0 };
        gbl_panel_5.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_panel_5.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
        panel_5.setLayout(gbl_panel_5);

        final JTable table = new JTable(tableModel) {
            private static final long serialVersionUID = 750751563851573015L;

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                case PARTY_COL:
                    return ComboItem.class;
                case ITEM_COL:
                    return ComboItem.class;
                case DETAIL_COL:
                    return String.class;
                case PRICE_COL:
                    return BigDecimal.class;
                case SUB_TOTAL_COL:
                    return BigDecimal.class;
                default:
                    return Integer.class;
                }
            }

            @Override
            // Always selectAll()
            public boolean editCellAt(int row, int column, EventObject e) {
                boolean result = super.editCellAt(row, column, e);
                final Component editor = getEditorComponent();
                if ((editor == null) || !(editor instanceof JTextComponent)) {
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
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        try {
            table.setAutoCreateRowSorter(false);
            TableColumnModel columnModel = table.getColumnModel();
            table.setTableHeader(new JTableHeader(columnModel) {
                private static final long serialVersionUID = 8903261917308082401L;

                @Override
                public Dimension getPreferredSize() {
                    Dimension d = super.getPreferredSize();
                    d.height = 32;
                    return d;
                }
            });
            table.setRowHeight(20);
            columnModel.getColumn(PARTY_COL).setPreferredWidth(100);
            columnModel.getColumn(ITEM_COL).setPreferredWidth(120);
            columnModel.getColumn(DETAIL_COL).setPreferredWidth(80);
            for (int i = DETAIL_COL + 1; i < columnNos; i++) {
                columnModel.getColumn(i).setCellEditor(new IntegerEditor(0, Integer.MAX_VALUE));
                columnModel.getColumn(i).setCellRenderer(rightRenderer);
                columnModel.getColumn(i).setPreferredWidth(25);
            }
            for (int i = SUB_TOTAL_COL + 1; i < (SUB_TOTAL_COL + 9); i++) {
                columnModel.getColumn(i).setPreferredWidth(0);
                columnModel.getColumn(i).setMinWidth(0);
                columnModel.getColumn(i).setMaxWidth(0);
                columnModel.getColumn(i).setWidth(0);
            }
            columnModel.getColumn(SUB_TOTAL_QUANTITY).setPreferredWidth(30);
            columnModel.getColumn(SUB_TOTAL_QUANTITY).setCellRenderer(rightRenderer);
            columnModel.getColumn(PRICE_COL).setPreferredWidth(75);
            columnModel.getColumn(PRICE_COL).setCellRenderer(rightRenderer);
            columnModel.getColumn(SUB_TOTAL_COL).setPreferredWidth(75);
            columnModel.getColumn(SUB_TOTAL_COL).setCellRenderer(rightRenderer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadOrderDetails();

        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Remove");
        popupMenu.add(deleteItem);

        deleteItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tableModel.removeRow(table.getSelectedRow());
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                if (SwingUtilities.isRightMouseButton(event)) {
                    Point point = event.getPoint();
                    int currentRow = table.rowAtPoint(point);
                    if (!shippedFlag) {
                        table.setRowSelectionInterval(currentRow, currentRow);
                        popupMenu.show(table.getTableHeader(), event.getX(), event.getY());
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(600, 300));
        GridBagConstraints gbc_table = new GridBagConstraints();
        gbc_table.fill = GridBagConstraints.BOTH;
        gbc_table.gridx = 0;
        gbc_table.gridy = 0;
        panel_5.add(scroll, gbc_table);

        final JPanel panel_7 = new JPanel();
        panel_7.setBackground(new Color(245, 222, 179));
        GridBagConstraints gbc_panel_7 = new GridBagConstraints();
        gbc_panel_7.fill = GridBagConstraints.BOTH;
        gbc_panel_7.anchor = GridBagConstraints.NORTH;
        gbc_panel_7.gridx = 0;
        gbc_panel_7.gridy = 2;
        add(panel_7, gbc_panel_7);
        GridBagLayout gbl_panel_7 = new GridBagLayout();
        gbl_panel_7.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
        gbl_panel_7.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 20 };
        gbl_panel_7.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
        gbl_panel_7.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
        panel_7.setLayout(gbl_panel_7);

        JLabel lblTaxScheme = new JLabel("Taxing Scheme :");
        lblTaxScheme.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblTaxScheme = new GridBagConstraints();
        gbc_lblTaxScheme.anchor = GridBagConstraints.EAST;
        gbc_lblTaxScheme.insets = new Insets(5, 0, 0, 0);
        gbc_lblTaxScheme.gridx = 0;
        gbc_lblTaxScheme.gridy = 0;
        panel_7.add(lblTaxScheme, gbc_lblTaxScheme);

        taxSchemeCombo = new JComboBox();
        taxSchemeCombo.setPrototypeDisplayValue(len);
        GridBagConstraints gbc_taxSchemeCombo = new GridBagConstraints();
        gbc_taxSchemeCombo.insets = new Insets(5, 5, 0, 10);
        gbc_taxSchemeCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_taxSchemeCombo.anchor = GridBagConstraints.NORTHWEST;
        gbc_taxSchemeCombo.gridx = 1;
        gbc_taxSchemeCombo.gridy = 0;
        panel_7.add(taxSchemeCombo, gbc_taxSchemeCombo);

        JLabel lblOrdTotal = new JLabel("Order-Total :");
        lblOrdTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblOrdTotal = new GridBagConstraints();
        gbc_lblOrdTotal.anchor = GridBagConstraints.EAST;
        gbc_lblOrdTotal.insets = new Insets(5, 5, 0, 0);
        gbc_lblOrdTotal.gridx = 4;
        gbc_lblOrdTotal.gridy = 0;
        //		panel_7.add(lblOrdTotal, gbc_lblOrdTotal);

        orderTotalField = new JTextField();
        orderTotalField.setEditable(false);
        orderTotalField.setHorizontalAlignment(SwingConstants.RIGHT);
        orderTotalField.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_balance = new GridBagConstraints();
        gbc_balance.insets = new Insets(5, 5, 0, 0);
        gbc_balance.fill = GridBagConstraints.HORIZONTAL;
        gbc_balance.gridx = 5;
        gbc_balance.gridy = 0;
        orderTotalField.setColumns(size);
        orderTotalField.setText(Utils.currencyFormat(salesOrderEntity.getOrderTotal()));
        //		panel_7.add(orderTotalField, gbc_balance);

        JLabel lblSubTotal = new JLabel("Sub-Total :");
        lblSubTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblSubTotal = new GridBagConstraints();
        gbc_lblSubTotal.anchor = GridBagConstraints.EAST;
        gbc_lblSubTotal.insets = new Insets(5, 5, 0, 0);
        gbc_lblSubTotal.gridx = 4;
        gbc_lblSubTotal.gridy = 1;
        panel_7.add(lblSubTotal, gbc_lblSubTotal);

        subTotalField = new JTextField();
        subTotalField.setEditable(false);
        subTotalField.setHorizontalAlignment(SwingConstants.RIGHT);
        subTotalField.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_balance1 = new GridBagConstraints();
        gbc_balance1.insets = new Insets(5, 5, 0, 0);
        gbc_balance1.fill = GridBagConstraints.HORIZONTAL;
        gbc_balance1.gridx = 5;
        gbc_balance1.gridy = 1;
        subTotalField.setColumns(size);
        subTotalField.setText(Utils.currencyFormat(salesOrderEntity.getSubTotal()));
        panel_7.add(subTotalField, gbc_balance1);

        JLabel lblDiscount = new JLabel("Discount :");
        lblDiscount.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblDiscount = new GridBagConstraints();
        gbc_lblDiscount.anchor = GridBagConstraints.EAST;
        gbc_lblDiscount.insets = new Insets(5, 5, 0, 0);
        gbc_lblDiscount.gridx = 2;
        gbc_lblDiscount.gridy = 1;
        panel_7.add(lblDiscount, gbc_lblOrdTotal);

        discountField = new JTextField();
        //		discountField.setEditable(false);
        discountField.setHorizontalAlignment(SwingConstants.RIGHT);
        discountField.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_discountField = new GridBagConstraints();
        gbc_discountField.insets = new Insets(0, 5, 0, 0);
        gbc_discountField.fill = GridBagConstraints.HORIZONTAL;
        gbc_discountField.gridx = 3;
        gbc_discountField.gridy = 1;
        discountField.setColumns(size);
        discountField.setText(Utils.currencyFormat(salesOrderEntity.getDiscount()));
        panel_7.add(discountField, gbc_balance);

        JLabel lblShipping = new JLabel("Shipping Status :");
        lblShipping.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblShipping = new GridBagConstraints();
        gbc_lblShipping.anchor = GridBagConstraints.EAST;
        gbc_lblShipping.insets = new Insets(5, 0, 0, 0);
        gbc_lblShipping.gridx = 0;
        gbc_lblShipping.gridy = 1;
        panel_7.add(lblShipping, gbc_lblShipping);

        shippingStatusCombo = new JComboBox();
        shippingStatusCombo.setPrototypeDisplayValue(len);
        shippingStatusCombo.addItem(new ComboItem(0, "Not Shipped"));
        shippingStatusCombo.addItem(new ComboItem(1, "Shipped"));
        GridBagConstraints gbc_inventoryStatusCombo = new GridBagConstraints();
        gbc_inventoryStatusCombo.insets = new Insets(5, 5, 0, 10);
        gbc_inventoryStatusCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_inventoryStatusCombo.gridx = 1;
        gbc_inventoryStatusCombo.gridy = 1;

        if (shippedFlag) {
            shippingStatusCombo.setEnabled(false);
        }

        shippingStatusCombo.setSelectedIndex(shippingStatus);
        panel_7.add(shippingStatusCombo, gbc_inventoryStatusCombo);

        JLabel lblinvoice = new JLabel("Invoice Status :");
        lblinvoice.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblinvoice = new GridBagConstraints();
        gbc_lblinvoice.anchor = GridBagConstraints.EAST;
        gbc_lblinvoice.insets = new Insets(5, 0, 0, 0);
        gbc_lblinvoice.gridx = 0;
        gbc_lblinvoice.gridy = 2;
        panel_7.add(lblinvoice, gbc_lblinvoice);

        invoiceStatusCombo = new JComboBox();
        invoiceStatusCombo.setPrototypeDisplayValue(len);
        invoiceStatusCombo.addItem(new ComboItem(0, "Uninvoiced"));
        invoiceStatusCombo.addItem(new ComboItem(1, "Invoiced"));
        GridBagConstraints gbc_invoiceStatusCombo = new GridBagConstraints();
        gbc_invoiceStatusCombo.insets = new Insets(5, 5, 0, 10);
        gbc_invoiceStatusCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_invoiceStatusCombo.gridx = 1;
        gbc_invoiceStatusCombo.gridy = 2;

        if (invoiceFlag) {
            invoiceStatusCombo.setEnabled(false);
        }
        invoiceStatusCombo.setSelectedIndex(invoiceStatus);
        panel_7.add(invoiceStatusCombo, gbc_invoiceStatusCombo);

        JLabel lblPayment = new JLabel("Payment Status :");
        lblPayment.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblPayment = new GridBagConstraints();
        gbc_lblPayment.anchor = GridBagConstraints.EAST;
        gbc_lblPayment.insets = new Insets(5, 0, 0, 0);
        gbc_lblPayment.gridx = 0;
        gbc_lblPayment.gridy = 3;
        panel_7.add(lblPayment, gbc_lblPayment);

        paymentStatusCombo = new JComboBox();
        paymentStatusCombo.setPrototypeDisplayValue(len);
        paymentStatusCombo.addItem(new ComboItem(0, "Unpaid"));
        paymentStatusCombo.addItem(new ComboItem(1, "Paid"));
        GridBagConstraints gbc_paymentStatusCombo = new GridBagConstraints();
        gbc_paymentStatusCombo.insets = new Insets(5, 5, 0, 10);
        gbc_paymentStatusCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_paymentStatusCombo.gridx = 1;
        gbc_paymentStatusCombo.gridy = 3;
        paymentStatusCombo.setSelectedIndex(paymentStatus);
        panel_7.add(paymentStatusCombo, gbc_paymentStatusCombo);

        JLabel lblRemarks = new JLabel("Remarks :");
        lblRemarks.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblRemarks = new GridBagConstraints();
        gbc_lblRemarks.anchor = GridBagConstraints.EAST;
        gbc_lblRemarks.insets = new Insets(5, 5, 0, 0);
        gbc_lblRemarks.gridx = 2;
        gbc_lblRemarks.gridy = 0;
        panel_7.add(lblRemarks, gbc_lblRemarks);

        remarks = new JTextArea();
        remarks.setFont(new Font("Monospaced", Font.BOLD, 12));
        remarks.setRows(2);
        remarks.setColumns(20);
        remarks.setBorder(new LineBorder(UIManager.getColor("TextArea.inactiveForeground")));
        GridBagConstraints gbc_textArea1 = new GridBagConstraints();
        gbc_textArea1.anchor = GridBagConstraints.NORTHWEST;
        gbc_textArea1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textArea1.insets = new Insets(5, 5, 0, 0);
        gbc_textArea1.gridheight = 2;
        gbc_textArea1.gridx = 3;
        gbc_textArea1.gridy = 0;
        remarks.setText(salesOrderEntity.getRemarks());
        JScrollPane scrollpane1 = new JScrollPane(remarks);
        panel_7.add(scrollpane1, gbc_textArea1);

        JLabel lblConditions = new JLabel("Conditions :");
        lblConditions.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblConditions = new GridBagConstraints();
        gbc_lblConditions.anchor = GridBagConstraints.EAST;
        gbc_lblConditions.insets = new Insets(5, 0, 0, 0);
        gbc_lblConditions.gridx = 2;
        gbc_lblConditions.gridy = 2;
        panel_7.add(lblConditions, gbc_lblConditions);

        conditions = new JTextArea();
        conditions.setFont(new Font("Monospaced", Font.BOLD, 12));
        conditions.setRows(2);
        conditions.setColumns(20);
        conditions.setBorder(new LineBorder(UIManager.getColor("TextArea.inactiveForeground")));
        conditions.setText(salesOrderEntity.getConditions());
        GridBagConstraints gbc_conditions = new GridBagConstraints();
        gbc_conditions.anchor = GridBagConstraints.NORTHWEST;
        gbc_conditions.fill = GridBagConstraints.HORIZONTAL;
        gbc_conditions.insets = new Insets(5, 5, 0, 0);
        gbc_conditions.gridheight = 2;
        gbc_conditions.gridx = 3;
        gbc_conditions.gridy = 2;
        JScrollPane scrollpane2 = new JScrollPane(conditions);
        panel_7.add(scrollpane2, gbc_conditions);

        JLabel lblTotal = new JLabel("Total :");
        lblTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblTotal = new GridBagConstraints();
        gbc_lblTotal.anchor = GridBagConstraints.EAST;
        gbc_lblTotal.insets = new Insets(0, 5, 0, 0);
        gbc_lblTotal.gridx = 4;
        gbc_lblTotal.gridy = 4;
        panel_7.add(lblTotal, gbc_lblTotal);

        totalField = new JTextField();
        totalField.setEditable(false);
        totalField.setHorizontalAlignment(SwingConstants.RIGHT);
        totalField.setFont(new Font("Tahoma", Font.BOLD, 12));
        totalField.setColumns(size);
        totalField.setText(Utils.currencyFormat(salesOrderEntity.getTotal()));
        GridBagConstraints gbc_totalField = new GridBagConstraints();
        gbc_totalField.insets = new Insets(0, 5, 0, 0);
        gbc_totalField.fill = GridBagConstraints.HORIZONTAL;
        gbc_totalField.gridx = 5;
        gbc_totalField.gridy = 4;
        panel_7.add(totalField, gbc_totalField);

        JLabel lblPaid = new JLabel("Paid :");
        lblPaid.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblPaid = new GridBagConstraints();
        gbc_lblPaid.anchor = GridBagConstraints.EAST;
        gbc_lblPaid.insets = new Insets(0, 5, 0, 0);
        gbc_lblPaid.gridx = 2;
        gbc_lblPaid.gridy = 4;
        panel_7.add(lblPaid, gbc_lblPaid);

        paidField = new JTextField();
        paidField.setHorizontalAlignment(SwingConstants.RIGHT);
        paidField.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_paidField = new GridBagConstraints();
        gbc_paidField.insets = new Insets(0, 5, 0, 0);
        gbc_paidField.fill = GridBagConstraints.HORIZONTAL;
        gbc_paidField.gridx = 3;
        gbc_paidField.gridy = 4;
        paidField.setColumns(size);
        paidField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
        panel_7.add(paidField, gbc_paidField);

        if (paidFlag) {
            taxSchemeCombo.setEnabled(false);
            paymentStatusCombo.setEnabled(false);
            paidField.setEditable(false);
        } else {
            paidField.setEditable(true);
        }

        JLabel lblBalance = new JLabel("Balance :");
        lblBalance.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblBalance = new GridBagConstraints();
        gbc_lblBalance.anchor = GridBagConstraints.EAST;
        gbc_lblBalance.insets = new Insets(0, 5, 0, 0);
        gbc_lblBalance.gridx = 4;
        gbc_lblBalance.gridy = 5;
        panel_7.add(lblBalance, gbc_lblBalance);

        balanceField = new JTextField();
        balanceField.setEditable(false);
        balanceField.setHorizontalAlignment(SwingConstants.RIGHT);
        balanceField.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_balanceField = new GridBagConstraints();
        gbc_balanceField.insets = new Insets(0, 5, 0, 0);
        gbc_balanceField.fill = GridBagConstraints.HORIZONTAL;
        gbc_balanceField.gridx = 5;
        gbc_balanceField.gridy = 5;
        balanceField.setColumns(size);
        balanceField.setText(Utils.currencyFormat(salesOrderEntity.getRemainingBalance()));
        panel_7.add(balanceField, gbc_balanceField);

        //show only when tax scheme is selected 
        lblTax1 = new JLabel();
        lblTax1.setFont(new Font("Tahoma", Font.BOLD, 12));
        final GridBagConstraints gbc_lbltax1 = new GridBagConstraints();
        gbc_lbltax1.anchor = GridBagConstraints.EAST;
        gbc_lbltax1.insets = new Insets(0, 5, 0, 0);
        gbc_lbltax1.gridx = 4;
        gbc_lbltax1.gridy = 2;

        taxField1 = new JTextField();
        taxField1.setEditable(false);
        taxField1.setHorizontalAlignment(SwingConstants.RIGHT);
        taxField1.setFont(new Font("Tahoma", Font.BOLD, 12));
        final GridBagConstraints gbc_taxField1 = new GridBagConstraints();
        gbc_taxField1.insets = new Insets(0, 5, 0, 0);
        gbc_taxField1.fill = GridBagConstraints.HORIZONTAL;
        gbc_taxField1.gridx = 5;
        gbc_taxField1.gridy = 2;
        taxField1.setColumns(size);

        lblTax2 = new JLabel();
        lblTax2.setFont(new Font("Tahoma", Font.BOLD, 12));
        final GridBagConstraints gbc_lbltax2 = new GridBagConstraints();
        gbc_lbltax2.anchor = GridBagConstraints.EAST;
        gbc_lbltax2.insets = new Insets(0, 5, 0, 0);
        gbc_lbltax2.gridx = 4;
        gbc_lbltax2.gridy = 3;

        taxField2 = new JTextField();
        taxField2.setEditable(false);
        taxField2.setHorizontalAlignment(SwingConstants.RIGHT);
        taxField2.setFont(new Font("Tahoma", Font.BOLD, 12));
        final GridBagConstraints gbc_taxField2 = new GridBagConstraints();
        gbc_taxField2.insets = new Insets(0, 5, 0, 0);
        gbc_taxField2.fill = GridBagConstraints.HORIZONTAL;
        gbc_taxField2.gridx = 5;
        gbc_taxField2.gridy = 3;
        taxField2.setColumns(size);

        TaxEntity taxingScheme = salesOrderEntity.getTaxingScheme();
        if (taxingScheme != null) {
            loadTaxSchemeCombo(taxingScheme);
            lblTax1.setText(taxingScheme.getPrimaryTaxName());
            lblTax2.setText(taxingScheme.getSecondaryTaxName());
            taxField1.setText(Utils.currencyFormat(salesOrderEntity.getTaxAmount1()));
            taxField2.setText(Utils.currencyFormat(salesOrderEntity.getTaxAmount2()));
            panel_7.add(lblTax1, gbc_lbltax1);
            panel_7.add(taxField1, gbc_taxField1);
            panel_7.add(lblTax2, gbc_lbltax2);
            panel_7.add(taxField2, gbc_taxField2);
        } else {
            loadTaxSchemeCombo(null);
        }

        JButton btnPayments = new JButton("Payments");
        btnPayments.setIcon(Utils.getImageIcon("pay.jpg"));
        btnPayments.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_btnPayments = new GridBagConstraints();
        gbc_btnPayments.anchor = GridBagConstraints.CENTER;
        gbc_btnPayments.gridx = 0;
        gbc_btnPayments.gridy = 6;
        panel_7.add(btnPayments, gbc_btnPayments);

        JButton btnOrderForm = new JButton("Order Form");
        btnOrderForm.setIcon(Utils.getImageIcon("OF.jpg"));
        btnOrderForm.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_btnOrderForm = new GridBagConstraints();
        gbc_btnOrderForm.anchor = GridBagConstraints.WEST;
        gbc_btnOrderForm.gridx = 1;
        gbc_btnOrderForm.gridy = 6;
        panel_7.add(btnOrderForm, gbc_btnOrderForm);

        JButton btnUpdateOrder = new JButton("Update Order");
        btnUpdateOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
        gbc_btnSaveOrder.anchor = GridBagConstraints.CENTER;
        gbc_btnSaveOrder.insets = new Insets(5, 10, 0, 0);
        gbc_btnSaveOrder.gridx = 3;
        gbc_btnSaveOrder.gridy = 6;
        panel_7.add(btnUpdateOrder, gbc_btnSaveOrder);

        if (!shippedFlag && !paidFlag) {
            JPanel panel_6 = new JPanel();
            FlowLayout flowLayout = (FlowLayout) panel_6.getLayout();
            flowLayout.setAlignment(FlowLayout.LEFT);
            GridBagConstraints gbc_panel_6 = new GridBagConstraints();
            gbc_panel_6.anchor = GridBagConstraints.WEST;
            gbc_panel_6.insets = new Insets(0, 0, 0, 5);
            gbc_panel_6.gridx = 0;
            gbc_panel_6.gridy = 1;
            panel_5.add(panel_6, gbc_panel_6);

            JButton addRowButton = new JButton("");
            addRowButton.setBorderPainted(false);
            addRowButton.setIcon(Utils.getImageIcon("add.png"));
            addRowButton.setVerticalAlignment(SwingConstants.TOP);
            addRowButton.setHorizontalAlignment(SwingConstants.LEFT);
            panel_6.add(addRowButton);

            JButton removeRowButton = new JButton("");
            removeRowButton.setBorderPainted(false);
            removeRowButton.setIcon(Utils.getImageIcon("remove.png"));
            removeRowButton.setVerticalAlignment(SwingConstants.TOP);
            panel_6.add(removeRowButton);

            addRowButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    ItemSelectionDialog dialog = new ItemSelectionDialog(parent);
                    if (tableModel.getRowCount() == 0) {
                        ComboItems items = dialog.showDialog();
                        if ((items.getItem() != null) && (items.getDetailMap() != null)) {
                            Map<ComboItem, ProductDetailEntity> detailMap = items.getDetailMap();
                            for (Map.Entry<ComboItem, ProductDetailEntity> entry : detailMap.entrySet()) {
                                ProductDetailEntity detailEntity = entry.getValue();
                                int xsmall = detailEntity.getXsmall() - detailEntity.getRes_xsmall();
                                int small = detailEntity.getSmall() - detailEntity.getRes_small();
                                int medium = detailEntity.getMedium() - detailEntity.getRes_medium();
                                int large = detailEntity.getLarge() - detailEntity.getRes_large();
                                int xlarge = detailEntity.getXlarge() - detailEntity.getRes_xlarge();
                                int xxlarge = detailEntity.getXxlarge() - detailEntity.getRes_xxlarge();
                                int xxxlarge = detailEntity.getXxxlarge() - detailEntity.getRes_xxxlarge();
                                int mix = detailEntity.getMix() - detailEntity.getRes_mix();
                                int total = xsmall + small + medium + large + xlarge + xxlarge + xxxlarge + mix;
                                if (items.isSelectAll()) {
                                    tableModel.addRow(new Object[] { items.getParty(), items.getItem(), entry.getKey(),
                                            xsmall, small, medium, large, xlarge, xxlarge, xxxlarge, mix, total,
                                            new BigDecimal("0.00"), new BigDecimal("0.00"), xsmall, small, medium,
                                            large, xlarge, xxlarge, xxxlarge, mix });
                                } else {
                                    tableModel.addRow(new Object[] { items.getParty(), items.getItem(), entry.getKey(),
                                            new Integer(0), new Integer(0), new Integer(0), new Integer(0),
                                            new Integer(0), new Integer(0), new Integer(0), new Integer(0),
                                            new Integer(0), new BigDecimal("0.00"), new BigDecimal("0.00"), xsmall,
                                            small, medium, large, xlarge, xxlarge, xxxlarge, mix });
                                }
                            }
                        }
                    } else {
                        Object valueAt = tableModel.getValueAt(tableModel.getRowCount() - 1, ITEM_COL);
                        if ((valueAt != null) && (valueAt.toString().trim().length() != 0)) {
                            ComboItems items = dialog.showDialog();
                            if ((items.getItem() != null) && (items.getDetailMap() != null)) {
                                Map<ComboItem, ProductDetailEntity> detailMap = items.getDetailMap();
                                for (Map.Entry<ComboItem, ProductDetailEntity> entry : detailMap.entrySet()) {
                                    ProductDetailEntity detailEntity = entry.getValue();
                                    int xsmall = detailEntity.getXsmall() - detailEntity.getRes_xsmall();
                                    int small = detailEntity.getSmall() - detailEntity.getRes_small();
                                    int medium = detailEntity.getMedium() - detailEntity.getRes_medium();
                                    int large = detailEntity.getLarge() - detailEntity.getRes_large();
                                    int xlarge = detailEntity.getXlarge() - detailEntity.getRes_xlarge();
                                    int xxlarge = detailEntity.getXxlarge() - detailEntity.getRes_xxlarge();
                                    int xxxlarge = detailEntity.getXxxlarge() - detailEntity.getRes_xxxlarge();
                                    int mix = detailEntity.getMix() - detailEntity.getRes_mix();
                                    int total = xsmall + small + medium + large + xlarge + xxlarge + xxxlarge + mix;
                                    if (items.isSelectAll()) {
                                        tableModel.addRow(new Object[] { items.getParty(), items.getItem(),
                                                entry.getKey(), xsmall, small, medium, large, xlarge, xxlarge,
                                                xxxlarge, mix, total, new BigDecimal("0.00"), new BigDecimal("0.00"),
                                                xsmall, small, medium, large, xlarge, xxlarge, xxxlarge, mix });
                                    } else {
                                        tableModel.addRow(new Object[] { items.getParty(), items.getItem(),
                                                entry.getKey(), new Integer(0), new Integer(0), new Integer(0),
                                                new Integer(0), new Integer(0), new Integer(0), new Integer(0),
                                                new Integer(0), new Integer(0), new BigDecimal("0.00"),
                                                new BigDecimal("0.00"), xsmall, small, medium, large, xlarge, xxlarge,
                                                xxxlarge, mix });
                                    }
                                }
                            }
                        } else {
                            String message = "The Item Name/Detail cannot be empty!";
                            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                            table.changeSelection(tableModel.getRowCount() - 1, ITEM_COL, false, false);
                            table.requestFocus();
                        }
                    }
                }
            });

            removeRowButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (!shippedFlag) {
                        tableModel.removeRow(tableModel.getRowCount() - 1);
                    }
                }
            });

        }

        tableModel.addTableModelListener(new TableModelListener() {

            public void tableChanged(TableModelEvent e) {
                int index = e.getColumn();
                int lastRow = e.getLastRow();

                switch (e.getType()) {
                case TableModelEvent.UPDATE:
                    if (((index >= (DETAIL_COL + 1)) && (index <= (SUB_TOTAL_QUANTITY - 1))) || (index == PRICE_COL)) {
                        StringBuilder msg = new StringBuilder("Exceed Available Inventory ");
                        validated = false;
                        if ((index >= (DETAIL_COL + 1)) && (index <= (SUB_TOTAL_QUANTITY - 1))) {
                            Integer valueAt = (Integer) tableModel.getValueAt(lastRow, index);
                            switch (index) {
                            case DETAIL_COL + 1:
                                Integer xSmallQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 1);
                                if (valueAt > xSmallQty) {
                                    validated = true;
                                    msg.append("XS: " + xSmallQty);
                                }
                                break;
                            case DETAIL_COL + 2:
                                Integer smallQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 2);
                                if (valueAt > smallQty) {
                                    validated = true;
                                    msg.append("S: " + smallQty);
                                }
                                break;
                            case DETAIL_COL + 3:
                                Integer mediumQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 3);
                                if (valueAt > mediumQty) {
                                    validated = true;
                                    msg.append("M: " + mediumQty);
                                }
                                break;
                            case DETAIL_COL + 4:
                                Integer largeQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 4);
                                if (valueAt > largeQty) {
                                    validated = true;
                                    msg.append("L: " + largeQty);
                                }
                                break;
                            case DETAIL_COL + 5:
                                Integer xlargeQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 5);
                                if (valueAt > xlargeQty) {
                                    validated = true;
                                    msg.append("1X: " + xlargeQty);
                                }
                                break;
                            case DETAIL_COL + 6:
                                Integer xxlargeQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 6);
                                if (valueAt > xxlargeQty) {
                                    validated = true;
                                    msg.append("2X: " + xxlargeQty);
                                }
                                break;
                            case DETAIL_COL + 7:
                                Integer xxxlargeQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 7);
                                if (valueAt > xxxlargeQty) {
                                    validated = true;
                                    msg.append("3X: " + xxxlargeQty);
                                }
                                break;
                            case DETAIL_COL + 8:
                                Integer mixQty = (Integer) tableModel.getValueAt(lastRow, SUB_TOTAL_COL + 8);
                                if (valueAt > mixQty) {
                                    validated = true;
                                    msg.append("Mix: " + mixQty);
                                }
                                break;
                            }
                        }
                        if (!validated) {
                            BigDecimal totalSubTotal = new BigDecimal("0.00");
                            for (int i = 0; i < tableModel.getRowCount(); i++) {
                                Integer rowCount = 0;
                                for (int j = DETAIL_COL + 1; j < columnNos; j++) {
                                    rowCount += (Integer) tableModel.getValueAt(i, j);
                                }
                                tableModel.setValueAt(rowCount, i, SUB_TOTAL_QUANTITY);
                                BigDecimal unitPrice = (BigDecimal) tableModel.getValueAt(i, PRICE_COL);
                                BigDecimal subTotal = unitPrice.multiply(new BigDecimal(rowCount.toString()));
                                tableModel.setValueAt(Utils.currencyFormat(subTotal), i, SUB_TOTAL_COL);
                                totalSubTotal = totalSubTotal.add(subTotal);
                            }
                            orderTotalField.setText(Utils.currencyFormat(totalSubTotal));
                            String dis = discountField.getText();
                            BigDecimal subtract = totalSubTotal.subtract(Utils.normalFormat(dis));
                            if ((dis.length() != 0) && (subtract.compareTo(BigDecimal.ZERO) > 0)) {
                                subTotalField.setText(Utils.currencyFormat(subtract));
                            } else {
                                subTotalField.setText(orderTotalField.getText());
                            }
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), msg, "Dialog", JOptionPane.ERROR_MESSAGE);
                            tableModel.setValueAt(0, lastRow, index);
                        }
                    } else {
                        //Do nothing
                    }
                    break;

                case TableModelEvent.DELETE:
                    BigDecimal totalSubTotal = new BigDecimal("0.00");
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        Integer rowCount = 0;
                        for (int j = DETAIL_COL + 1; j < columnNos; j++) {
                            rowCount += (Integer) tableModel.getValueAt(i, j);
                        }
                        tableModel.setValueAt(rowCount, i, SUB_TOTAL_QUANTITY);
                        BigDecimal unitPrice = (BigDecimal) tableModel.getValueAt(i, PRICE_COL);
                        BigDecimal subTotal = unitPrice.multiply(new BigDecimal(rowCount.toString()));
                        tableModel.setValueAt(Utils.currencyFormat(subTotal), i, SUB_TOTAL_COL);
                        totalSubTotal = totalSubTotal.add(subTotal);
                    }
                    orderTotalField.setText(Utils.currencyFormat(totalSubTotal));
                    String dis = discountField.getText();
                    BigDecimal subtract = totalSubTotal.subtract(Utils.normalFormat(dis));
                    if ((dis.length() != 0) && (subtract.compareTo(BigDecimal.ZERO) > 0)) {
                        subTotalField.setText(Utils.currencyFormat(subtract));
                    } else {
                        subTotalField.setText(orderTotalField.getText());
                    }
                default:
                    break;
                }
            }
        });

        subTotalField.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void changedUpdate(DocumentEvent e) {
                update();
            }

            private void update() {

                Runnable doUpdate = new Runnable() {

                    @Override
                    public void run() {
                        BigDecimal totalsubtotal = Utils.normalFormat(subTotalField.getText());
                        BigDecimal total = totalsubtotal;
                        if (taxSchemeCombo.getSelectedIndex() > 0) {
                            ComboItem item = (ComboItem) taxSchemeCombo.getSelectedItem();
                            TaxEntity entity = filterTaxEntity(item.getKey());
                            String primaryTaxName = entity.getPrimaryTaxName();
                            String secondaryTaxName = entity.getSecondaryTaxName();
                            BigDecimal taxRate1 = BigDecimal.valueOf(entity.getPrimaryTaxRate());
                            BigDecimal taxRate2 = BigDecimal.valueOf(entity.getSecondaryTaxRate());
                            BigDecimal percentageFactor1 = taxRate1.divide(hundred, 6, BigDecimal.ROUND_CEILING);
                            BigDecimal percentageFactor2 = taxRate2.divide(hundred, 6, BigDecimal.ROUND_CEILING);
                            lblTax1.setText(primaryTaxName);
                            lblTax2.setText(secondaryTaxName);
                            panel_7.add(lblTax1, gbc_lbltax1);
                            panel_7.add(lblTax2, gbc_lbltax2);

                            BigDecimal tax1 = totalsubtotal.multiply(percentageFactor1);
                            taxField1.setText(Utils.currencyFormat(tax1));
                            total = total.add(tax1);
                            panel_7.add(taxField1, gbc_taxField1);

                            if (entity.getIsSecondaryCompound()) {
                                BigDecimal tax2 = total.multiply(percentageFactor2);
                                taxField2.setText(Utils.currencyFormat(tax2));
                                total = total.add(tax2);
                            } else {
                                BigDecimal tax2 = totalsubtotal.multiply(percentageFactor2);
                                taxField2.setText(Utils.currencyFormat(tax2));
                                total = total.add(tax2);
                            }
                            panel_7.add(taxField2, gbc_taxField2);
                            totalField.setText(Utils.currencyFormat(total));
                        } else {
                            panel_7.remove(lblTax1);
                            panel_7.remove(lblTax2);
                            panel_7.remove(taxField1);
                            panel_7.remove(taxField2);
                            taxField1.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                            taxField2.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                            totalField.setText(Utils.currencyFormat(totalsubtotal));
                        }
                        panel_7.revalidate();
                        panel_7.repaint();
                    }
                };
                SwingUtilities.invokeLater(doUpdate);
            }

        });

        totalField.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void changedUpdate(DocumentEvent e) {
                update();
            }

            private void update() {
                Runnable doUpdate = new Runnable() {

                    @Override
                    public void run() {
                        int selectedIndex = paymentStatusCombo.getSelectedIndex();
                        BigDecimal paidAmount = salesOrderEntity.getPaidAmount();
                        BigDecimal total = Utils.normalFormat(totalField.getText());
                        if (selectedIndex == PaymentStatus.PAID.ordinal()) {
                            paidField.setText(Utils.currencyFormat(total.subtract(paidAmount)));
                            balanceField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                        } else {
                            if (paidField.getText().trim().length() != 0) {
                                BigDecimal paid = Utils.normalFormat(paidField.getText()).add(paidAmount);
                                balanceField.setText(Utils.currencyFormat(total.subtract(paid)));
                            } else {
                                balanceField.setText(Utils.currencyFormat(total.subtract(paidAmount)));
                            }
                        }
                    }
                };
                SwingUtilities.invokeLater(doUpdate);
            }

        });

        taxSchemeCombo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                BigDecimal totalsubtotal = Utils.normalFormat(subTotalField.getText());
                BigDecimal total = totalsubtotal;
                if (combo.getSelectedIndex() > 0) {
                    ComboItem item = (ComboItem) combo.getSelectedItem();
                    TaxEntity entity = filterTaxEntity(item.getKey());
                    String primaryTaxName = entity.getPrimaryTaxName();
                    String secondaryTaxName = entity.getSecondaryTaxName();
                    BigDecimal taxRate1 = BigDecimal.valueOf(entity.getPrimaryTaxRate());
                    BigDecimal taxRate2 = BigDecimal.valueOf(entity.getSecondaryTaxRate());
                    BigDecimal percentageFactor1 = taxRate1.divide(hundred, 6, BigDecimal.ROUND_CEILING);
                    BigDecimal percentageFactor2 = taxRate2.divide(hundred, 6, BigDecimal.ROUND_CEILING);
                    lblTax1.setText(primaryTaxName);
                    lblTax2.setText(secondaryTaxName);
                    panel_7.add(lblTax1, gbc_lbltax1);
                    panel_7.add(lblTax2, gbc_lbltax2);

                    BigDecimal tax1 = totalsubtotal.multiply(percentageFactor1);
                    taxField1.setText(Utils.currencyFormat(tax1));
                    total = total.add(tax1);
                    panel_7.add(taxField1, gbc_taxField1);

                    if (entity.getIsSecondaryCompound()) {
                        BigDecimal tax2 = total.multiply(percentageFactor2);
                        taxField2.setText(Utils.currencyFormat(tax2));
                        total = total.add(tax2);
                    } else {
                        BigDecimal tax2 = totalsubtotal.multiply(percentageFactor2);
                        taxField2.setText(Utils.currencyFormat(tax2));
                        total = total.add(tax2);
                    }
                    panel_7.add(taxField2, gbc_taxField2);
                    totalField.setText(Utils.currencyFormat(total));
                } else {
                    panel_7.remove(lblTax1);
                    panel_7.remove(lblTax2);
                    panel_7.remove(taxField1);
                    panel_7.remove(taxField2);
                    taxField1.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                    taxField2.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                    totalField.setText(Utils.currencyFormat(totalsubtotal));
                }
                panel_7.revalidate();
                panel_7.repaint();
                int selectedIndex = paymentStatusCombo.getSelectedIndex();
                BigDecimal paidAmount = salesOrderEntity.getPaidAmount();
                if (selectedIndex == PaymentStatus.PAID.ordinal()) {
                    BigDecimal totalAmt = Utils.normalFormat(totalField.getText());
                    paidField.setText(Utils.currencyFormat(totalAmt.subtract(paidAmount)));
                    balanceField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                } else {
                    if (paidField.getText().trim().length() != 0) {
                        BigDecimal paid = Utils.normalFormat(paidField.getText()).add(paidAmount);
                        balanceField.setText(Utils.currencyFormat(total.subtract(paid)));
                    } else {
                        balanceField.setText(Utils.currencyFormat(total.subtract(salesOrderEntity.getPaidAmount())));
                    }
                }
            }
        });

        btnPayments.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                new ViewPaymentDetails(parent, salesOrderEntity);
            }
        });

        btnOrderForm.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JCheckBox checkbox = new JCheckBox("Include Tax details");
                Object[] params = { checkbox, "View", "Send Email" };
                int action1 = JOptionPane.showOptionDialog(SOUpdateDialog.this,
                        "What you want to do with this order form?", "Order Form", JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, params, params[1]);
                boolean dontShow = checkbox.isSelected();
                if (action1 == 1) {
                    if (dontShow) {
                        Utils.viewJasperReport("orderFormWithTax.jasper", salesOrderEntity.getSalesOrderId(), true);
                    } else {
                        Utils.viewJasperReport("orderForm.jasper", salesOrderEntity.getSalesOrderId(), true);
                    }
                } else if (action1 == 2) {
                    CustomerEntity customer = salesOrderEntity.getCustomer();
                    String email = customer.getEmail();
                    if ((email != null) && !"".equals(email)) {
                        String subject = "Order Form";
                        String message = "Hi " + customer.getCustomerName()
                                + ",\nPlease find attached order form.\nThanks";
                        JasperPrint viewJasperReport = null;
                        if (dontShow) {
                            viewJasperReport = Utils.viewJasperReport("orderFormWithTax.jasper",
                                    salesOrderEntity.getSalesOrderId(), false);
                        } else {
                            viewJasperReport = Utils.viewJasperReport("orderForm.jasper",
                                    salesOrderEntity.getSalesOrderId(), false);
                        }
                        OutputStream output;
                        try {
                            File file = new File(Utils.getFilesDirectory("Order_Form.pdf"));
                            output = new FileOutputStream(file);
                            JasperExportManager.exportReportToPdfStream(viewJasperReport, output);

                            Properties smtpProperties = new ConfigUtility().loadProperties();

                            EmailUtility.sendEmail(smtpProperties, email, subject, message, file);

                            JOptionPane.showMessageDialog(new JFrame(), "The e-mail has been sent successfully!",
                                    "Message", JOptionPane.INFORMATION_MESSAGE);

                        } catch (FileNotFoundException e1) {
                            e1.printStackTrace();
                        } catch (JRException e1) {
                            e1.printStackTrace();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(new JFrame(),
                                    "Error while sending the e-mail: " + ex.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    } else {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Please add email address for customer " + customer.getCustomerName(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        paidField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (paidField.getText().trim().length() != 0) {
                    paidField.setText(Utils.currencyFormat(Utils.normalFormat(paidField.getText())));
                } else {
                    paidField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                paidField.setCaretPosition(paidField.getDocument().getLength());
                paidField.selectAll();
            }
        });

        discountField.addFocusListener(new FocusListener() {

            @Override
            public void focusLost(FocusEvent e) {
                if (discountField.getText().trim().length() != 0) {
                    discountField.setText(Utils.currencyFormat(Utils.normalFormat(discountField.getText())));
                } else {
                    discountField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                discountField.setCaretPosition(discountField.getDocument().getLength());
                discountField.selectAll();
            }
        });

        discountField.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void changedUpdate(DocumentEvent e) {
                update();
            }

            private void update() {

                Runnable doUpdate = new Runnable() {

                    @Override
                    public void run() {
                        BigDecimal total = Utils.normalFormat(orderTotalField.getText());
                        if (discountField.getText().trim().length() != 0) {
                            BigDecimal discount = Utils.normalFormat(discountField.getText());
                            if ((discount.compareTo(total) > 0) || (discount.compareTo(BigDecimal.ZERO) < 0)) {
                                discountField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                                JOptionPane.showMessageDialog(new JFrame(),
                                        "Discount can't be more than order total or negative!", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            } else {
                                subTotalField.setText(Utils.currencyFormat(total.subtract(discount)));
                            }
                        }
                    }
                };
                SwingUtilities.invokeLater(doUpdate);
            }

        });

        paidField.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            public void insertUpdate(DocumentEvent e) {
                update();
            }

            public void changedUpdate(DocumentEvent e) {
                update();
            }

            private void update() {
                BigDecimal total = Utils.normalFormat(totalField.getText());
                if (paidField.getText().trim().length() != 0) {
                    BigDecimal paid = Utils.normalFormat(paidField.getText()).add(salesOrderEntity.getPaidAmount());
                    balanceField.setText(Utils.currencyFormat(total.subtract(paid)));
                } else {
                    balanceField.setText(Utils.currencyFormat(total.subtract(salesOrderEntity.getPaidAmount())));
                }
            }
        });

        paymentStatusCombo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                int selectedIndex = combo.getSelectedIndex();
                BigDecimal total = Utils.normalFormat(totalField.getText());
                BigDecimal paidAmount = salesOrderEntity.getPaidAmount();
                switch (selectedIndex) {
                case 0:
                    paidField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                    balanceField.setText(Utils.currencyFormat(total.subtract(paidAmount)));
                    paidField.setEditable(true);
                    break;
                case 1:
                    paidField.setEditable(false);
                    paidField.setText(Utils.currencyFormat(total.subtract(paidAmount)));
                    balanceField.setText(Utils.currencyFormat(new BigDecimal("0.00")));
                    break;
                }
            }
        });

        btnUpdateOrder.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "Add at least one item to the table !", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    if (checkForNullEntries()) {
                        JOptionPane.showMessageDialog(new JFrame(), "The Item Name/Detail cannot be empty!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        table.changeSelection(tableModel.getRowCount() - 1, ITEM_COL, false, false);
                        table.requestFocus();
                    } else if (checkForDuplicateEntries()) {
                        JOptionPane.showMessageDialog(new JFrame(),
                                "Quantity cannot be present for the same Item,Detail & Size!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else if ((invoiceFlag || (invoiceStatusCombo.getSelectedIndex() == 1))
                            && (taxSchemeCombo.getSelectedIndex() <= 0)) {
                        JOptionPane.showMessageDialog(new JFrame(), "Please choose taxing scheme!", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else if (!validated) {
                        boolean shipped = false;
                        boolean notshipped = false;
                        int selectedShippingStatus = shippingStatusCombo.getSelectedIndex();
                        if ((selectedShippingStatus == ShippingStatus.SHIPPED.ordinal()) && !shippedFlag) {
                            shipped = true;
                            salesOrderEntity.setShippingStatus(selectedShippingStatus);
                            salesOrderEntity.setShippingDate(Utils.getDateInMillies(new Date()));
                        } else if ((selectedShippingStatus == ShippingStatus.NOT_SHIPPED.ordinal()) && !shippedFlag) {
                            notshipped = true;
                        }

                        int selectedInvoiceStatus = invoiceStatusCombo.getSelectedIndex();
                        if ((selectedInvoiceStatus == InvoiceStatus.INVOICED.ordinal()) && !invoiceFlag) {
                            salesOrderEntity.setInvoiceStatus(selectedInvoiceStatus);
                            try {
                                long orderId = salesOrderEntity.getSalesOrderId();
                                JasperPrint viewJasperReport = Utils.viewJasperReport("invoice.jasper", orderId, false);
                                salesOrderEntity.setInvoice(new InvoiceEntity(new SerialBlob(JasperExportManager
                                        .exportReportToPdf(viewJasperReport)), Utils.getDateInMillies(new Date())));
                            } catch (JRException e1) {
                                e1.printStackTrace();
                            } catch (SerialException e1) {
                                e1.printStackTrace();
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        } else if ((selectedInvoiceStatus == InvoiceStatus.UNINVOICED.ordinal()) && invoiceFlag) {
                            salesOrderEntity.setInvoiceStatus(selectedInvoiceStatus);
                        }

                        int selectedPayStatus = paymentStatusCombo.getSelectedIndex();
                        if ((selectedPayStatus == PaymentStatus.PAID.ordinal()) && !paidFlag) {
                            salesOrderEntity.setPaymentStatus(selectedPayStatus);
                        }

                        int selectedTaxScheme = taxSchemeCombo.getSelectedIndex();
                        //fetch tax scheme
                        TaxEntity fetchTaxEntity = salesOrderEntity.getTaxingScheme();
                        if (selectedTaxScheme > 0) {
                            ComboItem taxScheme = (ComboItem) taxSchemeCombo.getSelectedItem();
                            //							if((fetchTaxEntity!= null && taxScheme.getKey() != fetchTaxEntity.getTaxId()) || fetchTaxEntity == null){
                            fetchTaxEntity = manageTaxes.fetchTaxEntity(taxScheme.getKey());
                            BigDecimal taxAmount1 = Utils.normalFormat(taxField1.getText());
                            BigDecimal taxAmount2 = Utils.normalFormat(taxField2.getText());
                            salesOrderEntity.setTaxingScheme(fetchTaxEntity);
                            salesOrderEntity.setTaxAmount1(taxAmount1);
                            salesOrderEntity.setTaxAmount2(taxAmount2);
                            //							}
                        } else {
                            if (fetchTaxEntity != null) {
                                salesOrderEntity.setTaxingScheme(null);
                                BigDecimal taxAmount1 = Utils.normalFormat(taxField1.getText());
                                BigDecimal taxAmount2 = Utils.normalFormat(taxField2.getText());
                                salesOrderEntity.setTaxAmount1(taxAmount1);
                                salesOrderEntity.setTaxAmount2(taxAmount2);
                            }
                        }

                        //						if(!paidFlag || !shippedFlag){
                        BigDecimal orderTotal = Utils.normalFormat(orderTotalField.getText());
                        BigDecimal discount = Utils.normalFormat(discountField.getText());
                        BigDecimal subTotal = Utils.normalFormat(subTotalField.getText());
                        BigDecimal total = Utils.normalFormat(totalField.getText());
                        BigDecimal paidAmountNew = Utils.normalFormat(paidField.getText());
                        BigDecimal remainingBalance = Utils.normalFormat(balanceField.getText());

                        BigDecimal paidAmountOld = salesOrderEntity.getPaidAmount();

                        //							if(paidAmountOld.compareTo(paidAmountNew) != 0){
                        SortedSet<PaymentEntity> payentities = salesOrderEntity.getPaymentDetails();
                        //								if(paidAmountOld.compareTo(paidAmountNew) > 0){
                        //									payentities.add(new PaymentEntity(paidAmountOld.subtract(paidAmountNew), OrderPayStatus.REFUND.ordinal(), System.currentTimeMillis()));
                        //								}else{
                        if (paidAmountNew.compareTo(BigDecimal.ZERO) > 0) {
                            payentities.add(new PaymentEntity(paidAmountNew, OrderPayStatus.PAID.ordinal(), System
                                    .currentTimeMillis()));
                        }
                        //								}
                        salesOrderEntity.setPaymentDetails(payentities);
                        //							}
                        salesOrderEntity.setOrderTotal(orderTotal);
                        salesOrderEntity.setDiscount(discount);
                        salesOrderEntity.setSubTotal(subTotal);
                        salesOrderEntity.setTotal(total);
                        salesOrderEntity.setPaidAmount(paidAmountOld.add(paidAmountNew));
                        salesOrderEntity.setRemainingBalance(remainingBalance);

                        Vector dataVector = tableModel.getDataVector();
                        TreeSet<SalesOrderDetailEntity> newOrderDetails = new TreeSet<SalesOrderDetailEntity>();
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            Vector row = (Vector) dataVector.get(i);

                            ComboItem party = (ComboItem) row.elementAt(PARTY_COL);
                            ComboItem item = (ComboItem) row.elementAt(ITEM_COL);
                            ComboItem detail = (ComboItem) row.elementAt(DETAIL_COL);

                            int xsmall = Integer.parseInt(row.elementAt(DETAIL_COL + 1).toString());
                            int small = Integer.parseInt(row.elementAt(DETAIL_COL + 2).toString());
                            int medium = Integer.parseInt(row.elementAt(DETAIL_COL + 3).toString());
                            int large = Integer.parseInt(row.elementAt(DETAIL_COL + 4).toString());
                            int xlarge = Integer.parseInt(row.elementAt(DETAIL_COL + 5).toString());
                            int xxlarge = Integer.parseInt(row.elementAt(DETAIL_COL + 6).toString());
                            int xxxlarge = Integer.parseInt(row.elementAt(DETAIL_COL + 7).toString());
                            int mix = Integer.parseInt(row.elementAt(DETAIL_COL + 8).toString());
                            int totalQuantity = Integer.parseInt(row.elementAt(SUB_TOTAL_QUANTITY).toString());

                            newOrderDetails.add(new SalesOrderDetailEntity(party.getKey(), party.getValue(), item
                                    .getKey(), item.getValue(), detail.getKey(), detail.getValue(), xsmall, small,
                                    medium, large, xlarge, xxlarge, xxxlarge, mix, totalQuantity, Utils
                                            .normalFormat(row.elementAt(PRICE_COL).toString()), Utils.normalFormat(row
                                            .elementAt(SUB_TOTAL_COL).toString())));
                        }
                        //						}	
                        salesOrderEntity.setConditions(conditions.getText());
                        salesOrderEntity.setVia(viaField.getText());
                        salesOrderEntity.setRefNo(refField.getText());
                        salesOrderEntity.setRemarks(remarks.getText());
                        long currentTimeMillis = System.currentTimeMillis();
                        salesOrderEntity.setUpdatedOn(currentTimeMillis);

                        boolean flag = manageSalesOrders.updateSalesOrder(salesOrderEntity, newOrderDetails, shipped,
                                notshipped);
                        if (flag) {
                            String message = "Sales Order updated successfully !";
                            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
                                    JOptionPane.INFORMATION_MESSAGE);
                            flagForRefresh = true;
                            hideDialog();
                        } else {
                            String message = "Error while updating sales order !";
                            JOptionPane.showMessageDialog(new JFrame(), message, "Dialog", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

            private boolean checkForNullEntries() {
                int rowCount = tableModel.getRowCount();
                Object valueAt = null;
                if (rowCount > 0) {
                    valueAt = tableModel.getValueAt(rowCount - 1, ITEM_COL);
                    if (valueAt instanceof String) {
                        return "".equals(valueAt.toString().trim());
                    }
                }
                return false;
            }

            private boolean checkForDuplicateEntries() {
                boolean flag = false;
                int rows = tableModel.getRowCount();
                for (int i = 0; i < rows; i++) {
                    ComboItem mainParty = (ComboItem) tableModel.getValueAt(i, PARTY_COL);
                    ComboItem mainItem = (ComboItem) tableModel.getValueAt(i, ITEM_COL);
                    ComboItem mainDetail = (ComboItem) tableModel.getValueAt(i, DETAIL_COL);
                    for (int j = i + 1; j < rows; j++) {
                        ComboItem childParty = (ComboItem) tableModel.getValueAt(j, PARTY_COL);
                        ComboItem childItem = (ComboItem) tableModel.getValueAt(j, ITEM_COL);
                        ComboItem childDetail = (ComboItem) tableModel.getValueAt(j, DETAIL_COL);
                        if (mainParty.getValue().equalsIgnoreCase(childParty.getValue())
                                && mainItem.getValue().equalsIgnoreCase(childItem.getValue())
                                && mainDetail.getValue().equalsIgnoreCase(childDetail.getValue())) {
                            for (int k = DETAIL_COL + 1; k < columnNos; k++) {
                                Integer mainValue = (Integer) tableModel.getValueAt(i, k);
                                Integer childValue = (Integer) tableModel.getValueAt(j, k);
                                if ((mainValue > 0) && (childValue > 0)) {
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (flag) {
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                return flag;
            }
        });

    }

    private void loadOrderDetails() {
        tableModel.setNumRows(0);
        for (SalesOrderDetailEntity detail : entities) {
            ProductDetailEntity detailEntity = manageProducts.fetchProductDetailEntity(detail.getItemDetailId());
            tableModel.addRow(new Object[] { new ComboItem(detail.getPartyId(), detail.getPartyName()),
                    new ComboItem(detail.getItemId(), detail.getItemName()),
                    new ComboItem(detail.getItemDetailId(), detail.getDetailName()), detail.getXsmall(),
                    detail.getSmall(), detail.getMedium(), detail.getLarge(), detail.getXlarge(), detail.getXxlarge(),
                    detail.getXxxlarge(), detail.getMix(), detail.getTotalQuantity(), detail.getUnitPrice(),
                    Utils.currencyFormat(detail.getSubTotal()),
                    detail.getXsmall() + (detailEntity.getXsmall() - detailEntity.getRes_xsmall()),
                    detail.getSmall() + (detailEntity.getSmall() - detailEntity.getRes_small()),
                    detail.getMedium() + (detailEntity.getMedium() - detailEntity.getRes_medium()),
                    detail.getLarge() + (detailEntity.getLarge() - detailEntity.getRes_large()),
                    detail.getXlarge() + (detailEntity.getXlarge() - detailEntity.getRes_xlarge()),
                    detail.getXxlarge() + (detailEntity.getXxlarge() - detailEntity.getRes_xxlarge()),
                    detail.getXxxlarge() + (detailEntity.getXxxlarge() - detailEntity.getRes_xxxlarge()),
                    detail.getMix() + (detailEntity.getMix() - detailEntity.getRes_mix()) });
        }
    }

    @SuppressWarnings("unchecked")
    private void loadTaxSchemeCombo(TaxEntity entity) {
        taxSchemeCombo.removeAllItems();
        taxSchemeCombo.addItem(new ComboItem(0, ""));
        listTaxingSchemes = new ManageTaxes().listTaxingSchemes();
        for (TaxEntity tax : listTaxingSchemes) {
            ComboItem item = new ComboItem(tax.getTaxId(), tax.getTaxSchemeName() + " - " + tax.getPrimaryTaxRate()
                    + "/" + tax.getSecondaryTaxRate());
            taxSchemeCombo.addItem(item);
            if ((entity != null) && (tax.getTaxId() == entity.getTaxId())) {
                taxSchemeCombo.setSelectedItem(item);
            }
        }
    }

    private TaxEntity filterTaxEntity(long key) {
        TaxEntity taxentity = null;
        for (TaxEntity entity : listTaxingSchemes) {
            if (entity.getTaxId() == key) {
                taxentity = entity;
                break;
            }
        }
        return taxentity;
    }

    private void hideDialog() {
        setVisible(false);
        dispose();
    }
}
