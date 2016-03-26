package main;

public enum PaymentStatus {
	UNPAID{
		@Override
		public String toString(){
			return "Unpaid";
		}
	}, PAID{
		@Override
		public String toString(){
			return "Paid";
		}
	}
}
