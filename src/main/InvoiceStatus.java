package main;

public enum InvoiceStatus {
	UNINVOICED{
		@Override
		public String toString(){
			return "Uninvoiced";
		}
	}, INVOICED{
		@Override
		public String toString(){
			return "Invoiced";
		}
	}
}
