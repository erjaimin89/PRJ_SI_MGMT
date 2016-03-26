package reportgui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import main.ComboItem;
import main.DateLabelFormatter;
import main.InvoiceStatus;
import main.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class SalesOrderDetailsDialog extends JDialog {
    private static final long serialVersionUID = 9117997297046137399L;

    private JDatePickerImpl datePickerFrom;
    private JDatePickerImpl datePickerTo;

    public SalesOrderDetailsDialog(Frame parent) {
        super(parent);
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(new UtilDateModel());
        JDatePanelImpl datePanelTo = new JDatePanelImpl(new UtilDateModel());
        DateLabelFormatter formatter = new DateLabelFormatter();
        datePickerFrom = new JDatePickerImpl(datePanelFrom, formatter);
        datePickerTo = new JDatePickerImpl(datePanelTo, formatter);

        createLayout();

        setTitle("Sales Order Details Report");
        setSize(600, 300);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setVisible(true);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void createLayout() {
        JPanel panel = (JPanel) getContentPane();
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[] { 0, 0 };
        layout.rowHeights = new int[] { 0 };
        layout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        layout.rowWeights = new double[] { 1.0 };
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
        gbl_panel_4.columnWidths = new int[] { 0, 0, 0, 0, 0 };
        gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        panel_4.setLayout(gbl_panel_4);

        JLabel lblOrderDate = new JLabel("Order Date :");
        lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
        gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
        gbc_lblOrderDate.insets = new Insets(20, 5, 0, 0);
        gbc_lblOrderDate.gridx = 0;
        gbc_lblOrderDate.gridy = 0;
        panel_4.add(lblOrderDate, gbc_lblOrderDate);

        GridBagConstraints gbc_textField_3 = new GridBagConstraints();
        gbc_textField_3.insets = new Insets(20, 5, 0, 0);
        gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_3.gridx = 1;
        gbc_textField_3.gridy = 0;
        panel_4.add(datePickerFrom, gbc_textField_3);

        JLabel lblTo = new JLabel("to");
        lblTo.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblTo = new GridBagConstraints();
        gbc_lblTo.anchor = GridBagConstraints.CENTER;
        gbc_lblTo.insets = new Insets(20, 0, 0, 0);
        gbc_lblTo.gridx = 2;
        gbc_lblTo.gridy = 0;
        panel_4.add(lblTo, gbc_lblTo);

        GridBagConstraints gbc_textField_4 = new GridBagConstraints();
        gbc_textField_4.insets = new Insets(20, 0, 0, 10);
        gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_4.gridx = 3;
        gbc_textField_4.gridy = 0;
        panel_4.add(datePickerTo, gbc_textField_4);

        JLabel lblInvoiceStatus = new JLabel("Invoice Status :");
        lblInvoiceStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblInvoiceStatus = new GridBagConstraints();
        gbc_lblInvoiceStatus.anchor = GridBagConstraints.EAST;
        gbc_lblInvoiceStatus.insets = new Insets(20, 5, 0, 0);
        gbc_lblInvoiceStatus.gridx = 0;
        gbc_lblInvoiceStatus.gridy = 1;
        panel_4.add(lblInvoiceStatus, gbc_lblInvoiceStatus);

        final JComboBox invoiceStatusCombo = new JComboBox();
        invoiceStatusCombo.setPrototypeDisplayValue("012345678901234");
        invoiceStatusCombo.addItem(new ComboItem(0, ""));
        invoiceStatusCombo.addItem(new ComboItem(1, "Uninvoiced"));
        invoiceStatusCombo.addItem(new ComboItem(2, "Invoiced"));
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.insets = new Insets(20, 5, 0, 0);
        gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_1.gridx = 1;
        gbc_comboBox_1.gridy = 1;
        panel_4.add(invoiceStatusCombo, gbc_comboBox_1);

        JLabel lblPaymentStatus = new JLabel("Payment Status :");
        lblPaymentStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblPaymentStatus = new GridBagConstraints();
        gbc_lblPaymentStatus.anchor = GridBagConstraints.EAST;
        gbc_lblPaymentStatus.insets = new Insets(20, 5, 0, 0);
        gbc_lblPaymentStatus.gridx = 2;
        gbc_lblPaymentStatus.gridy = 1;
        panel_4.add(lblPaymentStatus, gbc_lblPaymentStatus);

        final JComboBox paymentStatusCombo = new JComboBox();
        paymentStatusCombo.setPrototypeDisplayValue("012345678901234");
        paymentStatusCombo.addItem(new ComboItem(0, ""));
        paymentStatusCombo.addItem(new ComboItem(1, "Unpaid"));
        paymentStatusCombo.addItem(new ComboItem(2, "Paid"));
        GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
        gbc_comboBox_2.insets = new Insets(20, 5, 0, 10);
        gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox_2.gridx = 3;
        gbc_comboBox_2.gridy = 1;
        panel_4.add(paymentStatusCombo, gbc_comboBox_2);

        JLabel lblInventoryStatus = new JLabel("Shipping Status :");
        lblInventoryStatus.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblInventoryStatus = new GridBagConstraints();
        gbc_lblInventoryStatus.anchor = GridBagConstraints.EAST;
        gbc_lblInventoryStatus.insets = new Insets(20, 5, 0, 0);
        gbc_lblInventoryStatus.gridx = 0;
        gbc_lblInventoryStatus.gridy = 2;
        panel_4.add(lblInventoryStatus, gbc_lblInventoryStatus);

        final JComboBox inventoryStatusCombo = new JComboBox();
        inventoryStatusCombo.setPrototypeDisplayValue("012345678901234");
        inventoryStatusCombo.addItem(new ComboItem(0, ""));
        inventoryStatusCombo.addItem(new ComboItem(1, "Not Shipped"));
        inventoryStatusCombo.addItem(new ComboItem(2, "Shipped"));
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(20, 5, 0, 0);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 2;
        panel_4.add(inventoryStatusCombo, gbc_comboBox);

        JLabel lbltaxScheme = new JLabel("Taxing Scheme :");
        lbltaxScheme.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lbltaxScheme = new GridBagConstraints();
        gbc_lbltaxScheme.anchor = GridBagConstraints.EAST;
        gbc_lbltaxScheme.insets = new Insets(20, 5, 0, 0);
        gbc_lbltaxScheme.gridx = 2;
        gbc_lbltaxScheme.gridy = 2;
        panel_4.add(lbltaxScheme, gbc_lbltaxScheme);

        final JComboBox taxSchemeCombo = new JComboBox();
        taxSchemeCombo.setPrototypeDisplayValue("012345678901234");
        taxSchemeCombo.addItem(new ComboItem(0, ""));
        taxSchemeCombo.addItem(new ComboItem(1, "Yes"));
        taxSchemeCombo.addItem(new ComboItem(2, "No"));
        GridBagConstraints gbc_taxSchemeCombo = new GridBagConstraints();
        gbc_taxSchemeCombo.insets = new Insets(20, 5, 0, 10);
        gbc_taxSchemeCombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_taxSchemeCombo.gridx = 3;
        gbc_taxSchemeCombo.gridy = 2;
        panel_4.add(taxSchemeCombo, gbc_taxSchemeCombo);

        JButton btnUpdateOrder = new JButton("Generate Report");
        btnUpdateOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
        gbc_btnSaveOrder.anchor = GridBagConstraints.CENTER;
        gbc_btnSaveOrder.insets = new Insets(40, 5, 0, 0);
        gbc_btnSaveOrder.gridx = 2;
        gbc_btnSaveOrder.gridy = 4;
        panel_4.add(btnUpdateOrder, gbc_btnSaveOrder);

        btnUpdateOrder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date fromDate = (Date) datePickerFrom.getModel().getValue();
                Date toDate = (Date) datePickerTo.getModel().getValue();
                if ((fromDate != null) && (toDate != null) && fromDate.after(toDate)) {
                    JOptionPane.showMessageDialog(new JFrame(), "From date cannot be greater than to date!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Calendar cal = Calendar.getInstance();
                    int shipStatus = inventoryStatusCombo.getSelectedIndex() - 1;
                    int invStatus = invoiceStatusCombo.getSelectedIndex() - 1;
                    int payStatus = paymentStatusCombo.getSelectedIndex() - 1;
                    int selectedTax = taxSchemeCombo.getSelectedIndex() - 1;
                    StringBuilder sb = new StringBuilder();
                    HashMap map = new HashMap();
                    map.put("fromDate", 0l);
                    map.put("toDate", 0l);
                    String status = "";
                    sb.append("1=1 ");
                    if (shipStatus >= 0) {
                        status += (shipStatus == 0) ? "Not Shipped," : "Shipped,";
                        sb.append(" and sims.SALES_ORDERS.SHIPPING_STATUS = " + shipStatus);
                    }
                    if (invStatus >= 0) {
                        status += (invStatus == 0) ? "Uninvoiced," : "Invoiced,";
                        sb.append(" and sims.SALES_ORDERS.INVOICE_STATUS = " + invStatus);

                        if ((invStatus == InvoiceStatus.UNINVOICED.ordinal()) && (selectedTax >= 0)) {
                            status += (selectedTax == 0) ? "With Tax," : "W/o Tax,";
                            if (selectedTax == 0) {
                                sb.append(" and sims.SALES_ORDERS.`TAX_ID` IS NOT NULL");
                            } else {
                                sb.append(" and sims.SALES_ORDERS.`TAX_ID` IS NULL");
                            }
                        }
                    }
                    if (payStatus >= 0) {
                        status += (payStatus == 0) ? "Not Paid" : "Paid";
                        sb.append(" and sims.SALES_ORDERS.PAYMENT_STATUS = " + payStatus);
                    }
                    if (fromDate != null) {
                        cal.setTime(fromDate);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        map.put("fromDate", cal.getTimeInMillis());
                        sb.append(" and sims.SALES_ORDERS.ORDER_DATE >= " + cal.getTimeInMillis());
                    }
                    if (toDate != null) {
                        cal.setTime(toDate);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        map.put("toDate", cal.getTimeInMillis());
                        sb.append(" and sims.SALES_ORDERS.ORDER_DATE <= " + cal.getTimeInMillis());
                    }
                    sb.append(" ");

                    map.put("whereClause", sb.toString());

                    map.put("status", status);
                    JasperPrint jpPrint = null;
                    try {
                        jpPrint = JasperFillManager.fillReport(Utils.getReportJasperName("OrderDetailsReport.jasper"),
                                map, hibernate.Database.getConnection());
                        JasperViewer jr = new JasperViewer(jpPrint, false);
                        if (!jpPrint.getPages().isEmpty()) {
                            jr.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
                            jr.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            jr.setVisible(true);
                        }
                    } catch (JRException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }

}
