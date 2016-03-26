package main;

import java.awt.Dialog.ModalExclusionType;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class Utils {

	public static String formatOrderDate(long orderDate) {
		if(orderDate > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");    
			Date resultdate = new Date(orderDate);
			return sdf.format(resultdate);
		}else
			return "";
	}
	
	public static String formatOrderDateTime(long orderDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
		Date resultdate = new Date(orderDate);
		return sdf.format(resultdate);
	}

	public static String formatOrderId(long salesOrderId) {
		return "SO-"+String.format("%06d", salesOrderId);
	}
	
	public static Long normalOrderId(String salesOrderId) {
		return Long.parseLong(salesOrderId.replaceFirst("^SO-0+(?!$)", ""));
	}
	
	public static String formatBackUpOrderId(long backUpOrderId) {
		return "BO-"+String.format("%06d", backUpOrderId);
	}
	
	public static Long normalBackUpOrderId(String backUpOrderId) {
		return Long.parseLong(backUpOrderId.replaceFirst("^BO-0+(?!$)", ""));
	}
	
	public static Long normalPurchaseOrderId(String salesOrderId) {
		return Long.parseLong(salesOrderId.replaceFirst("^PO-0+(?!$)", ""));
	}
	
	public static String currencyFormat(BigDecimal n) {
	    return NumberFormat.getCurrencyInstance().format(n);
	}
	
	public static BigDecimal normalFormat(String str) {
	    return new BigDecimal(str.replaceAll("\\$", "").replaceAll("\\,", ""));
	}
	
	public static ImageIcon getImageIcon(String name){
		return new ImageIcon(Utils.class.getResource("/resources/images/"+name));
	}
	
	public static InputStream getReportJasperName(String name){
		return Utils.class.getResourceAsStream("/resources/reports/"+name);
	}
	
	public static String getFilesDirectory(String name){
		return System.getProperty("user.dir") + 
				File.separator +"resources" + 
				File.separator + "files" + 
				File.separator+name; 
	}

	public static String formatPurchaseOrderId(long purchaseOrderId) {
		return "PO-"+String.format("%06d", purchaseOrderId);
	}
	
	public static long getDateInMillies(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static JasperPrint viewJasperReport(String fileName, long orderId, boolean isDisplay){
		HashMap map = new HashMap();
		map.put("orderId", orderId);
		JasperPrint jpPrint = null;
		try {
		jpPrint = JasperFillManager.fillReport(Utils.getReportJasperName(fileName), map, hibernate.Database.getConnection());
		if(isDisplay){
				JasperViewer jr = new JasperViewer(jpPrint, false);
				if(!jpPrint.getPages().isEmpty()){
					jr.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
					jr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					jr.setVisible(true);
				}
			}
		} catch (JRException e) {
			e.printStackTrace();
		}
		return jpPrint;
	}
}
