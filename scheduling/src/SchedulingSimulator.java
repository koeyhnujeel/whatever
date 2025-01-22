import java.util.LinkedList;
import java.util.Queue;

public class SchedulingSimulator {
	public static void main(String[] args) {
		Scheduling scheduling = new Scheduling();
		scheduling.fifo(getProcesses());
		System.out.println();

		scheduling.roundRobin(getProcesses(), 1);
		System.out.println();

		scheduling.roundRobin(getProcesses(), 4);
		System.out.println();

		scheduling.nonPreemptiveSjf(getProcesses());
		System.out.println();

		scheduling.srtf(getProcesses());
		System.out.println();
	}

	private static Queue<Process> getProcesses() {
		Queue<Process> processQueue = new LinkedList<>();
		processQueue.offer(new Process('A', 0, 3));
		processQueue.offer(new Process('B', 2, 6));
		processQueue.offer(new Process('C', 4, 4));
		processQueue.offer(new Process('D', 6, 5));
		processQueue.offer(new Process('E', 8, 2));
		return processQueue;
	}
}
