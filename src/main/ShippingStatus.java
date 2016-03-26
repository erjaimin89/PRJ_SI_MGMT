package main;

public enum ShippingStatus {
	NOT_SHIPPED{
		@Override
		public String toString(){
			return "Not Shipped";
		}
	}, SHIPPED{
		@Override
		public String toString(){
			return "Shipped";
		}
	}
}
