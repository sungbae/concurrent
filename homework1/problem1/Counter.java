// Name: Sung Bae
// Course: Concurrent Programming (CSCI-4830)

public class Counter implements Runnable {
	private static int count;
	private final static int THREADS = 128;
	static Thread[] t = new Thread[THREADS];

	public static int getCount() {
		return count;
	}

	public void run() {
		try{
			count++;
			System.out.println(count);
		} catch (Exception e) {}
	}

	public static void main(String[] args) {
		Runnable incrementCounter = new Counter();
		for (int i = 0; i < THREADS; i++) {
			System.out.println("Starting Thread-" + i);
			t[i] = new Thread(incrementCounter);
		}
		for (int i = 0; i < THREADS; i++) {
			t[i].start();
		}
		try {
			for (int i = 0; i < THREADS; i++) {
				t[i].join();
			}
		} catch (InterruptedException e) {}
		System.out.println("The final number for the count is: " + getCount());
	}
}
