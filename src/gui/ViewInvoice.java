package gui;

import java.awt.Frame;

import javax.swing.JDialog;

import main.Utils;
import net.sf.jasperreports.engine.JasperPrint;

public class ViewInvoice extends JDialog {

	private static final long serialVersionUID = -2577288379415138852L;
	private Long orderId;
	private boolean isDisplay;

	public ViewInvoice(Frame parent, Long orderId, boolean isDisplay) {
		super(parent);
		this.orderId = orderId;
		this.isDisplay = isDisplay;
	}
	
	public JasperPrint showInvoice(){
		JasperPrint viewJasperReport = null;
//		try {
			viewJasperReport = Utils.viewJasperReport("invoice.jasper", orderId, isDisplay);
//			manageSalesOrders.saveInvoice(orderId, JasperExportManager.exportReportToPdf(viewJasperReport));
//		} catch (JRException e1) {
//			e1.printStackTrace();
//		}
		return viewJasperReport;
	}
}
