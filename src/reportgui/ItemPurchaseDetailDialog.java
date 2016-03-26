package reportgui;

import hibernate.ProductEntity;
import hibernate.UserEntity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import main.UserType;
import main.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import contoller.ManageProducts;
import contoller.ManageUsers;

@SuppressWarnings("rawtypes")
public class ItemPurchaseDetailDialog extends JDialog {
    private static final long serialVersionUID = 6761841706701925636L;

    private static ManageProducts manageProducts = new ManageProducts();
    private List<ProductEntity> list;

    private JDatePickerImpl datePickerFrom;
    private JDatePickerImpl datePickerTo;
    private JComboBox partyCombo;
    private JComboBox itemcombo;

    public ItemPurchaseDetailDialog(Frame parent) {
        super(parent);
        JDatePanelImpl datePanelFrom = new JDatePanelImpl(new UtilDateModel());
        JDatePanelImpl datePanelTo = new JDatePanelImpl(new UtilDateModel());
        DateLabelFormatter formatter = new DateLabelFormatter();
        datePickerFrom = new JDatePickerImpl(datePanelFrom, formatter);
        datePickerTo = new JDatePickerImpl(datePanelTo, formatter);

        createLayout();

        setTitle("Purchase Order Details Report");
        setSize(600, 300);
        setModalityType(ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setVisible(true);
    }

    @SuppressWarnings({ "unchecked" })
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

        JLabel lblParty = new JLabel("Party Name :");
        lblParty.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblParty = new GridBagConstraints();
        gbc_lblParty.insets = new Insets(20, 5, 30, 0);
        gbc_lblParty.anchor = GridBagConstraints.EAST;
        gbc_lblParty.gridx = 0;
        gbc_lblParty.gridy = 0;
        panel_4.add(lblParty, gbc_lblParty);

        partyCombo = new JComboBox();
        partyCombo.setPrototypeDisplayValue("0123456789012345678");
        GridBagConstraints gbc_orderByCombo = new GridBagConstraints();
        gbc_orderByCombo.insets = new Insets(20, 5, 30, 0);
        gbc_orderByCombo.fill = GridBagConstraints.BOTH;
        gbc_orderByCombo.gridx = 1;
        gbc_orderByCombo.gridy = 0;
        panel_4.add(partyCombo, gbc_orderByCombo);

        JLabel lblItemName = new JLabel("Item Name :");
        lblItemName.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblCustomerName = new GridBagConstraints();
        gbc_lblCustomerName.anchor = GridBagConstraints.NORTHEAST;
        gbc_lblCustomerName.insets = new Insets(20, 5, 30, 0);
        gbc_lblCustomerName.gridx = 2;
        gbc_lblCustomerName.gridy = 0;
        panel_4.add(lblItemName, gbc_lblCustomerName);

        itemcombo = new JComboBox();
        //		itemcombo.setEditable(true);
        itemcombo.setPrototypeDisplayValue("0123456789012345678");
        GridBagConstraints gbc_itemcombo = new GridBagConstraints();
        gbc_itemcombo.insets = new Insets(20, 5, 30, 0);
        gbc_itemcombo.anchor = GridBagConstraints.NORTHWEST;
        gbc_itemcombo.fill = GridBagConstraints.HORIZONTAL;
        gbc_itemcombo.gridx = 3;
        gbc_itemcombo.gridy = 0;
        panel_4.add(itemcombo, gbc_itemcombo);

        loadPartyCombo();

        JLabel lblOrderDate = new JLabel("From Date :");
        lblOrderDate.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblOrderDate = new GridBagConstraints();
        gbc_lblOrderDate.anchor = GridBagConstraints.EAST;
        gbc_lblOrderDate.insets = new Insets(20, 5, 0, 0);
        gbc_lblOrderDate.gridx = 0;
        gbc_lblOrderDate.gridy = 1;
        panel_4.add(lblOrderDate, gbc_lblOrderDate);

        GridBagConstraints gbc_textField_3 = new GridBagConstraints();
        gbc_textField_3.insets = new Insets(20, 5, 0, 0);
        gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_3.gridx = 1;
        gbc_textField_3.gridy = 1;
        panel_4.add(datePickerFrom, gbc_textField_3);

        JLabel lblTo = new JLabel("to");
        lblTo.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_lblTo = new GridBagConstraints();
        gbc_lblTo.anchor = GridBagConstraints.CENTER;
        gbc_lblTo.insets = new Insets(20, 0, 0, 0);
        gbc_lblTo.gridx = 2;
        gbc_lblTo.gridy = 1;
        panel_4.add(lblTo, gbc_lblTo);

        GridBagConstraints gbc_textField_4 = new GridBagConstraints();
        gbc_textField_4.insets = new Insets(20, 0, 0, 10);
        gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_4.gridx = 3;
        gbc_textField_4.gridy = 1;
        panel_4.add(datePickerTo, gbc_textField_4);

        JButton btnUpdateOrder = new JButton("Generate Report");
        btnUpdateOrder.setFont(new Font("Tahoma", Font.BOLD, 12));
        GridBagConstraints gbc_btnSaveOrder = new GridBagConstraints();
        gbc_btnSaveOrder.anchor = GridBagConstraints.CENTER;
        gbc_btnSaveOrder.insets = new Insets(40, 5, 0, 0);
        gbc_btnSaveOrder.gridx = 2;
        gbc_btnSaveOrder.gridy = 2;
        panel_4.add(btnUpdateOrder, gbc_btnSaveOrder);

        partyCombo.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ComboItem party = (ComboItem) e.getItem();
                    if (party.getKey() != 0) {
                        boolean loadItems = loadItems(itemcombo, null, party.getKey());
                        if (!loadItems) {
                            JOptionPane.showMessageDialog(new JFrame(), "No Items found for this party!", "Dialog",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        itemcombo.removeAllItems();
                    }
                }
            }
        });

        btnUpdateOrder.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date fromDate = (Date) datePickerFrom.getModel().getValue();
                Date toDate = (Date) datePickerTo.getModel().getValue();
                int selectedIndex = partyCombo.getSelectedIndex();
                if (selectedIndex <= 0) {
                    JOptionPane.showMessageDialog(new JFrame(), "Please select Party First!", "Dialog",
                            JOptionPane.ERROR_MESSAGE);
                } else if ((fromDate != null) && (toDate != null) && fromDate.after(toDate)) {
                    JOptionPane.showMessageDialog(new JFrame(), "From date cannot be greater than to date!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Calendar cal = Calendar.getInstance();
                    ComboItem selectedParty = (ComboItem) partyCombo.getSelectedItem();
                    ComboItem selectedItem = (ComboItem) itemcombo.getSelectedItem();
                    StringBuilder sb = new StringBuilder();
                    HashMap map = new HashMap();
                    map.put("fromDate", 0l);
                    map.put("toDate", 0l);
                    sb.append("  sims.PURCHASE_ORDERS_DETAILS.`PARTY_ID` = " + selectedParty.getKey());
                    if (fromDate != null) {
                        cal.setTime(fromDate);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        map.put("fromDate", cal.getTimeInMillis());
                        sb.append(" and sims.PURCHASE_ORDERS.ORDER_DATE >= " + cal.getTimeInMillis());
                    }
                    if (toDate != null) {
                        cal.setTime(toDate);
                        cal.set(Calendar.HOUR_OF_DAY, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        map.put("toDate", cal.getTimeInMillis());
                        sb.append(" and sims.PURCHASE_ORDERS.ORDER_DATE <= " + cal.getTimeInMillis());
                    }
                    if (selectedItem.getKey() > 0) {
                        sb.append(" and sims.PURCHASE_ORDERS_DETAILS.`ITEM_ID` = " + selectedItem.getKey());
                    }
                    sb.append(" ");

                    map.put("whereClause", sb.toString());

                    JasperPrint jpPrint = null;
                    try {
                        if (selectedItem.getKey() > 0) {
                            jpPrint = JasperFillManager.fillReport(
                                    Utils.getReportJasperName("ItemPurchaseReport2.jasper"), map,
                                    hibernate.Database.getConnection());
                        } else {
                            jpPrint = JasperFillManager.fillReport(
                                    Utils.getReportJasperName("ItemPurchaseReport1.jasper"), map,
                                    hibernate.Database.getConnection());
                        }
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

    @SuppressWarnings("unchecked")
    private void loadPartyCombo() {
        partyCombo.removeAllItems();
        partyCombo.addItem(new ComboItem(0, ""));
        List<UserEntity> list = new ManageUsers().listUsers(UserType.PARTY.ordinal());
        for (UserEntity user : list) {
            partyCombo.addItem(new ComboItem(user.getUserId(), user.getUserName()));
        }
    }

    @SuppressWarnings({ "unchecked" })
    private boolean loadItems(JComboBox comboBox, String item, long partyId) {
        if (item == null) {
            list = manageProducts.listProductsByParty(partyId);
        } else {
            list = manageProducts.listProductsByNameAndParty(item, partyId);
        }
        comboBox.removeAllItems();
        if (list.size() > 0) {
            comboBox.addItem(new ComboItem(0, ""));
            for (ProductEntity product : list) {
                comboBox.addItem(new ComboItem(product.getItemId(), product.getItemName()));
            }
            return true;
        }
        return false;
    }
}