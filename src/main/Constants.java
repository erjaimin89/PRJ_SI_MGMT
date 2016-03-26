package main;


public class Constants {
	
	public static final String[] taxColumnNames = { "Tax Scheme Name", "Tax Name 1", "Tax Rate 1", 
		"Tax Name 2", "Tax Rate 2", "Compound Secondary"};
	
	public static final String[] custColumnNames = { "Name", "Company", "Balance",
		"Address", "Phone", "Email", "Fax", "View", "Update", "Activate/Deactivate", ""};
		//"Delete" };
	
	public static final String[] productColumnNames = {"Detail", "XS", "S", "M", "L",
		"1X", "2X", "3X", "Mix", "Sub-Total", ""};
	
	public static final String[] productDialogColumnNames = {"Detail", "XS", "S", "M", "L",
		"1X", "2X", "3X", "Mix", "Sub-Total"};
	
	public static final String[] productDialogColumnNames1 = {"Detail", "XS", "S", "M", "L",
		"1X", "2X", "3X", "Mix"};
	
	
	public static final String[] productListColumnNames = { "Party", "Item", "Style", "QTY on Hand", "QTY in Transit", "QTY in Production", "Update", "Detail"}; //, "Delete"};
	
	public static final String[] salesOrderColumnNames = { "Party", "Item", "Detail", "XS", "S",
		"M", "L", "1X", "2X", "3X", "Mix", "Total", "Unit Price",
		"Sub-Total", "","", "", "", "", "", "", "" };
	
	public static final String[] salesOrderListColumnNames = { "Order #", "Order Date", "Status", 
		"Customer", "Total", "Paid", "Balance", "Update", "Invoice", "Cancel"};
	
	public static final String[] salesOrderListColumnNamesCust = { "Order #", "Order Date", "Status", 
		 "Total", "Paid", "Balance"};
	
	public static final String[] paymentDetailsColumnNames = { "Payment Date", "Amount"};
	
	public static final String[] paymentDetailsColumnNamesCust = { "Payment Date", "Transaction", "Amount"};
	
	public static final String[] purchaseOrderColumnNamesCust = { "Party", "Item", "Total Quantity"};

	public static final String[] purchaseOrderColumnNamesCust1 = { "Party", "Item", "Total Quantity", "Details"};
	
	public static final String[] purchaseOrderListColumnNames = { "Lot #", "Order Date", "Arrival Date", "Shipping", "Total Quantity", "Update", "Cancel", ""};
	
	public static final String[] productInTransitColumnNames = {"Detail", "Lot #", "Arr. Date", "XS", "S", "M", "L",
		"1X", "2X", "3X", "Mix", "Total"};
	
	public static final String[] productInProductionColumnNames = {"Detail", "XS", "S", "M", "L",
		"1X", "2X", "3X", "Mix", "Total"};
	
	public static final String[] backUpOrderListColumnNames = { "Order #", "Order Date", "Party", "Shipping Date","Status", "Update", "Order Form", "Cancel"};
}
