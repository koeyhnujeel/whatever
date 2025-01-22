import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Scheduling {

	public void fifo(Queue<Process> processQueue) {
		int startTime = 0;
		int endTime = 0;
		int totalTime = getTotalTime(processQueue);

		System.out.println("----------------------------[ FIFO ]-----------------------------");

		while (!processQueue.isEmpty()) {
			Process currentProcess = processQueue.poll();
			System.out.print(currentProcess.getName() + " ");

			endTime = startTime + currentProcess.getServiceTime();

			for (int i = 0; i < totalTime; i++) {
				if (i >= startTime && i < endTime) {
					System.out.print("⬛️ ");
					startTime++;
				} else {
					System.out.print("◻️ ");
				}
			}

			System.out.println();
		}
	}

	public void roundRobin(Queue<Process> processQueue, int tq) {
		int startTime = 0;
		int totalTime = getTotalTime(processQueue);
		int processSize = processQueue.size();
		int[][] check = new int[processSize][totalTime];

		Queue<Process> readyQueue = new LinkedList<>();
		readyQueue.offer(processQueue.poll());

		System.out.println("---------------------[ Round Robin - TQ " + tq + " ]----------------------");

		while (!readyQueue.isEmpty()) {
			Process currentProcess = readyQueue.poll();
			int idx = currentProcess.getName() - 65;

			for (int i = 0; i < tq; i++) {
				check[idx][startTime] = 1;
				currentProcess.minusServiceTime();
				startTime++;

				if (currentProcess.getServiceTime() == 0) {
					break;
				}
			}

			while (!processQueue.isEmpty()) {
				Process peeked = processQueue.peek();
				if (peeked.getArrivalTime() <= startTime) {
					readyQueue.offer(processQueue.poll());
				} else {
					break;
				}
			}

			if (currentProcess.getServiceTime() != 0) {
				readyQueue.offer(currentProcess);
			}
		}

		output(processSize, totalTime, check);
	}

	public void nonPreemptiveSjf(Queue<Process> processQueue) {
		int startTime = 0;
		int totalTime = getTotalTime(processQueue);
		int processSize = processQueue.size();
		int[][] check = new int[processSize][totalTime];

		PriorityQueue<Process> readyQueue = new PriorityQueue<>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				if (o1.getServiceTime() == o2.getServiceTime()) return o1.getArrivalTime() - o2.getArrivalTime();
				else return o1.getServiceTime() - o2.getServiceTime();
			}
		});

		readyQueue.offer(processQueue.poll());

		System.out.println("---------------------[ Non-preemptive SJF ]----------------------");

		while (!readyQueue.isEmpty()) {
			Process currentProcess = readyQueue.poll();
			int idx = currentProcess.getName() - 65;

			for (int i = 0; i < totalTime; i++) {
				check[idx][startTime] = 1;
				currentProcess.minusServiceTime();
				startTime++;

				if (currentProcess.getServiceTime() == 0) {
					break;
				}
			}

			while (!processQueue.isEmpty()) {
				Process peeked = processQueue.peek();
				if (peeked.getArrivalTime() <= startTime) {
					readyQueue.offer(processQueue.poll());
				} else {
					break;
				}
			}
		}

		output(processSize, totalTime, check);
	}

	public void srtf(Queue<Process> processQueue) {
		int startTime = 0;
		int totalTime = getTotalTime(processQueue);
		int processSize = processQueue.size();
		int[][] check = new int[processSize][totalTime];

		PriorityQueue<Process> readyQueue = new PriorityQueue<>(new Comparator<Process>() {
			@Override
			public int compare(Process o1, Process o2) {
				if (o1.getServiceTime() == o2.getServiceTime()) return o1.getArrivalTime() - o2.getArrivalTime();
				else return o1.getServiceTime() - o2.getServiceTime();
			}
		});

		readyQueue.offer(processQueue.poll());

		System.out.println("-----------------------[ preemptive SJF ]------------------------");

		while (!readyQueue.isEmpty()) {
			Process currentProcess = readyQueue.peek();
			int idx = currentProcess.getName() - 65;

			for (int i = 0; i < totalTime; i++) {
				check[idx][startTime] = 1;
				currentProcess.minusServiceTime();
				startTime++;

				if (!processQueue.isEmpty() && processQueue.peek().getArrivalTime() <= startTime) {
					Process nextProcess = processQueue.poll();
					readyQueue.offer(nextProcess);

					if (nextProcess.getServiceTime() < currentProcess.getServiceTime()) {
						break;
					}
				}

				if (currentProcess.getServiceTime() == 0) {
					readyQueue.poll();
					break;
				}
			}
		}

		output(processSize, totalTime, check);
	}

	public void mlfq(Queue<Process> processQueue, int queueCount, int tq) {
		int startTime = 0;
		int totalTime = getTotalTime(processQueue);
		int processSize = processQueue.size();
		int[][] check = new int[processSize][totalTime];
	}

	private int getTotalTime(Queue<Process> processQueue) {
		return processQueue.stream()
			.mapToInt(Process::getArrivalTime)
			.sum();
	}

	private void output(int processSize, int totalTime, int[][] check) {
		for (int i = 0; i < processSize; i++) {
			System.out.print((char)(i + 65) + " ");
			for (int j = 0; j < totalTime; j++) {
				if (check[i][j] == 1) System.out.print("⬛️ ");
				else System.out.print("◻️ ");
			}
			System.out.println();
		}
	}
}
