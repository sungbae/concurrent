import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

// Name: Sung Bae
// Course: Concurrent Programming (CSCI-4830)

class Bakery implements Lock {
	
	private Boolean[] flag;
	private Integer[] label;
	private int size;
	
	// Here is the Bakery constructor
	// We will input the number of threads which is n
	// Now we create the arrays with default values of false and 0
	// for flag and label respectively
	public Bakery(int n) {
		this.size = n;
		flag = new Boolean[n];
		label = new Integer[n];
		for (int i = 0; i < n; i++) {
			flag[i] = false;
			label[i] = 0;
		}
	}

	// This is just a helper function in order to find the max value in label
	private int getMaxLabel(Integer[] label) {
		// Set some number less than 0 as a temp for now
		int maxValue = -1;
		for (int entry : label) {
			// For each entry in the array look for a value that is greater than maxValue
			// then set that value as maxValue
			if (entry > maxValue) {
				maxValue = entry;
			}
		}
		return maxValue;
	}
	
	@Override
	public void lock() {
		// Get the thread ID but this will return as a String
		String threadId = (Thread.currentThread().getName()).substring(7);
		// Convert that string into an int
		int i = Integer.parseInt(threadId);
		// Since the lock has been called then that means the thread wants to access the critical section
		// set flag as true and find the max label and +1 in order to preserve first come first serve
		flag[i] = true;
		label[i] = (getMaxLabel(label) + 1);
		for (int k = 0; k < size; k++) {
			// We are comparing cases where flag[k] is true and k != i
			// Spin (wait) if label[k] < label[i] or
			// if they are equal and position k is lower than position (i.e. id) i
			// note: Atomic[int].get() is supported by the atomic class in order to retrieve the entry
            while ((k != i) && flag[k] && ((label[k] < label[i]) || ((label[k] == label[i]) && k < i))) {
            }
		}	
	}
	
	@Override
	public void unlock() {
		// Get the thread ID and convert that as int as the parameter for the flag array
		String threadId = (Thread.currentThread().getName()).substring(7);
		// Set that flag[threadId] as false
		flag[Integer.parseInt(threadId)] = false;
	}
	
	/**
	 * The methods below are needed in order to keep compatibility with the lock interface.
	 */

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}