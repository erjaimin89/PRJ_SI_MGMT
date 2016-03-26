package gui;

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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import main.DateLabelFormatter;
import main.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class CustomerBalanceDialog extends JDialog {
    private static final long serialVersionUID = -1524494673597865311L;
    private JDatePickerImpl datePickerFrom;
    private JDatePickerImpl datePickerTo;

    public CustomerBalanceDialog(Frame parent) {
        super(parent);
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(new UtilDateModel());
        JDatePanelImpl datePanelTo = new JDatePanelImpl(new UtilDateModel());
        DateLabelFormatter formatter = new DateLabelFormatter();
        datePickerFrom = new JDatePickerImpl(datePanelFrom, formatter);
        datePickerTo = new JDatePickerImpl(datePanelTo, formatter);

        createLayout();

        setTitle("Customer Balance Report");
        setSize(400, 200);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setVisible(true);

    }

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
        gbl_panel_4.columnWidths = new int[] { 0, 0, 0 };
        gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panel_4.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
        gbc_lblTo.insets = new Insets(20, 5, 0, 0);
        gbc_lblTo.gridx = 2;
        gbc_lblTo.gridy = 0;
        panel_4.add(lblTo, gbc_lblTo);

        GridBagConstraints gbc_textField_4 = new GridBagConstraints();
        gbc_textField_4.insets = new Insets(20, 0, 0, 10);
        gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_4.gridx = 1;
        gbc_textField_4.gridy = 1;
        panel_4.add(datePickerTo, gbc_textField_4);

        JButton btnUpdateOrder = new JButton("Generate Report");
        btnUpdateOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
        gbc_btnSaveOrder.anchor = GridBagConstraints.CENTER;
        gbc_btnSaveOrder.insets = new Insets(40, 5, 0, 0);
        gbc_btnSaveOrder.gridx = 1;
        gbc_btnSaveOrder.gridy = 2;
        panel_4.add(btnUpdateOrder, gbc_btnSaveOrder);

        btnUpdateOrder.addActionListener(new ActionListener() {

            @SuppressWarnings({ "unchecked", "rawtypes" })
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fromDate = (Date) datePickerFrom.getModel().getValue();
                Date toDate = (Date) datePickerTo.getModel().getValue();
                if ((fromDate != null) && (toDate != null) && fromDate.after(toDate)) {
                    JOptionPane.showMessageDialog(new JFrame(), "From date cannot be greater than to date!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Calendar cal = Calendar.getInstance();
                    StringBuilder sb = new StringBuilder();
                    HashMap map = new HashMap();
                    map.put("fromDate", 0l);
                    map.put("toDate", 0l);
                    sb.append("1=1 ");
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

                    JasperPrint jpPrint = null;
                    try {
                        jpPrint = JasperFillManager.fillReport(Utils.getReportJasperName("customer.jasper"), map,
                                hibernate.Database.getConnection());
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
