package main;

public enum ContainerStatus {
	IN_TRANSIT{
		@Override
		public String toString(){
			return "In Transit";
		}
	}, ARRIVED{
		@Override
		public String toString(){
			return "Arrived";
		}
	}
}
