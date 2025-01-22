public class Process {
	private char name;
	private int arrivalTime;
	private int serviceTime;

	public Process(char name, int arrivalTime, int serviceTime) {
		this.name = name;
		this.arrivalTime = arrivalTime;
		this.serviceTime = serviceTime;
	}

	public char getName() {
		return name;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void minusServiceTime() {
		this.serviceTime--;
	}
}
