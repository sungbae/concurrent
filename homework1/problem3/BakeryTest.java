public class BakeryTest {
	private final int n;
	private static Bakery bakery;
	private Thread[] thread;
	public static int count = 0;
	
	public BakeryTest(int n) {
		this.n = n;
		thread = new Thread[n];
		bakery = new Bakery(n);
	}
	
	public void testParallel() throws Exception {
		for (int i = 0; i < n; i++) {
			thread[i] = new MyThread();
		}
		for (int i = 0; i < n; i++) {
			thread[i].start();
		}
		for (int i = 0; i < n; i++) {
			thread[i].join();
		}
	}
	
	public static void main(String[] args) {
		BakeryTest test = new BakeryTest(128);
		try {
			test.testParallel();
		} catch (Exception e) {
			System.out.println("Exception caught");
		}
	}
	
	class MyThread extends Thread {
		public void run() {
			bakery.lock();
			for (int i = 0; i < 1000; i++) {
				count++;
			}
			System.out.println(count);
			bakery.unlock();
		}
	}
}
