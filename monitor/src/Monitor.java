import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Monitor implements MonitorMBean {
	private final List<Thread> threads;
	private final AtomicInteger value;

	public Monitor(List<Thread> threads, AtomicInteger value) {
		this.threads = threads;
		this.value = value;
	}

	@Override
	public int getThreadCount() {
		return threads.size();
	}

	@Override
	public int getValue() {
		return value.get();
	}
}
