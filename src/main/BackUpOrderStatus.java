package main;

public enum BackUpOrderStatus {
	NOT_SENT{
		@Override
		public String toString(){
			return "Not Sent"; 
		}
	}, SENT{
		@Override
		public String toString(){
			return "Sent"; 
		}
	}
}
