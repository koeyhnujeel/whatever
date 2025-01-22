import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import javax.management.MBeanServer;
import javax.management.ObjectName;

final class RandomApplication {
	private int threadSleepTimeLimit = 3 * 1000;
	private int threadCountLimit = 10;
	private int incrementValueLimit = 3;

	private final List<Thread> threads = new ArrayList<>();
	private final AtomicInteger value = new AtomicInteger(0);

	// getter, setter
	// ------------------------------------------------------------
	// ------------------------------------------------------------

	public int getIncrementValueLimit() {
		return incrementValueLimit;
	}

	public void setIncrementValueLimit(int incrementValueLimit) {
		this.incrementValueLimit = incrementValueLimit;
	}

	public int getThreadCountLimit() {
		return threadCountLimit;
	}

	public void setThreadCountLimit(int threadCountLimit) {
		this.threadCountLimit = threadCountLimit;
	}

	public int getThreadSleepTimeLimit() {
		return threadSleepTimeLimit;
	}

	public void setThreadSleepTimeLimit(int threadSleepTimeLimit) {
		this.threadSleepTimeLimit = threadSleepTimeLimit;
	}

	public int getThreadCount() {
		return threads.size();
	}

	public int getValue() {
		return value.get();
	}

	// ------------------------------------------------------------
	// ------------------------------------------------------------

	public void printResult() {
		printResult(1000);
	}

	public void printResult(int milliseconds) {
		while (true) {
			try {
				System.out.println("Thread count: " + getThreadCount());
				System.out.println("Value: " + getValue());
				System.out.println("==================================");
				Thread.sleep(milliseconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void changeVariable() {
		while (true) {
			Scanner scanner = new Scanner(System.in);
			int option = scanner.nextInt();

			switch (option) {
				case 1 -> setThreadSleepTimeLimit(scanner.nextInt());
				case 2 -> setThreadCountLimit(scanner.nextInt());
				case 3 -> setIncrementValueLimit(scanner.nextInt());
				default -> System.out.println("존재하지 않는 옵션입니다.");
			}
		}
	}

	public void start() {
		// 1초마다 현재 스레드 개수와 value 값을 출력
		new Thread(this::printResult).start();

		new Thread(this::changeVariable).start();

		// 1 ~ n개 사이의 스레드를 랜덤하게 생성하고, join을 통해 전체 종료를 대기 한다
		while(true) {
			IntStream.range(1, new Random(System.currentTimeMillis()).nextInt(threadCountLimit)).forEach(i -> {
				Thread thread = new Thread(() -> {
					try {
						// 0 ~ n초 사이의 랜덤한 시간만큼 스레드를 대기
						Thread.sleep(new Random(System.currentTimeMillis()).nextInt(threadSleepTimeLimit));
						// -3 ~ 3 사이의 랜덤 정수(증분)
						int ra = new Random(System.currentTimeMillis()).nextInt(incrementValueLimit * 2 + 1) - incrementValueLimit;

						value.updateAndGet(v -> v + ra);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
				thread.start();
				threads.add(thread);
			});

			threads.forEach(thread -> {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});

			threads.clear();
		}
	}

	public void setMBean() {
		try {
			MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
			Monitor monitor = new Monitor(threads, value);
			ObjectName objectName = new ObjectName("com/zunza/monitoring:type=Monitor");
			mBeanServer.registerMBean(monitor, objectName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

public class Main {
	public static void main(String[] args) {
		RandomApplication app = new RandomApplication();
		app.setMBean();
		app.start();
	}
}

