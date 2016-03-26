package main;

public enum OrderPayStatus {
	INVOICE{
		@Override
		public String toString(){
			return "Charged for order# ";
		}
	}, PAID{
		@Override
		public String toString(){
			return "Payment for order# ";
		}
	}, REFUND{
		@Override
		public String toString(){
			return "Refund for order# ";
		}
	}
}
